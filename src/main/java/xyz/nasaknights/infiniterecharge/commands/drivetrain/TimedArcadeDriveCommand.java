package xyz.nasaknights.infiniterecharge.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import xyz.nasaknights.infiniterecharge.RobotContainer;

public class TimedArcadeDriveCommand extends CommandBase
{
    private long time;
    private long driveTimeLength;
    private double throttle;

    public TimedArcadeDriveCommand(long time, double throttle)
    {
        this.driveTimeLength = time;
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
        System.out.println("Stopping timed arcade");
        RobotContainer.getDrivetrain().arcadeDrive(0, 0, false);
        RobotContainer.getDrivetrain().stopAllMotors();
    }

    @Override
    public boolean isFinished()
    {
        return System.currentTimeMillis() >= time;
    }

    public long getLengthInMillis()
    {
        return driveTimeLength;
    }
}