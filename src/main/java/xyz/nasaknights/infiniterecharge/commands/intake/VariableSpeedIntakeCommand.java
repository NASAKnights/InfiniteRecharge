package xyz.nasaknights.infiniterecharge.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import xyz.nasaknights.infiniterecharge.RobotContainer;
import xyz.nasaknights.infiniterecharge.util.controllers.ControllerRegistry;
import xyz.nasaknights.infiniterecharge.util.controllers.PS4ControllerMappings;

public class VariableSpeedIntakeCommand extends CommandBase
{
    public VariableSpeedIntakeCommand()
    {
        addRequirements(RobotContainer.getIntake());
    }

    @Override
    public void execute()
    {
        double intakeAxis = ControllerRegistry.getRawAxis(ControllerRegistry.ControllerAssignment.OPERATOR, PS4ControllerMappings.RIGHT_Y_AXIS.getID());

        if (intakeAxis >= .1)
        {
            RobotContainer.getIntake().setIntakeExtended(true);
            RobotContainer.getQueuerSubsystem().setBeltPower(intakeAxis >= .5 ? .7 : 0);
        } else if (intakeAxis <= -.1)
        {
            RobotContainer.getIntake().setIntakeExtended(false);
            RobotContainer.getQueuerSubsystem().setBeltPower(intakeAxis <= -.5 ? -.7 : 0);
        } else
        {
            RobotContainer.getQueuerSubsystem().setBeltPower(0);
            RobotContainer.getIntake().setIntakeExtended(false);
        }

        RobotContainer.getIntake().setIntakePower(intakeAxis);
    }

    @Override
    public boolean isFinished()
    {
        return false;
    }
}
