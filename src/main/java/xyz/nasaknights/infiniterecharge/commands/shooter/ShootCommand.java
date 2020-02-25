package xyz.nasaknights.infiniterecharge.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import xyz.nasaknights.infiniterecharge.RobotContainer;
import xyz.nasaknights.infiniterecharge.util.controllers.ControllerRegistry;

public class ShootCommand extends CommandBase
{
    public ShootCommand()
    {
        addRequirements(RobotContainer.getShooterSubsystem());
    }

    @Override
    public void initialize()
    {
        RobotContainer.getShooterSubsystem().setHoodExtended(true);
    }

    @Override
    public void execute()
    {
        RobotContainer.getQueuerSubsystem().setQueuerIntakePower(1);
        RobotContainer.getShooterSubsystem().set(1);

//        if(RobotContainer.getShooterSubsystem())
//        {
//            RobotContainer.getQueuerSubsystem().setQueuerIntakePower(1);
//        }
    }

    @Override
    public void end(boolean interrupted)
    {
        RobotContainer.getQueuerSubsystem().setQueuerIntakePower(0);
        RobotContainer.getShooterSubsystem().set(0);
        RobotContainer.getShooterSubsystem().setHoodExtended(false);
    }

    @Override
    public void cancel()
    {
        RobotContainer.getShooterSubsystem().set(0);
    }

    @Override
    public boolean isFinished()
    {
        return !ControllerRegistry.isShooterButtonHeld();
    }
}
