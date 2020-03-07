package xyz.nasaknights.infiniterecharge.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import xyz.nasaknights.infiniterecharge.Constants;
import xyz.nasaknights.infiniterecharge.commands.drivetrain.DriveCommand;
import xyz.nasaknights.infiniterecharge.commands.drivetrain.DriveToAngleCommand;
import xyz.nasaknights.infiniterecharge.commands.drivetrain.VisionDriveAssistCommand;
import xyz.nasaknights.infiniterecharge.util.control.motors.wpi.Lazy_WPI_TalonFX;

/**
 * <p>The programmatic representation of the drivetrain, which consists of six Falcon 500 motors as the drive motors,
 * a single solenoid used switching between low and high gear for the motors, and a double solenoid for engaging
 * the Power Takeoff to use the drive motors for the climb.</p>
 * <p>The drivetrain can be controlled using the {@link DriveCommand} command, the {@link DriveToAngleCommand}
 * command, and the {@link VisionDriveAssistCommand} command.</p>
 * <p>The default command for the drivetrain subsystem is the {@link DriveCommand} command which will run
 * if no other commands that require the drivetrain subsystem. For more on command-based programming,
 * <a href = https://docs.wpilib.org/en/latest/docs/software/commandbased/index.html>click here</a> to see
 * the WPILib documentation for Command-Based Programming.</p>
 *
 * @see DriveCommand
 * @see DriveToAngleCommand
 * @see VisionDriveAssistCommand
 */
public class DrivetrainSubsystem extends SubsystemBase
{

    // TODO Verify these angle values, min is 0, max is 180
    private static final double LEFT_DRIVE_ANGLE = 180;
    private static final double LEFT_NEUTRAL_ANGLE = 160;
    private static final double RIGHT_DRIVE_ANGLE = 0;
    private static final double RIGHT_NEUTRAL_ANGLE = 20;

    private boolean isNeutral = false;

    private SpeedControllerGroup left, right; // Speed Controller groups that include all motors on left and right sides (init. in constructor)

    private DifferentialDrive drive; // standard library drive for West Coast drivetrains

    // declarations of the motor controllers
    private Lazy_WPI_TalonFX leftMaster, leftFront,
            leftRear,
            rightMaster,
            rightFront,
            rightRear;

    // servo declarations
    private Servo leftNeutralServo;
    private Servo rightNeutralServo;

    // solenoid delcarations
    private Solenoid driveGearShifter;
    private Solenoid powerTakeoffShifter;

    // TODO Verify these values
    private DoubleSolenoid.Value climbGear = DoubleSolenoid.Value.kForward;
    private DoubleSolenoid.Value driveGear = DoubleSolenoid.Value.kReverse;

    // some utilities for changing the max speeds of the drivetrain
    private double maxThrottle, maxTurn;

    // enumerator for keeping track of the default max speeds, defaulted to FULL_SPEED
    private DrivetrainSpeedState speedState = DrivetrainSpeedState.FULL_SPEED;

    /**
     * Constructor for the {@link DrivetrainSubsystem} class.
     */
    public DrivetrainSubsystem()
    {
        initMotors(); // set up motors
        initPneumatics(); // set up solenoids

        drive.setSafetyEnabled(false);

        //        leftNeutralServo = new Servo(Constants.LEFT_DRIVETRAIN_NEUTRAL_SERVO_PWM_ID);
        //        rightNeutralServo = new Servo(Constants.RIGHT_DRIVETRAIN_NEUTRAL_SERVO_PWM_ID);
        //        testServo = new Servo(2);
    }

    /**
     * Sets the max speeds of the drivetrain.
     *
     * @param maxThrottle Max forward and backwards speed
     * @param maxTurn     Max rotational speed
     */
    public void setMaxSpeeds(double maxThrottle, double maxTurn)
    {
        this.maxThrottle = maxThrottle;
        this.maxTurn = maxTurn;
    }

