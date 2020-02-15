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

    // Intake Constants
    public static final int INTAKE_VICTOR = 9;
    public static final int INTAKE_FORWARD_CHANNEL = 3;
    public static final int INTAKE_REVERSE_CHANNEL = 4;

    public static final int PCM_ID = 40;

    public static final DriverProfile CURRENT_DRIVER_PROFILE = DriverProfile.DEFAULT;

}