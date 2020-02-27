package xyz.nasaknights.infiniterecharge.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import xyz.nasaknights.infiniterecharge.Constants;
import xyz.nasaknights.infiniterecharge.util.control.motors.Lazy_TalonFX;
import xyz.nasaknights.infiniterecharge.util.control.pneumatics.Lazy_DoubleSolenoid;

public class ShooterSubsystem extends SubsystemBase
{
    Lazy_TalonFX left, right;

    private Lazy_DoubleSolenoid hoodShifter;

    public ShooterSubsystem()
    {
        initMotors();
    }

    private void initMotors()
    {
        // TODO Re-enable once these are on the robot and this mechanism is installed
        left = new Lazy_TalonFX(Constants.LEFT_SHOOTER_TALON_FX); // instantiate the left
        right = new Lazy_TalonFX(Constants.RIGHT_SHOOTER_TALON_FX); // instantiate the right
        hoodShifter = new Lazy_DoubleSolenoid(Constants.PCM_ID, Constants.HOOD_FORWARD_CHANNEL, Constants.HOOD_REVERSE_CHANNEL);
    }

    public void set(double power)
    {
        left.set(ControlMode.PercentOutput, -power);
        right.set(ControlMode.PercentOutput, power);
    }

    public void setHoodExtended(boolean extended)
    {
        hoodShifter.set(extended ? DoubleSolenoid.Value.kForward : DoubleSolenoid.Value.kReverse);
    }

    public boolean getHoodExtended()
    {
        return hoodShifter.get() == DoubleSolenoid.Value.kForward;
    }

    public void toggleHoodExtended()
    {
        hoodShifter.set((hoodShifter.get() == DoubleSolenoid.Value.kForward) ? DoubleSolenoid.Value.kReverse : DoubleSolenoid.Value.kForward);
    }

//    public boolean isShooterUpToSpeed()
//    {
//        return left.getActiveTrajectoryVelocity()
//    }
}
