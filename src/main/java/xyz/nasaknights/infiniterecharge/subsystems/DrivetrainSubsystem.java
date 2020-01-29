package xyz.nasaknights.infiniterecharge.subsystems;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import xyz.nasaknights.infiniterecharge.RobotContainer;
import xyz.nasaknights.infiniterecharge.Constants;
import xyz.nasaknights.infiniterecharge.commands.drivetrain.DriveCommand;
import xyz.nasaknights.infiniterecharge.util.control.motors.wpi.Lazy_WPI_TalonFX;


public class DrivetrainSubsystem extends SubsystemBase
{
    //utilities for the drive methods
    private final TalonFXControlMode percentOutput = TalonFXControlMode.PercentOutput;

    private static final DriveCommand driveCommand = new DriveCommand();

    //a configuration for the TalonFX motor controllers
    private TalonFXConfiguration talonFXConfiguration = new TalonFXConfiguration()
    {{
        supplyCurrLimit = new SupplyCurrentLimitConfiguration()
        {{
            enable = true; // enables current limiting
            currentLimit = 32; // limit to 32 amps
            triggerThresholdCurrent = 32; // when 32 amps of power are hit limit to 32 amps
            triggerThresholdTime = 0; // when 32 amps are hit limit to 32 amps after 0 seconds
        }};
        statorCurrLimit = new StatorCurrentLimitConfiguration()
        {{
            enable = true; // enables current limiting
            currentLimit = 32; // limit to 32 amps
            triggerThresholdCurrent = 32; // when 32 amps of power are hit limit to 32 amps
            triggerThresholdTime = 0; // when 32 amps are hit limit to 32 amps after 0 seconds
        }};
        // default TalonFXConfiguration stuff
        //        motorCommutation = MotorCommutation.Trapezoidal;
        //        absoluteSensorRange = AbsoluteSensorRange.Unsigned_0_to_360;
        //        integratedSensorOffsetDegrees = 0;
        //        initializationStrategy = SensorInitializationStrategy.BootToZero;

    }};

    private DifferentialDrive differential; // differential drive for ease of coding with West Coast drivetrains

    private Lazy_WPI_TalonFX leftMaster, // left master TalonFX
            leftFront, // left front TalonFX
            leftRear, // left rear TalonFX
            rightMaster, // right master TalonFX
            rightFront, // right front TalonFX
            rightRear; // right rear TalonFX

    //turn PID control
    private PIDController turnController;

    // should be tunable using the SmartDashboard application
    private double pTurn = 1.0; // p (proportional) variable
    private double iTurn = 0.0; // i (integral) variable
    private double dTurn = 0.0; // d (derivative) variable

    // TODO Finish and integrate distance PID and utils for it

    //    private static final int TICKS_PER_ROTATION = 2048;
    //    private static final double LOW_GEAR_RATIO = 0.0;
    //    private static final double HIGH_GEAR_RATIO = 0.0;
    //    private static final double WHEEL_CIRCUMFERENCE = 8 * Math.PI;
    //    private PIDController distanceController;
    //    private double pDistance = 1.0;
    //    private double iDistance = 0.0;
    //    private double dDistance = 0.0;

    private Solenoid driveGearShifter;
    private DoubleSolenoid powerTakeoffShifter;

    // TODO Verify these values
    private DoubleSolenoid.Value climbGear = DoubleSolenoid.Value.kForward;
    private DoubleSolenoid.Value driveGear = DoubleSolenoid.Value.kReverse;

