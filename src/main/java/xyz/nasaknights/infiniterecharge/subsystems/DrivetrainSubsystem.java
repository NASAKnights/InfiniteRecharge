package xyz.nasaknights.infiniterecharge.subsystems;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpiutil.math.MathUtil;
import xyz.nasaknights.infiniterecharge.RobotContainer;
import xyz.nasaknights.infiniterecharge.util.RobotMap;
import xyz.nasaknights.infiniterecharge.util.control.motors.Lazy_TalonFX;


public class DrivetrainSubsystem extends SubsystemBase
{
    //utilities for the drive methods
    public static final double kDefaultQuickStopAlpha = 0.1;
    public static final double kDefaultQuickStopThreshold = 0.2;
    private final TalonFXControlMode percentOutput = TalonFXControlMode.PercentOutput;
    public boolean rightSideInverted = false;
    private TalonFXConfiguration talonFXConfiguration = new TalonFXConfiguration()
    {{
        supplyCurrLimit = new SupplyCurrentLimitConfiguration()
        {{
            enable = true;
            currentLimit = 32;
            triggerThresholdCurrent = 32;
            triggerThresholdTime = 0;
        }};
        statorCurrLimit = new StatorCurrentLimitConfiguration()
        {{
            enable = true;
            currentLimit = 32;
            triggerThresholdCurrent = 32;
            triggerThresholdTime = 0;
        }};
        // default TalonFXConfiguration stuff
        //        motorCommutation = MotorCommutation.Trapezoidal;
        //        absoluteSensorRange = AbsoluteSensorRange.Unsigned_0_to_360;
        //        integratedSensorOffsetDegrees = 0;
        //        initializationStrategy = SensorInitializationStrategy.BootToZero;

    }};

    //private TalonFXConfiguration configuration;
    private Lazy_TalonFX leftMaster, leftFront, leftRear, rightMaster, rightFront, rightRear;
    private double deadband = 0.02;
    private double quickStopThreshold = kDefaultQuickStopThreshold;
    private double quickStopAlpha = kDefaultQuickStopAlpha;
    private double quickStopAccumulator;
    //turn PID control
    private PIDController turnController;
    private double pTurn = 1.0;
    private double iTurn = 0.0;
    private double dTurn = 0.0;


    public DrivetrainSubsystem()
    {
        initMotors();
        initPIDTurnController();
    }

    public PIDController getTurnController()
    {
        return turnController;
    }

    public boolean isAtAngle()
    {
        return getTurnController().atSetpoint();
    }

    private void initPIDTurnController()
    {
        turnController = new PIDController(pTurn, iTurn, dTurn);

        //turn controller settings
        turnController.setTolerance(0.5, 5);
        turnController.enableContinuousInput(-180, 180); //enable continuous input
        turnController.setIntegratorRange(-5, 5); //Integral proportion cannot add more than 5 or less than -5
    }

    public double[] getTurnPID()
    {
        double[] returnArray = {pTurn, iTurn, dTurn};
        return returnArray;
    }

    public void setTurnPID(double[] pidArray)
    {
        for (int index = 0; index > pidArray.length; index++)
        {
            if (index == 0)
            {
                pTurn = pidArray[index];
            } else if (index == 1)
            {
                iTurn = pidArray[index];
            } else if (index == 2)
            {
                dTurn = pidArray[index];
            }

            setTurnPID();
        }
    }

    public void setTurnPID()
    {
        turnController.setPID(pTurn, iTurn, dTurn);
    }

    public void drive(double throttle, double turn)
    {
        switch (RobotContainer.getProfile().getDriveType())
        {
            case ARCADE_DRIVE:
                arcadeDrive(throttle, turn);
                break;
            case CURVATURE_DRIVE:
                curvatureDrive(throttle, turn, true);
                break;
        }
    }

    private void arcadeDrive(double throttle, double turn)
    {
        throttle = MathUtil.clamp(throttle, -1, 1);
        throttle = applyDeadband(throttle, deadband);

        turn = MathUtil.clamp(turn, -1, 1);
        turn = applyDeadband(turn, deadband);

        double leftMotorOutput;
        double rightMotorOutput;

        double maxInput = Math.copySign(Math.max(Math.abs(throttle), Math.abs(turn)), throttle);

        if (throttle >= 0.0)
        {
            // First quadrant, else second quadrant
            if (turn >= 0.0)
            {
                leftMotorOutput = maxInput;
                rightMotorOutput = throttle - turn;
            } else
            {
                leftMotorOutput = throttle + turn;
                rightMotorOutput = maxInput;
            }
        } else
        {
            // Third quadrant, else fourth quadrant
            if (turn >= 0.0)
            {
                leftMotorOutput = throttle + turn;
                rightMotorOutput = maxInput;
            } else
            {
                leftMotorOutput = maxInput;
                rightMotorOutput = throttle - turn;
            }
        }

        setLeft(percentOutput, MathUtil.clamp(leftMotorOutput, -1.0, 1.0));
        setRight(percentOutput, MathUtil.clamp(rightMotorOutput, -1.0, 1.0) * -1);
    }

