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

  public final CANSparkMax m_hangLeftPivot;
  public final CANSparkMax m_hangRightPivot;

  public final SparkAbsoluteEncoder m_pivotLeftEncoder; /* = new DutyCycleEncoder(8); */
  // public final DutyCycleEncoder m_pivotRightEncoder = new DutyCycleEncoder(9);


  public Hanger() {
    m_hangLeftPivot = new CANSparkMax(kHangerLeftPivotFollowerPort, MotorType.kBrushless);
    m_hangRightPivot = new CANSparkMax(kHangerRightPivotPort, MotorType.kBrushless);

    m_pivotLeftEncoder = m_hangLeftPivot.getAbsoluteEncoder(Type.kDutyCycle);

    setMotor(m_hangLeftPivot, false);
    setMotor(m_hangRightPivot, true);

    m_controlPanelTab = Shuffleboard.getTab("Hanger");
      m_controlPanelStatus = m_controlPanelTab.getLayout("Absolute Encoder", BuiltInLayouts.kList)
        .withSize(3, 3)
        .withProperties(Map.of("Label Position", "TOP"));
      shuffleboardInit();
  }

  private void shuffleboardInit() {
    m_controlPanelStatus.addNumber("Pivot Encoder", () -> getLeftPivotAngle());
  }

   public void turnMotor(double speed) {
      m_hangLeftPivot.set(speed);
      m_hangRightPivot.set(speed);
    }

  private void pivotEncoderInit(AbsoluteEncoder encoder) {
    encoder.setPositionConversionFactor(kAnglePerRevolution);
  }

  // public void encoderReset(RelativeEncoder encoder) {
  //   encoder.setPosition(kLeftPivotArmNeutral);
  //   encoder.setPosition(kRightPivotArmNeutral);
  // }

  public double getLeftPivotAngle() {
    return (360 - m_pivotLeftEncoder.getPosition() * 360) - 114.03170385079261 + 90;
  }

  // public double getRightPivotAngle() {
  //   return (360 - m_pivotRightEncoder.getAbsolutePosition() * 360) - 114.03170385079261 + 90;
  // }

  public void setMotor(CANSparkMax motor, boolean inverse) {
    motor.restoreFactoryDefaults();
    motor.setIdleMode(IdleMode.kBrake);
    motor.setInverted(inverse);
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    // m_controlPanelStatus.addNumber("Pivot Encoder", () -> getLeftPivotAngle());
  }
}
