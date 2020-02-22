package xyz.nasaknights.infiniterecharge.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import xyz.nasaknights.infiniterecharge.RobotContainer;

public class DrivetrainShiftCommand extends InstantCommand
{
    public DrivetrainShiftCommand()
    {
        super(() -> RobotContainer.getDrivetrain().setHighGear(!RobotContainer.getDrivetrain().isDriveInHighGear()));
    }
}
