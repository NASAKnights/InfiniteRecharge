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

        left = new Lazy_TalonFX(Constants.LEFT_SHOOTER_TALON_FX); // instantiate the left

        left.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);

        left.setInverted(true);
        left.config_kF(0, kF);
        left.config_kP(0, kP);
        left.config_kI(0, kI);
        left.config_kD(0, kD);

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

    public void set(double power)
    {
        left.set(ControlMode.PercentOutput, power);
//        right.set(ControlMode.PercentOutput, -power);
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
//        System.out.println("Left: " + left.getSelectedSensorVelocity() + "; Right: " + right.getSelectedSensorVelocity() + "; Avg: " + ((left.getSelectedSensorVelocity() + right.getSelectedSensorVelocity()) / 2) + "; abs: " + Math.abs(((left.getSelectedSensorVelocity() + right.getSelectedSensorVelocity()) / 2) - targetRPM));
//        return Math.abs(((left.getSelectedSensorVelocity() + right.getSelectedSensorVelocity()) / 2) - targetRPM) < 750; // Checks if difference between average shooter encoder ticks and target is under 750
//        return Math.abs(left.getSelectedSensorVelocity() - targetRPM) < 750; TODO Disabled due to mechanical malfunctions, replace when possible; will need to be tuned for one motor (if situation is still applicable)
        return true;
    }

    public void setTargetShooterRPM(int rpm)
    {
        this.targetRPM = rpm;
        left.set(ControlMode.Velocity, rpm);
//        right.set(ControlMode.Velocity, rpm);
    }
}
