package xyz.nasaknights.infiniterecharge.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import xyz.nasaknights.infiniterecharge.RobotContainer;

public class ReverseIntakeCommand extends CommandBase
{
    @Override
    public void initialize()
    {
        RobotContainer.getIntake().setIntakeExtended(true);
        RobotContainer.getQueuerSubsystem().setBeltPower(-1);
        RobotContainer.getQueuerSubsystem().setQueuerIntakePower(-.75);
        RobotContainer.getIntake().setIntakePower(-.5);
    }

    @Override
    public void end(boolean interrupted)
    {
        RobotContainer.getIntake().setIntakeExtended(false);
        RobotContainer.getQueuerSubsystem().setBeltPower(0);
        RobotContainer.getQueuerSubsystem().setQueuerIntakePower(0);
        RobotContainer.getIntake().setIntakePower(0);
    }
}
