package xyz.nasaknights.infiniterecharge;

import com.team2363.logger.HelixEvents;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import xyz.nasaknights.infiniterecharge.commands.drivetrain.DriveCommand;
import xyz.nasaknights.infiniterecharge.commands.drivetrain.PathFollowCommand;
import xyz.nasaknights.infiniterecharge.commands.drivetrain.paths.ThreeByThree;
import xyz.nasaknights.infiniterecharge.util.controllers.DriverProfile;

public class Robot extends TimedRobot
{
    private static DriveCommand driveCommand = new DriveCommand();

    private RobotContainer robotContainer;

    private Spark vlight;

    private DriverProfile driverProfile = DriverProfile.BH;

    //    VisionClient vclnt = RobotContainer.getVisionClient();

    @Override
    public void robotInit()
    {
        robotContainer = new RobotContainer();
        //        vclnt.setLightOn(true);
        RobotContainer.setProfile(Constants.CURRENT_DRIVER_PROFILE);
        RobotContainer.getDrivetrain().setMaxSpeeds(Constants.CURRENT_DRIVER_PROFILE.getMaxThrottle(), Constants.CURRENT_DRIVER_PROFILE.getMaxTurn());
        vlight = new Spark(2);

        RobotContainer.getDrivetrain().setHighGear(false); // low gear
        RobotContainer.getDrivetrain().setDrivetrainNeutral(false); // drive gear
        RobotContainer.getDrivetrain().setPowerTakeoffExtended(false); // retract pto
        RobotContainer.getClimberSubsystem().setWinchExtended(false); // retract winch
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
        vlight.set(-1);
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
        HelixEvents.getInstance().startLogging();
        RobotContainer.getDrivetrain().resetEncoders();
        RobotContainer.getIMU().reset();
        new PathFollowCommand(new ThreeByThree()).schedule();
    }

    @Override
    public void autonomousPeriodic()
    {
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
