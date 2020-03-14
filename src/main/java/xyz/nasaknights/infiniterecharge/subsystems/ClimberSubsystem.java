package xyz.nasaknights.infiniterecharge.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.EncoderType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import xyz.nasaknights.infiniterecharge.Constants;
import xyz.nasaknights.infiniterecharge.util.control.motors.Lazy_SparkMax;

import static com.revrobotics.CANSparkMaxLowLevel.MotorType.*;

public class ClimberSubsystem extends SubsystemBase
{
    Solenoid climberArm;

    Lazy_SparkMax leftClimb, rightClimb;

    CANEncoder leftClimbEncoder, rightClimbEncoder;

    public ClimberSubsystem()
    {
        climberArm = new Solenoid(Constants.PCM_ID, Constants.CLIMB_WINCH_CHANNEL);
        initMotors();
        initSensors();
    }

    public boolean isClimbArmExtended()
    {
        return climberArm.get();
    }

    public void setClimbArmExtended(boolean extended)
    {
        climberArm.set(extended);
    }

    private void initMotors()
    {
        leftClimb = new Lazy_SparkMax(Constants.LEFT_CLIMB_SPARK_MAX, kBrushless);
        rightClimb = new Lazy_SparkMax(Constants.RIGHT_CLIMB_SPARK_MAX, kBrushless);
    }

    private void initSensors()
    {
        leftClimbEncoder = new CANEncoder(leftClimb, EncoderType.kQuadrature, 4096);
        rightClimbEncoder = new CANEncoder(rightClimb, EncoderType.kQuadrature, 4096);
    }

    public void resetEncoderPosition()
    {
        leftClimbEncoder.setPosition(0);
        rightClimbEncoder.setPosition(0);
    }

    public double getLeftClimbEncoderPosition()
    {
        return leftClimbEncoder.getPosition();
    }

    public double getRightClimbEncoderPosition()
    {
        return rightClimbEncoder.getPosition();
    }

    public double getClimbEncoderPositions()
    {
        return (getLeftClimbEncoderPosition() + getRightClimbEncoderPosition()) / 2;
    }
}
