// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Blinkin;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;

import frc.robot.subsystems.Pivot;
import frc.robot.subsystems.Shooter;


public class Meltdown extends InstantCommand {
  /** Creates a new Meltdown. */
  private final Shooter m_shooter;
  private final Pivot m_pivot;
  private final Intake m_intake;
  private final Indexer m_indexer;
  private Blinkin m_blinkin;
  public Meltdown() {
    m_shooter = Shooter.getInstance();
    m_pivot = Pivot.getInstance();
    m_intake = Intake.getInstance();
    m_indexer = Indexer.getInstance();
    m_blinkin = Blinkin.getInstance();
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_shooter);
    addRequirements(m_pivot);
    addRequirements(m_intake);
    addRequirements(m_indexer);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_indexer.stop();
    m_intake.stopIntake();
    m_shooter.stopShooterMotors();
    m_pivot.stopPivotMotors();
    m_blinkin.redStrobe();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
