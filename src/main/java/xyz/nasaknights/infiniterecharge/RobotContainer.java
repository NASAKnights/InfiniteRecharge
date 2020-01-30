package xyz.nasaknights.infiniterecharge;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import xyz.nasaknights.infiniterecharge.commands.drivetrain.DriveToAngleCommand;
import xyz.nasaknights.infiniterecharge.commands.shooter.ShootCommand;
import xyz.nasaknights.infiniterecharge.subsystems.DrivetrainSubsystem;
import xyz.nasaknights.infiniterecharge.subsystems.ShooterSubsystem;
import xyz.nasaknights.infiniterecharge.util.controllers.ControllerRegistry;
import xyz.nasaknights.infiniterecharge.util.controllers.DriverProfile;
import xyz.nasaknights.infiniterecharge.util.controllers.PS4ControllerMappings;

public class RobotContainer
{
    private static final Compressor compressor = new Compressor(Constants.PCM_ID);

    private static final DrivetrainSubsystem drivetrainSubsystem = new DrivetrainSubsystem();
    private static final ShooterSubsystem shooterSubsystem = new ShooterSubsystem();

    private static DriverProfile profile;
    private static Joystick driver = new Joystick(Constants.DRIVER_ID);
    private static Joystick operator = new Joystick(Constants.OPERATOR_ID);
    private static AHRS navx;

    public RobotContainer()
    {
        ControllerRegistry.setupDriverJoystick(Constants.DRIVER_ID, Constants.CURRENT_DRIVER_PROFILE);

        configureButtonBindings();

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

    private void configureButtonBindings()
    {
        new JoystickButton(driver, PS4ControllerMappings.RIGHT_JOYSTICK.getID()).whenPressed(new DriveToAngleCommand(0.0));

        new JoystickButton(operator, PS4ControllerMappings.X.getID()).whileHeld(new ShootCommand());
    }

    //    public Command getAutonomousCommand()
    //    {
    //        // TODO Work on autonomous?
    //        return autonomousCommand;
    //    }
}
