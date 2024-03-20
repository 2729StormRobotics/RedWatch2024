// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {
  public static double passivePower = 0.0;

  // Motors and encoders for the flywheels
  public final CANSparkMax m_leftFlywheel;
  public final RelativeEncoder m_leftFlywheelEncoder;
  public final CANSparkMax m_rightFlywheel;
  public final RelativeEncoder m_rightFlywheelEncoder;
  
  /** Creates a new Shooter. */
  public Shooter() {
    // All motors initialization
    m_leftFlywheel = new CANSparkMax(Constants.ShooterConstants.kLeftFlywheelID, MotorType.kBrushless);
    m_rightFlywheel = new CANSparkMax(Constants.ShooterConstants.kRightFlywheelID, MotorType.kBrushless);

    motorInit(m_leftFlywheel, Constants.ShooterConstants.kLeftFlywheelInverted);
    motorInit(m_rightFlywheel, Constants.ShooterConstants.kRightFlywheelInverted);

    // Flywheel encoder initialization
    m_leftFlywheelEncoder = m_leftFlywheel.getEncoder();
    m_rightFlywheelEncoder = m_rightFlywheel.getEncoder();
    
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

  // Sets shooter speed in a voltage, from 0-12
  public void setShooterSpeed(double leftPower, double rightPower) {
    m_leftFlywheel.setVoltage(leftPower*12);
    m_rightFlywheel.setVoltage(rightPower*12);
  }

  public void stopShooterMotors() {
    m_leftFlywheel.set(0);
    m_rightFlywheel.set(0);
  }

  /*
   * GETTERS
   */
  public double getLeftVoltage() {
    return m_leftFlywheel.getBusVoltage()*m_leftFlywheel.getAppliedOutput();
  }

  public double getRightVoltage() {
    return m_rightFlywheel.getBusVoltage()*m_rightFlywheel.getAppliedOutput();
  }

  public double getRightPower(){
    return m_rightFlywheelEncoder.getVelocity() * m_rightFlywheelEncoder.getVelocityConversionFactor();
  }

  // returns rpm of left shooter motor
  public double getLeftRPM() {
    return m_leftFlywheelEncoder.getVelocity();
  }

  // returms rpm of right shooter motor
  public double getRightRPM() {
    return m_rightFlywheelEncoder.getVelocity();
  }

  // returns average rpm between both shooter motors
  public double getAverageRPM() {
    return (getLeftRPM() + getRightRPM())/2;
  }

  @Override
  public void periodic() {
    // Put RPM and pivot angle on shuffleboard
    SmartDashboard.putNumber("Shooter RPM", getAverageRPM());
    SmartDashboard.putNumber("Left Shooter Voltage", getLeftVoltage()); //check value on smart dashbord for a rev cmd
    SmartDashboard.putNumber("Right Shooter Voltage", getRightVoltage());
  }
}
