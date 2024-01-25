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

import edu.wpi.first.wpilibj.AnalogPotentiometer;


public class TrapSubsytem extends SubsystemBase {

    /* Initialize Trap Subsystem */
    
    // Telescoping arm components
    public final CANSparkMax m_ArmMotor;
    public final RelativeEncoder m_ArmEncoder;
    private final AnalogPotentiometer stringPot = new AnalogPotentiometer(0, 180, 30);

    // Intake components
    private final CANSparkMax m_HandMotor;
    private final RelativeEncoder m_HandEncoder;



    // Constructor
    public TrapSubsytem () {
        // Initialize telescoping arm
        // Need to assign motor ports
        m_Armmotor = new CANSparkMax(Constants.TrapConstants.kArmMotorID, MotorType.kBrushless);
        m_ArmMotor.restoreFactoryDefaults();
        m_ArmMotor.setIdleMode(IdleMode.kBrake);
        m_ArmMotor.setSmartCurrentLimit(45);

        m_ArmEncoder = m_motor.getEncoder();
        m_ArmEncoder.setPosition(0);

        // Initialize hand components
        m_HandMotor = new CANSparkMax(Constants.TrapConstants.kHandMotorID, MotorType.kBrushless);
        m_HandMotor.restoreFactoryDefaults();
        m_HandMotor.setIdleMode(IdleMode.kBrake);
        m_HandMotor.setSmartCurrentLimit(45);

        m_HandEncoder = m_motor.getEncoder();
        m_HandEncoder.setPosition(0);
        
    }

    // Arm Motor
    //Need to work on motor speeds and constants
    public void setArmSpeed(double speed) {
        m_ArmMotor.set(speed);
    }

    public void stopArmMotor() {
        m_ArmMotor.set(0);
    }

    // Hand Motor
    public void handIntake(double speed) {
        m_HandMotor.set(speed);
    }

    public void handOuttake(double speed) {
        m_HandMotor.set(-speed);
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

    public double getPotDifference(double potDist) {
        return Math.abs(getPotValue() - potDist);
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    } 
}
