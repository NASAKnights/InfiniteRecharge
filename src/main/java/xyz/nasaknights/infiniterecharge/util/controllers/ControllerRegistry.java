package xyz.nasaknights.infiniterecharge.util.controllers;

import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import xyz.nasaknights.infiniterecharge.Constants;
import xyz.nasaknights.infiniterecharge.commands.drivetrain.*;
import xyz.nasaknights.infiniterecharge.commands.intake.IntakeCommand;
import xyz.nasaknights.infiniterecharge.commands.intake.IntakeExtensionCommand;
import xyz.nasaknights.infiniterecharge.commands.drivetrain.DrivetrainShiftCommand;
import xyz.nasaknights.infiniterecharge.commands.shooter.*;

import static xyz.nasaknights.infiniterecharge.util.controllers.PS4ControllerMappings.*;

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

        new JoystickButton(driver, SQUARE.getID()).whenPressed(new DrivetrainShiftCommand());
        new JoystickButton(driver, CIRCLE.getID()).whenPressed(new ToggleHalfDrivePowerCommand());
    }

    public static void setupOperatorJoystick(int port, DriverProfile profile)
    {
        operator = new NKJoystick(port, DriverProfile.DEFAULT);

        // TODO Add button init here

//        new JoystickButton(operator, PS4ControllerMappings.LEFT_BUMPER.getID()).whileHeld(new IntakeCommand(1));
//        new JoystickButton(operator, PS4ControllerMappings.RIGHT_BUMPER.getID()).whileHeld(new ShootCommand(), true);
        new JoystickButton(operator, LEFT_BUMPER.getID()).whileHeld(new IntakeCommand(.75));
        new JoystickButton(operator, RIGHT_BUMPER.getID()).whileHeld(new ShootCommand());

//        new JoystickButton(operator, PS4ControllerMappings.SQUARE.getID()).whenPressed(new IntakeExtensionCommand());
        new JoystickButton(operator, PS4ControllerMappings.TRIANGLE.getID()).whileHeld(new ShootCommand(false));
        new JoystickButton(operator, PS4ControllerMappings.CIRCLE.getID()).whileHeld(new ShootCommand(true));
        new JoystickButton(operator, SQUARE.getID()).whenPressed(new IntakeExtensionCommand());
        new JoystickButton(operator, TRIANGLE.getID()).whenPressed(new ToggleHoodExtensionCommand());
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

    public static boolean isShooterButtonHeld()
    {
        return operator.getRawButton(PS4ControllerMappings.RIGHT_BUMPER.getID());
    }
}
