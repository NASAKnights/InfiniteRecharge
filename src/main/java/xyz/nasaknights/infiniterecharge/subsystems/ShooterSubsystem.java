package xyz.nasaknights.infiniterecharge.subsystems;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import xyz.nasaknights.infiniterecharge.Constants;
import xyz.nasaknights.infiniterecharge.util.control.motors.Lazy_SparkMax;

public class ShooterSubsystem extends SubsystemBase
{
    Lazy_SparkMax left, right;

    SpeedControllerGroup shooterMotors;

    public ShooterSubsystem()
    {
        initMotors();
    }

    private void initMotors()
    {
        // TODO Re-enable once these are on the robot and this mechanism is installed
        left = new Lazy_SparkMax(Constants.LEFT_SHOOTER_SPARK_MAX, MotorType.kBrushless); // instantiate the left NEO
        right = new Lazy_SparkMax(Constants.RIGHT_SHOOTER_SPARK_MAX, MotorType.kBrushless); // instantiate the right NEO
        right.setInverted(true); // set the right NEO inverted
        shooterMotors = new SpeedControllerGroup(left, right); // instantiates a new SpeedControllerGroup with the NEOs
    }

    public void set(double power)
    {
        shooterMotors.set(power); // sets the power of the shooter
    }

    public double get()
    {
         return shooterMotors.get();
    }

}
