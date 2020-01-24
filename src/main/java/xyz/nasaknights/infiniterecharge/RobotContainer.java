package xyz.nasaknights.infiniterecharge;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Joystick;
import xyz.nasaknights.infiniterecharge.subsystems.DrivetrainSubsystem;
import xyz.nasaknights.infiniterecharge.util.controllers.DriverProfile;

public class RobotContainer
{
    private static final DrivetrainSubsystem drivetrainSubsystem = new DrivetrainSubsystem();

    private static DriverProfile profile;

    private static Joystick driver = new Joystick(Constants.DRIVER_ID);
    private static Joystick operator = new Joystick(Constants.OPERATOR_ID);

    private static final Compressor compressor = new Compressor(Constants.PCM_ID);

    public RobotContainer()
    {
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

    public static DrivetrainSubsystem getDrivetrain()
    {
        return drivetrainSubsystem;
    }

    private void configureButtonBindings()
    {

    }

//    public Command getAutonomousCommand()
//    {
//        // TODO Work on autonomous?
//        return autonomousCommand;
//    }
}
