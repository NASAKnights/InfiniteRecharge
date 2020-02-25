package xyz.nasaknights.infiniterecharge.commands.intake;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import xyz.nasaknights.infiniterecharge.RobotContainer;

public class IntakeExtensionCommand extends InstantCommand
{
    public IntakeExtensionCommand()
    {
        super(new Runnable()
        {
            @Override
            public void run()
            {
                System.out.println(!RobotContainer.getIntake().isIntakeExtended());
                RobotContainer.getIntake().setIntakeExtended(!RobotContainer.getIntake().isIntakeExtended());

            }
        }, RobotContainer.getIntake());
    }

    public IntakeExtensionCommand(boolean extend)
    {
        super(() -> RobotContainer.getIntake().setIntakeExtended(extend), RobotContainer.getIntake());
    }
}
