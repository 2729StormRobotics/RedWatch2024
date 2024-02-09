// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Indexer;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Indexer;
import frc.robot.Constants.IndexerConstants;

public class setIndexerSpeeds extends Command {
  private Indexer m_indexer;
  private Double m_speeds;
  /** Creates a new setSpeeds. */
  public setIndexerSpeeds(Indexer indexer, Double speed) {
    m_indexer = indexer;
    m_speeds = speed;
    addRequirements(m_indexer);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    IndexerConstants.kIndexerSpeed = m_speeds;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