    private void curvatureDrive(double throttle, double turn, boolean quickTurn)
    {
        throttle = MathUtil.clamp(throttle, -1.0, 1.0);
        throttle = applyDeadband(throttle, deadband);

        turn = MathUtil.clamp(turn, -1.0, 1.0);
        turn = applyDeadband(turn, deadband);

        double angularPower;
        boolean overPower;

        if (quickTurn)
        {
            if (Math.abs(throttle) < quickStopThreshold)
            {
                quickStopAccumulator = (1 - quickStopAlpha) * quickStopAccumulator + quickStopAlpha * MathUtil.clamp(turn, -1.0, 1.0) * 2;
            }
            overPower = true;
            angularPower = turn;
        } else
        {
            overPower = false;
            angularPower = Math.abs(throttle) * turn - quickStopAccumulator;

            if (quickStopAccumulator > 1)
            {
                quickStopAccumulator -= 1;
            } else if (quickStopAccumulator < -1)
            {
                quickStopAccumulator += 1;
            } else
            {
                quickStopAccumulator = 0.0;
            }
        }

        double leftMotorOutput = throttle + angularPower;
        double rightMotorOutput = throttle - angularPower;

        // If rotation is overpowered, reduce both outputs to within acceptable range
        if (overPower)
        {
            if (leftMotorOutput > 1.0)
            {
                rightMotorOutput -= leftMotorOutput - 1.0;
                leftMotorOutput = 1.0;
            } else if (rightMotorOutput > 1.0)
            {
                leftMotorOutput -= rightMotorOutput - 1.0;
                rightMotorOutput = 1.0;
            } else if (leftMotorOutput < -1.0)
            {
                rightMotorOutput -= leftMotorOutput + 1.0;
                leftMotorOutput = -1.0;
            } else if (rightMotorOutput < -1.0)
            {
                leftMotorOutput -= rightMotorOutput + 1.0;
                rightMotorOutput = -1.0;
            }
        }

        // Normalize the wheel speeds
        double maxMagnitude = Math.max(Math.abs(leftMotorOutput), Math.abs(rightMotorOutput));
        if (maxMagnitude > 1.0)
        {
            leftMotorOutput /= maxMagnitude;
            rightMotorOutput /= maxMagnitude;
        }

        double rightSideInvertedMultiplier = rightSideInverted ? -1 : 1;

        setLeft(percentOutput, leftMotorOutput);
        setRight(percentOutput, rightMotorOutput * rightSideInvertedMultiplier);

    }

    public boolean driveToAngle(double angle)
    {
        arcadeDrive(0.0, turnController.calculate(RobotContainer.getIMU().getAngle(), angle));

        return turnController.atSetpoint();
    }

    private double applyDeadband(double value, double deadband)
    {
        if (Math.abs(value) > deadband)
        {
            if (value > 0.0)
            {
                //value is positive
                return (value - deadband) / (1.0 - deadband);
            } else
            {
                //if value is negative
                return (value + deadband) / (1.0 - deadband);
            }
        } else
        {
            return 0.0;
        }
    }

    private void initMotors()
    {

        leftMaster = new Lazy_TalonFX(RobotMap.LEFT_MASTER);
        leftFront = new Lazy_TalonFX(RobotMap.LEFT_FRONT);
        leftRear = new Lazy_TalonFX(RobotMap.LEFT_REAR);
        rightMaster = new Lazy_TalonFX(RobotMap.RIGHT_MASTER);
        rightFront = new Lazy_TalonFX(RobotMap.RIGHT_FRONT);
        rightRear = new Lazy_TalonFX(RobotMap.RIGHT_REAR);

        configureMotors();

    }

    private void configureMotors()
    {
        leftMaster.configFactoryDefault();
        leftFront.configFactoryDefault();
        leftRear.configFactoryDefault();
        rightMaster.configFactoryDefault();
        rightFront.configFactoryDefault();
        rightRear.configFactoryDefault();

        leftMaster.configAllSettings(talonFXConfiguration);
        leftFront.configAllSettings(talonFXConfiguration);
        leftRear.configAllSettings(talonFXConfiguration);
        rightMaster.configAllSettings(talonFXConfiguration);
        rightFront.configAllSettings(talonFXConfiguration);
        rightRear.configAllSettings(talonFXConfiguration);

        leftFront.setInverted(true);
        leftRear.setInverted(true);
        rightFront.setInverted(true);
        rightRear.setInverted(true);

        leftFront.follow(leftMaster);
        leftRear.follow(leftRear);
        rightFront.follow(rightMaster);
        rightRear.follow(rightMaster);
    }

    public void prepareClimbMotors()
    {
        StatorCurrentLimitConfiguration climbStator = new StatorCurrentLimitConfiguration()
        {{
            enable = true; //enables current limiting
            currentLimit = 8; // 20 percent power
            triggerThresholdCurrent = 8; //starts limit at 20 percent power
            triggerThresholdTime = 0; //starts limiting 0 seconds after threshold current is reached
        }};

        leftMaster.configStatorCurrentLimit(climbStator);
        leftFront.configStatorCurrentLimit(climbStator);
        leftRear.configStatorCurrentLimit(climbStator);
        rightMaster.configStatorCurrentLimit(climbStator);
        rightFront.configStatorCurrentLimit(climbStator);
        rightRear.configStatorCurrentLimit(climbStator);
    }

    public void setLeft(TalonFXControlMode controlMode, double output)
    {
        leftMaster.set(controlMode, output);
    }

    public void setRight(TalonFXControlMode controlMode, double output)
    {
        rightMaster.set(controlMode, output);
    }

    public void setQuickStopThreshold(double quickStopThreshold)
    {
        this.quickStopThreshold = quickStopThreshold;
    }

    @Override
    public void periodic()
    {

    }

    public void stop()
    {
        setLeft(percentOutput, 0);
        setRight(percentOutput, 0);
    }
}
