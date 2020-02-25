package xyz.nasaknights.infiniterecharge;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Joystick;
import xyz.nasaknights.infiniterecharge.subsystems.DrivetrainSubsystem;
import xyz.nasaknights.infiniterecharge.subsystems.IntakeSubsystem;
import xyz.nasaknights.infiniterecharge.subsystems.QueuerSubsystem;
import xyz.nasaknights.infiniterecharge.subsystems.ShooterSubsystem;
import xyz.nasaknights.infiniterecharge.util.controllers.ControllerRegistry;
import xyz.nasaknights.infiniterecharge.util.controllers.DriverProfile;

public class RobotContainer
{
    private static final Compressor compressor = new Compressor(Constants.PCM_ID);

    private static final DrivetrainSubsystem drivetrainSubsystem = new DrivetrainSubsystem();
    private static final IntakeSubsystem intakeSubsystem = new IntakeSubsystem();
    private static final ShooterSubsystem shooterSubsystem = new ShooterSubsystem();
    private static final QueuerSubsystem queuerSubsystem = new QueuerSubsystem();

    private static DriverProfile profile;
    private static Joystick driver = new Joystick(Constants.DRIVER_ID);
    private static Joystick operator = new Joystick(Constants.OPERATOR_ID);
    private static AHRS navx = new AHRS(Constants.IMU_PORT);

    public RobotContainer()
    {
        ControllerRegistry.setupDriverJoystick(Constants.DRIVER_ID, Constants.CURRENT_DRIVER_PROFILE);
        ControllerRegistry.setupOperatorJoystick(Constants.OPERATOR_ID, Constants.CURRENT_DRIVER_PROFILE);

        compressor.setClosedLoopControl(true);
        compressor.start();
    }

    public static Joystick getDriver()
    {
        return driver;
    }

    public static double getDriverRawAxis(int axis)
    {
        return getDriver().getRawAxis(axis);
    }

    public static Joystick getOperator()
    {
        return operator;
    }

    public static double getOperatorRawAxis(int axis)
    {
        return getOperator().getRawAxis(axis);
    }

    public static DriverProfile getProfile()
    {
        return profile;
    }

    public static void setProfile(DriverProfile profile)
    {
        RobotContainer.profile = profile;
    }

    public static AHRS getIMU()
    {
        return navx;
    }

    public static DrivetrainSubsystem getDrivetrain()
    {
        return drivetrainSubsystem;
    }

    public static IntakeSubsystem getIntake()
    {
        return intakeSubsystem;
    }
  
    public static void initIMU()
    {
        navx = new AHRS(Constants.IMU_PORT);
    }

    public static double getAutonomousThrottleSpeed()
    {
        return 0.0;
    }

    public static double getAutonomousTurnSpeed()
    {
        return 0.0;
    }

    public static ShooterSubsystem getShooterSubsystem()
    {
        return shooterSubsystem;
    }

    public static QueuerSubsystem getQueuerSubsystem()
    {
        return queuerSubsystem;
    }

    //    public Command getAutonomousCommand()
    //    {
    //        // TODO Work on autonomous?
    //        return autonomousCommand;
    //    }
}
