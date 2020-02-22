package xyz.nasaknights.infiniterecharge.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import xyz.nasaknights.infiniterecharge.RobotContainer;
import xyz.nasaknights.infiniterecharge.util.controllers.ControllerRegistry;
import xyz.nasaknights.infiniterecharge.util.controllers.DriverProfile;

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
        double leftXAxis = ControllerRegistry.getRawAxis(ControllerRegistry.ControllerAssignment.DRIVER, LEFT_X_AXIS.getID());

        double leftYAxis = ControllerRegistry.getRawAxis(ControllerRegistry.ControllerAssignment.DRIVER, LEFT_Y_AXIS.getID());
        double rightXAxis = ControllerRegistry.getRawAxis(ControllerRegistry.ControllerAssignment.DRIVER, RIGHT_X_AXIS.getID());


        switch (RobotContainer.getProfile().getControlType())
        {
            case GTA:
                throttle = rightTrigger - leftTrigger;
                turn = leftXAxis * -1;
                break;
            case GTA_REVERSED:
                throttle = leftTrigger - rightTrigger;
                turn = rightXAxis * -1;
            case STICKS:
                throttle = leftYAxis;
                turn = rightXAxis * -1;
                break;
            default:
                throttle = 0;
                turn = 0;
                break;
        }

        if (RobotContainer.getProfile().getDriveType() == DriverProfile.DriveType.ARCADE_DRIVE)
        {
            RobotContainer.getDrivetrain().arcadeDrive(throttle, turn, RobotContainer.getProfile().doesWantSquaredInputs());
        } else
        {
            RobotContainer.getDrivetrain().curvatureDrive(throttle, turn, true);
        }
    }

    @Override
    public void end(boolean interrupted)
    {

    }
}
