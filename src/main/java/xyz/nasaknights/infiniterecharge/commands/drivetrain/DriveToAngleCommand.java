package xyz.nasaknights.infiniterecharge.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import xyz.nasaknights.infiniterecharge.RobotContainer;

/**
 * <p>A drive method that enables the drivetrain to turn to a certain angle using the PID control algorithm and
 * the output from the NavX IMU sensor.</p>
 * <p> See WPILib's
 * <a href = http://docs.wpilib.org/en/latest/docs/software/advanced-control/introduction/introduction-to-pid.html>Introduction to PID</a>,
 * <a href = http://docs.wpilib.org/en/latest/docs/software/advanced-control/introduction/tuning-pid-controller.html>Tuning a PID Controller</a>, and
 * <a href = http://docs.wpilib.org/en/latest/docs/software/advanced-control/controllers/pidcontroller.html>PID Control in WPILib</a>
 * for more information on PID control.</p>
 *
 * @see xyz.nasaknights.infiniterecharge.subsystems.DrivetrainSubsystem
 * @see com.kauailabs.navx.frc.AHRS
 * @see edu.wpi.first.wpilibj.controller.PIDController
 */
public class DriveToAngleCommand extends CommandBase
{

    private final int COUNT_NEEDED = 5; //count must be this or greater to actually be at the right angle
    private double angle;
    private int count = 0; //count for if the bot is at the setpoint

    /**
     * @deprecated This constructor has been deprecated due to the VisionClient being not implemented at the time.
     * It will be un-deprecated when the Vision Client is added.
     */
    @Deprecated
    public DriveToAngleCommand()
    {
        // TODO link angle with Vision Client
        this.angle = 0.0;
        addRequirements(RobotContainer.getDrivetrain());
    }

    public DriveToAngleCommand(double angle)
    {
        this.angle = angle;
    }

    @Override
    public void initialize()
    {
        //sensor and PID reset
        RobotContainer.getIMU().reset();
        RobotContainer.getDrivetrain().getTurnController().reset();
    }

    @Override
    public void execute()
    {
        RobotContainer.getDrivetrain().driveToAngle(angle);

        if (RobotContainer.getDrivetrain().getTurnController().atSetpoint())
        {
            count++;
        } else
        {
            count = 0;
        }
    }

    @Override
    public boolean isFinished()
    {
        return (count >= COUNT_NEEDED) && RobotContainer.getDrivetrain().getTurnController().atSetpoint();
    }

    @Override
    public void end(boolean interrupted)
    {
        System.out.println("PID Turn Command Ended");
    }

}
