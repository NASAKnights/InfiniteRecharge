package xyz.nasaknights.infiniterecharge.util.control.motors;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

/**
 * A lazy implementation of the WPI_TalonSRX class to reduce CAN overhead and add a default control mode.
 */
public class Lazy_WPI_TalonSRX extends WPI_TalonSRX {

    private ControlMode defaultControlMode = ControlMode.PercentOutput;
    private ControlMode lastControlMode;
    private double lastPower;

    /**
     * <p>
     * The Lazy_TalonSRX constructor makes a new TalonSRX with added abilities to
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
    public Lazy_WPI_TalonSRX(int deviceNumber) {
        super(deviceNumber);
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
    public void set(ControlMode controlMode, double power) {

        if (controlMode != lastControlMode || power != lastPower) {

            lastControlMode = controlMode;
            lastPower = power;
            super.set(controlMode, power);

        }

    }

    public ControlMode getDefaultControlMode() {
        return defaultControlMode;
    }

    public void setDefaultControlMode(ControlMode defaultControlMode) {
        this.defaultControlMode = defaultControlMode;
    }

    @Override
    public void set(double speed) {
        set(defaultControlMode, speed);
    }

}