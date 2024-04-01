// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.IntakeConstants;
public class Intake extends SubsystemBase {
  //Initialize intake motor

  private static Intake intake;
  public final CANSparkMax m_intakeMotor;
  private final RelativeEncoder m_intakeEncoder;

  // Direction of the Intake (intake, eject, none)
  public static boolean m_intake_direction;


  /** Creates a new Intake. */
  public Intake() {

    // Initialize left and right motors
    m_intakeMotor = new CANSparkMax(IntakeConstants.kIntakeMotor, MotorType.kBrushless);

    m_intakeMotor.restoreFactoryDefaults();

    m_intakeMotor.setIdleMode(IdleMode.kCoast);

    m_intakeEncoder = m_intakeMotor.getEncoder();
    encoderInit(m_intakeEncoder);

    m_intake_direction = true;

  }

  private void encoderInit(RelativeEncoder encoder) {
    encoderReset(encoder);
  }

  private void encoderReset(RelativeEncoder encoder) {
    encoder.setPosition(0);
  }
  
  // Get average encoder velocity
  public double getVelocity() {
    return ((m_intakeMotor.getEncoder().getVelocity()));
  }

  // Runs Intake motors based on speed
  public void runIntake(double speed) {
    m_intakeMotor.set(speed);
  }

  // Stops Intake motors
  public void stopIntake() {
    m_intakeMotor.set(0);
    // LEDSegment.MainStrip.setColor(LEDs.orange);

  }

  // Runs Intake motors to intake an Note
  public void intakeItem() {
    runIntake(IntakeConstants.kIntakeMotorSpeed);
    // LEDSegment.MainStrip.setBandAnimation(LEDs.orange, 0.7);

  }

  // Runs Intake motors to eject an Note
  public void ejectItem() {
    runIntake(IntakeConstants.kEjectMotorSpeed);
  }

  // Sets up settings for Intake motors
  public void setMotor(CANSparkMax motor, boolean inverse) {
    motor.restoreFactoryDefaults();
    motor.setIdleMode(IdleMode.kBrake);
    motor.setInverted(inverse);
    motor.setSmartCurrentLimit(Constants.IntakeConstants.kCurrentLimit);
    motor.burnFlash();
  }

  // public boolean isNotePresent(){
  //   //checks for Note
  //   return !m_noteDetector.get();
  // }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public static Intake getInstance(){
    if(intake == null){
      intake = new Intake();
    }
    return intake;
  }
}
