package xyz.nasaknights.infiniterecharge.util.controllers;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import xyz.nasaknights.infiniterecharge.*;
import xyz.nasaknights.infiniterecharge.commands.climb.ExtendWinchSequenceCommand;
import xyz.nasaknights.infiniterecharge.commands.climb.LockClimbCommand;
import xyz.nasaknights.infiniterecharge.commands.drivetrain.DriveMotorClimbCommand;
import xyz.nasaknights.infiniterecharge.commands.drivetrain.DrivetrainShiftCommand;
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
    }

    public static void setupOperatorJoystick(int port, DriverProfile profile)
    {
        operator = new NKJoystick(port, DriverProfile.DEFAULT);

        // TODO Add button init here

        //        new JoystickButton(operator, PS4ControllerMappings.LEFT_BUMPER.getID()).whileHeld(new IntakeCommand(1));
        //        new JoystickButton(operator, PS4ControllerMappings.RIGHT_BUMPER.getID()).whileHeld(new ShootCommand(), true);

        //        new JoystickButton(operator, PS4ControllerMappings.SQUARE.getID()).whenPressed(new IntakeExtensionCommand());
        //        new JoystickButton(operator, PS4ControllerMappings.TRIANGLE.getID()).whileHeld(new ShootCommand(false));
        //        new JoystickButton(operator, PS4ControllerMappings.CIRCLE.getID()).whileHeld(new ShootCommand(true));
        //
        //        new JoystickButton(operator, PS4ControllerMappings.OPTIONS.getID()).whenPressed(new ExtendWinchSequenceCommand());
        //        new JoystickButton(operator, PS4ControllerMappings.SHARE.getID()).whenPressed(new DriveMotorClimbCommand()).whenReleased(new LockClimbCommand());

        // toggle power takeoff
        new JoystickButton(operator, PS4ControllerMappings.X.getID()).whenPressed(new InstantCommand(() ->
        {
            RobotContainer.getDrivetrain().setPowerTakeoffExtended(!RobotContainer.getDrivetrain().isInClimbGear());
            System.out.println("##########################");
            System.out.println((RobotContainer.getDrivetrain().isInClimbGear()) ? "Switched to climb gear" : "Reverted to drive gear");
            System.out.println("##########################");
        }, RobotContainer.getDrivetrain()));

        // toggle drive in neutral
        new JoystickButton(operator, PS4ControllerMappings.CIRCLE.getID()).whenPressed(new InstantCommand(() ->
        {
            RobotContainer.getDrivetrain().setDrivetrainNeutral(!RobotContainer.getDrivetrain().isDriveNeutral());
            System.out.println("===========================");
            System.out.println((RobotContainer.getDrivetrain().isDriveNeutral()) ? "Switched drive to neutral" : "Drive is not neutral anymore");
            System.out.println("===========================");
        }));

        // toggle climb winch
        new JoystickButton(operator, PS4ControllerMappings.SQUARE.getID()).whenPressed(new InstantCommand(() ->
        {
            RobotContainer.getClimberSubsystem().setWinchExtended(!RobotContainer.getClimberSubsystem().getWinchExtended());
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println((RobotContainer.getClimberSubsystem().getWinchExtended()) ? "Extended the climb winch" : "Retracted climb winch");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }));

        //new JoystickButton(operator, PS4ControllerMappings.TRIANGLE.getID()).whileHeld(new DriveMotorClimbCommand());
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
