package xyz.nasaknights.infiniterecharge;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import xyz.nasaknights.infiniterecharge.commands.drivetrain.DriveCommand;
import xyz.nasaknights.infiniterecharge.commands.drivetrain.TimedArcadeDriveCommand;
import xyz.nasaknights.infiniterecharge.commands.shooter.ShootCommand;
import xyz.nasaknights.infiniterecharge.util.controllers.DriverProfile;
import xyz.nasaknights.infiniterecharge.util.lighting.IndicatorLightUtil;
import xyz.nasaknights.infiniterecharge.util.vision.VisionClient;

import java.util.concurrent.TimeUnit;

public class Robot extends TimedRobot
{
    private final ShootCommand autonomousShootCommand = new ShootCommand(true);
    private final TimedArcadeDriveCommand autonomousDriveCommand = new TimedArcadeDriveCommand(TimeUnit.SECONDS.toMillis(2), .7);
    VisionClient visionClient;

    private static DriveCommand driveCommand = new DriveCommand();
    private long autonomousStart;
    private boolean driveStarted = false; // autonomous boolean to check if drive command has started

    @Override
    public void robotInit()
    {
        //
        //          NOTHING BEFORE HERE
        //

        visionClient = RobotContainer.getVisionClient();

        RobotContainer.setProfile(Constants.CURRENT_DRIVER_PROFILE);
        RobotContainer.getDrivetrain().setMaxSpeeds(Constants.CURRENT_DRIVER_PROFILE.getMaxThrottle(), Constants.CURRENT_DRIVER_PROFILE.getMaxTurn());

        driveStarted = false;

        RobotContainer.getDrivetrain().setDrivetrainNeutral(false);
        RobotContainer.getDrivetrain().setHighGear(false);
    }

    @Override
    public void robotPeriodic()
    {
        CommandScheduler.getInstance().run();

        //        SmartDashboard.putNumber("Turn Controller Integral", RobotContainer.getDrivetrain().getTurnI());
        //        SmartDashboard.putNumber("Turn Controller Derivative", RobotContainer.getDrivetrain().getTurnD());
        //        RobotContainer.getDrivetrain().setTurnP(SmartDashboard.getNumber("Turn Controller Proportional", RobotContainer.getDrivetrain().getTurnP()));
        //        RobotContainer.getDrivetrain().setTurnI(SmartDashboard.getNumber("Turn Controller Integral", RobotContainer.getDrivetrain().getTurnI()));
        //        RobotContainer.getDrivetrain().setTurnD(SmartDashboard.getNumber("Turn Controller Derivative", RobotContainer.getDrivetrain().getTurnD()));

        //        SmartDashboard.putNumber("Vision Client Distance", Units.metersToFeet(visionClient.getDistance() * 100));
        //        SmartDashboard.putNumber("", visionClient.getAngle());
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
        if (isAutonomousShootCommandDone(8))
        {
            autonomousShootCommand.end(true);
            driveStarted = true;
            autonomousDriveCommand.schedule();
        }
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
        shooterTuning();
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

    private boolean isAutonomousShootCommandDone(long secondsUntilDriveCommand)
    {

        // if number of seconds has elapsed, is shooting, is not driving, and drive has not started
        return (System.currentTimeMillis() > (autonomousStart + TimeUnit.SECONDS.toMillis(secondsUntilDriveCommand))) && autonomousShootCommand.isScheduled() && !autonomousDriveCommand.isScheduled() && !driveStarted;

    }

    private void shooterTuning()
    {

        // rpm setter
        RobotContainer.getShooterSubsystem().setTargetShooterRPM((int) SmartDashboard.getNumber("Shooter RPM", RobotContainer.getShooterSubsystem().getTargetRPM()));

        // shooter proportional setter
        RobotContainer.getShooterSubsystem().setFlywheelProportional(SmartDashboard.getNumber("Shooter Proportional", RobotContainer.getShooterSubsystem().getFlywheelProportional()));

        // shooter integral setter
        RobotContainer.getShooterSubsystem().setFlywheelIntegral(SmartDashboard.getNumber("Shooter Integral", RobotContainer.getShooterSubsystem().getFlywheelIntegral()));

        // shooter derivative setter
        RobotContainer.getShooterSubsystem().setFlywheelDerivative(SmartDashboard.getNumber("Shooter Derivative", RobotContainer.getShooterSubsystem().getFlywheelDerivative()));

        // shooter feed forward setter
        RobotContainer.getShooterSubsystem().setFlywheelFeedForward(SmartDashboard.getNumber("Shooter Feed Forward", RobotContainer.getShooterSubsystem().getFlywheelFeedForward()));
    }
}