    /**
     * Toggles the max speeds of drivetrain motion between full and reduced speed (currently half).
     */
    public void toggleMaxSpeeds()
    {
        if (speedState == DrivetrainSpeedState.FULL_SPEED)
        {
            maxThrottle /= 2;
            maxTurn /= 2;
            speedState = DrivetrainSpeedState.HALF_SPEED;
        } else
        {
            maxThrottle *= 2;
            maxTurn *= 2;
            speedState = DrivetrainSpeedState.FULL_SPEED;
        }
    }

    /**
     * Makes a new instance the default drive command, {@link DriveCommand}.
     *
     * @return A {@link DriveCommand}
     */
    @Override
    public Command getDefaultCommand()
    {
        return new DriveCommand();
    }

    /**
     * Drives the robot in arcade drive.
     *
     * @param throttle      forwards and backwards speed
     * @param turn          rotational speed
     * @param squaredInputs if true will square the inputs from the source for more control at lower speeds
     */
    public void arcadeDrive(double throttle, double turn, boolean squaredInputs)
    {
        drive.arcadeDrive(throttle * maxThrottle, turn * maxTurn, squaredInputs);
    }

    public void curvatureDrive(double throttle, double turn, boolean isQuickTurn)
    {
        drive.curvatureDrive(throttle * maxThrottle, turn * maxTurn, isQuickTurn);
    }

    private void initMotors()
    {
        leftMaster = new Lazy_WPI_TalonFX(Constants.LEFT_MASTER);
        leftFront = new Lazy_WPI_TalonFX(Constants.LEFT_FRONT);
        leftRear = new Lazy_WPI_TalonFX(Constants.LEFT_REAR);
        rightMaster = new Lazy_WPI_TalonFX(Constants.RIGHT_MASTER);
        rightFront = new Lazy_WPI_TalonFX(Constants.RIGHT_FRONT);
        rightRear = new Lazy_WPI_TalonFX(Constants.RIGHT_REAR);

        left = new SpeedControllerGroup(leftMaster, leftFront, leftRear);
        right = new SpeedControllerGroup(rightMaster, rightFront, rightRear);

        leftMaster.configFactoryDefault();
        leftFront.configFactoryDefault();
        leftRear.configFactoryDefault();
        rightMaster.configFactoryDefault();
        rightFront.configFactoryDefault();
        rightRear.configFactoryDefault();

        leftMaster.setNeutralMode(NeutralMode.Coast);
        leftFront.setNeutralMode(NeutralMode.Coast);
        leftRear.setNeutralMode(NeutralMode.Coast);
        rightMaster.setNeutralMode(NeutralMode.Coast);
        rightFront.setNeutralMode(NeutralMode.Coast);
        rightRear.setNeutralMode(NeutralMode.Coast);

        leftMaster.setInverted(false);
        leftFront.setInverted(true);
        leftRear.setInverted(true);

        rightMaster.setInverted(false);
        rightFront.setInverted(true);
        rightRear.setInverted(true);

        drive = new DifferentialDrive(left, right);

        leftNeutralServo = new Servo(Constants.LEFT_DRIVETRAIN_NEUTRAL_SERVO_PWM_ID);
        rightNeutralServo = new Servo(Constants.RIGHT_DRIVETRAIN_NEUTRAL_SERVO_PWM_ID);
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
        driveGearShifter = new Solenoid(Constants.PCM_ID, Constants.DRIVETRAIN_GEAR_SHIFTER_CHANNEL);
        powerTakeoffShifter = new Solenoid(Constants.PCM_ID, Constants.DRIVETRAIN_POWER_TAKEOFF_CHANNEL); // TODO Enable once added
    }

    public void setHighGear(boolean highGear)
    {
        driveGearShifter.set(!highGear);
    }

    public void setPowerTakeoffExtended(boolean extended)
    {
        powerTakeoffShifter.set(extended);
    }

    public boolean isDriveInHighGear()
    {
        return driveGearShifter.get();
    }

