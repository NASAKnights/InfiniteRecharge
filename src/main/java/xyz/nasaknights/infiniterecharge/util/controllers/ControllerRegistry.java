package xyz.nasaknights.infiniterecharge.util.controllers;

import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import xyz.nasaknights.infiniterecharge.Constants;
import xyz.nasaknights.infiniterecharge.commands.drivetrain.DrivetrainShiftCommand;

public class ControllerRegistry
{
    private static NKJoystick driver;
    private static NKJoystick operator;

    private static boolean doesDriverWantSquaredInputs;

    private static int driverPort = -1;
    private static int operatorPort = -2;

    public static void setupDriverJoystick(int port, DriverProfile profile)
    {
        try
        {
            if (port == driverPort || port == operatorPort)
            {
                throw new PortAlreadyAllocatedException(port, "thrown while attempting to init. driver");
            }

            driver = new NKJoystick(port, Constants.CURRENT_DRIVER_PROFILE);
            doesDriverWantSquaredInputs = profile.doesWantSquaredInputs();

            // TODO Add button init here
            
            new JoystickButton(driver, PS4ControllerMappings.SQUARE.getID()).whenPressed(new DrivetrainShiftCommand());
        } catch (PortAlreadyAllocatedException e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void setupOperatorJoystick(int port, DriverProfile profile)
    {
        try
        {
            if (port == driverPort || port == operatorPort)
            {
                throw new PortAlreadyAllocatedException(port, "thrown while attempting to init. operator");
            }

            operator = new NKJoystick(port, DriverProfile.DEFAULT);

            // TODO Add button init here

            // new JoystickButton(operator, PS4ControllerMappings.X.getID()).whileHeld(new IntakeCommand());

        } catch (PortAlreadyAllocatedException e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
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

    static class PortAlreadyAllocatedException extends Exception
    {
        public PortAlreadyAllocatedException(int port, String details)
        {
            super("The provided port is already in use. Please check your ControllerRegistry class. Tried to assign port " + port + " with details: " + details);
        }
    }
}
