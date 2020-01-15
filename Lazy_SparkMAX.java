package xyz.nasaknights.infiniterecharge.util.control.motors;

import com.revrobotics.CANSparkMax;

public class Lazy_SparkMAX extends CANSparkMax
{

    private transient double lastValue;

    Lazy_SparkMAX(int channel, MotorType type)
    {
        super(channel, type);
    }

    /**
     * @return the lastValue
     */
    public double getLastValue()
    {
        return lastValue;
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