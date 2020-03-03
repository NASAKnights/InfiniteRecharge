package xyz.nasaknights.infiniterecharge.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.PrintCommand;

import static xyz.nasaknights.infiniterecharge.RobotContainer.getClimberSubsystem;
import static xyz.nasaknights.infiniterecharge.RobotContainer.getDrivetrain;

public class DriveMotorClimbCommand extends CommandBase
{
    private static double power = 0.80;

    public DriveMotorClimbCommand()
    {
        addRequirements(getDrivetrain());
    }

    @Override
    public void initialize()
    {
        getDrivetrain().setDrivetrainNeutral(true);
        getDrivetrain().setPowerTakeoffExtended(true);
        getClimberSubsystem().setWinchExtended(false);

        getDrivetrain().prepareClimbMotors();

        getDrivetrain().resetEncoders();
    }

    @Override
    public void execute()
    {
        getDrivetrain().setMotorPercents(power, power);
        new PrintCommand("Average Encoder Value: " + (getDrivetrain().getLeftEncoderPosition() + getDrivetrain().getRightEncoderPosition() / 2)).schedule();
    }

    @Override
    public void end(boolean interrupted)
    {
        // TODO Make autonomous with encoder values later???
        new PrintCommand("########## Ending Climb Controlled portion ##########\n " + "Average Encoder value: " + (getDrivetrain().getLeftEncoderPosition() + getDrivetrain().getRightEncoderPosition() / 2)).schedule();
    }

    @Override
    public boolean isFinished()
    {
        return false;
    }
}
