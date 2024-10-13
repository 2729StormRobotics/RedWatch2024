package frc.robot.commandgroups;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.LED.BlinkinLEDController;
import frc.robot.subsystems.groundIntake.GroundIntake;

public class IntakeThenLoad extends SequentialCommandGroup {
  private BlinkinLEDController m_blinkin;
  private GroundIntake m_intake;
  /** Creates a new IntakeLoad */
  public IntakeThenLoad(GroundIntake intake) {
    m_intake = intake;
    m_blinkin = BlinkinLEDController.getInstance();
    addCommands(
        new InstantCommand(
            () -> {
              m_blinkin.orangeHeartbeat();
            }),
        // will run intake and indexer until beambreak detects note
        new ParallelDeadlineGroup(
            // new Load(), //deadline command
            // new IntakeItem()
            m_intake.manualCommand(11.0), // Replace 12.0 with the desired intake voltage
            new WaitCommand(1.0) // Wait for 1 second to ensure the intake is running
            ),
        new InstantCommand(
            () -> {
              m_blinkin.orange();
            }),
        // will stop intake once note is detected
        new InstantCommand(
            () -> {
              m_intake.manualCommand(0);
            }),
        new InstantCommand(
            () -> {
              m_blinkin.setAllianceColorSolid();
            }));
  }
}
