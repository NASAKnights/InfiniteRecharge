package xyz.nasaknights.infiniterecharge.util.controllers;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import xyz.nasaknights.infiniterecharge.Constants;
import xyz.nasaknights.infiniterecharge.RobotContainer;
import xyz.nasaknights.infiniterecharge.commands.climb.ExtendClimbArmCommand;
import xyz.nasaknights.infiniterecharge.commands.climb.RetractClimbArmCommand;
import xyz.nasaknights.infiniterecharge.commands.drivetrain.DriveCommand;
import xyz.nasaknights.infiniterecharge.commands.drivetrain.DrivetrainShiftCommand;
import xyz.nasaknights.infiniterecharge.commands.drivetrain.VisionDriveAssistCommand;
import xyz.nasaknights.infiniterecharge.commands.shooter.ShootCommand;

import static xyz.nasaknights.infiniterecharge.subsystems.ShooterSubsystem.ShooterPosition.*;
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

        new JoystickButton(driver, SQUARE.getID()).whileHeld(new DrivetrainShiftCommand());

        new JoystickButton(driver, 1).whenPressed(new VisionDriveAssistCommand()).whenReleased(new InstantCommand(() ->
        {
            System.out.println("Ending Vision Drive");
            RobotContainer.getVisionClient().setButtonPressed(false);
            new DriveCommand().schedule();
            RobotContainer.getVisionClient().setLightOn(false);
        }));

        //        new JoystickButton(driver, RIGHT_BUMPER.getID()).whenPressed(new InstantCommand(() -> getDrivetrain().setDrivetrainNeutral(false)));
        //        new JoystickButton(driver, LEFT_BUMPER.getID()).whenPressed(new InstantCommand(() -> getDrivetrain().setDrivetrainNeutral(true)));
        //
        //        new JoystickButton(driver, SHARE.getID()).whenPressed(new InstantCommand(() -> getDrivetrain().setPowerTakeoffExtended(true)));
        //        new JoystickButton(driver, OPTIONS.getID()).whenPressed(new InstantCommand(() -> getDrivetrain().setPowerTakeoffExtended(false)));
    }

    public static void setupOperatorJoystick(int port, DriverProfile profile)
    {
        operator = new NKJoystick(port, profile);

        // TODO Add button init here

        //        new JoystickButton(operator, LEFT_BUMPER.getID()).whileHeld(new IntakeCommand(1));
        //        new JoystickButton(operator, RIGHT_BUMPER.getID()).whileHeld(new ShootCommand(), true);

        new JoystickButton(operator, TRIANGLE.getID()).whileHeld(new ShootCommand(WALL_SHOT)); // WALL_SHOT
        new JoystickButton(operator, CIRCLE.getID()).whileHeld(new ShootCommand(AUTO_LINE_SHOT)); // AUTO_SHOT
        new JoystickButton(operator, X.getID()).whileHeld(new ShootCommand(TRENCH_SHOT));

        new JoystickButton(operator, OPTIONS.getID()).whenPressed(new ExtendClimbArmCommand().andThen(new DriveCommand()));
        new JoystickButton(operator, SHARE.getID()).whileHeld(new RetractClimbArmCommand(true).andThen(new DriveCommand()));
        new JoystickButton(operator, LEFT_BUMPER.getID()).whileHeld(new RetractClimbArmCommand(false).andThen(new DriveCommand()));
    }

    public static double getRawAxis(ControllerAssignment controller, int axisID)
    {
        return (controller == ControllerAssignment.DRIVER ? driver : operator).getRawAxis(axisID);
    }

    public static boolean isOperatorLeftBumperPressed()
    {
        return operator.getRawButton(LEFT_BUMPER.getID());
    }

    public static boolean isOperatorSharePressed()
    {
        return operator.getRawButton(SHARE.getID());
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
