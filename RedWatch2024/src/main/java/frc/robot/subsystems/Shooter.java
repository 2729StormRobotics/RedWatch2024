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
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.commands.Shooter.SoftStop;

public class Shooter extends SubsystemBase {
  // Motors and absolute encoder for pivoting for the shooter
  public final CANSparkMax m_rightPivot;
  public final AbsoluteEncoder m_PivotEncoder;


  // Motors and encoders for the flywheels
  public final CANSparkMax m_leftFlywheel;
  public final RelativeEncoder m_leftFlywheelEncoder;
  public final CANSparkMax m_rightFlywheel;
  public final RelativeEncoder m_rightFlywheelEncoder;

  public static double iterations = 0; // counter used for the number of iteration in Newton's Method
  
  /** Creates a new Shooter. */
  public Shooter() {
    // All motors initialization
    m_rightPivot = new CANSparkMax(Constants.ShooterConstants.kRightPivotID, MotorType.kBrushless);
    m_leftFlywheel = new CANSparkMax(Constants.ShooterConstants.kLeftFlywheelID, MotorType.kBrushless);
    m_rightFlywheel = new CANSparkMax(Constants.ShooterConstants.kRightFlywheelID, MotorType.kBrushless);

    motorInit(m_leftFlywheel, Constants.ShooterConstants.kLeftFlywheelInverted);
    motorInit(m_rightFlywheel, Constants.ShooterConstants.kRightFlywheelInverted);
    motorInit(m_rightPivot, Constants.ShooterConstants.kRightPivotInverted);


    // Pivot encoder initialization
    m_PivotEncoder = m_rightPivot.getAbsoluteEncoder(Type.kDutyCycle);

    // Flywheel encoder initialization
    m_leftFlywheelEncoder = m_leftFlywheel.getEncoder();
    m_rightFlywheelEncoder = m_rightFlywheel.getEncoder();
    
  }

  public void motorInit(CANSparkMax motor, boolean invert) {
    motor.restoreFactoryDefaults();
    motor.setIdleMode(IdleMode.kCoast);
    motor.setSmartCurrentLimit(Constants.ShooterConstants.kCurrentLimit);
    motor.setInverted(invert);
  }

  /*
   * SETTERS
   */

  // Sets pivot speed in a percentage, from 0-1
  public void setPivotSpeed(double power) {
    m_rightPivot.set(power);
  }

  // Sets shooter speed in a percentage, from 0-1
  public void setShooterSpeed(double leftPower, double rightPower) {
    m_leftFlywheel.set(leftPower);
    m_rightFlywheel.set(rightPower);
  }

  public void stopPivotMotors() {
    m_rightPivot.set(0);
  }

  public void stopShooterMotors() {
    m_leftFlywheel.set(0);
    m_rightFlywheel.set(0);
  }

  /*
   * GETTERS
   */

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

  // returns pivot angle of shooter in degrees
  public double getPivotAngle() {
    if ((m_PivotEncoder.getPosition())* 360 > 300) {
      return 0;
    }
    return (m_PivotEncoder.getPosition()) * 360;
  }

  public double getOptimalAngle(double distance) {
    return Constants.ShooterConstants.ShooterInterpolationTable.getOutput(distance);
  }

  public double getPivotFeedForward() {
    return Constants.ShooterConstants.kPivotFF * Math.cos(Math.toRadians(getPivotAngle() + 49));
  }

  @Override
  public void periodic() {
    // Put RPM and pivot angle on shuffleboard
    SmartDashboard.putNumber("Shooter RPM", getAverageRPM());
    SmartDashboard.putNumber("Shooter Angle", getPivotAngle());
    if (getPivotAngle() >= 65 ){
      CommandScheduler.getInstance().schedule(new SoftStop(this, getPivotAngle()));
    }
  }
}
