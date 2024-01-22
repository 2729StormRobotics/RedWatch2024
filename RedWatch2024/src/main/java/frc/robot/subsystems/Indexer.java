// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.IndexerConstants.*;
import edu.wpi.first.wpilibj.DigitalInput;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax.IdleMode;

public class Indexer extends SubsystemBase {
  /** Creates a new Indexer. */
  public final CANSparkMax m_bottomMotor;

  public final DigitalInput noteDetecter;



  public Indexer() {
    noteDetecter = new DigitalInput(kBeamBreakPort);
    m_bottomMotor  = new com.revrobotics.CANSparkMax(kIndexMotorPort, MotorType.kBrushless);
    motorInit(m_bottomMotor, false);



  }

  public boolean isNotePresent() {
    return !noteDetecter.get();

  }
    
  public void motorInit(CANSparkMax motor, boolean invert){
    motor.restoreFactoryDefaults();
    motor.setIdleMode(IdleMode.kCoast);
    motor.setSmartCurrentLimit(kCurrentLimit);
    motor.setInverted(invert);
    motor.setSmartCurrentLimit(kStallLimit);


  }

  public void setSpeedMotor(speed) {
    //speed in percent
    m_bottomMotor.set(speed);
  }

  public void stop() {
    m_bottomMotor.set(0)

  }
    

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

