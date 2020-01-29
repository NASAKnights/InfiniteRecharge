package xyz.nasaknights.infiniterecharge.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import xyz.nasaknights.infiniterecharge.RobotContainer;
import xyz.nasaknights.infiniterecharge.util.controllers.ControllerRegistry;

import static xyz.nasaknights.infiniterecharge.util.controllers.PS4ControllerMappings.*;

/**
 * The main command for the drivetrain subsystem.
 *
 * @see xyz.nasaknights.infiniterecharge.subsystems.DrivetrainSubsystem
 */
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

        double leftTrigger = ControllerRegistry.getRawAxis(ControllerRegistry.ControllerAssignment.DRIVER, LEFT_TRIGGER.getID());
        double rightTrigger = ControllerRegistry.getRawAxis(ControllerRegistry.ControllerAssignment.DRIVER, RIGHT_TRIGGER.getID());

        double leftYAxis = ControllerRegistry.getRawAxis(ControllerRegistry.ControllerAssignment.DRIVER, LEFT_Y_AXIS.getID());
        double rightXAxis = ControllerRegistry.getRawAxis(ControllerRegistry.ControllerAssignment.DRIVER, RIGHT_X_AXIS.getID());


        switch (RobotContainer.getProfile().getControlType())
        {
            case GTA:
                throttle = Math.pow(leftTrigger - rightTrigger, ControllerRegistry.doesDriverWantSquaredInputs() ? 2 : 1);
                turn = Math.pow(rightTrigger, ControllerRegistry.doesDriverWantSquaredInputs() ? 2 : 1);
                break;
            case STICKS:
                throttle = Math.pow(leftYAxis, ControllerRegistry.doesDriverWantSquaredInputs() ? 2 : 1);
                turn = Math.pow(rightXAxis, ControllerRegistry.doesDriverWantSquaredInputs() ? 2 : 1);
                break;
            default:
                throttle = 0;
                turn = 0;
                break;
        }
        RobotContainer.getDrivetrain().drive(throttle, turn);
    }

    @Override
    public void end(boolean interrupted)
    {

    }
}
