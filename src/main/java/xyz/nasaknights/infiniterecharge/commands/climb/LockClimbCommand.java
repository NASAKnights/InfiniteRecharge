package xyz.nasaknights.infiniterecharge.commands.climb;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import static xyz.nasaknights.infiniterecharge.RobotContainer.getClimberSubsystem;
import static xyz.nasaknights.infiniterecharge.RobotContainer.getDrivetrain;

public class LockClimbCommand extends SequentialCommandGroup
{
    public LockClimbCommand()
    {
        super(new InstantCommand(() -> getDrivetrain().setPowerTakeoffExtended(false)), new InstantCommand(() -> getClimberSubsystem().setClimbArmExtended(false)), new InstantCommand(() -> getDrivetrain().disableMotors()));
    }
}
