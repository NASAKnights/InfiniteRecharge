package xyz.nasaknights.infiniterecharge.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import xyz.nasaknights.infiniterecharge.RobotContainer;

public class IntakeCommand extends CommandBase
{
    public IntakeCommand()
    {
        addRequirements(RobotContainer.getIntake());
    }

    @Override
    public void initialize()
    {
        if (!RobotContainer.getIntake().getIntakeExtended())
            RobotContainer.getIntake().setIntakeExtended(true);
    }

    @Override
    public void execute()
    {
        RobotContainer.getIntake().intake();
    }

    @Override
    public void end(boolean interrupted)
    {
        if (RobotContainer.getIntake().getIntakeExtended())
            RobotContainer.getIntake().setIntakeExtended(false);
    }

    @Override
    public boolean isFinished()
    {
        return false;
    }
}
