package xyz.nasaknights.infiniterecharge.util.controllers;

import edu.wpi.first.wpilibj.command.Command;

import java.util.HashMap;

/**
 * An enum to quickly customize the some aspects of the drivetrain
 */
public enum DriverProfile
{
    DEFAULT(DriveType.CURVATURE_DRIVE, ControlType.GTA, false, new HashMap<>()
    {{
    }}),
    AUTONOMOUS(DriveType.ARCADE_DRIVE, ControlType.GTA, false, new HashMap<>()
    {{
    }}), //when the robot controls itself... why is this a thing (BH) TODO find a way around this
    BH(DriveType.ARCADE_DRIVE, ControlType.STICKS, false, new HashMap<>()
    {{
    }}),
    JG(DriveType.CURVATURE_DRIVE, ControlType.GTA, false, new HashMap<>()
    {{
    }}),
    TH(DriveType.CURVATURE_DRIVE, ControlType.GTA, false, new HashMap<>()
    {{
    }}),
    TM(DriveType.ARCADE_DRIVE, ControlType.STICKS, false, new HashMap<>()
    {{
    }});

    private DriveType driveType;
    private ControlType driveControlType;
    private boolean squaredInputs;
    private HashMap<PS4ControllerMappings, Command> buttons;

    DriverProfile(DriveType driveType, ControlType driveControlType, boolean squaredInputs, HashMap<PS4ControllerMappings, Command> buttons)
    {
        this.driveType = driveType;
        this.driveControlType = driveControlType;
        this.squaredInputs = squaredInputs;
        this.buttons = buttons;
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