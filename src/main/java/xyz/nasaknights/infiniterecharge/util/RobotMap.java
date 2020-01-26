package xyz.nasaknights.infiniterecharge.util;

import edu.wpi.first.wpilibj.SPI;

/**
 * @deprecated This class is outdated by the Constants class.
 */
@Deprecated
public class RobotMap
{
    //NavX IMU
    public static final SPI.Port IMU_PORT = SPI.Port.kMXP;

    //drivetrain
    public static final short LEFT_MASTER = 0;
    public static final short LEFT_FRONT = 0;
    public static final short LEFT_REAR = 0;
    public static final short RIGHT_MASTER = 0;
    public static final short RIGHT_FRONT = 0;
    public static final short RIGHT_REAR = 0;

    public static final int LEFT_SHOOTER_SPARK = 0;
    public static final int RIGHT_SHOOTER_SPARK = 0;
}