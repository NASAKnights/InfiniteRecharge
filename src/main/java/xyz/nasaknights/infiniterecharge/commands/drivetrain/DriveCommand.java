package xyz.nasaknights.infiniterecharge.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import xyz.nasaknights.infiniterecharge.RobotContainer;
import xyz.nasaknights.infiniterecharge.util.controllers.PS4ControllerMappings;

public class DriveCommand extends CommandBase {

    public DriveCommand() {
        addRequirements(RobotContainer.getDrivetrain());
        setName("Drive Command");
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        double throttle = 0.0;
        double turn = 0.0;
        switch (RobotContainer.getProfile().getControlType()) {
            case kGTA:
                double accel = RobotContainer.getDriverRawAxis(PS4ControllerMappings.LEFT_TRIGGER.getID());
                double decel = RobotContainer.getDriverRawAxis(PS4ControllerMappings.RIGHT_TRIGGER.getID());
                throttle = accel - decel;
                turn = RobotContainer.getDriverRawAxis(PS4ControllerMappings.RIGHT_X_AXIS.getID());
            case kSticks:
                throttle = RobotContainer.getDriverRawAxis(PS4ControllerMappings.LEFT_Y_AXIS.getID());
                turn = RobotContainer.getDriverRawAxis(PS4ControllerMappings.RIGHT_X_AXIS.getID());
        }
        RobotContainer.getDrivetrain().drive(throttle, turn);
    }

    @Override
    public void end(boolean interrupted) {
        
    }

}
