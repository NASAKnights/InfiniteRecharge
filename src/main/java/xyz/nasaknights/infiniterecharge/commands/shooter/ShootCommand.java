package xyz.nasaknights.infiniterecharge.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import xyz.nasaknights.infiniterecharge.RobotContainer;

public class ShootCommand extends CommandBase
{
    public ShootCommand()
    {
        addRequirements(RobotContainer.getShooterSubsystem());
    }

    @Override
    public void execute()
    {
        RobotContainer.getShooterSubsystem().set(0.50);
    }

    @Override
    public void end(boolean interrupted)
    {
        RobotContainer.getShooterSubsystem().set(0);
    }

    @Override
    public boolean isFinished()
    {
        return false;
    }
}
