package xyz.nasaknights.infiniterecharge.commands.climb;

import edu.wpi.first.wpilibj2.command.*;
import xyz.nasaknights.infiniterecharge.commands.drivetrain.DriveCommand;

import static xyz.nasaknights.infiniterecharge.RobotContainer.getClimberSubsystem;
import static xyz.nasaknights.infiniterecharge.RobotContainer.getDrivetrain;

public class ExtendWinchSequenceCommand extends SequentialCommandGroup // TODO make individual commands
{
    public ExtendWinchSequenceCommand()
    {
        super(new InstantCommand(() ->
        {
            getDrivetrain().setDrivetrainNeutral(true);
            getDrivetrain().setPowerTakeoffExtended(true);
        }, getDrivetrain()), new WaitCommand(0.25), new InstantCommand(() ->
        {
            getClimberSubsystem().setWinchExtended(true);
            getDrivetrain().setHighGear(true);
        }, getClimberSubsystem(), getDrivetrain()), new WaitCommand(2), new InstantCommand(() ->
        {
            getDrivetrain().setPowerTakeoffExtended(false);
            getDrivetrain().setHighGear(false);
            new DriveCommand().schedule();
        }, getDrivetrain()));
    }
}