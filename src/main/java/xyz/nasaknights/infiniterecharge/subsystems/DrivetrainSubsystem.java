package xyz.nasaknights.infiniterecharge.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import xyz.nasaknights.infiniterecharge.Constants;
import xyz.nasaknights.infiniterecharge.commands.drivetrain.DriveCommand;
import xyz.nasaknights.infiniterecharge.util.control.motors.wpi.Lazy_WPI_TalonFX;

public class DrivetrainSubsystem extends SubsystemBase
{
    private DifferentialDrive drive;
    private SpeedControllerGroup left, right;

    private Lazy_WPI_TalonFX leftMaster,
            leftFront,
            leftRear,
            rightMaster,
            rightFront,
            rightRear;

    public DrivetrainSubsystem()
    {
        initMotors();
    }

    @Override
    public Command getDefaultCommand()
    {
        return new DriveCommand();
    }

    public void arcadeDrive(double throttle, double turn, boolean squaredInputs)
    {
        drive.arcadeDrive(throttle, turn, squaredInputs);
    }

    public void curvatureDrive(double throttle, double turn, boolean isQuickTurn)
    {
        drive.curvatureDrive(throttle, turn, isQuickTurn);
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

        left = new SpeedControllerGroup(leftMaster, leftFront, leftRear);
        right = new SpeedControllerGroup(rightMaster, rightFront, rightRear);

        drive = new DifferentialDrive(left, right);
    }

    private void configureMotors()
    {
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
    }

    @Override
    public void periodic()
    {

    }

    public void stop()
    {
        left.stopMotor();
        right.stopMotor();
    }
}