    public boolean isInClimbGear()
    {
        return powerTakeoffShifter.get();
    }

    @Override
    public void periodic()
    {
    }

    /**
     * Defaults both the leftMaster and rightMaster encoder values to zero.
     *
     * @author Bradley Hooten (hello@bradleyh.me)
     */
    public void resetEncoders()
    {
        leftMaster.getSensorCollection().setIntegratedSensorPosition(0, 10);
        rightMaster.getSensorCollection().setIntegratedSensorPosition(0, 10);
    }

    public double getLeftEncoderPosition()
    {
        System.out.println("LEFT ENCODER: " + leftMaster.getSensorCollection().getIntegratedSensorPosition());
        return leftMaster.getSensorCollection().getIntegratedSensorPosition();
    }

    public double getRightEncoderPosition()
    {
        System.out.println("RIGHT ENCODER: " + rightMaster.getSensorCollection().getIntegratedSensorPosition());
        return rightMaster.getSensorCollection().getIntegratedSensorPosition();
    }

    /**
     * Sets the drivetrain motor percentages by side.
     *
     * @param left Target percentage output of the left side
     * @param right Target percentage output of the right side
     * @author Bradley Hooten (hello@bradleyh.me)
     */
    public void setMotorPercents(double left, double right)
    {
        System.out.println("LEFT: " + left + "; RIGHT: " + right);

//        if(left >= .4)
//        {
//            left = .4;
//        }
//        else if(left <= .4)
//        {
//            left = -.4;
//        }
//
//        if(right >= .4)
//        {
//            right = .4;
//        }
//        else if(right <= -.4)
//        {
//            right = -.4;
//        }

        leftMaster.set(ControlMode.PercentOutput, left);
        leftFront.set(ControlMode.PercentOutput, left);
        leftRear.set(ControlMode.PercentOutput, left);

        rightMaster.set(ControlMode.PercentOutput, right);
        rightFront.set(ControlMode.PercentOutput, right);
        rightRear.set(ControlMode.PercentOutput, right);
    }

    /**
     * Clears any faults on the drivetrain motors.
     *
     * @author Bradley Hooten (hello@bradleyh.me)
     */
    public void clearFaults()
    {
        leftMaster.clearStickyFaults();
        leftFront.clearStickyFaults();
        leftRear.clearStickyFaults();

        rightMaster.clearStickyFaults();
        rightFront.clearStickyFaults();
        rightRear.clearStickyFaults();
    }

    /**
     * Stops all of the motors on the drivetrain.
     *
     * @author Bradley Hooten (hello@bradleyh.me)
     */
    public void stopAllMotors()
    {
        leftMaster.set(ControlMode.PercentOutput, 0);
        leftFront.set(ControlMode.PercentOutput, 0);
        leftRear.set(ControlMode.PercentOutput, 0);

        rightMaster.set(ControlMode.PercentOutput, 0);
        rightFront.set(ControlMode.PercentOutput, 0);
        rightRear.set(ControlMode.PercentOutput, 0);
    }

    /**
     * This method toggles the neutrality of the drivetrain. This should be used to shift control to the climber for
     * the endgame period.
     *
     * @author Bradley Hooten (hello@bradleyh.me)
     */
    public void setDrivetrainNeutral(boolean isNeutral)
    {
        this.isNeutral = isNeutral;
    }

    public boolean isDriveNeutral()
    {
        return isNeutral;
    }

    public enum DrivetrainSpeedState
    {
        FULL_SPEED, HALF_SPEED
    }

    public void runPeriodicServoTask()
    {
        leftNeutralServo.setAngle(isNeutral ? 170 : 150);
        rightNeutralServo.setAngle(isNeutral ? 35 : 55);
    }

    public void disableMotors()
    {
        leftMaster.disable();
        leftFront.disable();
        leftRear.disable();
        rightMaster.disable();
        rightFront.disable();
        rightRear.disable();
    }
}
