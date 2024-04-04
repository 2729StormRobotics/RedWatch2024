// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {
  public static double passivePower = 0.0;

  private static Shooter shooter;
  // Motors and encoders for the flywheels
  public final CANSparkMax m_bottomFlywheel;
  public final RelativeEncoder m_bottomFlywheelEncoder;
  public final CANSparkMax m_topFlywheel;
  public final RelativeEncoder m_topFlywheelEncoder;
  
  /** Creates a new Shooter. */
  public Shooter() {
    // All motors initialization
    m_bottomFlywheel = new CANSparkMax(Constants.ShooterConstants.kBottomFlywheelID, MotorType.kBrushless);
    m_topFlywheel = new CANSparkMax(Constants.ShooterConstants.kTopFlywheelID, MotorType.kBrushless);

    motorInit(m_bottomFlywheel, Constants.ShooterConstants.kLeftFlywheelInverted);
    motorInit(m_topFlywheel, Constants.ShooterConstants.kRightFlywheelInverted);

    // Flywheel encoder initialization
    m_bottomFlywheelEncoder = m_bottomFlywheel.getEncoder();
    m_topFlywheelEncoder = m_topFlywheel.getEncoder();
    
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
  public void setShooterSpeed(double topPower, double bottomPower) {
    m_bottomFlywheel.setVoltage(bottomPower*12);
    m_topFlywheel.setVoltage(topPower*12);
    // LEDSegment.MainStrip.setColor(LEDs.green);

  }

  public void stopShooterMotors() {
    m_bottomFlywheel.set(0);
    m_topFlywheel.set(0);
    // LEDSegment.MainStrip.setFadeAnimation(LEDs.red, 0.7);

  }

  /*
   * GETTERS
   */
  public double getLeftVoltage() {
    return m_bottomFlywheel.getBusVoltage()*m_bottomFlywheel.getAppliedOutput();
  }

  public double getRightVoltage() {
    return m_topFlywheel.getBusVoltage()*m_topFlywheel.getAppliedOutput();
  }

  public double gettopPower(){
    return m_topFlywheelEncoder.getVelocity() * m_topFlywheelEncoder.getVelocityConversionFactor();
  }

  // returns rpm of left shooter motor
  public double getLeftRPM() {
    return m_bottomFlywheelEncoder.getVelocity();
  }

  // returms rpm of right shooter motor
  public double getRightRPM() {
    return m_topFlywheelEncoder.getVelocity();
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
    if (getAverageRPM()>=5800){
      Blinkin.getInstance().green();
    }
    else{
      if(getAverageRPM()>=2500){
        Blinkin.getInstance().yellow();
      }
    }
  }

  public static Shooter getInstance(){
    if(shooter == null){
      shooter = new Shooter();
    }
    return shooter;
  }
}
