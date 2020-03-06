package xyz.nasaknights.infiniterecharge.commands.climb;

import edu.wpi.first.wpilibj2.command.*;
import xyz.nasaknights.infiniterecharge.Robot;
import xyz.nasaknights.infiniterecharge.RobotContainer;
import xyz.nasaknights.infiniterecharge.commands.drivetrain.DriveCommand;

import static xyz.nasaknights.infiniterecharge.RobotContainer.getClimberSubsystem;
import static xyz.nasaknights.infiniterecharge.RobotContainer.getDrivetrain;

public class PrepareDriveToClimbCommand extends SequentialCommandGroup
{
    private double power;

    public PrepareDriveToClimbCommand(double power)
    {
        super(new InstantCommand(() ->
        {
            getDrivetrain().setDrivetrainNeutral(true);
            getDrivetrain().setPowerTakeoffExtended(true);
        }), new WaitCommand(0.25), new InstantCommand(() -> RobotContainer.getDrivetrain().setHighGear(true)), new InstantCommand(() ->
        {
            System.out.println("####################\n");
            System.out.println("# Retracting Winch #");
            System.out.println("\n####################");
            getClimberSubsystem().setWinchExtended(false);
        }), new InstantCommand(() -> RobotContainer.getDrivetrain().prepareClimbMotors()));
        addRequirements(getDrivetrain());
        this.power = power;
    }

    @Override
    public void end(boolean interrupted)
    {
        new WaitUntilCommand(0.5)
        {
            @Override
            public void execute()
            {
                RobotContainer.getDrivetrain().setMotorPercents(power, power);
            }

            @Override
            public void end(boolean interrupted)
            {
                new DriveCommand().schedule();
            }
        };
    }
}