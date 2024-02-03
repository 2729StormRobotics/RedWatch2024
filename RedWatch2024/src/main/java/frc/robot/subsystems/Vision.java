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

  public double note_x = -999999;
  public double note_y = -999999;

  /** Creates a new NoteDetection. */
  public Vision() {
    m_controlpanelTab = Shuffleboard.getTab("Vision");

    m_status = m_controlpanelTab.getLayout("Status", BuiltInLayouts.kList)
      .withProperties(Map.of("Label position", "TOP"))
      .withPosition(0, 0)
      .withSize(2, 4);

    m_status.addNumber("note_x", () -> note_x);
    m_status.addNumber("note_y", () -> note_y);
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
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    note_x = SmartDashboard.getNumber("note_x", -999999);
    note_y = SmartDashboard.getNumber("note_y", -999999);
  }
}
