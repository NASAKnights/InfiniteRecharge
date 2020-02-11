package xyz.nasaknights.infiniterecharge.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import xyz.nasaknights.infiniterecharge.RobotContainer;

public class MusicCommand extends CommandBase
{
    public MusicCommand()
    {

    }

    @Override
    public void initialize() {
        RobotContainer.getTalonFXOrchestra().loadMusic("dotf.chrp");
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub
        RobotContainer.getTalonFXOrchestra().play();
    }
}