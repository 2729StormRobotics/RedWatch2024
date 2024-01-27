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
import frc.robot.Constants.ShooterConstants;

public class Shooter extends SubsystemBase {
  // Motor for pivoting for the shooter
  public final CANSparkMax m_leftPivot;
  public final AbsoluteEncoder m_leftPivotEncoder;

  public final CANSparkMax m_rightPivot;
  public final AbsoluteEncoder m_rightPivotEncoder;

  // Motors for the flywheels
  public final CANSparkMax m_leftFlywheel;
  public final RelativeEncoder m_leftFlywheelEncoder;
  public final CANSparkMax m_rightFlywheel;
  public final RelativeEncoder m_rightFlywheelEncoder;


  
  /** Creates a new Shooter. */
  public Shooter() {
    // All motors initialization
    m_leftPivot = new CANSparkMax(Constants.ShooterConstants.kLeftPivotID, MotorType.kBrushless);
    m_rightPivot = new CANSparkMax(Constants.ShooterConstants.kRightPivotID, MotorType.kBrushless);
    m_leftFlywheel = new CANSparkMax(Constants.ShooterConstants.kLeftFlywheelID, MotorType.kBrushless);
    m_rightFlywheel = new CANSparkMax(Constants.ShooterConstants.kRightFlywheelID, MotorType.kBrushless);

    motorInit(m_leftFlywheel, Constants.ShooterConstants.kLeftFlywheelInverted);
    motorInit(m_rightFlywheel, Constants.ShooterConstants.kRightFlywheelInverted);
    motorInit(m_leftPivot, Constants.ShooterConstants.kLeftPivot);
    motorInit(m_rightPivot, Constants.ShooterConstants.kRightPivot);


    // Pivot encoder
    m_leftPivotEncoder = m_leftPivot.getAbsoluteEncoder(Type.kDutyCycle);
    m_rightPivotEncoder = m_rightPivot.getAbsoluteEncoder(Type.kDutyCycle);

    // Flywheel encoder
    m_leftFlywheelEncoder = m_leftFlywheel.getEncoder();
    m_rightFlywheelEncoder = m_rightFlywheel.getEncoder();
    
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

  public void setPivotSpeed(double power) {
    m_leftPivot.set(power);
    m_rightPivot.set(power);
  }

  public void setShooterSpeed(double power) {
    m_leftFlywheel.set(power);
    m_rightFlywheel.set(power);
  }

  public void stopPivotMotors() {
    m_leftPivot.set(0);
    m_rightPivot.set(0);
  }

  public void stopShooterMotors() {
    m_leftFlywheel.set(0);
    m_rightPivot.set(0);
  }

  /*
   * GETTERS
   */

  public double getLeftRPM() {
    return m_leftFlywheelEncoder.getVelocity();
  }

  public double getRightRPM() {
    return m_rightFlywheelEncoder.getVelocity();
  }

  public double getAverageRPM() {
    return (getLeftRPM() + getRightRPM())/2;
  }

  public double getLeftAngle() {
    return m_leftPivotEncoder.getPosition() - Constants.ShooterConstants.kLeftPivotOffset;
  }

  public double getRightAngle() {
    return m_rightPivotEncoder.getPosition() - Constants.ShooterConstants.kRightPivotOffset;
  }

  public double getAverageAngle() {
    return (getLeftAngle() + getRightAngle())/2;
  }

  /*
   * Command will return the optimal angle to shoot at
   * Derivation of the Formula can be seen here:
   * https://drive.google.com/file/d/1dcSJj9QoYHzQPaUIWQf76gtZUhYFJNyI/view?usp=drive_link
   * The graph of this (including Taylor Series approximation):
   * https://www.desmos.com/calculator/fsfd7xb0pe
   * The Newton Method is used to approximate the solutions
   */

  // F(x) and F'(x) functions needed for getOptimalAngle() method
  public static double f(double x, double dist) {
    // Renaming constants so that they are easier to follow and the code looks less scary
    double l = Constants.ShooterConstants.shooterLength;
    double h = Constants.ShooterConstants.goalHeight;
    double k = Constants.ShooterConstants.k;
  
    // Coefficients of the Polynomial
    double a = 2*dist/15;
    double b = (-h/3 + Math.pow(l, 2)/3 * k - k*dist*l/12);
    double c = -2*dist/3;
    double d = k*dist*l + h - Math.pow(l, 2)*k;
    double e = dist;
    double f = k*Math.pow(dist, 2) - 2*k*dist*l - h + Math.pow(l, 2)*k;

    // Evaluating f(x)
    return a*Math.pow(x, 5) + b*Math.pow(x, 4) + c*Math.pow(x, 3) + d*Math.pow(x, 2) + e*x + f;
  }

  public static double f_prime(double x, double dist) {
    // Renaming constants so that they are easier to follow and the code looks less scary
    double l = Constants.ShooterConstants.shooterLength;
    double h = Constants.ShooterConstants.goalHeight;
    double k = Constants.ShooterConstants.k;
  
    // Coefficients of the Polynomial
    double a = 2*dist/15;
    double b = (-h/3 + Math.pow(l, 2)/3 * k - k*dist*l/12);
    double c = -2*dist/3;
    double d = k*dist*l + h - Math.pow(l, 2)*k;
    double e = dist;
    double f = k*Math.pow(dist, 2) - 2*k*dist*l - h + Math.pow(l, 2)*k;

    // Evaluating f'(x)
    return 5*a*Math.pow(x, 4) + 4*b*Math.pow(x, 3) + 3*c*Math.pow(x, 2) + 2*d*x + e;
  }

  // Newton's Method (recursive)
  public double getOptimalAngle(double init, double dist) {
    double x0 = 0;
    double x1 = x0 - f(x0, dist)/f_prime(x0, dist);
    if (Math.abs(x1 - x0) < 1e-2) {
      return x1;
    }
    return getOptimalAngle(x1, dist);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Shooter RPM", getAverageRPM());
    SmartDashboard.putNumber("Shooter Angle", getAverageAngle());
  }
}
