package xyz.nasaknights.infiniterecharge.util.controllers;

import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import xyz.nasaknights.infiniterecharge.Constants;
import xyz.nasaknights.infiniterecharge.Robot;
import xyz.nasaknights.infiniterecharge.RobotContainer;
import xyz.nasaknights.infiniterecharge.commands.climb.ExtendWinchSequenceCommand;
import xyz.nasaknights.infiniterecharge.commands.climb.LockClimbCommand;
import xyz.nasaknights.infiniterecharge.commands.drivetrain.*;
import xyz.nasaknights.infiniterecharge.commands.intake.IntakeCommand;
import xyz.nasaknights.infiniterecharge.commands.intake.IntakeExtensionCommand;
import xyz.nasaknights.infiniterecharge.commands.shooter.ShootCommand;
import xyz.nasaknights.infiniterecharge.commands.shooter.ToggleHoodExtensionCommand;

import javax.annotation.Nullable;
import java.awt.*;


public class ControllerRegistry
{
    private static NKJoystick driver;
    private static NKJoystick operator;
    private static NKJoystick test;

    private static boolean doesDriverWantSquaredInputs;

    private static int driverPort = Constants.DRIVER_ID;
    private static int operatorPort = Constants.OPERATOR_ID;

    public static void setupDriverJoystick(int port, DriverProfile profile)
    {
        driver = new NKJoystick(port, Constants.CURRENT_DRIVER_PROFILE);
        doesDriverWantSquaredInputs = profile.doesWantSquaredInputs();

        // TODO Add button init here

        //        new JoystickButton(driver, PS4ControllerMappings.SQUARE.getID()).whenPressed(new DrivetrainShiftCommand());
        new JoystickButton(driver, PS4ControllerMappings.SQUARE.getID()).whenPressed(new InstantCommand(() -> RobotContainer.getDrivetrain().setHighGear(true))).whenReleased(new InstantCommand(() -> RobotContainer.getDrivetrain().setHighGear(false)));
    }

    public static void setupOperatorJoystick(int port, DriverProfile profile)
    {
        operator = new NKJoystick(port, profile);

        // TODO Add button init here

        new JoystickButton(operator, PS4ControllerMappings.LEFT_BUMPER.getID()).whileHeld(new IntakeCommand(.75));
        new JoystickButton(operator, PS4ControllerMappings.RIGHT_BUMPER.getID()).whileHeld(new ShootCommand(false));

        new JoystickButton(operator, PS4ControllerMappings.SQUARE.getID()).whenPressed(new IntakeExtensionCommand());
        new JoystickButton(operator, PS4ControllerMappings.TRIANGLE.getID()).whenPressed(new ToggleHoodExtensionCommand());

        new JoystickButton(operator, PS4ControllerMappings.OPTIONS.getID()).whenPressed(new ExtendWinchSequenceCommand());
        new JoystickButton(operator, PS4ControllerMappings.SHARE.getID()).whenPressed(new DriveMotorClimbCommand()).whenReleased(new LockClimbCommand());
    }

    public static void setupTestJoystick(int port, @Nullable DriverProfile profile)
    {
        test = new NKJoystick(port, (profile == null) ? DriverProfile.DEFAULT : profile);

        // toggle neutral
        new JoystickButton(test, 1).whenPressed(new InstantCommand(() ->
        {
            RobotContainer.getDrivetrain().setDrivetrainNeutral(!RobotContainer.getDrivetrain().isDriveNeutral());
            System.out.println((RobotContainer.getDrivetrain().isDriveNeutral()) ? "Switched drivetrain to neutral mode" : "Switched drivetrain to drive mode");
        }, RobotContainer.getDrivetrain()));

        // toggle power takeoff
        new JoystickButton(test, 2).whenPressed(new InstantCommand(() ->
        {
            RobotContainer.getDrivetrain().setPowerTakeoffExtended(!RobotContainer.getDrivetrain().isInClimbGear());
            System.out.println((RobotContainer.getDrivetrain().isInClimbGear()) ? "Power takeoff extended" : "Power takeoff retracted");
        }, RobotContainer.getDrivetrain()));

        // toggle winch
        new JoystickButton(test, 3).whenPressed(new InstantCommand(() ->
        {
            RobotContainer.getClimberSubsystem().setWinchExtended(RobotContainer.getClimberSubsystem().getWinchExtended());
            System.out.println(RobotContainer.getClimberSubsystem().getWinchExtended() ? "Extended Climb Winch" : "Retracted Climb Winch");
        }, RobotContainer.getClimberSubsystem()));

        // move drive motors
        new JoystickButton(test, 4).whileHeld(new DriveMotorClimbCommand());

        // test servo command
        new JoystickButton(test, 5).whileHeld(new FunctionalCommand(() -> System.out.println("init servo command"), () -> RobotContainer.getDrivetrain().setServoSpeeds(0.50, 0.50), System.out::println, () -> test.getRawButton(5), RobotContainer.getDrivetrain()));
    }

    public static double getRawAxis(ControllerAssignment controller, int axisID)
    {
        return (controller == ControllerAssignment.DRIVER ? driver : operator).getRawAxis(axisID);
    }

    public static boolean doesDriverWantSquaredInputs()
    {
        return doesDriverWantSquaredInputs;
    }

    public static boolean isShooterButtonHeld()
    {
        return operator.getRawButton(PS4ControllerMappings.RIGHT_BUMPER.getID());
    }

    public enum ControllerAssignment
    {
        DRIVER, OPERATOR
    }
}
