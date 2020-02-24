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
        RobotContainer.getQueuerSubsystem().setQueuerFeederPower(.75);
        RobotContainer.getShooterSubsystem().set(0.8);
    }

    @Override
    public void end(boolean interrupted)
    {
        RobotContainer.getQueuerSubsystem().setQueuerFeederPower(.1);
        RobotContainer.getShooterSubsystem().set(0);
    }

    @Override
    public boolean isFinished()
    {
        return false;
    }
}
