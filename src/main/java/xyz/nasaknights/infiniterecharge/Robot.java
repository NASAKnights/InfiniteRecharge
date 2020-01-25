package xyz.nasaknights.infiniterecharge;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import xyz.nasaknights.infiniterecharge.commands.drivetrain.DriveCommand;
import xyz.nasaknights.infiniterecharge.util.controllers.DriverProfile;

public class Robot extends TimedRobot
{
    private static DriveCommand driveCommand = new DriveCommand();

    private RobotContainer robotContainer;

    private DriverProfile driverProfile = DriverProfile.AUTONOMOUS;

    @Override
    public void robotInit()
    {
        robotContainer = new RobotContainer();
        RobotContainer.setProfile(driverProfile);
        driveCommand.schedule(); // schedules the Drive Command which cannot be interruptible
    }

    @Override
    public void robotPeriodic()
    {
        CommandScheduler.getInstance().run();

        SmartDashboard.putBoolean("Vision Control Active", RobotContainer.getProfile() == DriverProfile.AUTONOMOUS);
        SmartDashboard.putNumberArray("Turn PID variables", RobotContainer.getDrivetrain().getTurnPID());
        RobotContainer.getDrivetrain().setTurnPID(SmartDashboard.getNumberArray("Turn PID variables", RobotContainer.getDrivetrain().getTurnPID()));
    }
    @Override
    public void disabledInit()
    {
    }

    @Override
    public void disabledPeriodic()
    {
    }

    @Override
    public void autonomousInit()
    {
    }

    @Override
    public void autonomousPeriodic()
    {
    }

    @Override
    public void teleopInit()
    {
    }

    @Override
    public void teleopPeriodic()
    {
    }

    @Override
    public void testInit()
    {
        // Cancels all running commands at the start of test mode.
        CommandScheduler.getInstance().cancelAll();
    }

    @Override
    public void testPeriodic()
    {
    }

    public static DriveCommand getDriveCommand()
    {
        return driveCommand;
    }
}
