// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.SparkAbsoluteEncoder.Type;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {
  // Motor for pivoting for the shooter
  // public final CANSparkMax m_leftPivot;
  // public final AbsoluteEncoder m_leftPivotEncoder;

  // public final CANSparkMax m_rightPivot;
  // public final AbsoluteEncoder m_rightPivotEncoder;

  // Motors for the flywheels
  public final CANSparkMax m_leftFlywheel;
  public final RelativeEncoder m_leftFlywheelEncoder;
  // public final CANSparkMax m_rightFlywheel;
  // public final RelativeEncoder m_rightFlywheelEncoder;


  
  /** Creates a new Shooter. */
  public Shooter() {
    // All motors initialization
    // m_leftPivot = new CANSparkMax(Constants.ShooterConstants.kLeftPivotID, MotorType.kBrushless);
    // m_rightPivot = new CANSparkMax(Constants.ShooterConstants.kRightPivotID, MotorType.kBrushless);
    m_leftFlywheel = new CANSparkMax(Constants.ShooterConstants.kLeftFlywheelID, MotorType.kBrushless);
    // m_rightFlywheel = new CANSparkMax(Constants.ShooterConstants.kRightFlywheelID, MotorType.kBrushless);

    motorInit(m_leftFlywheel, Constants.ShooterConstants.kLeftFlywheelInverted);
    // motorInit(m_rightFlywheel, Constants.ShooterConstants.kRightFlywheelInverted);
    // motorInit(m_leftPivot, Constants.ShooterConstants.kLeftPivot);
    // motorInit(m_rightPivot, Constants.ShooterConstants.kRightPivot);


    // Pivot encoder
    // m_leftPivotEncoder = m_leftPivot.getAbsoluteEncoder(Type.kDutyCycle);
    // m_rightPivotEncoder = m_rightPivot.getAbsoluteEncoder(Type.kDutyCycle);

    // Flywheel encoder
    m_leftFlywheelEncoder = m_leftFlywheel.getEncoder();
    // m_rightFlywheelEncoder = m_rightFlywheel.getEncoder();
    
  }

  public void motorInit(CANSparkMax motor, boolean invert) {
    motor.restoreFactoryDefaults();
    motor.setIdleMode(IdleMode.kBrake);
    motor.setSmartCurrentLimit(Constants.ShooterConstants.kCurrentLimit);
    motor.setInverted(invert);
  }

  /*
   * SETTERS
   */

  // public void setPivotSpeed(double power) {
  //   m_leftPivot.set(power);
  //   m_rightPivot.set(power);
  // }

  public void setShooterSpeed(double power) {
    m_leftFlywheel.set(power);
    // m_rightFlywheel.set(power);
  }

  // public void stopPivotMotors() {
  //   m_leftPivot.set(0);
  //   m_rightPivot.set(0);
  // }

  // public void stopShooterMotors() {
  //   m_leftFlywheel.set(0);
  //   m_rightPivot.set(0);
  // }

  /*
   * GETTERS
   */

  public double getLeftRPM() {
    return m_leftFlywheelEncoder.getVelocity();
  }

  // public double getRightRPM() {
  //   return m_rightFlywheelEncoder.getVelocity();
  // }

  // public double getAverageRPM() {
  //   return (getLeftRPM() + getRightRPM())/2;
  // }

  // public double getLeftAngle() {
  //   return m_leftPivotEncoder.getPosition() - Constants.ShooterConstants.kLeftPivotOffset;
  // }

  // public double getRightAngle() {
  //   return m_rightPivotEncoder.getPosition() - Constants.ShooterConstants.kRightPivotOffset;
  // }

  // public double getAverageAngle() {
  //   return (getLeftAngle() + getRightAngle())/2;
  // }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Shooter RPM", getLeftRPM());
    // SmartDashboard.putNumber("Shooter Angle", getAverageAngle());
  }
}
