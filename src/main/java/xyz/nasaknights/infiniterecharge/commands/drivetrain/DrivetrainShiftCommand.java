package xyz.nasaknights.infiniterecharge.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import xyz.nasaknights.infiniterecharge.RobotContainer;

public class DrivetrainShiftCommand extends CommandBase
{
    @Override
    public void initialize()
    {
        RobotContainer.getDrivetrain().setHighGear(true);
    }

    @Override
    public void end(boolean interrupted)
    {
        RobotContainer.getDrivetrain().setHighGear(false);
    }
}
