package xyz.nasaknights.infiniterecharge;

import edu.wpi.first.wpilibj.SPI;
import xyz.nasaknights.infiniterecharge.util.controllers.DriverProfile;

public final class Constants
{
    ///////////////////////////////////////////////////////////////////////////////
    //                           Motor Constants                                 //
    //    This section contains all CAN (or PWM) IDs for motors on the robot.    //
    ///////////////////////////////////////////////////////////////////////////////

    // Shooter Subsystem
    public static final short LEFT_SHOOTER_TALON_FX = 7;
    public static final short RIGHT_SHOOTER_TALON_FX = 8;

    // Queuer Subsystem
    public static final short RIGHT_BELT_VICTOR_ID = 11;
    public static final short LEFT_BELT_VICTOR_ID = 12;

    // Queuer Subsystem - Intake: for clarification, this moves the ball from the queuer into the shooter
    public static final short SHOOTER_FEEDER_VICTOR = 10;

    // Drivetrain
    public static final int LEFT_MASTER = 1;
    public static final int LEFT_FRONT = 2;
    public static final int LEFT_REAR = 3;
    public static final int RIGHT_MASTER = 4;
    public static final int RIGHT_FRONT = 5;
    public static final int RIGHT_REAR = 6;

    // Drivetrain Subsystem - Servos: for clarification, these motors either disable or enable drivetrain neutrality; disabled a/o Feb. 22, 2020
    public static final int LEFT_DRIVETRAIN_NEUTRAL_SERVO_PWM_ID = 1;
    public static final int RIGHT_DRIVETRAIN_NEUTRAL_SERVO_PWM_ID = 0;

    // Intake Subsystem
    public static final int INTAKE_VICTOR = 9;


    ///////////////////////////////////////////////////////////////////////////////
    //                         Pneumatic Constants                               //
    // This section contains all CAN IDs for pneumatic components on the robot.  //
    ///////////////////////////////////////////////////////////////////////////////

    // Pneumatic Control Module
    public static final short PCM_ID = 40;

    // Intake Subsystem PCM IDs
    public static final short INTAKE_FORWARD_CHANNEL = 3;
    public static final short INTAKE_REVERSE_CHANNEL = 4;

    public static final short HOOD_FORWARD_CHANNEL = 5;
    public static final short HOOD_REVERSE_CHANNEL = 2;

    // Drivetrain Subsystem PCM IDs
    public static final short DRIVETRAIN_GEAR_SHIFTER_CHANNEL = 0;
    public static final short DRIVETRAIN_POWER_TAKEOFF_CHANNEL = 1;

    // Climb Subsystem PCM IDs
    public static final short CLIMB_WINCH_CHANNEL = 7;

    ///////////////////////////////////////////////////////////////////////////////
    //                       Miscellaneous Constants                             //
    //  This sections includes all constants that were not already encompassed.  //
    ///////////////////////////////////////////////////////////////////////////////

    // Controller ID Constants
    public static final short DRIVER_ID = 0;
    public static final short OPERATOR_ID = 1;

    // Driver Profile
    public static final DriverProfile CURRENT_DRIVER_PROFILE = DriverProfile.MK;

    // CAN ID for Power Distribution Panel
    public static final int PDP_ID = 0;

    // SPI Port for NavX
    public static final SPI.Port IMU_PORT = SPI.Port.kMXP;


}