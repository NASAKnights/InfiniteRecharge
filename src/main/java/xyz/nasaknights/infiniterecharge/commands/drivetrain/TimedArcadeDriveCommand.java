package xyz.nasaknights.infiniterecharge.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import xyz.nasaknights.infiniterecharge.RobotContainer;

public class TimedArcadeDriveCommand extends CommandBase
{
    private long time;
    private double throttle;

    public TimedArcadeDriveCommand(long time, double throttle)
    {
        this.time = time;
        this.throttle = throttle;
    }

    @Override
    public void initialize()
    {
        time += System.currentTimeMillis();
    }

    @Override
    public void execute()
    {
        RobotContainer.getDrivetrain().arcadeDrive(this.throttle, 0, false);
    }

    @Override
    public void end(boolean interrupted)
    {
        RobotContainer.getDrivetrain().stopAllMotors();
    }

    @Override
    public boolean isFinished()
    {
        return System.currentTimeMillis() >= time;
    }
}
