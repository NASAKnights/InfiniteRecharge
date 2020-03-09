package xyz.nasaknights.infiniterecharge.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import xyz.nasaknights.infiniterecharge.RobotContainer;

import java.util.concurrent.TimeUnit;

public class ShootCommand extends CommandBase
{
    private boolean far;
    private long totalTime;
//    private boolean hasReachedTarget = false;

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
//        RobotContainer.getShooterSubsystem().setTargetShooterRPM(far ? 16000 : 14000);
        RobotContainer.getShooterSubsystem().set(far ? .87 : .77);
//        if (RobotContainer.getShooterSubsystem().isShooterUpToSpeed() && !hasReachedTarget)
//        {
//            totalTime = System.currentTimeMillis() - totalTime;
//            hasReachedTarget = true;
//            System.out.println("Reached target in " + totalTime + " ms");
//        }

        // Temporary fix due to disablement of right shooter motor due to mechanical malfunction
        if (System.currentTimeMillis() >= totalTime + TimeUnit.SECONDS.toMillis(1))
        {
            RobotContainer.getQueuerSubsystem().setQueuerIntakePower(RobotContainer.getShooterSubsystem().isShooterUpToSpeed() ? .7 : 0);
        }

        RobotContainer.getQueuerSubsystem().setBeltPower(RobotContainer.getShooterSubsystem().isShooterUpToSpeed() ? .75 : 0);
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
