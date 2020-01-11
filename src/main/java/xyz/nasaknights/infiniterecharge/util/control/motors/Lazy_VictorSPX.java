package xyz.nasaknights.infiniterecharge.util.control.motors;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class Lazy_VictorSPX extends VictorSPX
{
    private transient ControlMode currentMode;
    private transient double value;

    /**
     * Constructor
     *
     * @param deviceNumber [0,62]
     */
    public Lazy_VictorSPX(int deviceNumber)
    {
        super(deviceNumber);
    }


    @Override
    public void set(ControlMode mode, double value)
    {
        if (mode != currentMode || this.value != value)
        {
            super.set(mode, value);
            this.currentMode = mode;
            this.value = value;
        }
    }
}
