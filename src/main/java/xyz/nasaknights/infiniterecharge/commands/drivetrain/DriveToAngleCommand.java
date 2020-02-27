package xyz.nasaknights.infiniterecharge.commands.drivetrain;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;

import java.util.function.DoubleSupplier;

import static xyz.nasaknights.infiniterecharge.RobotContainer.*;

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
    // TODO Set these to final once PID values are tuned and placed in their respective variables below
    private static double P = 1.0;
    private static double I = 0.0;
    private static double D = 0.0;

    private final int COUNT_NEEDED = 5; //count must be this or greater to actually be at the right angle
    private int count = 0; //count for if the bot is at the setpoint

    public DriveToAngleCommand(double angle)
    {
        super(new PIDController(P, I, D)
        {{
            // sets the tolerance to 0.25
            this.setTolerance(0.25);
            // tells controller to wrap values so they are between -180 and 180 like a circle
            this.enableContinuousInput(-180, 180);
        }}, () -> getIMU().getAngle(), angle, output -> getDrivetrain().arcadeDrive(0, output, true), getDrivetrain());
    }

    public DriveToAngleCommand(DoubleSupplier setpointSource)
    {
        super(new PIDController(P, I, D)
        {{
            // sets the tolerance to 0.25
            this.setTolerance(0.25);
            // tells controller to wrap values so they are between -180 and 180 like a circle
            this.enableContinuousInput(-180, 180);
        }}, () -> getIMU().getAngle(), setpointSource, output -> getDrivetrain().arcadeDrive(0, output, true), getDrivetrain());
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
        if (this.m_controller.atSetpoint())
        {
            count++;
        } else
        {
            count = 0;
        }
    }

    @Override
    public boolean isFinished()
    {
        return count >= COUNT_NEEDED;
    }

    @Override
    public void end(boolean interrupted)
    {
        this.m_controller.close();
    }

}
