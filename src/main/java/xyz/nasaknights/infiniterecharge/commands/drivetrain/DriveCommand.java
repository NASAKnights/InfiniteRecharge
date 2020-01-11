package xyz.nasaknights.infiniterecharge.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import xyz.nasaknights.infiniterecharge.RobotContainer;
import xyz.nasaknights.infiniterecharge.util.controllers.PS4ControllerMappings;

public class DriveCommand extends CommandBase
{

    public DriveCommand()
    {
        addRequirements(RobotContainer.getDrivetrain());
        setName("Drive Command");
    }

    @Override
    public void initialize()
    {

    }

    @Override
    public void execute()
    {
        double throttle;
        double turn;
        switch (RobotContainer.getProfile().getControlType())
        {
            case kGTA:
                throttle = RobotContainer.getDriverRawAxis(PS4ControllerMappings.LEFT_TRIGGER.getID()) - RobotContainer.getDriverRawAxis(PS4ControllerMappings.RIGHT_TRIGGER.getID());
                turn = RobotContainer.getDriverRawAxis(PS4ControllerMappings.RIGHT_X_AXIS.getID());
            case kSticks:
                throttle = RobotContainer.getDriverRawAxis(PS4ControllerMappings.LEFT_Y_AXIS.getID());
                turn = RobotContainer.getDriverRawAxis(PS4ControllerMappings.RIGHT_X_AXIS.getID());
                break;
            default:
                throttle = 0;
                turn = 0;
        }
        RobotContainer.getDrivetrain().drive(throttle, turn);
    }

    @Override
    public void end(boolean interrupted)
    {
        RobotContainer.getDrivetrain().stop();
    }
}
