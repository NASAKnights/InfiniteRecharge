package xyz.nasaknights.infiniterecharge.commands.climb;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import static xyz.nasaknights.infiniterecharge.RobotContainer.getClimberSubsystem;
import static xyz.nasaknights.infiniterecharge.RobotContainer.getDrivetrain;

public class ExtendClimbArmCommand extends SequentialCommandGroup // TODO make individual commands
{
    public ExtendClimbArmCommand()
    {
        super(new InstantCommand(() ->
        {
            getDrivetrain().setDrivetrainNeutral(true);
            getDrivetrain().setPowerTakeoffExtended(true);
        }, getDrivetrain()), new WaitCommand(.25), new InstantCommand(() ->
        {
            getClimberSubsystem().setClimbArmExtended(true);
            getDrivetrain().setHighGear(true);
        }, getClimberSubsystem(), getDrivetrain()), new WaitCommand(4), new InstantCommand(() ->
        {
//            getDrivetrain().setPowerTakeoffExtended(true);
            getDrivetrain().setHighGear(false);
//            new PrepareDriveToClimbCommand(.8).schedule();
        }, getDrivetrain()));
    }
}