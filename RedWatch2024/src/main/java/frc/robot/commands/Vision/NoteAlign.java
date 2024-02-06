// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Vision;

import frc.robot.Constants;
import frc.robot.Constants.*;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Vision;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;

public class NoteAlign extends Command {

  private final Drivetrain m_drivetrain;
  private final Vision m_vision;
  private final XboxController m_driverController;

  private double turnError;
  private double turnPower;

  /** Creates a new NoteAlign. */
  public NoteAlign(Drivetrain drivetrain, Vision vision, XboxController driverController) {
    m_drivetrain = drivetrain;
    m_vision = vision;
    m_driverController = driverController;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    turnError = 0;
    turnPower = 0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    turnError = m_vision.getNoteXSkew();
    turnPower = turnError * VisionConstants.kPTurn;

    m_drivetrain.drive(
      MathUtil.applyDeadband(m_driverController.getLeftY()/4, OperatorConstants.kDriveDeadband),
      MathUtil.applyDeadband(m_driverController.getLeftX()/4, OperatorConstants.kDriveDeadband),
      (-turnPower - MathUtil.applyDeadband(m_driverController.getRightX()/4, OperatorConstants.kDriveDeadband)),
      true, true);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return (Math.abs(turnError) < VisionConstants.kNoteTolerance);
  }
}
