// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.SparkAbsoluteEncoder.Type;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Pivot extends SubsystemBase {
  // Motors and absolute encoder for pivoting for the shooter
  public final CANSparkMax m_rightPivot;
  public final AbsoluteEncoder m_PivotEncoder;

  /** Creates a new Pivot. */
  public Pivot() {
    // All motors initialization
    m_rightPivot = new CANSparkMax(Constants.PivotConstants.kRightPivotID, MotorType.kBrushless);
    motorInit(m_rightPivot, Constants.PivotConstants.kRightPivotInverted);
    
    // Pivot encoder initialization
    m_PivotEncoder = m_rightPivot.getAbsoluteEncoder(Type.kDutyCycle);
  }

  public void motorInit(CANSparkMax motor, boolean invert) {
    motor.restoreFactoryDefaults();
    motor.setIdleMode(IdleMode.kCoast);
    motor.setSmartCurrentLimit(Constants.ShooterConstants.kCurrentLimit);
    motor.setInverted(invert);
    motor.burnFlash();
  }

  /*
   * SETTERS
   */

  // Sets pivot speed in a voltage, from 0-12
  public void setPivotSpeed(double power) {
    m_rightPivot.setVoltage(power*12);
  }

  public void stopPivotMotors() {
    m_rightPivot.set(0);
  }

  /*
   * GETTERS
   */

  // returns pivot angle of shooter in degrees
  public double getPivotAngle() {
    if ((m_PivotEncoder.getPosition())* 360 > 300) { // ensure that encoder does not accidentally wrap around to 360
      return 0;
    }
    return (m_PivotEncoder.getPosition()) * 360;
  }

  public double getOptimalAngle(double distance) {
    return Constants.PivotConstants.ShooterInterpolationTable.getOutput(distance);
  }

  public double getPivotFeedForward() {
    return Constants.PivotConstants.kPivotFF * Math.cos(Math.toRadians(getPivotAngle() + 35.5));//40
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Shooter Angle", getPivotAngle());

  }
}
