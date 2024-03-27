// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Indexer;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Indexer;
// import frc.robot.subsystems.LEDs;
// import frc.robot.subsystems.LEDs.LEDSegment;

public class SourceFeed extends Command {
  /** Creates a new feed. */
  private final Indexer m_indexer;
  private Boolean m_part1 = true;
  private int count = 0;
  // Use addRequirements() here to declare subsystem dependencies.

  public SourceFeed(/* , boolean part1 */) {
    // initializes index
    m_indexer = Indexer.getInstance();
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_indexer);
  }

  // Called when the command is initially scheduled.
  // Runs motor in the opposite direction
  @Override
  public void initialize() {
    m_indexer.runNoteThrough(true);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (m_part1 && m_indexer.isNotePresent()) {
      count++;
      m_part1 = false;
    } else if (!m_part1 && !m_indexer.isNotePresent()) {
      count++;
      m_part1 = true;
    }
  }

  // Called once the command ends or is interrupted.
  // When called stops running the motor
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  // If note is detected by beam break sensor, calls end() to stop
  // If not motor will keep running
  @Override
  public boolean isFinished() {
    return (count == 3);

    // if (m_part1)
    // return (m_indexer.isNotePresent());
    // else
    // return (!m_indexer.isNotePresent());
  }
}
