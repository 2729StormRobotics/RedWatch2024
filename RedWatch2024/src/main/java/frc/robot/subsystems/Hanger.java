// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.HangerConstants;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class Hanger extends SubsystemBase {
  private static Hanger hanger;
  /** Creates a new Hanger. */

  public final CANSparkMax m_hangLeft;
  public final CANSparkMax m_hangRight;


  public Hanger() {
    m_hangLeft = new CANSparkMax(HangerConstants.kLeftHanger, MotorType.kBrushless);
    m_hangRight = new CANSparkMax(HangerConstants.kRightHanger, MotorType.kBrushless);


    setMotor(m_hangLeft, false);
    setMotor(m_hangRight, true);

  }


  public void setLeftSpeed(double speed) {
    m_hangLeft.set(speed);
  }

  public void setRightSpeed(double speed) {
    m_hangRight.set(speed);
  }

  public void setSpeed(double speed) {
    m_hangRight.set(speed);
    m_hangLeft.set(speed);
  }

  public void stopMotors (){
    m_hangLeft.set(0);
    m_hangRight.set(0);
  }

  public void setMotor(CANSparkMax motor, boolean inverse) {
    motor.restoreFactoryDefaults();
    motor.setIdleMode(IdleMode.kBrake);
    motor.setInverted(inverse);
    motor.setSmartCurrentLimit(HangerConstants.kCurrentLimit);
    motor.burnFlash();
  }
  public static Hanger getInstance(){
    if(hanger == null){
      hanger = new Hanger();
    }
    return hanger;
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    // m_controlPanelStatus.addNumber("Pivot Encoder", () -> getLeftPivotAngle());
  }
}
