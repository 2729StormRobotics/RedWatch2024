// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commandgroups.AutoCommandGroups;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Vision;

public class AutoNoteAlign extends Command {
  /** Creates a new RotationAllign. */
  Vision m_vision; 
  Drivetrain m_driveSubsystem;
  private final PIDController m_controller;
  private double m_turnError;
  private double m_turnPower;

  public AutoNoteAlign() {
    // Use addRequirements() here to declare subsystem dependencies.
    m_vision = Vision.getInstance(); 
    m_driveSubsystem = Drivetrain.getInstance();
    m_controller = new PIDController(0.002, Constants.VisionConstants.kITurn, 0);
    addRequirements(m_driveSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_turnError = 0;
    m_turnPower = 0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_turnError = m_vision.getX(); // Horizontal angle away from target
    m_turnPower = m_turnError * Constants.VisionConstants.kPTurn; // Calculate P value
    m_turnPower += Math.copySign(Constants.VisionConstants.kSTurn, m_turnPower); // Add feedforward value
    SmartDashboard.putNumber("turnError", m_turnError);
    // drive the robot
    m_driveSubsystem.drive(
      -0.4, 0,
      (m_controller.calculate(m_vision.getNoteXSkew()) + m_controller.calculate(m_vision.getNoteXSkew()) + Math.copySign(Constants.VisionConstants.kSTurn, m_controller.calculate(m_vision.getNoteXSkew()))),
      false, true);

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // return m_vision.getNoteXSkew() == 0.0;
    // return (Math.abs(turnError) < Constants.VisionConstants.kTolerance);
    return false;
  }
}
