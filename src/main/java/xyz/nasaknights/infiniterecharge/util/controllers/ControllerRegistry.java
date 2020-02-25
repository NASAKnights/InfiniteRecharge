package xyz.nasaknights.infiniterecharge.util.controllers;

import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import xyz.nasaknights.infiniterecharge.Constants;
import xyz.nasaknights.infiniterecharge.commands.drivetrain.DrivetrainShiftCommand;
import xyz.nasaknights.infiniterecharge.commands.intake.IntakeCommand;
import xyz.nasaknights.infiniterecharge.commands.intake.IntakeExtensionCommand;
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

        new JoystickButton(driver, PS4ControllerMappings.SQUARE.getID()).whenPressed(new DrivetrainShiftCommand());
    }

    public static void setupOperatorJoystick(int port, DriverProfile profile)
    {
        operator = new NKJoystick(port, DriverProfile.DEFAULT);

        // TODO Add button init here

        new JoystickButton(operator, PS4ControllerMappings.LEFT_BUMPER.getID()).whileHeld(new IntakeCommand(.75));
        new JoystickButton(operator, PS4ControllerMappings.RIGHT_BUMPER.getID()).whileHeld(new ShootCommand());

        new JoystickButton(operator, PS4ControllerMappings.SQUARE.getID()).whenPressed(new IntakeExtensionCommand());
    }

    public static double getRawAxis(ControllerAssignment controller, int axisID)
    {
        return (controller == ControllerAssignment.DRIVER ? driver : operator).getRawAxis(axisID);
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
