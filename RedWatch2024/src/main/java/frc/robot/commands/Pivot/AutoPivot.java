// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Pivot;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.Constants.VisionConstants;
import frc.robot.lib.LinearInterpolationTable;
import frc.robot.subsystems.LEDs;
import frc.robot.subsystems.Pivot;
import frc.robot.subsystems.Vision;
import frc.robot.subsystems.LEDs.LEDSegment;

import java.awt.geom.Point2D;
import java.lang.invoke.ConstantBootstraps;


public class AutoPivot extends Command {
  private final Vision m_vision;
  private final Pivot m_pivot;
  private double m_turnError;
  private double m_turnPower;
  private double m_setpoint;

  double deltaHeight;
  double deltaAngle;
  double dist;
  double timeElapsed = 0;

  private final Point2D[] ShootingPoints = new Point2D[]{ // array of exp determined data points of (dist, angle)
      new Point2D.Double(-0.01, 54),
      new Point2D.Double(0.93, 54),
      new Point2D.Double(1.77, 43.5),
      new Point2D.Double(2, 39.32),
      new Point2D.Double(2.32, 36.17),
      new Point2D.Double(2.6, 34.75),
      new Point2D.Double(2.9, 32.3)
    };
  private final LinearInterpolationTable ShooterInterpolationTable = new LinearInterpolationTable(ShootingPoints);

  /** Creates a new AutoPivot. */
  public AutoPivot(Vision vision, Pivot pivot) {
    m_vision = vision;
    m_pivot = pivot;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_vision);
    addRequirements(m_pivot);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_turnError = 0;
    m_turnPower = 0;
    m_setpoint = 0;
    timeElapsed = 0;
    deltaAngle = Math.toRadians(VisionConstants.limelightAngle + m_vision.getY());
    if (m_vision.getY() == 0){
      LEDSegment.MainStrip.setColor(LEDs.blue);
    }
    else
    {
      LEDSegment.MainStrip.setBandAnimation(LEDs.yellow, 0.6);
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    if (m_vision.getY() == 0){
      LEDSegment.MainStrip.setColor(LEDs.blue);
    }
    else
    {
      LEDSegment.MainStrip.setBandAnimation(LEDs.yellow, 0.6);
    }
    timeElapsed += 0.02;

    deltaHeight = VisionConstants.speakerTagHeight - VisionConstants.limelightHeight;
    dist = deltaHeight / Math.tan(deltaAngle);

    m_setpoint = ShooterInterpolationTable.getOutput(dist);
    if (m_setpoint < 10) {
      m_setpoint = 10;
    }
    m_turnError = m_setpoint - m_pivot.getPivotAngle();
    m_turnPower = m_turnError * Constants.PivotConstants.kPPivot;
    if (m_turnPower > 0.2) {
      m_turnPower = 0.2;
    }
    else if (m_turnPower < -0.2) {
      m_turnPower = -0.2;
    }

    m_turnPower += m_pivot.getPivotFeedForward();
    m_pivot.setPivotSpeed(m_turnPower);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    LEDSegment.MainStrip.setColor(LEDs.allianceColor);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return (Math.abs(m_turnError) < Constants.PivotConstants.kPivotTolerance) || timeElapsed > 2;
  }
}