package xyz.nasaknights.infiniterecharge.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import xyz.nasaknights.infiniterecharge.Constants;
import xyz.nasaknights.infiniterecharge.util.control.motors.Lazy_VictorSPX;
import xyz.nasaknights.infiniterecharge.util.control.pneumatics.Lazy_DoubleSolenoid;

public class IntakeSubsystem extends SubsystemBase
{
    private static final DoubleSolenoid.Value DEPLOYED_INTAKE = DoubleSolenoid.Value.kReverse;
    private static final DoubleSolenoid.Value RETRACTED_INTAKE = DoubleSolenoid.Value.kForward;
    Lazy_VictorSPX intake;
    Lazy_DoubleSolenoid doubleSolenoid;

    public IntakeSubsystem()
    {
        intake = new Lazy_VictorSPX(Constants.INTAKE_VICTOR);
        intake.setInverted(true);
        doubleSolenoid = new Lazy_DoubleSolenoid(Constants.PCM_ID, Constants.INTAKE_FORWARD_CHANNEL, Constants.INTAKE_REVERSE_CHANNEL);
    }

    public void setIntakePower(double power)
    {
        intake.set(ControlMode.PercentOutput, power);
    }

    public boolean isIntakeExtended()
    {
        return doubleSolenoid.get() == DEPLOYED_INTAKE;
    }

    public void setIntakeExtended(boolean extended)
    {
        doubleSolenoid.set(extended ? DEPLOYED_INTAKE : RETRACTED_INTAKE);
    }

    @Override
    public Command getDefaultCommand()
    {
        return new CommandBase()
        {
        };
    }

    public void toggleIntakeExtended()
    {
        doubleSolenoid.set((doubleSolenoid.get() == DoubleSolenoid.Value.kForward) ? DoubleSolenoid.Value.kReverse : DoubleSolenoid.Value.kForward);
    }
}
