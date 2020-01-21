package xyz.nasaknights.infiniterecharge.util.controllers;

import xyz.nasaknights.infiniterecharge.Constants;

public class ControllerRegistry
{
    private static NKJoystick driver;
    private static NKJoystick operator;

    private static boolean doesDriverWantSquaredInputs;

    private static int driverPort;
    private static int operatorPort;

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
        DRIVER,
        OPERATOR
    }

    static class PortAlreadyAllocatedException extends Exception
    {
        public PortAlreadyAllocatedException(int port, String details)
        {
            super("The provided port is already in use. Please check your ControllerRegistry class. Tried to assign port " + port + " with details: " + details);
        }
    }
}
