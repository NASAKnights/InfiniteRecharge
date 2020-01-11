package xyz.nasaknights.infiniterecharge.util.control.motors.wpi;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

/**
 * A lazy implementation of the WPI_TalonFX class to reduce CAN overhead.
 */
public class Lazy_WPI_TalonFX extends WPI_TalonFX
{
    private ControlMode lastControlMode;
    private double lastPower;

    public Lazy_WPI_TalonFX(int deviceNumber)
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