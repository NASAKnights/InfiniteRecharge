package xyz.nasaknights.infiniterecharge.util.control.pneumatics;

import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 * A lazy implementation of the DoubleSolenoid class to reduce CAN overhead.
 */
public class Lazy_DoubleSolenoid extends DoubleSolenoid
{
    private Value lastValue;

    /**
     * Creates an instance of a Lazy_DoubleSolenoid
     *
     * @param forwardChannel The forward channel number on the PCM (0..7).
     * @param reverseChannel The reverse channel number on the PCM (0..7).
     */
    public Lazy_DoubleSolenoid(int forwardChannel, int reverseChannel)
    {
        super(forwardChannel, reverseChannel);
    }

    /**
     * Creates an instance of a Lazy_DoubleSolenoid
     *
     * @param moduleNumber   The module number of the solenoid to module
     * @param forwardChannel The forward channel number on the PCM (0..7).
     * @param reverseChannel The reverse channel number on the PCM (0..7).
     */
    public Lazy_DoubleSolenoid(int moduleNumber, int forwardChannel, int reverseChannel)
    {
        super(moduleNumber, forwardChannel, reverseChannel);
    }

    /**
     * Sets the Value of the Lazy_DoubleSolenoid
     *
     * @param value The value to set the Lazy_DoubleSolenoid
     */
    @Override
    public void set(Value value)
    {
        if (lastValue != value)
        {
            super.set(value);
            this.lastValue = value;
        }
    }

}