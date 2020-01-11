package xyz.nasaknights.infiniterecharge.util.control.motors.wpi;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

/**
 * A lazy implementation of the WPI_VictorSPX class to reduce CAN overhead and add a default control mode
 */
public class Lazy_WPI_VictorSPX extends WPI_VictorSPX
{
    private ControlMode lastControlMode;
    private double lastPower;

    public Lazy_WPI_VictorSPX(int deviceNumber)
    {
        super(deviceNumber);
    }

    @Override
    public void set(ControlMode mode, double power)
    {
        if (mode != lastControlMode || power != lastPower)
        {
            lastControlMode = mode;
            lastPower = power;
            super.set(mode, power);
        }
    }
}