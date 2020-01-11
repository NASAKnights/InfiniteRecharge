package xyz.nasaknights.infiniterecharge.util.control.motors;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

public class Lazy_TalonFX extends TalonFX
{
    private transient TalonFXControlMode currentMode;
    private transient double value;

    /**
     * Constructor
     *
     * @param deviceNumber [0,62]
     */
    public Lazy_TalonFX(int deviceNumber)
    {
        super(deviceNumber);
    }

    @Override
    public void set(TalonFXControlMode mode, double value)
    {
        if (mode != currentMode || this.value != value)
        {
            super.set(mode, value);
            this.currentMode = mode;
            this.value = value;
        }
    }
}
