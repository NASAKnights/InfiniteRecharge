package xyz.nasaknights.infiniterecharge.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import xyz.nasaknights.infiniterecharge.Constants;
import xyz.nasaknights.infiniterecharge.util.control.motors.Lazy_TalonFX;
import xyz.nasaknights.infiniterecharge.util.control.pneumatics.Lazy_DoubleSolenoid;

public class ShooterSubsystem extends SubsystemBase
{
    private static int targetRPM = 0; // max ~19800

    Lazy_TalonFX left, right;

    private Lazy_DoubleSolenoid hoodShifter;

    // declare some PIDF controller variables
    private double currentP = Integer.MIN_VALUE, currentI = Integer.MIN_VALUE, currentD = Integer.MIN_VALUE, currentF = Integer.MIN_VALUE;
    private Double currentPD;

    public ShooterSubsystem()
    {
        initMotors();
    }

    private void initMotors()
    {

        double kP = 0.0;
        double kI = 0;
        double kD = 0;
        double kF = 0.0547;

        left = new Lazy_TalonFX(Constants.LEFT_SHOOTER_TALON_FX); // instantiate the left

        left.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);

        left.setInverted(true);

        setFlywheelProportional(kP);
        setFlywheelIntegral(kI);
        setFlywheelDerivative(kD);
        setFlywheelFeedForward(kF);

        //        right = new Lazy_TalonFX(Constants.RIGHT_SHOOTER_TALON_FX); // instantiate the right
        //
        //        right.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
        //
        //        right.config_kF(0, kF);
        //        right.config_kP(0, kP);
        //        right.config_kI(0, kI);
        //        right.config_kD(0, kD);

