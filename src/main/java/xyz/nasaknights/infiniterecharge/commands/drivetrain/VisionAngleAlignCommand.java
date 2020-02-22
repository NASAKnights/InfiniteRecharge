package xyz.nasaknights.infiniterecharge.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import xyz.nasaknights.infiniterecharge.RobotContainer;

public class VisionAngleAlignCommand extends CommandBase
{
    private static final double VARIATION = 0.5;
    private static final int COUNT_NEEDED = 5;
    private int count;

    public VisionAngleAlignCommand()
    {
        addRequirements(RobotContainer.getDrivetrain());
    }

    @Override
    public void initialize()
    {
        System.out.println("Starting vision control");
    }

    @Override
    public void execute()
    {
        RobotContainer.getDrivetrain().arcadeDrive(0, RobotContainer.getVisionClient().getTurn(), true);
        if (Math.abs(RobotContainer.getVisionClient().getAngle()) < VARIATION)
        {
            count++;
        } else
        {
            count = 0;
        }
    }

    @Override
    public void end(boolean interrupted)
    {
        System.out.println("Ending Vision Control");
    }

    @Override
    public boolean isFinished()
    {
        return Math.abs(RobotContainer.getVisionClient().getAngle()) < VARIATION && count >= COUNT_NEEDED;
    }
}
