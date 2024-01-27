// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.IndexerConstants.*;
import edu.wpi.first.wpilibj.DigitalInput;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import frc.robot.Constants;
import frc.robot.Constants.IndexerConstants;

public class Indexer extends SubsystemBase {
  /** Creates a new Indexer. */
 
  //Spark used for indexer motor
  public final CANSparkMax motor;
  public final DigitalInput noteDetector;


  /**
   * Creates a new Indexer.
   */
  public Indexer() {
      noteDetector = new DigitalInput(IndexerConstants.kBeamBreakPort);
      motor = new com.revrobotics.CANSparkMax(IndexerConstants.kIndexMotorPort, MotorType.kBrushless);
  }
    // checks for note
  public boolean isNotePresent() {
      return !noteDetector.get();
  }
   
  //PID of motor
  public void initMotor(boolean invert){
    this.motor.restoreFactoryDefaults();
    this.motor.setIdleMode(com.revrobotics.CANSparkMax.IdleMode.kCoast);
    this.motor.setSmartCurrentLimit(kCurrentLimit);
    this.motor.setInverted(invert);
    this.motor.setSmartCurrentLimit(kStallLimit);
  }

  public void runIndexer(double speed) {
    //speed in percent
    motor.set(speed);
  }

  public void runNoteThrough() {
    runIndexer(IndexerConstants.kIndexerSpeed);
  }

  public void stop() {
    //Stops motor
    motor.set(0);
  }
    

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void load(double d) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'load'");
  }
}
