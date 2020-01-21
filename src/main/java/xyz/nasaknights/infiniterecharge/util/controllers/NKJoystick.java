package xyz.nasaknights.infiniterecharge.util.controllers;

import edu.wpi.first.wpilibj.Joystick;

public class NKJoystick extends Joystick
{
    private DriverProfile profile;

    public NKJoystick(int port, DriverProfile profile)
    {
        super(port);
        this.profile = profile;
    }

    public boolean useSquaredInputs()
    {
        return profile.doesWantSquaredInputs();
    }
}
