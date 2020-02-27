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

        if (Math.abs(intakeAxis) > 0.05)
        {
            if (!RobotContainer.getIntake().isIntakeExtended())
                RobotContainer.getIntake().setIntakeExtended(true);
            RobotContainer.getIntake().setIntakePower(intakeAxis);
        } else
        {
            if (RobotContainer.getIntake().isIntakeExtended())
            {
                RobotContainer.getIntake().setIntakeExtended(false);
            }
        }
    }

    @Override
    public boolean isFinished()
    {
        return false;
    }
}
