package xyz.nasaknights.infiniterecharge.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpiutil.math.MathUtil;
import xyz.nasaknights.infiniterecharge.RobotContainer;
import xyz.nasaknights.infiniterecharge.util.RobotMap;
import xyz.nasaknights.infiniterecharge.util.control.motors.Lazy_TalonSRX;
import xyz.nasaknights.infiniterecharge.util.control.motors.Lazy_VictorSPX;

//import com.ctre.phoenix.motorcontrol.TalonFXControlMode;


public class DrivetrainSubsystem extends SubsystemBase
{
    //utilities for the drive methods
    public static final double kDefaultQuickStopThreshold = 0.2;
    public static final double kDefaultQuickStopAlpha = 0.1;
    private final ControlMode percentOutput = ControlMode.PercentOutput;
    public boolean rightSideInverted = false;
    private Lazy_TalonSRX leftMaster, rightMaster;
    private Lazy_VictorSPX leftFront, leftRear, rightFront, rightRear;
    //private TalonFXConfiguration configuration;
    /*private Lazy_TalonFX leftMaster,
            leftFront,
            leftRear,
            rightMaster,
            rightFront,
            rightRear;*/
    private double deadband = 0.02;
    private double quickStopThreshold = kDefaultQuickStopThreshold;
    private double quickStopAlpha = kDefaultQuickStopAlpha;
    private double quickStopAccumulator;

    //turn PID control
    private PIDController turnController;
    private double kP = 1.0;
    private double kI = 0.0;
    private double kD = 0.0;

    public DrivetrainSubsystem()
    {
        initMotors();
        initPIDTurnController();
    }

    public PIDController getTurnController()
    {
        return turnController;
    }

    private void initPIDTurnController()
    {
        turnController = new PIDController(kP, kI, kD);

        //turn controller settings
        turnController.setTolerance(0.5, 5);
        turnController.enableContinuousInput(-180, 180); //enable continuous input
        turnController.setIntegratorRange(-5, 5); //Integral proportion cannot add more than 5 or less than -5
    }

    public void drive(double throttle, double turn)
    {
        switch (RobotContainer.getProfile().getDriveType())
        {
            case kArcadeDrive:
                arcadeDrive(throttle, turn);
                break;
            case kCurvatureDrive:
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

        leftMaster = new Lazy_TalonSRX(RobotMap.LEFT_MASTER);
        leftFront = new Lazy_VictorSPX(RobotMap.LEFT_FRONT);
        leftRear = new Lazy_VictorSPX(RobotMap.LEFT_REAR);
        rightMaster = new Lazy_TalonSRX(RobotMap.RIGHT_MASTER);
        rightFront = new Lazy_VictorSPX(RobotMap.RIGHT_FRONT);
        rightRear = new Lazy_VictorSPX(RobotMap.RIGHT_REAR);

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

        leftFront.follow(leftMaster);
        leftRear.follow(leftRear);
        rightFront.follow(rightMaster);
        rightRear.follow(rightMaster);
    }

    public void setLeft(ControlMode controlMode, double output)
    {
        leftMaster.set(controlMode, output);
    }

    public void setRight(ControlMode controlMode, double output)
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
