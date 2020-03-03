package xyz.nasaknights.infiniterecharge.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import xyz.nasaknights.infiniterecharge.Constants;

public class ClimberSubsystem extends SubsystemBase
{
    Solenoid winch;

    public ClimberSubsystem()
    {
        winch = new Solenoid(Constants.CLIMB_WINCH_CHANNEL);
    }

    public void setWinchExtended(boolean extended)
    {
        winch.set(extended);
    }

    public boolean getWinchExtended()
    {
        return winch.get();
    }
}
