// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.util.Map;

import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.Constants.VisionConstants;

public class Vision extends SubsystemBase {

  private static ShuffleboardTab m_controlpanelTab;

  private static ShuffleboardLayout m_status;
  
  public static String target = "HIGH";
  NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
  NetworkTableEntry tx = table.getEntry("tx");
  NetworkTableEntry ty = table.getEntry("ty");
  NetworkTableEntry ta = table.getEntry("ta");
  private double x;
  private double y;
  private double area;

  public double note_x = 0;
  public double note_y = 0;

  /** Creates a new NoteDetection. */
  public Vision() {
    m_controlpanelTab = Shuffleboard.getTab("Vision");

    m_status = m_controlpanelTab.getLayout("Status", BuiltInLayouts.kList)
      .withProperties(Map.of("Label position", "TOP"))
      .withPosition(0, 0)
      .withSize(2, 4);

    m_status.addNumber("note_x", () -> note_x);
    m_status.addNumber("note_y", () -> note_y);
    
    table.getEntry("pipeline").setNumber(Constants.VisionConstants.kAprilTagPipeline);
    table.getEntry("ledMode").setNumber(Constants.VisionConstants.kLightOffValue);
  }

  public double xAlign() {
    double xError = 0;
    double xPower = 0;

    xError = getNoteXSkew();

    if (Math.abs(xError) < VisionConstants.kNoteTolerance) {
      return 0;
    }

    xPower *= VisionConstants.kPX;
    
    return xPower;
  }

  public double getNoteXSkew() {
    return note_x;
  }

  public double getNoteYSkew() {
    return note_y;

  public void setPipeline(double pipeline) {
    table.getEntry("pipeline").setNumber(pipeline);
  }

  public void setLight(double value) {
    table.getEntry("ledMode").setNumber(value);
  }

  // return horizontal angle from limelight to target
  public double getX() {
    return x;
  }

  // returns vertical angle from limelight to target
  public double getY() {
    return y;
  }

  // returns relative area of the target compared with FOV
  public double getArea() {
    return area; 
  }

  /*
   * Methods to get distance (meters) away from vision targets
   */

  public double getSpeakerDistance() {
    double deltaHeight = Constants.VisionConstants.speakerTagHeight - Constants.VisionConstants.limelightHeight;
    double deltaAngle = Math.toRadians(Constants.VisionConstants.limelightAngle + getY());
    double dist = deltaHeight/Math.tan(deltaAngle);
    
    return dist;
  }

  public double getAmpDistance() {
    double deltaHeight = Constants.VisionConstants.ampTagHeight - Constants.VisionConstants.limelightHeight;
    double deltaAngle = Math.toRadians(Constants.VisionConstants.limelightAngle + getY());
    double dist = deltaHeight/Math.tan(deltaAngle);
    
    return dist;
  }

  public double getStageDistance() {
    double deltaHeight = Constants.VisionConstants.stageTagHeight - Constants.VisionConstants.limelightHeight;
    double deltaAngle = Math.toRadians(Constants.VisionConstants.limelightAngle + getY());
    double dist = deltaHeight/Math.tan(deltaAngle);
    
    return dist;
  }

  public void switchPipeline(int pipeline) {
    table.getEntry("pipeline").setNumber(pipeline);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    note_x = SmartDashboard.getNumber("note_x", 0);
    note_y = SmartDashboard.getNumber("note_y", 0);
    
    x = tx.getDouble(0.0);
    y = ty.getDouble(0.0);

    area = ta.getDouble(0.0);

    SmartDashboard.putNumber("LimelightX", x);
    SmartDashboard.putNumber("LimelightY", y);
    SmartDashboard.putNumber("LimelightArea", area);
    SmartDashboard.putString("Target", Vision.target);
    
  }
}
