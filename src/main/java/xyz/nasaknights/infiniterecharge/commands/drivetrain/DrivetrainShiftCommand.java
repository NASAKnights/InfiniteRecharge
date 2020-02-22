package xyz.nasaknights.infiniterecharge.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import xyz.nasaknights.infiniterecharge.RobotContainer;

public class DrivetrainShiftCommand extends InstantCommand
{
    public DrivetrainShiftCommand()
    {
        super(() -> RobotContainer.getDrivetrain().setHighGear(!RobotContainer.getDrivetrain().isDriveInHighGear()));
    }

//    boolean done = false;
//
//    public DrivetrainShiftCommand()
//    {
//        System.out.println("Attempting shift");
//    }
//
//    @Override
//    public void initialize()
//    {
//        RobotContainer.getDrivetrain().setHighGear(!RobotContainer.getDrivetrain().isDriveInHighGear());
//        done = true;
//    }
//
//    @Override
//    public void execute()
//    {
//
//    }
//
//    @Override
//    public boolean isFinished()
//    {
//        return done;
//    }
}
