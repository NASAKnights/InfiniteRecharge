package xyz.nasaknights.infiniterecharge.util.controllers;

import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import xyz.nasaknights.infiniterecharge.Constants;
import xyz.nasaknights.infiniterecharge.commands.climb.ExtendClimbArmCommand;
import xyz.nasaknights.infiniterecharge.commands.climb.RetractClimbArmCommand;
import xyz.nasaknights.infiniterecharge.commands.drivetrain.DriveCommand;
import xyz.nasaknights.infiniterecharge.commands.drivetrain.DrivetrainShiftCommand;
import xyz.nasaknights.infiniterecharge.commands.drivetrain.VisionDriveAssistCommand;
import xyz.nasaknights.infiniterecharge.commands.shooter.ShootCommand;


public class ControllerRegistry
{
    private static NKJoystick driver;
    private static NKJoystick operator;

    private static boolean doesDriverWantSquaredInputs;

    private static int driverPort = Constants.DRIVER_ID;
    private static int operatorPort = Constants.OPERATOR_ID;

    public static void setupDriverJoystick(int port, DriverProfile profile)
    {
        driver = new NKJoystick(port, Constants.CURRENT_DRIVER_PROFILE);
        doesDriverWantSquaredInputs = profile.doesWantSquaredInputs();

        // TODO Add button init here

        new JoystickButton(driver, PS4ControllerMappings.SQUARE.getID()).whileHeld(new DrivetrainShiftCommand());

        new JoystickButton(driver, PS4ControllerMappings.X.getID()).whileHeld(new VisionDriveAssistCommand());

//        new JoystickButton(driver, PS4ControllerMappings.RIGHT_BUMPER.getID()).whenPressed(new InstantCommand(() -> RobotContainer.getDrivetrain().setDrivetrainNeutral(false)));
//        new JoystickButton(driver, PS4ControllerMappings.LEFT_BUMPER.getID()).whenPressed(new InstantCommand(() -> RobotContainer.getDrivetrain().setDrivetrainNeutral(true)));
//
//        new JoystickButton(driver, PS4ControllerMappings.SHARE.getID()).whenPressed(new InstantCommand(() -> RobotContainer.getDrivetrain().setPowerTakeoffExtended(true)));
//        new JoystickButton(driver, PS4ControllerMappings.OPTIONS.getID()).whenPressed(new InstantCommand(() -> RobotContainer.getDrivetrain().setPowerTakeoffExtended(false)));
    }

    public static void setupOperatorJoystick(int port, DriverProfile profile)
    {
        operator = new NKJoystick(port, DriverProfile.DEFAULT);

        // TODO Add button init here

        //        new JoystickButton(operator, PS4ControllerMappings.LEFT_BUMPER.getID()).whileHeld(new IntakeCommand(1));
        //        new JoystickButton(operator, PS4ControllerMappings.RIGHT_BUMPER.getID()).whileHeld(new ShootCommand(), true);

        new JoystickButton(operator, PS4ControllerMappings.TRIANGLE.getID()).whileHeld(new ShootCommand(false));
        new JoystickButton(operator, PS4ControllerMappings.CIRCLE.getID()).whileHeld(new ShootCommand(true));

        new JoystickButton(operator, PS4ControllerMappings.OPTIONS.getID()).whenPressed(new ExtendClimbArmCommand().andThen(new DriveCommand()));
        new JoystickButton(operator, PS4ControllerMappings.SHARE.getID()).whileHeld(new RetractClimbArmCommand(true).andThen(new DriveCommand()));
        new JoystickButton(operator, PS4ControllerMappings.LEFT_BUMPER.getID()).whileHeld(new RetractClimbArmCommand(false).andThen(new DriveCommand()));
    }

    public static double getRawAxis(ControllerAssignment controller, int axisID)
    {
        return (controller == ControllerAssignment.DRIVER ? driver : operator).getRawAxis(axisID);
    }

    public static boolean isOperatorLeftBumperPressed()
    {
        return operator.getRawButton(PS4ControllerMappings.LEFT_BUMPER.getID());
    }

    public static boolean isOperatorSharePressed()
    {
        return operator.getRawButton(PS4ControllerMappings.SHARE.getID());
    }

    public static boolean doesDriverWantSquaredInputs()
    {
        return doesDriverWantSquaredInputs;
    }

    public enum ControllerAssignment
    {
        DRIVER, OPERATOR
    }
}
