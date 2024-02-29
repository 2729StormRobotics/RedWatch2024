// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commandgroups;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Indexer.Load;
import frc.robot.commands.Intake.IntakeItem;
import frc.robot.commands.Intake.StopIntake;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.LEDs;
import frc.robot.subsystems.LEDs.LEDSegment;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class IntakeThenLoad extends SequentialCommandGroup {
  private final Intake m_intake;
  private final Indexer m_indexer;

  /** Creates a new IntakeLoad. */
  public IntakeThenLoad(Intake intake, Indexer indexer) {
    m_indexer = indexer;
    m_intake = intake;

    addCommands(
      // new InstantCommand(() -> {LEDSegment.MainStrip.setBandAnimation(LEDs.orange, 0.6);}),
      //will run the intake & indexer until the beambreak detects the note
      new ParallelDeadlineGroup(
        
        new Load(m_indexer), //"deadline" commmand 
        new IntakeItem(m_intake) 
      ),
      // new InstantCommand(() -> {LEDSegment.MainStrip.clearAnimation();LEDSegment.MainStrip.setColor(LEDs.orange);}),
      //will stop the intake once the note is detected
      new StopIntake(m_intake)

    );
  }
}
