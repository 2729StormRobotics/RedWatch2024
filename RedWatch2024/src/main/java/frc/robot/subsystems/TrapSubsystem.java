// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;

import java.util.Map;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.TrapConstants;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;

import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class TrapSubsystem extends SubsystemBase {

    /* Initialize Trap Subsystem */

    // Telescoping arm components
    // NEO for vertical movement
    public final CANSparkMax m_ArmMotor;
    public final RelativeEncoder m_ArmEncoder;
    // NEO for rotation
    public final CANSparkMax m_AngleMotor;
    public final RelativeEncoder m_AngleEncoder;
    // String Potentiometer
    private final AnalogPotentiometer stringPot = new AnalogPotentiometer(0, 180, 30);

    // Intake components
    // NEO 550
    private final CANSparkMax m_HandMotor;
    private final RelativeEncoder m_HandEncoder;
    // Beam Break
    private final DigitalInput m_NoteDetector;

    // Shuffleboard and Smartdashboard
    private final ShuffleboardLayout m_controlPanelStatus;
    private final ShuffleboardTab m_controlPanelTab;

    // Constructor
    public TrapSubsystem() {
        // Initialize telescoping arm
        m_ArmMotor = new CANSparkMax(Constants.TrapConstants.kArmMotorID, MotorType.kBrushless);
        m_ArmMotor.restoreFactoryDefaults();
        m_ArmMotor.setIdleMode(IdleMode.kBrake);
        m_ArmMotor.setSmartCurrentLimit(45);

        m_ArmEncoder = m_ArmMotor.getEncoder();
        m_ArmEncoder.setPosition(0);

        //
        m_AngleMotor = new CANSparkMax(Constants.TrapConstants.kAngleMotorID, MotorType.kBrushless);
        m_AngleMotor.restoreFactoryDefaults();
        m_AngleMotor.setIdleMode(IdleMode.kBrake);
        m_AngleMotor.setSmartCurrentLimit(45);

        m_AngleEncoder = m_AngleMotor.getEncoder();
        m_AngleEncoder.setPosition(0);

        // Initialize hand components
        m_HandMotor = new CANSparkMax(Constants.TrapConstants.kHandMotorID, MotorType.kBrushless);
        m_HandMotor.restoreFactoryDefaults();
        m_HandMotor.setIdleMode(IdleMode.kBrake);
        m_HandMotor.setSmartCurrentLimit(45);

        m_HandEncoder = m_HandMotor.getEncoder();
        m_HandEncoder.setPosition(0);

        m_NoteDetector = new DigitalInput(TrapConstants.kBeamBreakPortID);

        // Shuffleboard and Smartdashboard
        m_controlPanelTab = Shuffleboard.getTab("stringpot");
        m_controlPanelStatus = m_controlPanelTab.getLayout("String Pot", BuiltInLayouts.kList)
                .withSize(3, 3)
                .withProperties(Map.of("Label position", "TOP"));

        shuffleboardInit();

    }

    /* SETTER Methods */
    // Arm Motor
    public void setArmSpeed(double speed) {
        m_ArmMotor.set(speed);
    }

    public void stopArmMotor() {
        m_ArmMotor.set(0);
    }

    // Arm angle motor
    public void setAngleSpeed(double speed) {
        m_AngleMotor.set(speed);
    }

    public void stopAngleMotor() {
        m_AngleMotor.set(0);
    }

    // Hand Motor
    public void handIntake(double speed) {
        m_HandMotor.set(speed);
    }

    public void handOuttake(double speed) {
        m_HandMotor.set(-1 * speed);
    }

    public void stopHandMotor() {
        m_HandMotor.set(0);
    }

    // Encoders
    public void resetArmPosition() {
        m_ArmEncoder.setPosition(0);
    }

    public void resetAnglePosition() {
        m_AngleEncoder.setPosition(0);
    }

    public void setAnglePosition(double angle) {
        m_AngleEncoder.setPosition(angle);
    }

    public void resetHandPosition() {
        m_HandEncoder.setPosition(0);
    }

    // Initialize the shuffleboard.
    private void shuffleboardInit() {
        // Arm
        m_controlPanelStatus.addNumber("Arm Length", () -> getPotValue());
        m_controlPanelStatus.addNumber("Angle Encoder", () -> getAnglePosition());

        // Hand
        m_controlPanelStatus.addBoolean("Beam Break", () -> isNotePresent());

    }

    /* GETTER Methods */
    // Arm Encoder
    public double getArmSpeed() {
        return m_ArmEncoder.getVelocity();
    }

    public double getArmPosition() {
        return m_ArmEncoder.getPosition();
    }

    // Angle Encoder
    public double getAngleSpeed() {
        return m_AngleEncoder.getVelocity();
    }

    public double getAnglePosition() {
        return m_AngleEncoder.getPosition();
    }

    // Hand Encoder
    public double getHandSpeed() {
        return m_HandEncoder.getVelocity();
    }

    public double getHandPosition() {
        return m_HandEncoder.getPosition();
    }

    // Potentiometer
    public double getPotValue() {
        return stringPot.get();
    }

    public double getPotDifference(double potDist) {
        return Math.abs(getPotValue() - potDist);
    }

    // Beam Break
    // Checks if note is present
    public boolean isNotePresent() {
        return !m_NoteDetector.get();
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }
}
