package xyz.nasaknights.infiniterecharge.util.control.motors;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

/**
 * A lazy implementation of the WPI_VictorSPX class to reduce CAN overhead and add a default control mode
 */
public class Lazy_WPI_VictorSPX extends WPI_VictorSPX {

    private ControlMode defaultControlMode = ControlMode.PercentOutput;
    private ControlMode lastControlMode;
    private double lastPower;

    /**
     * <p>
     * The Lazy_VictorSPX constructor makes a new VictorSPX with added abilities to
     * reduce CAN overhead.
     * </p>
     * 
     * <p>
     * If the default control mode needs to be something other than PercentOutput,
     * you can set it using the {@code setDefaultControlMode(ControlMode defaultControlMode)} method.
     * </p>
     * 
     * @param deviceNumber CAN address
     */
    public Lazy_WPI_VictorSPX(int deviceNumber) {
        super(deviceNumber);
    }

    public ControlMode getDefaultControlMode() {
        return defaultControlMode;
    }

    public void setDefaultControlMode(ControlMode defaultControlMode) {
        this.defaultControlMode = defaultControlMode;
    }

    /**
     * <p>
     * This {@code set()} method reduces CAN overhead by only set the motor controller
     * if the ControlMode or power is different than the last one.
     * </p>
     * 
     * <p>
     * All other {@code set()} methods for this class run into this method.
     * </p>
     */
    @Override
    public void set(ControlMode mode, double power) {
        if (mode != lastControlMode || power != lastPower) {
            lastControlMode = mode;
            lastPower = power;
            super.set(mode, power);
        }
    
    }

    @Override
    public void set(double power) {
        set(defaultControlMode, power);
    }

}