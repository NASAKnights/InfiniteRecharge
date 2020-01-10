package xyz.nasaknights.infiniterecharge;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import xyz.nasaknights.infiniterecharge.util.driver.DriverProfile;

public class Robot extends TimedRobot {

    private RobotContainer robotContainer;

    private DriverProfile driverProfile = DriverProfile.kRobot;

    @Override
    public void robotInit() {
        robotContainer = new RobotContainer();
        RobotContainer.setProfile(driverProfile);
    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
    }

    @Override
    public void disabledInit() {
    }

    @Override
    public void disabledPeriodic() {
    }

    @Override
    public void autonomousInit() {
    }

    @Override
    public void autonomousPeriodic() {
    }

    @Override
    public void teleopInit() {
    }

    @Override
    public void teleopPeriodic() {
    }

    @Override
    public void testInit() {
        // Cancels all running commands at the start of test mode.
        CommandScheduler.getInstance().cancelAll();
    }

    @Override
    public void testPeriodic() {
    }
}
