package xyz.nasaknights.infiniterecharge.commands.climb;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import xyz.nasaknights.infiniterecharge.RobotContainer;
import xyz.nasaknights.infiniterecharge.commands.drivetrain.TimedArcadeDriveCommand;

import java.util.concurrent.TimeUnit;

public class RetractClimbArmCommand extends SequentialCommandGroup
{
    public RetractClimbArmCommand()
    {
        super(
                new InstantCommand(() ->
                {
                    RobotContainer.getDrivetrain().setDrivetrainNeutral(true);
                    RobotContainer.getDrivetrain().setPowerTakeoffExtended(true);
                }),
                new WaitCommand(.25),
                new InstantCommand(() ->
                {
                    RobotContainer.getDrivetrain().setHighGear(true);
                    RobotContainer.getClimberSubsystem().setClimbArmExtended(false);
                    RobotContainer.getDrivetrain().prepareClimbMotors();
                }),
                new TimedArcadeDriveCommand(TimeUnit.SECONDS.toMillis(1), .8)
        );
    }
}
