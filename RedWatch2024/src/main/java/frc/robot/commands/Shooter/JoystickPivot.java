// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Shooter;


import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Shooter;

public class JoystickPivot extends Command {
    private final Shooter m_shooter;
    private double m_stick;


  /** Creates a new JoystickPivot. */
  public JoystickPivot(Double stick, Shooter shooter) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_shooter = shooter;
    m_stick = stick;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_shooter.stopPivotMotors();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(Math.abs(m_stick) > Constants.ShooterConstants.kPivotSpeedLimiter)
      m_stick = Math.copySign(Constants.ShooterConstants.kPivotSpeedLimiter, m_stick);
    else
      m_shooter.setPivotSpeed(m_stick);
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
