package xyz.nasaknights.infiniterecharge.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import xyz.nasaknights.infiniterecharge.Robot;
import xyz.nasaknights.infiniterecharge.RobotContainer;
import xyz.nasaknights.infiniterecharge.util.controllers.DriverProfile;

public class VisionDriveAssistCommand extends CommandBase
{

    DriverProfile realDriver;

    public VisionDriveAssistCommand()
    {
        // If any subsystems are needed, you will need to pass them into the requires() method
    }

    @Override
    public void initialize()
    {
        realDriver = RobotContainer.getProfile();
        RobotContainer.setProfile(DriverProfile.AUTONOMOUS);
    }

    @Override
    public void execute()
    {
    }

    @Override
    public boolean isFinished()
    {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return RobotContainer.getDrivetrain().isAtAngle();
    }

    @Override
    public void end(boolean interrupted)
    {
        RobotContainer.setProfile(realDriver);
        Robot.getDriveCommand().schedule();
    }
}
