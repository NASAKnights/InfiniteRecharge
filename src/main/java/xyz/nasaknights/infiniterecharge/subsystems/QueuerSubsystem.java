package xyz.nasaknights.infiniterecharge.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import xyz.nasaknights.infiniterecharge.Constants;
import xyz.nasaknights.infiniterecharge.util.control.motors.Lazy_VictorSPX;

public class QueuerSubsystem extends SubsystemBase
{
    private Lazy_VictorSPX leftBelt, rightBelt, intake;

    public QueuerSubsystem()
    {
        leftBelt = new Lazy_VictorSPX(Constants.LEFT_BELT_VICTOR_ID);
        rightBelt = new Lazy_VictorSPX(Constants.RIGHT_BELT_VICTOR_ID);

        intake = new Lazy_VictorSPX(Constants.QUEUER_INTAKE_VICTOR);
    }

    public void setBeltPower(double power)
    {
        leftBelt.set(ControlMode.PercentOutput, power);
        rightBelt.set(ControlMode.PercentOutput, -power);
    }

    public void setQueuerIntakePower(double power)
    {
        intake.set(ControlMode.PercentOutput, power);
    }
}
