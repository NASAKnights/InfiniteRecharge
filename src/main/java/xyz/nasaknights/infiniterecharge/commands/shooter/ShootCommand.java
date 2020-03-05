package xyz.nasaknights.infiniterecharge.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import xyz.nasaknights.infiniterecharge.RobotContainer;

public class ShootCommand extends CommandBase
{
    private boolean far;
    private long totalTime;
    private boolean hasReachedTarget = false;

    public ShootCommand(boolean far)
    {
        this.far = far;
        addRequirements(RobotContainer.getShooterSubsystem());
    }

    @Override
    public void initialize()
    {
//        RobotContainer.getShooterSubsystem().setHoodExtended(false);
        totalTime = System.currentTimeMillis();

        if (far)
        {
            RobotContainer.getShooterSubsystem().setHoodExtended(true);
        }
    }

    @Override
    public void execute()
    {
        RobotContainer.getShooterSubsystem().setTargetShooterRPM(16000);

        if (RobotContainer.getShooterSubsystem().isShooterUpToSpeed() && !hasReachedTarget)
        {
            totalTime = System.currentTimeMillis() - totalTime;
            hasReachedTarget = true;
            System.out.println("Reached target in " + totalTime + " ms");
        }

        RobotContainer.getQueuerSubsystem().setQueuerIntakePower(RobotContainer.getShooterSubsystem().isShooterUpToSpeed() ? 1 : 0);
        RobotContainer.getQueuerSubsystem().setBeltPower(RobotContainer.getShooterSubsystem().isShooterUpToSpeed() ? 1 : 0);
    }

    @Override
    public void end(boolean interrupted)
    {
        RobotContainer.getQueuerSubsystem().setQueuerIntakePower(0);
        RobotContainer.getShooterSubsystem().set(0);
        RobotContainer.getShooterSubsystem().setHoodExtended(false);
    }

    @Override
    public boolean isFinished()
    {
        return false; //return !ControllerRegistry.isShooterButtonHeld();
    }
}
