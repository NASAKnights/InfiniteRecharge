package xyz.nasaknights.infiniterecharge;

import xyz.nasaknights.infiniterecharge.subsystems.DrivetrainSubsystem;

public class RobotContainer {
    private static final DrivetrainSubsystem drivetrainSubsystem = new DrivetrainSubsystem();

    public RobotContainer() {
        // Configure the button bindings
        configureButtonBindings();
    }

    public static DrivetrainSubsystem getDrivetrain() {
        return drivetrainSubsystem;
    }

    private void configureButtonBindings() {

    }

//    public Command getAutonomousCommand()
//    {
//        // TODO Work on autonomous?
//        return autonomousCommand;
//    }
}