    public DrivetrainSubsystem()
    {
        initMotors(); // set up motors
        initPneumatics(); // set up solenoids
        initPIDTurnController(); // set up turn PID
        setDefaultCommand(driveCommand); // will run this DriveCommand if no other commands are running that require the DrivetrainSubsystem
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

    public double getTurnP()
    {
        return turnController.getP();
    }

    public double getTurnI()
    {
        return turnController.getI();
    }

    public double getTurnD()
    {
        return turnController.getD();
    }

    public void setTurnP(double p)
    {
        if (pTurn != p)
        {
            turnController.setP(p);
        }
    }

    public void setTurnI(double i)
    {
        if (iTurn != i)
        {
            turnController.setI(i);
        }
    }

    public void setTurnD(double d)
    {
        if (dTurn != d)
        {
            turnController.setD(d);
        }
    }

    public void drive(double throttle, double turn)
    {
        switch (RobotContainer.getProfile().getDriveType())
        {
            case ARCADE_DRIVE:
                differential.arcadeDrive(throttle, turn);
                break;
            case CURVATURE_DRIVE:
                differential.curvatureDrive(throttle, turn, true);
                break;
        }
    }

    public void driveToAngle(double angle)
    {
        differential.arcadeDrive(0.0, turnController.calculate(RobotContainer.getIMU().getAngle(), angle));
    }

    private void initMotors()
    {

        leftMaster = new Lazy_WPI_TalonFX(Constants.LEFT_MASTER);
        leftFront = new Lazy_WPI_TalonFX(Constants.LEFT_FRONT);
        leftRear = new Lazy_WPI_TalonFX(Constants.LEFT_REAR);
        rightMaster = new Lazy_WPI_TalonFX(Constants.RIGHT_MASTER);
        rightFront = new Lazy_WPI_TalonFX(Constants.RIGHT_FRONT);
        rightRear = new Lazy_WPI_TalonFX(Constants.RIGHT_REAR);

        configureMotors();

        differential = new DifferentialDrive(leftMaster, rightMaster);
    }

    private void configureMotors()
    {
        //uses the global TalonFXConfiguration to configure the TalonFXs
        leftMaster.configAllSettings(talonFXConfiguration);
        leftFront.configAllSettings(talonFXConfiguration);
        leftRear.configAllSettings(talonFXConfiguration);
        rightMaster.configAllSettings(talonFXConfiguration);
        rightFront.configAllSettings(talonFXConfiguration);
        rightRear.configAllSettings(talonFXConfiguration);

        //forces the non-master talons to follow the master talons on their respective sides
        leftFront.follow(leftMaster);
        leftRear.follow(leftRear);
        rightFront.follow(rightMaster);
        rightRear.follow(rightMaster);
    }

    public void prepareClimbMotors()
    {
        // climb current limit configuration, so we don't break the robot as we climb
        StatorCurrentLimitConfiguration climbStator = new StatorCurrentLimitConfiguration()
        {{
            enable = true; //enables current limiting
            currentLimit = 8; // 20 percent power
            triggerThresholdCurrent = 8; //starts limit at 20 percent power
            triggerThresholdTime = 0; //starts limiting 0 seconds after threshold current is reached
        }};

        //uses the climb current configuration to configure the talons for the climb
        leftMaster.configStatorCurrentLimit(climbStator);
        leftFront.configStatorCurrentLimit(climbStator);
        leftRear.configStatorCurrentLimit(climbStator);
        rightMaster.configStatorCurrentLimit(climbStator);
        rightFront.configStatorCurrentLimit(climbStator);
        rightRear.configStatorCurrentLimit(climbStator);
    }

    private void initPneumatics()
    {
        driveGearShifter = new Solenoid(Constants.SINGLE_DRIVE_GEAR_CHANNEL);
        powerTakeoffShifter = new DoubleSolenoid(Constants.FORWARD_POWER_TAKEOFF_CHANNEL, Constants.REVERSE_POWER_TAKEOFF_CHANNEL);
    }

    public void setHighGear(boolean highGear)
    {
        driveGearShifter.set(highGear);
    }

    public void setClimbExtended(boolean extended)
    {
        powerTakeoffShifter.set(extended ? climbGear : driveGear);
    }

    public boolean isDriveAtHighGear()
    {
        return driveGearShifter.get();
    }

    public boolean isAtClimbGear()
    {
        return powerTakeoffShifter.get() == climbGear;
    }

    @Override
    public void periodic()
    {

    }
}
