// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.IndexerConstants.*;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import frc.robot.Constants.IndexerConstants;

public class Indexer extends SubsystemBase {
  /** Creates a new Indexer. */
 
  //Spark used for indexer motor
  public final CANSparkMax m_IndexerMotor;
  public final DigitalInput m_NoteDectector;


  /**
   * Creates a new Indexer.
   */
  public Indexer() {
      // Creates an note dectector instance
      m_NoteDectector = new DigitalInput(IndexerConstants.kBeamBreakPort);
      // Creates an indexer motor instance
      m_IndexerMotor = new com.revrobotics.CANSparkMax(IndexerConstants.kIndexMotorPort, MotorType.kBrushless);
  }
    // Determines whether note is detected or not
  public boolean isNotePresent() {
      return !m_NoteDectector.get();
  }
   
  //PID of motor
  public void initMotor(boolean invert){
    this.m_IndexerMotor.restoreFactoryDefaults();
    this.m_IndexerMotor.setIdleMode(com.revrobotics.CANSparkMax.IdleMode.kCoast);
    this.m_IndexerMotor.setSmartCurrentLimit(kCurrentLimit);
    this.m_IndexerMotor.setInverted(invert);
    this.m_IndexerMotor.setSmartCurrentLimit(kStallLimit);
  }

  public void runIndexer(double speed) {
    //speed in percent
    m_IndexerMotor.set(speed);
  }
    //Sets motor at indexer speed, 50%, (runs motor)
  public void runNoteThrough() {
    runIndexer(IndexerConstants.kIndexerSpeed);
  }

  public void stop() {
    //Sets motor at speed 0 (stops running the motor)
    m_IndexerMotor.set(0);
  }
    

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putBoolean("Note detected?", isNotePresent());
  }

  public void load(double d) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'load'");
  }
}
