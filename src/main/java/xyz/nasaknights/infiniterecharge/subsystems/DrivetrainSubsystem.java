package xyz.nasaknights.infiniterecharge.subsystems;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import xyz.nasaknights.infiniterecharge.RobotContainer;
import xyz.nasaknights.infiniterecharge.util.RobotMap;
import xyz.nasaknights.infiniterecharge.util.control.motors.Lazy_WPI_TalonFX;

public class DrivetrainSubsystem extends SubsystemBase {

    private DifferentialDrive drive;
    private SpeedControllerGroup left, right;

    private Lazy_WPI_TalonFX leftMaster,
                         leftFront,
                         leftRear,
                         rightMaster,
                         rightFront,
                         rightRear;

    public DrivetrainSubsystem() {
        initMotors();
    }

    public void drive(double throttle, double turn) {
        switch (RobotContainer.getProfile().getDriveType()) {
            case kArcadeDrive:
                arcadeDrive(throttle, turn);
                break;
            case kCurvatureDrive:
                curvatureDrive(throttle, turn, true);
                break;
        }
    }

    private void arcadeDrive(double throttle, double turn) {
        drive.arcadeDrive(throttle, turn);
    }

    private void curvatureDrive(double throttle, double turn, boolean isQuickTurn) {
        drive.curvatureDrive(throttle, turn, isQuickTurn);
    }

    private void initMotors() {

        leftMaster = new Lazy_WPI_TalonFX(RobotMap.LEFT_MASTER);
        leftFront = new Lazy_WPI_TalonFX(RobotMap.LEFT_FRONT);
        leftRear = new Lazy_WPI_TalonFX(RobotMap.LEFT_REAR);
        rightMaster = new Lazy_WPI_TalonFX(RobotMap.RIGHT_MASTER);
        rightFront = new Lazy_WPI_TalonFX(RobotMap.RIGHT_FRONT);
        rightRear = new Lazy_WPI_TalonFX(RobotMap.RIGHT_REAR);

        configureMotors();

        left = new SpeedControllerGroup(leftMaster, leftFront, leftRear);
        right = new SpeedControllerGroup(rightMaster, rightFront, rightRear);

        drive = new DifferentialDrive(left, right);

    }

    private void configureMotors() {

        leftMaster.configFactoryDefault();
        leftFront.configFactoryDefault();
        leftRear.configFactoryDefault();
        rightMaster.configFactoryDefault();
        rightFront.configFactoryDefault();
        rightRear.configFactoryDefault();

    }

    @Override
    public void periodic() {

    }
}
