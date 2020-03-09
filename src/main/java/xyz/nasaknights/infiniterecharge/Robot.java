package xyz.nasaknights.infiniterecharge;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import xyz.nasaknights.infiniterecharge.commands.drivetrain.DriveCommand;
import xyz.nasaknights.infiniterecharge.commands.drivetrain.TimedArcadeDriveCommand;
import xyz.nasaknights.infiniterecharge.commands.shooter.ShootCommand;
import xyz.nasaknights.infiniterecharge.util.controllers.DriverProfile;
import xyz.nasaknights.infiniterecharge.util.vision.VisionClient;

import java.util.concurrent.TimeUnit;

public class Robot extends TimedRobot
{
    private final ShootCommand autonomousShootCommand = new ShootCommand(true);
    private final TimedArcadeDriveCommand autonomousDriveCommand = new TimedArcadeDriveCommand(TimeUnit.SECONDS.toMillis(2), .7);
    VisionClient vclnt = RobotContainer.getVisionClient();

    private static DriveCommand driveCommand = new DriveCommand();
    private long autonomousStart;
    private boolean driveStarted = false; // autonomous boolean to check if drive command has started

    @Override
    public void robotInit()
    {
        //
        //          NOTHING BEFORE HERE
        //

        RobotContainer.setProfile(Constants.CURRENT_DRIVER_PROFILE);
        RobotContainer.getDrivetrain().setMaxSpeeds(Constants.CURRENT_DRIVER_PROFILE.getMaxThrottle(), Constants.CURRENT_DRIVER_PROFILE.getMaxTurn());
        driveCommand.schedule(); // schedules a Drive Command which cannot be interruptible

        driveStarted = false;

        RobotContainer.getDrivetrain().setDrivetrainNeutral(false);
        RobotContainer.getDrivetrain().setHighGear(false);
    }

    @Override
    public void robotPeriodic()
    {
        CommandScheduler.getInstance().run();

        RobotContainer.getDrivetrain().runPeriodicServoTask();

        SmartDashboard.putBoolean("Vision Control Active", RobotContainer.getProfile() == DriverProfile.AUTONOMOUS);
//        SmartDashboard.putNumber("Turn Controller Proportional", RobotContainer.getDrivetrain().getTurnP());
//        SmartDashboard.putNumber("Turn Controller Integral", RobotContainer.getDrivetrain().getTurnI());
//        SmartDashboard.putNumber("Turn Controller Derivative", RobotContainer.getDrivetrain().getTurnD());
//        RobotContainer.getDrivetrain().setTurnP(SmartDashboard.getNumber("Turn Controller Proportional", RobotContainer.getDrivetrain().getTurnP()));
//        RobotContainer.getDrivetrain().setTurnI(SmartDashboard.getNumber("Turn Controller Integral", RobotContainer.getDrivetrain().getTurnI()));
//        RobotContainer.getDrivetrain().setTurnD(SmartDashboard.getNumber("Turn Controller Derivative", RobotContainer.getDrivetrain().getTurnD()));
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
//        HelixEvents.getInstance().startLogging();
//        RobotContainer.getDrivetrain().resetEncoders();
//        RobotContainer.getIMU().reset();
//        new PathFollowCommand(new ThreeByThree()).schedule();

        autonomousStart = System.currentTimeMillis();

        autonomousShootCommand.schedule();
    }

    @Override
    public void autonomousPeriodic()
    {
        if ((System.currentTimeMillis() > (autonomousStart + TimeUnit.SECONDS.toMillis(8))) && autonomousShootCommand.isScheduled() && !autonomousDriveCommand.isScheduled() && !driveStarted)
        {
            autonomousShootCommand.end(true);
            driveStarted = true;
            autonomousDriveCommand.schedule();
        }
//        else if(autonomousDriveCommand.isScheduled())
    }

    @Override
    public void teleopInit()
    {
        RobotContainer.getIntake().setIntakeExtended(false);
        RobotContainer.getDrivetrain().getDefaultCommand().schedule();
        RobotContainer.getIntake().getDefaultCommand().schedule();

        RobotContainer.getQueuerSubsystem().setBeltPower(0);

        RobotContainer.getShooterSubsystem().setHoodExtended(false);
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
        RobotContainer.getDrivetrain().setDrivetrainNeutral(false);
    }
}
