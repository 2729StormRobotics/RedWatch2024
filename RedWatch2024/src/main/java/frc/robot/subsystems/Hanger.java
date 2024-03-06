// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.HangerConstants.*;

import java.util.Map;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkAbsoluteEncoder;
import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.SparkAbsoluteEncoder.Type;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.HangerConstants;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class Hanger extends SubsystemBase {
  /** Creates a new Hanger. */

  private final ShuffleboardLayout m_controlPanelStatus;
  private final ShuffleboardTab m_controlPanelTab;

  public final CANSparkMax m_hangLeft;
  public final CANSparkMax m_hangRight;


  public Hanger() {
    m_hangLeft = new CANSparkMax(HangerConstants.kLeftHanger, MotorType.kBrushless);
    m_hangRight = new CANSparkMax(HangerConstants.kRightHanger, MotorType.kBrushless);


    setMotor(m_hangLeft, false);
    setMotor(m_hangRight, true);

    m_controlPanelTab = Shuffleboard.getTab("Hanger");
      m_controlPanelStatus = m_controlPanelTab.getLayout("Absolute Encoder", BuiltInLayouts.kList)
        .withSize(3, 3)
        .withProperties(Map.of("Label Position", "TOP"));
      shuffleboardInit();
  }

  private void shuffleboardInit() {
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


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    // m_controlPanelStatus.addNumber("Pivot Encoder", () -> getLeftPivotAngle());
  }
}
