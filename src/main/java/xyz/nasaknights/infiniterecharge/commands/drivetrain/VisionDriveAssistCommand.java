package xyz.nasaknights.infiniterecharge.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import xyz.nasaknights.infiniterecharge.Robot;
import xyz.nasaknights.infiniterecharge.RobotContainer;

import static java.lang.Math.abs;

/**
 * This command will be used for controlling the drivetrain with the data from the Vision Client.
 *
 * @see xyz.nasaknights.infiniterecharge.subsystems.DrivetrainSubsystem
 */
public class VisionDriveAssistCommand extends CommandBase
{
    private static final double ANGLE_REQUIRED = 0.5;

    public VisionDriveAssistCommand()
    {
        addRequirements(RobotContainer.getDrivetrain());
    }

    @Override
    public void initialize()
    {
        System.out.println("Starting Vision Drive");
        RobotContainer.getVisionClient().setButtonPressed(true);
    }

    @Override
    public void execute()
    {
        System.out.println("Vision Turn: " + RobotContainer.getVisionClient().getTurn());
        RobotContainer.getDrivetrain().arcadeDrive(0, RobotContainer.getVisionClient().getTurn(), false);
    }

    @Override
    public boolean isFinished()
    {
        return abs(RobotContainer.getVisionClient().getAngle()) <= ANGLE_REQUIRED;
    }

    @Override
    public void end(boolean interrupted)
    {
        System.out.println("Ending Vision Drive");
        RobotContainer.getVisionClient().setButtonPressed(false);
        RobotContainer.getDrivetrain().getDefaultCommand().schedule();
    }
}
