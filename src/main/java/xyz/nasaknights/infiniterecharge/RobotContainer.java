package xyz.nasaknights.infiniterecharge;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import xyz.nasaknights.infiniterecharge.commands.drivetrain.DriveToAngleCommand;
import xyz.nasaknights.infiniterecharge.subsystems.DrivetrainSubsystem;
import xyz.nasaknights.infiniterecharge.util.RobotMap;
import xyz.nasaknights.infiniterecharge.util.controllers.PS4ControllerMappings;
import xyz.nasaknights.infiniterecharge.util.controllers.DriverProfile;

public class RobotContainer
{

    private static final DrivetrainSubsystem drivetrainSubsystem = new DrivetrainSubsystem();

    private static DriverProfile profile;
    private static Joystick driver = new Joystick(Constants.DRIVER_ID);
    private static Joystick operator = new Joystick(Constants.OPERATOR_ID);
    private static AHRS navx;

    public RobotContainer()
    {

        // Configure the button bindings
        configureButtonBindings();

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
        navx = new AHRS(RobotMap.IMU_PORT);
    }

    public static double getAutonomousThrottleSpeed()
    {
        return 0.0;
    }

    public static double getAutonomousTurnSpeed()
    {
        return 0.0;
    }

    private void configureButtonBindings()
    {
        new JoystickButton(driver, PS4ControllerMappings.RIGHT_JOYSTICK.getID()).whenPressed(new DriveToAngleCommand(0.0));
    }

    //    public Command getAutonomousCommand()
    //    {
    //        // TODO Work on autonomous?
    //        return autonomousCommand;
    //    }
}
