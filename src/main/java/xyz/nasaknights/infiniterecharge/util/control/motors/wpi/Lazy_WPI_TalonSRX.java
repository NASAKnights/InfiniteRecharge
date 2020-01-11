package xyz.nasaknights.infiniterecharge.util.control.motors.wpi;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

/**
 * A lazy implementation of the WPI_TalonSRX class to reduce CAN overhead and add a default control mode.
 */
public class Lazy_WPI_TalonSRX extends WPI_TalonSRX
{
    private ControlMode lastControlMode;
    private double lastPower;

    public Lazy_WPI_TalonSRX(int deviceNumber)
    {
        super(deviceNumber);
    }

    @Override
    public void set(ControlMode controlMode, double power)
    {
        if (controlMode != lastControlMode || power != lastPower)
        {
            lastControlMode = controlMode;
            lastPower = power;
            super.set(controlMode, power);
        }
    }
}