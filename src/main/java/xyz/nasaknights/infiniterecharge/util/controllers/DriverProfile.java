package xyz.nasaknights.infiniterecharge.util.controllers;

/**
 * An enum to quickly customize the some aspects of the drivetrain
 */
public enum DriverProfile
{
    DEFAULT(DriveType.CURVATURE_DRIVE, ControlType.GTA, false),
    AUTONOMOUS(DriveType.ARCADE_DRIVE, ControlType.GTA, false), //when the robot controls itself... why is this a thing (BH) TODO find a way around this
    BH(DriveType.ARCADE_DRIVE, ControlType.STICKS, false),
    JG(DriveType.CURVATURE_DRIVE, ControlType.GTA, false),
    TH(DriveType.CURVATURE_DRIVE, ControlType.GTA, false),
    TM(DriveType.ARCADE_DRIVE, ControlType.STICKS, false);

    private DriveType driveType;
    private ControlType driveControlType;
    private boolean squaredInputs;

    DriverProfile(DriveType driveType, ControlType driveControlType, boolean squaredInputs)
    {
        this.driveType = driveType;
        this.driveControlType = driveControlType;
        this.squaredInputs = squaredInputs;
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

    public enum ControlType
    {
        GTA,
        STICKS
    }

    public enum DriveType
    {
        ARCADE_DRIVE,
        CURVATURE_DRIVE
    }
}