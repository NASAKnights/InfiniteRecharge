package xyz.nasaknights.infiniterecharge;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Joystick;
import xyz.nasaknights.infiniterecharge.subsystems.*;
import xyz.nasaknights.infiniterecharge.util.controllers.ControllerRegistry;
import xyz.nasaknights.infiniterecharge.util.controllers.DriverProfile;
import xyz.nasaknights.infiniterecharge.util.vision.VisionClient;
import xyz.nasaknights.infiniterecharge.util.vision.VisionClient.VisionClientInitializationException;

public class RobotContainer
{
    private static final Compressor compressor = new Compressor(Constants.PCM_ID);

    private static final DrivetrainSubsystem drivetrainSubsystem = new DrivetrainSubsystem();
    private static final IntakeSubsystem intakeSubsystem = new IntakeSubsystem();
    private static final ShooterSubsystem shooterSubsystem = new ShooterSubsystem();
    private static final QueuerSubsystem queuerSubsystem = new QueuerSubsystem();
    private static final ClimberSubsystem climberSubsystem = new ClimberSubsystem();

    private static DriverProfile profile = Constants.CURRENT_DRIVER_PROFILE;
    private static Joystick driver = new Joystick(Constants.DRIVER_ID);
    private static Joystick operator = new Joystick(Constants.OPERATOR_ID);
    private static AHRS navx = new AHRS(Constants.IMU_PORT);
    private static VisionClient visionClient;

    static
    {
        ControllerRegistry.setupDriverJoystick(Constants.DRIVER_ID, Constants.CURRENT_DRIVER_PROFILE);
        ControllerRegistry.setupOperatorJoystick(Constants.OPERATOR_ID, Constants.CURRENT_DRIVER_PROFILE);

        compressor.setClosedLoopControl(true);
        compressor.start();
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

    public static VisionClient getVisionClient()
    {
        if (visionClient == null)
        {
            try
            {
                visionClient = new VisionClient(Constants.VISION_CLIENT_IP_ADDRESS, Constants.VISION_CLIENT_PORT);
            } catch (VisionClientInitializationException e)
            {
                e.printStackTrace();
            }
        }
        return visionClient;
    }

    public static DrivetrainSubsystem getDrivetrain()
    {
        return drivetrainSubsystem;
    }

    public static IntakeSubsystem getIntake()
    {
        return intakeSubsystem;
    }

    public static ShooterSubsystem getShooterSubsystem()
    {
        return shooterSubsystem;
    }

    public static QueuerSubsystem getQueuerSubsystem()
    {
        return queuerSubsystem;
    }

    public static ClimberSubsystem getClimberSubsystem()
    {
        return climberSubsystem;
    }
}
