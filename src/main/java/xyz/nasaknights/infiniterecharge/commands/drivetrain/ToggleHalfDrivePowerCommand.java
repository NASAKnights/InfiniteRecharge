package xyz.nasaknights.infiniterecharge.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import xyz.nasaknights.infiniterecharge.RobotContainer;

public class ToggleHalfDrivePowerCommand extends InstantCommand
{
    public ToggleHalfDrivePowerCommand()
    {
        super(() ->
        {
            RobotContainer.getDrivetrain().toggleMaxSpeeds();
            System.out.println("Switched max drive speeds");
        }, RobotContainer.getDrivetrain());
    }
}
