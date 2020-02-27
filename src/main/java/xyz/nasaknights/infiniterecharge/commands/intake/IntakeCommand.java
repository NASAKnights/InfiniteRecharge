package xyz.nasaknights.infiniterecharge.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import xyz.nasaknights.infiniterecharge.RobotContainer;

public class IntakeCommand extends CommandBase
{
    double speed;

    public IntakeCommand(double speed)
    {
        this.speed = speed;
    }

    @Override
    public void initialize()
    {
        RobotContainer.getIntake().setIntakeExtended(true);
    }

    @Override
    public void execute()
    {
        RobotContainer.getIntake().setIntakePower(speed);
    }

    @Override
    public void end(boolean interrupted)
    {
        RobotContainer.getIntake().setIntakeExtended(false);
        RobotContainer.getIntake().setIntakePower(0);
    }
}
