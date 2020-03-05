package xyz.nasaknights.infiniterecharge.subsystems;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import xyz.nasaknights.infiniterecharge.Constants;
import xyz.nasaknights.infiniterecharge.util.control.motors.Lazy_SparkMax;
import xyz.nasaknights.infiniterecharge.util.control.motors.Lazy_TalonFX;
import xyz.nasaknights.infiniterecharge.util.control.pneumatics.Lazy_DoubleSolenoid;

public class ShooterSubsystem extends SubsystemBase
{
    Lazy_TalonFX left, right;

    SpeedControllerGroup shooterMotors;

    private Lazy_DoubleSolenoid hoodShifter;

    public ShooterSubsystem()
    {
        initMotors();
    }

    private void initMotors()
    {
        // TODO Re-enable once these are on the robot and this mechanism is installed
        left = new Lazy_TalonFX(Constants.LEFT_SHOOTER_SPARK_MAX); // instantiate the left NEO
        right = new Lazy_TalonFX(Constants.RIGHT_SHOOTER_SPARK_MAX); // instantiate the right NEO
        right.setInverted(true); // set the right NEO inverted
        hoodShifter = new Lazy_DoubleSolenoid(Constants.PCM_ID, Constants.HOOD_FORWARD_CHANNEL, Constants.HOOD_REVERSE_CHANNEL);
        right.follow(left);
        //        shooterMotors = new SpeedControllerGroup(left, right); // instantiates a new SpeedControllerGroup with the NEOs
    }

    public void set(double power)
    {
        left.set(TalonFXControlMode.Velocity, power); // sets the power of the shooter
    }

    public void setHoodExtended(boolean extended)
    {
        hoodShifter.set(extended ? DoubleSolenoid.Value.kReverse : DoubleSolenoid.Value.kForward);
    }

    public double get()
    {
        return left.getSensorCollection().getIntegratedSensorVelocity();
    }

    public boolean getHoodExtended()
    {
        return hoodShifter.get() == DoubleSolenoid.Value.kForward;
    }

    public void toggleHoodExtended()
    {
        hoodShifter.set((hoodShifter.get() == DoubleSolenoid.Value.kForward) ? DoubleSolenoid.Value.kReverse : DoubleSolenoid.Value.kForward);
    }
}
