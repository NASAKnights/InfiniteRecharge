package xyz.nasaknights.infiniterecharge.commands.drivetrain;

import com.team2363.commands.HelixFollower;
import com.team2363.controller.PIDController;
import com.team2363.logger.HelixEvents;
import com.team319.trajectory.Path;
import edu.wpi.first.wpilibj2.command.CommandBase;
import xyz.nasaknights.infiniterecharge.RobotContainer;

public class PathFollowCommand extends CommandBase
{
    private LegacyPathFollower lpf;

    public PathFollowCommand(Path path)
    {
        lpf = new LegacyPathFollower(path);

        addRequirements(RobotContainer.getDrivetrain());
    }

    @Override
    public void initialize()
    {
        lpf.publicInit();
    }

    @Override
    public void execute()
    {
        lpf.publicExec();
    }

    @Override
    public boolean isFinished()
    {
        return lpf.publicIsFinished();
    }

    @Override
    public void end(boolean interrupted)
    {
        lpf.publicEnd(interrupted);
    }

    private static class LegacyPathFollower extends HelixFollower
    {
        private PIDController heading = new PIDController(0, 0, 0, .001);
        private PIDController distance = new PIDController(0, 0,  0, .001);

        /**
         * This will import the path class based on the name of the path provided
         *
         * @param path the name of the path to run
         */
        public LegacyPathFollower(Path path)
        {
            super(path);
        }

        @Override
        public void resetDistance()
        {
            RobotContainer.getDrivetrain().resetEncoders();
        }

        @Override
        public PIDController getHeadingController()
        {
            return heading;
        }

        @Override
        public PIDController getDistanceController()
        {
            return distance;
        }

        @Override
        public double getCurrentDistance()
        {
            return ((RobotContainer.getDrivetrain().getLeftEncoderPosition() + RobotContainer.getDrivetrain().getRightEncoderPosition()) / 2) / 28608.2857;
        }

        @Override
        public double getCurrentHeading()
        {
            return Math.toRadians(RobotContainer.getIMU().getAngle());
        }

        @Override
        public void useOutputs(double left, double right)
        {
            RobotContainer.getDrivetrain().setMotorPercents(left, right);
        }

        public void publicInit()
        {
            this.initialize();
        }

        public void publicExec()
        {
            this.execute();
        }

        public void publicEnd(boolean interrupted)
        {
            if(interrupted)
            {
                this.interrupted();
            }
            else
            {
                this.end();
            }

            System.out.println("Path done, stopping drive");
            RobotContainer.getDrivetrain().stopAllMotors();
        }

        public boolean publicIsFinished()
        {
            return this.isFinished();
        }
    }
}
