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
        RobotContainer.getQueuerSubsystem().setQueuerIntakePower(1);
        RobotContainer.getShooterSubsystem().set(-0.9);
    }

    @Override
    public void end(boolean interrupted)
    {
        System.out.println("Ending shooter");
        RobotContainer.getQueuerSubsystem().setQueuerIntakePower(0);
        RobotContainer.getShooterSubsystem().set(0);
    }

    @Override
    public void cancel()
    {
        RobotContainer.getShooterSubsystem().set(0);
    }

    @Override
    public boolean isFinished()
    {
        return false;
    }
}
