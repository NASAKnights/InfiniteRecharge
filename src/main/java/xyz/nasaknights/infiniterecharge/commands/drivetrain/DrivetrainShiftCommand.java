package xyz.nasaknights.infiniterecharge.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import xyz.nasaknights.infiniterecharge.RobotContainer;

public class DrivetrainShiftCommand extends CommandBase
{
    @Override
    public void initialize()
    {
        System.out.println("Attempting gear shift");
        RobotContainer.getDrivetrain().setHighGear(true);
    }

    @Override
    public void end(boolean interrupted)
    {
        System.out.println("Ending high gear");
        RobotContainer.getDrivetrain().setHighGear(false);
    }
}
