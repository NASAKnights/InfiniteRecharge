package xyz.nasaknights.infiniterecharge;

import edu.wpi.first.wpilibj.SPI;
import xyz.nasaknights.infiniterecharge.util.controllers.DriverProfile;

public final class Constants
{
    // Controller Constants
    public static final short DRIVER_ID = 0;
    public static final short OPERATOR_ID = 1;

    // Drive Motor Constants
    public static final int LEFT_MASTER = 1;
    public static final int LEFT_FRONT = 2;
    public static final int LEFT_REAR = 3;
    public static final int RIGHT_MASTER = 4;
    public static final int RIGHT_FRONT = 5;
    public static final int RIGHT_REAR = 6;

    public static final int PCM_ID = 40;

    public static final SPI.Port IMU_PORT = SPI.Port.kMXP;


    // Shooter Constants
    /**
     * The CAN ID for the left shooter motor.
     */
    public static final short LEFT_SPARK_MAX = 6;
    /**
     * The CAN ID for the right shooter motor.
     */
    public static final short RIGHT_SPARK_MAX = 7;
    //
    //    // intake Constants
    //    public static final short INTAKE_VICTOR = 8;
    //
    //    //Indexer Constants
    //    public static final short SECOND_INTAKE_VICTOR = 9;
    //    public static final short FIRST_BELT = 10;
    //    public static final short SECOND_BELT = 11;
    //
    //    //Climber Constants
    //    public static final short LEFT_CLIMB = 12;
    //    public static final short RIGHT_CLIMB = 13;
    //    public static final short CLIMB_WHEELS = 14;

    //Drive Pneumatics Constants
    public static final short SINGLE_DRIVE_GEAR_CHANNEL = 0;
    public static final short FORWARD_POWER_TAKEOFF_CHANNEL = 1;
    public static final short REVERSE_POWER_TAKEOFF_CHANNEL = 2;
    public static final short SINGLE_CLIMB_GEAR_SHIFT_CHANNEL = 3;
    public static final short SINGLE_HOOD_CHANNEL = 4;
    public static final short SINGLE_CONTROL_PANEL_CHANNEL = 5;
    public static final short FORWARD_INTAKE_CHANNEL = 6;
    public static final short REVERSE_INTAKE_CHANNEL = 7;

    // Driver Constants
    public static final DriverProfile CURRENT_DRIVER_PROFILE = DriverProfile.DEFAULT;


}