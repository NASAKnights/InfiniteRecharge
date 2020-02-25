package xyz.nasaknights.infiniterecharge.commands.shooter;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import xyz.nasaknights.infiniterecharge.RobotContainer;

public class ToggleHoodExtensionCommand extends InstantCommand
{
    public ToggleHoodExtensionCommand()
    {
        super(() -> RobotContainer.getShooterSubsystem().toggleHoodExtended(), RobotContainer.getShooterSubsystem());
    }
}
