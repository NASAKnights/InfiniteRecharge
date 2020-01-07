package xyz.nasaknights.infiniterecharge.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import xyz.nasaknights.infiniterecharge.RobotContainer;

public class ArcadeDriveCommand extends CommandBase {
    public ArcadeDriveCommand() {
        addRequirements(RobotContainer.getDrivetrain());
        setName("Arcade Drive Command");
    }


}
