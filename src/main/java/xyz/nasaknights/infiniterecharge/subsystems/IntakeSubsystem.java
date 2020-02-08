package xyz.nasaknights.infiniterecharge.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import xyz.nasaknights.infiniterecharge.Constants;
import xyz.nasaknights.infiniterecharge.commands.intake.AxisIntakeCommand;
import xyz.nasaknights.infiniterecharge.util.control.motors.Lazy_VictorSPX;

public class IntakeSubsystem extends SubsystemBase
{
    private static final DoubleSolenoid.Value DEPLOYED_INTAKE = DoubleSolenoid.Value.kForward;
    private static final DoubleSolenoid.Value RETRACTED_INTAKE = DoubleSolenoid.Value.kReverse;
    Lazy_VictorSPX intake;
    double defaultSpeed;
    DoubleSolenoid doubleSolenoid;

    public IntakeSubsystem()
    {
        initMotor();
        initPneumatics();
    }

    private void initMotor()
    {
        intake = new Lazy_VictorSPX(Constants.INTAKE_VICTOR);
    }

    public void intake()
    {
        intake(defaultSpeed);
    }

    public void intake(double power)
    {
        intake.set(ControlMode.PercentOutput, power);
    }

    private void setDefaultSpeed(double speed)
    {
        defaultSpeed = speed;
    }

    private void initPneumatics()
    {
        doubleSolenoid = new DoubleSolenoid(Constants.PCM_ID, Constants.INTAKE_FORWARD_CHANNEL, Constants.INTAKE_REVERSE_CHANNEL);
    }

    public boolean getIntakeExtended()
    {
        return doubleSolenoid.get() == DEPLOYED_INTAKE;
    }

    public void setIntakeExtended(boolean extended)
    {
        doubleSolenoid.set((extended) ? DEPLOYED_INTAKE : RETRACTED_INTAKE);
    }

    @Override
    public Command getDefaultCommand()
    {
        return new AxisIntakeCommand();
    }
}
