// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Hanger;

public class HangerControl extends Command {
  private final double m_leftPower; 
  private final double m_rightPower; 
  private final Hanger m_hanger; 
  /** Creates a new HangerControl. */
  public HangerControl(double leftPower, double rightPower) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_hanger = Hanger.getInstance();
    m_leftPower = leftPower;
    m_rightPower = rightPower;
    addRequirements(m_hanger);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_hanger.stopMotors();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_hanger.setLeftSpeed(m_leftPower*Constants.HangerConstants.kSpeedLimiter);
    m_hanger.setRightSpeed(m_rightPower*Constants.HangerConstants.kSpeedLimiter);

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
