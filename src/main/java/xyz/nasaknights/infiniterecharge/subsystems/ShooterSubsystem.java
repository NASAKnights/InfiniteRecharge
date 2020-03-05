package xyz.nasaknights.infiniterecharge.subsystems;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import xyz.nasaknights.infiniterecharge.Constants;
import xyz.nasaknights.infiniterecharge.util.control.pneumatics.Lazy_DoubleSolenoid;

public class ShooterSubsystem extends SubsystemBase
{
    Lazy_TalonFX left, right;

    private Lazy_DoubleSolenoid hoodShifter;

    private int targetRPM = 0; // max ~19800

    public ShooterSubsystem()
    {
        initMotors();
    }

    private void initMotors()
    {
        double kF = .0547;
        double kP = .0;
        double kI = 0;
        double kD = 0;

        // TODO Re-enable once these are on the robot and this mechanism is installed
        left = new Lazy_TalonFX(Constants.LEFT_SHOOTER_TALON_FX); // instantiate the left

        left.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);

        left.setInverted(true);
        left.config_kF(0, kF);
        left.config_kP(0, kP);
        left.config_kI(0, kI);
        left.config_kD(0, kD);

        right = new Lazy_TalonFX(Constants.RIGHT_SHOOTER_TALON_FX); // instantiate the right

        right.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);

        right.config_kF(0, kF);
        right.config_kP(0, kP);
        right.config_kI(0, kI);
        right.config_kD(0, kD);

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

    public boolean isShooterUpToSpeed()
    {
        System.out.println("Left: " + left.getSelectedSensorVelocity() + "; Right: " + right.getSelectedSensorVelocity() + "; Avg: " + ((left.getSelectedSensorVelocity() + right.getSelectedSensorVelocity()) / 2) + "; abs: " + Math.abs(((left.getSelectedSensorVelocity() + right.getSelectedSensorVelocity()) / 2) - targetRPM));
        return Math.abs(((left.getSelectedSensorVelocity() + right.getSelectedSensorVelocity()) / 2) - targetRPM) < 550;
    }

    public void setTargetShooterRPM(int rpm)
    {
        this.targetRPM = rpm;
        left.set(ControlMode.Velocity, rpm);
        right.set(ControlMode.Velocity, rpm);
    }
}
