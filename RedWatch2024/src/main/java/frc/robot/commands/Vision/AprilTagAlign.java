// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Vision;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Vision;

/*
 * Makes the robot automatically align to nearest apriltag (while still being able to drive translationally)
 */
public class AprilTagAlign extends Command {
  private final Vision m_vision; 
  private final Drivetrain m_drivetrain;
  private final Joystick m_translator;
  private double m_turnError;
  private double m_turnPower;
  /** Creates a new AprilTagAlign. */
  public AprilTagAlign(Vision vision, Drivetrain drivetrain, Joystick joystick) {
    m_vision = vision;
    m_drivetrain = drivetrain;
    m_translator = joystick;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_vision);
    addRequirements(m_drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // Values for PID Calculation
    m_turnError = 0;
    m_turnPower = 0;

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_turnError = m_vision.getX(); // Horizontal angle away from target
    m_turnPower = m_turnError * Constants.VisionConstants.kPTurn; // Calculate P value
    m_turnPower += Math.copySign(Constants.VisionConstants.kSTurn, m_turnPower); // Add feedforward value

    // drive the robot
    m_drivetrain.drive(
      MathUtil.applyDeadband(m_translator.getY()*OperatorConstants.translationMultiplier, OperatorConstants.kDriveDeadband),
      MathUtil.applyDeadband(m_translator.getX()*OperatorConstants.translationMultiplier, OperatorConstants.kDriveDeadband),
      (-m_turnPower - MathUtil.applyDeadband(m_translator.getX()*OperatorConstants.kDriveRotatorPort, OperatorConstants.kDriveDeadband)),
      true, true);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Math.abs(m_turnError) < Constants.VisionConstants.aprilTagAlignTolerance;
  }
}
