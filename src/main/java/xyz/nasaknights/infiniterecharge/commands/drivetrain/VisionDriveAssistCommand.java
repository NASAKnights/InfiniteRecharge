package xyz.nasaknights.infiniterecharge.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * This command will be used for controlling the drivetrain with the data from the Vision Client.
 *
 * @see xyz.nasaknights.infiniterecharge.subsystems.DrivetrainSubsystem
 */
public class VisionDriveAssistCommand extends CommandBase
{
    public VisionDriveAssistCommand()
    {
        addRequirements(RobotContainer.getDrivetrain());
    }

    @Override
    public void initialize()
    {
    }

    @Override
    public void execute()
    {
    }

    @Override
    public boolean isFinished()
    {
        return false;
    }

    @Override
    public void end(boolean interrupted)
    {
    }
}
