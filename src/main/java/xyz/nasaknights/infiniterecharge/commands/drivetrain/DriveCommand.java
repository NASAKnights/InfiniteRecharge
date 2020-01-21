package xyz.nasaknights.infiniterecharge.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import xyz.nasaknights.infiniterecharge.RobotContainer;
import xyz.nasaknights.infiniterecharge.util.controllers.ControllerRegistry;

import static xyz.nasaknights.infiniterecharge.util.controllers.PS4ControllerMappings.*;

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

        double leftYAxis = ControllerRegistry.getRawAxis(ControllerRegistry.ControllerAssignment.DRIVER, LEFT_Y_AXIS.getID());
        double rightXAxis = ControllerRegistry.getRawAxis(ControllerRegistry.ControllerAssignment.DRIVER, RIGHT_X_AXIS.getID());


        switch (RobotContainer.getProfile().getControlType())
        {
            case GTA:
                throttle = RobotContainer.getDriverRawAxis(LEFT_TRIGGER.getID()) - RobotContainer.getDriverRawAxis(RIGHT_TRIGGER.getID());
                turn = RobotContainer.getDriverRawAxis(RIGHT_X_AXIS.getID() * (RobotContainer.getProfile().doesWantSquaredInputs() ? 2 : 1));
            case STICKS:
                throttle = Math.pow(leftYAxis, ControllerRegistry.doesDriverWantSquaredInputs() ? 2 : 1);
                turn = Math.pow(rightXAxis, ControllerRegistry.doesDriverWantSquaredInputs() ? 2 : 1);
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