        hoodShifter = new Lazy_DoubleSolenoid(Constants.PCM_ID, Constants.HOOD_FORWARD_CHANNEL, Constants.HOOD_REVERSE_CHANNEL);
    }

    /**
     * Gets the last unique proportional value to which the flywheel was set.
     *
     * @return the current flywheel proportional value
     */
    public double getFlywheelProportional()
    {
        return currentP;
    }

    /**
     * Sets the Proportional value on the flywheel.
     *
     * @param kP the proportional value
     */
    public void setFlywheelProportional(double kP)
    {
        if (kP != currentP)
        {
            left.config_kP(0, kP);
            currentP = kP;
        }
    }

    /**
     * Gets the last unique integral value to which the flywheel was set.
     *
     * @return the current flywheel integral value
     */
    public double getFlywheelIntegral()
    {
        return currentI;
    }

    /**
     * Sets the Integral value on the flywheel.
     *
     * @param kI the integral value
     */
    public void setFlywheelIntegral(double kI)
    {
        if (kI != currentI)
        {
            left.config_kI(0, kI);
            currentI = kI;
        }

    }

    /**
     * Gets the last unique derivative value to which the flywheel was set.
     *
     * @return the current flywheel derivative value
     */
    public double getFlywheelDerivative()
    {
        return currentD;
    }

    /**
     * Sets the Derivative value on the flywheel.
     *
     * @param kD the derivative value
     */
    public void setFlywheelDerivative(double kD)
    {
        if (kD != currentD)
        {
            left.config_kD(0, kD);
            currentD = kD;
        }
    }

    /**
     * Gets the last unique feed forward value to which the flywheel was set.
     *
     * @return the current flywheel feed forward value
     */
    public double getFlywheelFeedForward()
    {
        return currentF;
    }

    /**
     * Sets the Feed Forward value on the flywheel.
     *
     * @param kF the feed forward value
     */
    public void setFlywheelFeedForward(double kF)
    {
        if (kF != currentF)
        {
            left.config_kF(0, kF);
            currentF = kF;
        }
    }

    public void set(double power)
    {
        left.set(ControlMode.PercentOutput, power);
        //        right.set(ControlMode.PercentOutput, -power);
    }

    public boolean getHoodExtended()
    {
        return hoodShifter.get() == DoubleSolenoid.Value.kForward;
    }

    public void setHoodExtended(boolean extended)
    {
        hoodShifter.set(extended ? DoubleSolenoid.Value.kForward : DoubleSolenoid.Value.kReverse);
    }

    public void toggleHoodExtended()
    {
        hoodShifter.set((hoodShifter.get() == DoubleSolenoid.Value.kForward) ? DoubleSolenoid.Value.kReverse : DoubleSolenoid.Value.kForward);
    }

    public boolean isShooterUpToSpeed()
    {
        //        System.out.println("Left: " + left.getSelectedSensorVelocity() + "; Right: " + right.getSelectedSensorVelocity() + "; Avg: " + ((left.getSelectedSensorVelocity() + right.getSelectedSensorVelocity()) / 2) + "; abs: " + Math.abs(((left.getSelectedSensorVelocity() + right.getSelectedSensorVelocity()) / 2) - targetRPM));
        //        return Math.abs(((left.getSelectedSensorVelocity() + right.getSelectedSensorVelocity()) / 2) - targetRPM) < 750; // Checks if difference between average shooter encoder ticks and target is under 750
        //        return Math.abs(left.getSelectedSensorVelocity() - targetRPM) < 750; // TODO Disabled due to mechanical malfunctions, replace when possible; will need to be tuned for one motor (if situation is still applicable)
        return true;
    }

    public void setTargetShooterRPM(int rpm)
    {
        this.targetRPM = rpm;
        //        left.set(ControlMode.Velocity, rpm);
        //        right.set(ControlMode.Velocity, rpm);
    }

    public void goToTargetRPM()
    {
        left.set(ControlMode.Velocity, targetRPM);
    }

    public int getTargetRPM()
    {
        return targetRPM;
    }

    public enum ShooterPosition
    {
        WALL_SHOT(false, .77, 14000), AUTO_LINE_SHOT(true, .87, 14500), TRENCH_SHOT(true, .95, 19500), TUNABLE_HIGH_SHOT(true, 0.0, targetRPM), TUNABLE_LOW_SHOT(false, 0.0, targetRPM);

        private boolean shouldHoodExtend;
        private double power;
        private int rpm;

        // TODO Tune RPM and remove power???
        ShooterPosition(boolean shouldHoodExtend, double power, int rpm)
        {
            this.shouldHoodExtend = shouldHoodExtend;
            this.power = power;
            this.rpm = rpm;
        }

        public boolean shouldHoodExtend()
        {
            return shouldHoodExtend;
        }

        public double getPower()
        {
            return power;
        }

        public int getTargetRPM()
        {
            return rpm;
        }
    }

    //    Lazy_SparkMax left, right;
    //
    //    private Lazy_DoubleSolenoid hoodShifter;
    //
    //    private int targetRPM = 0; // max ~19800
    //
    //    public ShooterSubsystem()
    //    {
    //        initMotors();
    //    }
    //
    //    private void initMotors()
    //    {
    //        double kF = .0547;
    //        double kP = .0;
    //        double kI = 0;
    //        double kD = 0;
    //
    //        // TODO Re-enable once these are on the robot and this mechanism is installed
    //        left = new Lazy_SparkMax(Constants.LEFT_SHOOTER_TALON_FX, CANSparkMaxLowLevel.MotorType.kBrushless); // instantiate the left
    //
    //        left.setInverted(true);
    //        left.getPIDController().setFF(kF);
    //        left.getPIDController().setP(kP);
    //        left.getPIDController().setI(kI);
    //        left.getPIDController().setD(kD);
    //
    //        right = new Lazy_SparkMax(Constants.RIGHT_SHOOTER_TALON_FX, CANSparkMaxLowLevel.MotorType.kBrushless); // instantiate the right
    //
    //        right.getPIDController().setFF(kF);
    //        right.getPIDController().setP(kP);
    //        right.getPIDController().setI(kI);
    //        right.getPIDController().setD(kD);
    //
    //        hoodShifter = new Lazy_DoubleSolenoid(Constants.PCM_ID, Constants.HOOD_FORWARD_CHANNEL, Constants.HOOD_REVERSE_CHANNEL);
    //    }
    //
    //    public void set(double power)
    //    {
    //        left.set(-power);
    //        right.set(power);
    //    }
    //
    //    public void setHoodExtended(boolean extended)
    //    {
    //        hoodShifter.set(extended ? DoubleSolenoid.Value.kForward : DoubleSolenoid.Value.kReverse);
    //    }
    //
    //    public boolean getHoodExtended()
    //    {
    //        return hoodShifter.get() == DoubleSolenoid.Value.kForward;
    //    }
    //
    //    public void toggleHoodExtended()
    //    {
    //        hoodShifter.set((hoodShifter.get() == DoubleSolenoid.Value.kForward) ? DoubleSolenoid.Value.kReverse : DoubleSolenoid.Value.kForward);
    //    }
    //
    //    public boolean isShooterUpToSpeed()
    //    {
    //        double leftVel = left.getEncoder().getVelocity();
    //        double rightVel = right.getEncoder().getVelocity();
    //        double avgVel = (leftVel + rightVel) / 2;
    //
    ////        System.out.println("LeftVel: " + leftVel + "; RightVel: " + rightVel + "; AvgVel: " + avgVel);
    //
    ////        return Math.abs(avgVel - targetRPM) < 750; // Checks if difference between average shooter encoder ticks and target is under 750
    //        return true;
    //    }
    //
    //    public void setTargetShooterRPM(int rpm)
    //    {
    //        this.targetRPM = rpm;
    ////        left
    ////        left.set(ControlMode.Velocity, rpm);
    ////        right.set(ControlMode.Velocity, rpm);
    //    }
}
