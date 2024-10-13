// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.



import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Constants;
import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.Vision;

/*
 * Makes the robot automatically align to nearest apriltag (while still being able to drive translationally)
 */
public class AprilTagAlign extends Command {
  private final Vision m_vision; 
  private final Drive m_drivetrain;
  private final Joystick m_translator;
  private final PIDController m_controller;
  private double m_turnError;
  private double m_turnPower;
  /** Creates a new AprilTagAlign. */
  public AprilTagAlign(Joystick joystick, Drive drive, Vision vision) {
    m_drivetrain = drive;
    m_translator = joystick;
    m_controller = new PIDController(Constants.VisionConstants.kPTurn, Constants.VisionConstants.kITurn, Constants.VisionConstants.kDTurn);
    // Use addRequirements() here to declare subsystem dependencies.
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
    m_turnPower = m_controller.calculate(m_vision.getX()) + Math.copySign(Constants.VisionConstants.kSTurn, m_controller.calculate(m_vision.getX())); // Calculate P value
    if (Math.abs(m_vision.getX()) < 0.5) {
      m_turnPower = 0;
    }
    SmartDashboard.putNumber("turnError", m_turnError);
    // drive the robot

    CommandScheduler.getInstance().schedule(DriveCommands.joystickDrive(m_drivetrain, null, null, null));;

    m_drivetrain.runVelocity(ChassisSpeeds.fromFieldRelativeSpeeds(m_turnError, m_turnPower, m_turnError, null))
    m_drivetrain.drive(
      MathUtil.applyDeadband(-m_translator.getY()*OperatorConstants.translationMultiplier, OperatorConstants.kDriveDeadband),
      MathUtil.applyDeadband(-m_translator.getX()*OperatorConstants.translationMultiplier, OperatorConstants.kDriveDeadband),
      (m_turnPower),
      true, true);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drivetrain.drive(
      0, 0, 0,
            true, true);
    
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // return Math.abs(m_turnError) < Constants.VisionConstants.aprilTagAlignTolerance;
    return false;
  }
}