package xyz.nasaknights.infiniterecharge.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;

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

        System.out.println("Average Encoder Value: " + (getDrivetrain().getLeftEncoderPosition() + getDrivetrain().getRightEncoderPosition() / 2));
    }

    @Override
    public void end(boolean interrupted)
    {
        // TODO Make autonomous with encoder values later???
        System.out.println("########## Ending Climb Controlled portion ##########\n " + "Average Encoder value: " + (getDrivetrain().getLeftEncoderPosition() + getDrivetrain().getRightEncoderPosition() / 2));
    }

    @Override
    public boolean isFinished()
    {
        return false;
    }
}
