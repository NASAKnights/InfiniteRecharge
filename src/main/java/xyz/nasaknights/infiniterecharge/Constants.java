package xyz.nasaknights.infiniterecharge;

import xyz.nasaknights.infiniterecharge.util.controllers.DriverProfile;

public final class Constants
{
    // Controller Constants
    public static final short DRIVER_ID = 0;
    public static final short OPERATOR_ID = 1;

    // Drive Motor Constants
    public static final int LEFT_MASTER = 0;
    public static final int LEFT_FRONT = 0;
    public static final int LEFT_REAR = 0;
    public static final int RIGHT_MASTER = 0;
    public static final int RIGHT_FRONT = 0;
    public static final int RIGHT_REAR = 0;

    // Shooter Constants
    /**
     * The CAN ID for the left shooter motor.
     */
    public static final short LEFT_SPARK_MAX = 0;
    /**
     * The CAN ID for the right shooter motor.
     */
    public static final short RIGHT_SPARK_MAX = 0;

    // Driver Constants
    public static final DriverProfile CURRENT_DRIVER_PROFILE = DriverProfile.DEFAULT;
}