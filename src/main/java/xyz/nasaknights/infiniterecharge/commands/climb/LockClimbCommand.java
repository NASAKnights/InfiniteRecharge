package xyz.nasaknights.infiniterecharge.commands.climb;

import edu.wpi.first.wpilibj2.command.*;

import static xyz.nasaknights.infiniterecharge.RobotContainer.*;

public class LockClimbCommand extends SequentialCommandGroup
{
    public LockClimbCommand()
    {
        super(new InstantCommand(() -> getDrivetrain().setPowerTakeoffExtended(false)), new InstantCommand(() -> getClimberSubsystem().setWinchExtended(false)), new InstantCommand(() -> getDrivetrain().disableMotors()));
    }
}
