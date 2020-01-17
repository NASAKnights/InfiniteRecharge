package xyz.nasaknights.infiniterecharge.util.control.motors.wpi;

import com.revrobotics.CANSparkMax;

/**
 * A lazy implementation of the CANSparkMax class to reduce CAN overhead.
 */
public class Lazy_SparkMax extends CANSparkMax
{
    private transient double lastValue;

    /**
     * Create a new SPARK MAX Controller
     *
     * @param deviceID The device ID.
     * @param type     The motor type connected to the controller. Brushless motors
     *                 must be connected to their matching color and the hall sensor
     *                 plugged in. Brushed motors must be connected to the Red and
     */
    public Lazy_SparkMax(int deviceID, MotorType type)
    {
        super(deviceID, type);
    }

    @Override
    public void set(double value)
    {
        if (value != lastValue)
        {
            super.set(value);
        }
    }
}
