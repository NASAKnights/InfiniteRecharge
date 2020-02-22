package xyz.nasaknights.infiniterecharge;

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

    // Vision Constants
    public static final String VISION_CLIENT_IP_ADDRESS = "10.1.22.43";
    public static final int VISION_CLIENT_PORT = 6969;

    public static final DriverProfile CURRENT_DRIVER_PROFILE = DriverProfile.DEFAULT;
}