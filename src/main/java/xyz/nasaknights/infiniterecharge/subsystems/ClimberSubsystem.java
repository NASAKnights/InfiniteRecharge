package xyz.nasaknights.infiniterecharge.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import xyz.nasaknights.infiniterecharge.Constants;

public class ClimberSubsystem extends SubsystemBase
{
    Solenoid climberArm;

    public ClimberSubsystem()
    {
        climberArm = new Solenoid(Constants.PCM_ID, Constants.CLIMB_WINCH_CHANNEL);
    }

    public boolean isClimbArmExtended()
    {
        return climberArm.get();
    }

    public void setClimbArmExtended(boolean extended)
    {
        climberArm.set(extended);
    }
}
