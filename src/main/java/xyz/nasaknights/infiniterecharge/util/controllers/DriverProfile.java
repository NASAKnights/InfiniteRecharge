package xyz.nasaknights.infiniterecharge.util.controllers;

/**
 * An enum to quickly customize the some aspects of the drivetrain
 */
public enum DriverProfile
{
    DEFAULT(DriveType.CURVATURE_DRIVE, ControlType.GTA, false, 1, 1), AUTONOMOUS(DriveType.ARCADE_DRIVE, ControlType.GTA, false, 1, 1), //when the robot controls itself... why is this a thing (BH) TODO find a way around this
    BH(DriveType.ARCADE_DRIVE, ControlType.STICKS, false, 1, 1), JG(DriveType.CURVATURE_DRIVE, ControlType.GTA, false, 1, 1), TH(DriveType.CURVATURE_DRIVE, ControlType.GTA, false, 1, 1),
    MK(DriveType.ARCADE_DRIVE, ControlType.GTA, true, .7, .7);

    private DriveType driveType;
    private ControlType driveControlType;
    private boolean squaredInputs;
    private double maxThrottle, maxTurn;

    DriverProfile(DriveType driveType, ControlType driveControlType, boolean squaredInputs, double maxThrottle, double maxTurn)
    {
        this.driveType = driveType;
        this.driveControlType = driveControlType;
        this.squaredInputs = squaredInputs;
        this.maxThrottle = maxThrottle;
        this.maxTurn = maxTurn;
    }

    /**
     * Gets the drive type.
     *
     * @return the drive type
     */
    public DriveType getDriveType()
    {
        return driveType;
    }

    /**
     * Gets the drive control type
     *
     * @return the type of controlling the base with the controllers
     */
    public ControlType getControlType()
    {
        return driveControlType;
    }

    public boolean doesWantSquaredInputs()
    {
        return squaredInputs;
    }

    public double getMaxThrottle()
    {
        return maxThrottle;
    }

    public double getMaxTurn()
    {
        return maxTurn;
    }

    public enum ControlType
    {
        GTA, GTA_REVERSED, STICKS
    }

    public enum DriveType
    {
        ARCADE_DRIVE, CURVATURE_DRIVE
    }
}
