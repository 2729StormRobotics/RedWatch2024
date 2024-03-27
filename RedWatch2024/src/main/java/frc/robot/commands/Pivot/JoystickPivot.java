// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Pivot;


import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Pivot;

public class JoystickPivot extends Command {
    private final Pivot m_pivot;
    private double m_stick;


  /** Creates a new JoystickPivot. */
  public JoystickPivot(Double stick) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_pivot = Pivot.getInstance();
    m_stick = stick;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_pivot.stopPivotMotors();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(Math.abs(m_stick) > Constants.PivotConstants.kPivotSpeedLimiter)
      m_stick = Math.copySign(Constants.PivotConstants.kPivotSpeedLimiter, m_stick);
    else
      m_pivot.setPivotSpeed(m_stick);
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
