package xyz.nasaknights.infiniterecharge.commands.drivetrain;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import xyz.nasaknights.infiniterecharge.RobotContainer;

/**
 * <p>A drive method that enables the drivetrain to turn to a certain angle using the PID control algorithm and
 * the output from the NavX IMU sensor.</p>
 * <p> See WPILib's
 * <a href = http://docs.wpilib.org/en/latest/docs/software/advanced-control/introduction/introduction-to-pid.html>Introduction to PID</a>,
 * <a href = http://docs.wpilib.org/en/latest/docs/software/advanced-control/introduction/tuning-pid-controller.html>Tuning a PID Controller</a>, and
 * <a href = http://docs.wpilib.org/en/latest/docs/software/advanced-control/controllers/pidcontroller.html>PID Control in WPILib</a>
 * for more information on PID control.</p>
 *
 * @see xyz.nasaknights.infiniterecharge.subsystems.DrivetrainSubsystem
 * @see com.kauailabs.navx.frc.AHRS
 * @see edu.wpi.first.wpilibj.controller.PIDController
 */
public class DriveToAngleCommand extends PIDCommand
{
    private final int COUNT_NEEDED = 5; //count must be this or greater to actually be at the right angle
    private double angle;
    private int count = 0; //count for if the bot is at the setpoint

    // TODO Set these to final once PID values are tuned and placed in their respective variables below
    private static double P;
    private static double I;
    private static double D;

    public DriveToAngleCommand(double angle)
    {
        super(new PIDController(P, I, D), () -> RobotContainer.getIMU().getAngle(), 0, value ->
        {
        }, RobotContainer.getDrivetrain());
        // TODO link angle with Vision Client
        this.angle = angle;
        addRequirements(RobotContainer.getDrivetrain());
    }

    @Override
    public void initialize()
    {
        m_controller.reset();
        getIMU().reset();
    }

    @Override
    public void execute()
    {
        RobotContainer.getDrivetrain().arcadeDrive(0, m_controller.calculate(m_setpoint.getAsDouble()), false);
    }

    @Override
    public boolean isFinished()
    {
        return this.m_controller.atSetpoint();
    }

    @Override
    public void end(boolean interrupted)
    {
        this.m_controller.close();
    }

}
