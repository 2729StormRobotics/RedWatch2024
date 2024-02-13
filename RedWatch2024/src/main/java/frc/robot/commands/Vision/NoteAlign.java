// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Vision;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.Constants;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Vision;

public class NoteAlign extends Command {
  /** Creates a new RotationAllign. */
  Vision m_vision; 
  Drivetrain m_driveSubsystem;
  Joystick m_driverController;
  double turnError;
  double turnPower;

  private final double m_rotationMultiplier = 1;
  private final double m_translationMultiplier = 0.6;

  public NoteAlign(Drivetrain driveSubsystem, Vision vision, Joystick driverController) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_vision = vision; 
    m_driveSubsystem = driveSubsystem;
    m_driverController = driverController;
    addRequirements(driveSubsystem);
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
    turnPower = turnError * Constants.VisionConstants.kPNoteTurn;

    SmartDashboard.putNumber("turnpower", turnPower);
    SmartDashboard.putNumber("turnerror", turnError);
    m_driveSubsystem.drive(
      -MathUtil.applyDeadband(m_driverController.getY()*m_translationMultiplier, OperatorConstants.kDriveDeadband),
      -MathUtil.applyDeadband(m_driverController.getX()*m_translationMultiplier, OperatorConstants.kDriveDeadband),
        (-turnPower),
        false, true);

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
    // return (Math.abs(turnError) < Constants.VisionConstants.kTolerance);
  }
}
