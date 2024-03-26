// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Pivot;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Pivot;

public class SoftStop extends InstantCommand {
  /** Creates a new SoftStop. */
  public Pivot m_pivot;
  private double m_startingAngle;
  public SoftStop(Pivot shooter, double angle) {
    m_startingAngle = angle;
    // Use addRequirements() here to declare subsystem dependencies.
    m_pivot = shooter;
    addRequirements(m_pivot);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_pivot.setPivotSpeed(-0.05);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_pivot.getPivotAngle()<= (m_startingAngle-5);
  }
}
