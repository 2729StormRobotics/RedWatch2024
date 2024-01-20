// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.motorcontrol.Spark;

import static frc.robot.Constants.LEDConstants.*;

public class LEDs extends SubsystemBase {
  
  // Creates a LED controller
  private final Spark m_ledDriver;

  /** Creates a new LEDs. */
  public LEDs() {
    // Initializes BlinkIn (LED controller)
    m_ledDriver = new Spark(kBlinkinDriverPort);
    resetLEDs();
  }

  public void setDisabledColor() {
    m_ledDriver.set(kDisabled);
  }

  public void setOff() {
    m_ledDriver.set(kLEDsOff);
  }

  public void resetLEDs() {
    m_ledDriver.set(kDefaultColor);
  }
  public double getCurrentLEDs() {
    return m_ledDriver.get();
  }
  public void setGiven(double color) {
    m_ledDriver.set(color);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}