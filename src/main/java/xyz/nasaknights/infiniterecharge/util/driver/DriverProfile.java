package xyz.nasaknights.infiniterecharge.util.driver;

/**
 * An enum to quickly customize the some aspects of the drivetrain
 */
public enum DriverProfile {

    kDefault(DriveType.kCurvatureDrive, ControlType.kGTA),
    kRobot(DriveType.kArcadeDrive, ControlType.kGTA), //when the robot controls itself
    kBH(DriveType.kArcadeDrive, ControlType.kSticks),
    kJG(DriveType.kCurvatureDrive, ControlType.kGTA),
    kTH(DriveType.kCurvatureDrive, ControlType.kGTA),
    kTM(DriveType.kArcadeDrive, ControlType.kSticks);

    private DriveType driveType;
    private ControlType driveControlType;
    
    DriverProfile(DriveType driveType, ControlType driveControlType) {
        this.driveType = driveType;
        this.driveControlType = driveControlType;
    }

    /**
     * Gets the drive type.
     * @return the drive type
     */
    public DriveType getDriveType() {
        return driveType;
    }

    /**
     * Gets the drive control type
     * @return the type of controlling the base with the controllers
     */
    public ControlType getControlType() {
        return driveControlType;
    }



}