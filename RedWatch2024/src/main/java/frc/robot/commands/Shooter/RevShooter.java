// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Shooter;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Shooter;

public class RevShooter extends Command {
  /** Creates a new RevShooter. */
  private final Shooter m_shooter;
  private final double m_leftPower;
  private final double m_rightPower;
  public RevShooter(Shooter shooter, double leftPower, double rightPower) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_shooter = shooter;
    m_leftPower = leftPower;
    m_rightPower = rightPower;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_shooter.setShooterSpeed(m_leftPower, m_rightPower);

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
    return (m_shooter.getLeftVoltage() >= m_leftPower*12) && (m_shooter.getRightVoltage() >= m_rightPower*12);
  }
}
