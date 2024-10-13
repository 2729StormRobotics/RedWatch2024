package frc.robot.commandgroups;


import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.groundIntake.GroundIntake;

public class IntakeCommand extends SequentialCommandGroup {
    public IntakeCommand(GroundIntake intake) {
        addCommands(
            intake.manualCommand(11.0), // Replace 12.0 with the desired intake voltage
            new WaitCommand(1.0), // Wait for 1 second to ensure the intake is running
            intake.manualCommand(0.0), // Stop the intake
            intake.manualCommand(.0) // Replace 48.0 with the desired intake voltage
        );
    }
}