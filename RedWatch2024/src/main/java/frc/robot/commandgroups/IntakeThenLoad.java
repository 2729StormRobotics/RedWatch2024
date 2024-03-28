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
import frc.robot.subsystems.Blinkin;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class IntakeThenLoad extends SequentialCommandGroup {
  private Blinkin m_blinkin;
  /** Creates a new IntakeLoad. */
  public IntakeThenLoad() {
    m_blinkin = Blinkin.getInstance();
    addCommands(
      new InstantCommand(() -> {m_blinkin.orangeHeartbeat();}),
      //will run the intake & indexer until the beambreak detects the note
      new ParallelDeadlineGroup(
        
        new Load(), //"deadline" commmand 
        new IntakeItem() 
      ),
      new InstantCommand(() -> {m_blinkin.orange();}),
      //will stop the intake once the note is detected
      new StopIntake(),
      new InstantCommand(() -> {m_blinkin.neutral();})


    );
  }
}
