// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Pivot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.Constants.VisionConstants;
import frc.robot.lib.LinearInterpolationTable;
// import frc.robot.subsystems.LEDs;
import frc.robot.subsystems.Pivot;
import frc.robot.subsystems.Vision;
// import frc.robot.subsystems.LEDs.LEDSegment; 

import java.awt.geom.Point2D;


public class AutoPivotNoEnd extends Command {
  private final Pivot m_pivot;
  private double m_turnError;
  private double m_turnPower;
  private double m_setpoint;
  private double m_ff;
  private double m_angle;
  private Vision m_vision;

  boolean isVision;
  double deltaHeight;
  double deltaAngle;
  double dist;
  double timeElapsed = 0;

  private final LinearInterpolationTable ShooterInterpolationTable = new LinearInterpolationTable(Constants.PivotConstants.ShootingPoints);

  /** Creates a new AutoPivot. */
  public AutoPivotNoEnd(Vision vision, Pivot pivot, boolean IsVision) {
    m_vision = vision;
    m_pivot = pivot;
    isVision = IsVision;

    // Use addRequirements() here to declare subsystem dependencies.
    // addRequirements(m_vision);
    addRequirements(m_pivot);
  }

  public AutoPivotNoEnd(double angle, Pivot pivot, boolean IsVision) {
    isVision = IsVision;
    m_angle = angle;
    m_pivot = pivot;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_pivot);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_turnError = 0;
    m_turnPower = 0;
    m_ff = 0;
    m_setpoint = 0;
    timeElapsed = 0;
    // LEDSegment.MainStrip.setBandAnimation(LEDs.yellow, 0.8); urvis dog was here
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    timeElapsed += 0.02;

    if(!isVision)
      m_setpoint = m_angle;
    else{
      deltaAngle = Math.toRadians(VisionConstants.limelightAngle + m_vision.getY());

      deltaHeight = VisionConstants.speakerTagHeight - VisionConstants.limelightHeight;
      dist = deltaHeight / Math.tan(deltaAngle);

    m_setpoint = ShooterInterpolationTable.getOutput(dist);
    if (m_setpoint < 10) {
      m_setpoint = 10;
    }
    // if (m_vision.getY() == 0.0) {
    //   m_setpoint = 49;
    // }
    }
    m_turnError = m_setpoint - m_pivot.getPivotAngle();
    
    if (m_setpoint < m_pivot.getPivotAngle())
    m_turnPower = m_turnError * Constants.PivotConstants.kPPivotDown;

  
  if (m_setpoint > m_pivot.getPivotAngle())
    m_turnPower = m_turnError * Constants.PivotConstants.kPPivotUp;

    m_pivot.setPivotSpeed(m_turnPower);
    SmartDashboard.putNumber("Auto angle not fraud", m_setpoint);//~
  }

  // Called once the command ends or is interrupted. ~
  @Override
  public void end(boolean interrupted) {
    m_pivot.setPivotSpeed(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }//~
}
