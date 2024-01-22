// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


public class Trap extends SubsystemBase {

    /* Initialize Trap Subsystem */
    
    // Telescoping arm components
    private final com.revrobotics.CANSparkMax m_ArmMotor;
    private final RelativeEncoder m_ArmEncoder;
    private final AnalogPotentiometer stringPot = new AnalogPotentiometer(0, 180, 30);

    // Intake components
    private final com.revrobotics.CANSparkMax m_HandMotor;
    private final RelativeEncoder m_HandEncoder;



    // Constructor
    public Trap () {
        // Initialize telescoping arm
        // Need to assign motor ports
        m_Armmotor = new com.revrobotics.CANSparkMax(Constants.kMotorPort, MotorType.kBrushless);
        m_ArmMotor.restoreFactoryDefaults();
        m_ArmMotor.setIdleMode(IdleMode.kBrake);
        m_ArmMotor.setSmartCurrentLimit(45);

        m_ArmEncoder = m_motor.getEncoder();
        m_ArmEncoder.setPosition(0);

        // Initialize hand components
        m_HandMotor = new com.revrobotics.CANSparkMax(Constants.kMotorPort, MotorType.kBrushless);
        m_HandMotor.restoreFactoryDefaults();
        m_HandMotor.setIdleMode(IdleMode.kBrake);
        m_HandMotor.setSmartCurrentLimit(45);

        m_HandEncoder = m_motor.getEncoder();
        m_HandEncoder.setPosition(0);
        
    }

    // Arm
    //Ask about motor speeds
    public void setArmSpeed(double speed) {
        m_ArmMotor.set(speed);
    }

    public void stopArmMotor() {
        m_ArmMotor.set(0);
    }

    // Hand
    public void setHandSpeed(double speed) {
        m_HandMotor.set(speed);
    }

    public void stopHandMotor() {
        m_HandMotor.set(0);
    }

    // Arm Encoder
    public double getArmSpeed() {
        return m_ArmEncoder.getVelocity();
    }

    public double getArmPosition() {
        return m_ArmEncoder.getPosition();
    }

    public void resetArmPosition() {
        m_ArmEncoder.setPosition(0);
    }

    // Hand Encoder
    public double getHandSpeed() {
        return m_HandEncoder.getVelocity();
    }

    public double getHandPosition() {
        return m_HandEncoder.getPosition();
    }

    public void resetHandPosition() {
        m_HandEncoder.setPosition(0);
    }

    // Potentiometer
    public double getPotValue() {
        return stringPot.get();
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    } 
}
