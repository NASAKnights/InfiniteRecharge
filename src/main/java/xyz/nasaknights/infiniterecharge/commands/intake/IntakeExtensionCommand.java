package xyz.nasaknights.infiniterecharge.commands.intake;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import xyz.nasaknights.infiniterecharge.RobotContainer;

public class IntakeExtensionCommand extends InstantCommand
{
    public IntakeExtensionCommand()
    {
        super(() ->
        {
            System.out.println("Attempting to toggle intake");
            RobotContainer.getIntake().toggleIntakeExtended();

        }, RobotContainer.getIntake());
    }

    public IntakeExtensionCommand(boolean extend)
    {
        super(() -> RobotContainer.getIntake().setIntakeExtended(extend), RobotContainer.getIntake());
    }
}
