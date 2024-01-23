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
  public final CANSparkMax motor;
  public final DigitalInput noteDetecter;


  /**
   * Creates a new Indexer.
   */
  public Indexer(kBeamBreakPort, kIndexMotorPort) {
      noteDetecter = new DigitalInput(kBeamBreakPort);
      motor = new com.revrobotics.CANSparkMax(kIndexMotorPort, MotorType.kBrushless);
  }

  public boolean isNotePresent() {
      return !noteDetecter.get();
  }
    
  public void initMotor(boolean invert){
    this.motor.restoreFactoryDefaults();
    this.motor.setIdleMode(IdleMode.kCoast);
    this.motor.setSmartCurrentLimit(kCurrentLimit);
    this.motor.setInverted(invert);
    this.motor.setSmartCurrentLimit(kStallLimit);
  }

  public void setSpeedMotor(speed) {
    //speed in percent
    motor.set(speed);
  }

  public void stop() {
    motor.set(0)
  }
    

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

