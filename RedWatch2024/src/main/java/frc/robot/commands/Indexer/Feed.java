// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Indexer;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Indexer;
import static frc.robot.Constants.IndexerConstants.*;

public class Feed extends Command {
  /** Creates a new feed. */
  private final Indexer m_indexer; 
    // Use addRequirements() here to declare subsystem dependencies.

  public Feed(Indexer indexer) {
    // initializes index 
    m_indexer = indexer;
    // Use addRequirements() here to declare subsystem dependencies.    
    addRequirements(indexer);
   }
      
    


  // Called when the command is initially scheduled.
  // Runs motor
  @Override
  public void initialize() {
    m_indexer.runNoteThrough();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}
  
  // Called once the command ends or is interrupted.
  // When called stops running the motor
  @Override
  public void end(boolean interrupted) {
    m_indexer.stop();
  }

  // Returns true when the command should end.
  // If note is detected by beam break sensor, calls end() to stop 
  // If not motor will keep running
  @Override
  public boolean isFinished() {
    if (m_indexer.isNotePresent())
      return true;
    return false;

  }
}
