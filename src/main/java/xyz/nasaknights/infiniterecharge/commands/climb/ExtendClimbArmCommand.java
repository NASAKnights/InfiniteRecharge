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
            getDrivetrain().setPowerTakeoffExtended(false);
        }, getDrivetrain()), new WaitCommand(.25), new InstantCommand(() ->
        {
            getDrivetrain().setHighGear(true);
            getClimberSubsystem().setClimbArmExtended(true);
        }, getClimberSubsystem(), getDrivetrain()), /*new TimedArcadeDriveCommand(4, .3),*/ new WaitCommand(3), new InstantCommand(() ->
        {
            getDrivetrain().setPowerTakeoffExtended(false);
            getDrivetrain().setHighGear(false);
//            new PrepareDriveToClimbCommand(.8).schedule();
        }, getDrivetrain()));
    }
}