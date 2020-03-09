package xyz.nasaknights.infiniterecharge.commands.climb;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import xyz.nasaknights.infiniterecharge.RobotContainer;
import xyz.nasaknights.infiniterecharge.util.controllers.ControllerRegistry;

public class RetractClimbArmCommand extends SequentialCommandGroup
{
    public RetractClimbArmCommand(boolean releasePneumatics)
    {
        super(
                new InstantCommand(() ->
                {
                    RobotContainer.getClimberSubsystem().setClimbArmExtended(false);
                    RobotContainer.getDrivetrain().setDrivetrainNeutral(true);
                    RobotContainer.getDrivetrain().setPowerTakeoffExtended(true);
                }),
                new WaitCommand(.5),
                new InstantCommand(() ->
                {
                    RobotContainer.getDrivetrain().setHighGear(true);
                    RobotContainer.getClimberSubsystem().setClimbArmExtended(!releasePneumatics);
                    RobotContainer.getDrivetrain().prepareClimbMotors();
                }),
                new CommandBase()
                {
                    @Override
                    public void execute()
                    {
                        RobotContainer.getDrivetrain().arcadeDrive(releasePneumatics ? .7 : .35, 0, false);
                        System.out.println("setting drivetrain to retract winch");
                    }

                    @Override
                    public void end(boolean interrupted)
                    {
                        System.out.println("stopping drivetrain");
                        RobotContainer.getDrivetrain().stopAllMotors();
                    }

                    @Override
                    public boolean isFinished()
                    {
                        System.out.println(!releasePneumatics ? !ControllerRegistry.isOperatorLeftBumperPressed() : !ControllerRegistry.isOperatorSharePressed());
                        return (!releasePneumatics ? !ControllerRegistry.isOperatorLeftBumperPressed() : !ControllerRegistry.isOperatorSharePressed());
                    }
                },
                new InstantCommand(() -> RobotContainer.getDrivetrain().setPowerTakeoffExtended(false))
        );
    }
}
