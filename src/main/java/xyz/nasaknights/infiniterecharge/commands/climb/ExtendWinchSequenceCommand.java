package xyz.nasaknights.infiniterecharge.commands.climb;

import edu.wpi.first.wpilibj2.command.*;

import static xyz.nasaknights.infiniterecharge.RobotContainer.*;

public class ExtendWinchSequenceCommand extends SequentialCommandGroup // TODO make individual commands
{
    public ExtendWinchSequenceCommand()
    {
        super(new InstantCommand(() ->
        {
            getDrivetrain().setDrivetrainNeutral(true);
            getDrivetrain().setPowerTakeoffExtended(true);
        }, getDrivetrain()), new InstantCommand(() -> getClimberSubsystem().setWinchExtended(true), getClimberSubsystem()), new InstantCommand(() ->
        {
            getDrivetrain().setPowerTakeoffExtended(false);
            getDrivetrain().setDrivetrainNeutral(false);
        }, getDrivetrain()));
    }
}