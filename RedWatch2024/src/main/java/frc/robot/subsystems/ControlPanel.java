// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.IndexerConstants;
import frc.robot.Constants.PivotConstants;

public class ControlPanel extends SubsystemBase {

  private final Drivetrain m_drivetrain;
  private final Indexer m_indexer;
  private final Intake m_intake;
  private final Shooter m_shooter;
  private final Pivot m_pivot;
  private final Vision m_vision;
  public int r=225;
  public int b=0;
  public int g=0;
  private static ControlPanel controlPanel;

  /** Creates a new ControlPanel. */
  public ControlPanel() {
    m_drivetrain = Drivetrain.getInstance();
    m_indexer = Indexer.getInstance();

    m_intake = Intake.getInstance();
    m_shooter = Shooter.getInstance();
    m_pivot = Pivot.getInstance();
    m_vision = Vision.getInstance();

    PowerDistribution m_PD = new PowerDistribution(20,ModuleType.kRev);
    BuiltInAccelerometer BiA = new BuiltInAccelerometer();
    // Drivetrain
    SmartDashboard.putNumber("Average Speed", m_drivetrain.getTurnRate()); // How fast the robot is
    SmartDashboard.putNumber("Robot Heading", m_drivetrain.getHeading()); // How far the robot is
    SmartDashboard.putNumber("Pitch", m_drivetrain.m_gyro.getPitch()); // Pitch of robot
    SmartDashboard.putNumber("Yaw", m_drivetrain.m_gyro.getYaw());// Yaw of robot\
    // motor controllers
    SmartDashboard.putData("Swerve Drive", new Sendable() {
      @Override
      public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("SwerveDrive");
    
        builder.addDoubleProperty("Front Left Angle", () -> m_drivetrain.m_frontLeft.getPosition().angle.getRadians(), null);
        builder.addDoubleProperty("Front Left Velocity", () -> m_drivetrain.m_frontLeft.m_drivingEncoder.getVelocity(), null);
    
        builder.addDoubleProperty("Front Right Angle", () -> m_drivetrain.m_frontRight.getPosition().angle.getRadians(), null);
        builder.addDoubleProperty("Front Right Velocity", () -> m_drivetrain.m_frontRight.m_drivingEncoder.getVelocity(), null);
    
        builder.addDoubleProperty("Back Left Angle", () -> m_drivetrain.m_rearLeft.getPosition().angle.getRadians(), null);
        builder.addDoubleProperty("Back Left Velocity", () -> m_drivetrain.m_rearLeft.m_drivingEncoder.getVelocity(), null);
    
        builder.addDoubleProperty("Back Right Angle", () -> m_drivetrain.m_rearRight.getPosition().angle.getRadians(), null);
        builder.addDoubleProperty("Back Right Velocity", () -> m_drivetrain.m_rearRight.m_drivingEncoder.getVelocity(), null);
    
        builder.addDoubleProperty("Robot Angle", () -> Math.toRadians(m_drivetrain.getHeading()), null);
      }
    });
    SmartDashboard.putData("ResetGyro-180", new InstantCommand(() -> {m_drivetrain.m_gyro.setAngleAdjustment(-180);m_drivetrain.m_gyro.reset();}));
    

    // voltage
    SmartDashboard.putNumber("Total Voltage", m_PD.getVoltage());
    SmartDashboard.putNumber("Temp", m_PD.getTemperature());
    SmartDashboard.putData("PDH", m_PD);
    SmartDashboard.putData("Gyro", m_drivetrain.m_gyro);
    SmartDashboard.putData("BuiltInAccelerometer", BiA);

    //  Indexer
    SmartDashboard.putNumber("Indexer Velocity", m_indexer.getIndexerRPM());
    SmartDashboard.putNumber("Indexer Speed", IndexerConstants.kIndexerSpeed); // How fast the indexer is
 // How fast the indexer is
    SmartDashboard.putBoolean("Beam break status", m_indexer.isNotePresent());
    // Shooter
    SmartDashboard.putNumber("Pivot Encoder", m_pivot.getPivotAngle()); // Angle of shooter
    SmartDashboard.putNumber("Flywheel RPM", m_shooter.getAverageRPM());
    // setkPPivot = m_shooterStatus.add("Pivot PID", m_shooter.pivot)
    // setShooterSpeeds = m_shooterStatus.add("Shooter Speed", 1)
    // .withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 1)).getEntry();
    // setPivotSpeeds = m_shooterStatus.add("Pivot Speed", 1)
    // .withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 1)).getEntry();
    SmartDashboard.putNumber("Speaker Shooter Speeds", 0.75);
    // SmartDashboard.putNumber("pivot Speeds", 0.1);
    SmartDashboard.putNumber("kPPivot", PivotConstants.kPPivotUp);
    SmartDashboard.putNumber("kDPivot", PivotConstants.kDPivot);


    // Intake a
    SmartDashboard.putNumber("Intake RPM", m_intake.getVelocity());

    // Motor voltages
    SmartDashboard.putNumber("Average Shooter Voltages", (getMotorAppliedVoltage(m_shooter.m_bottomFlywheel) + getMotorAppliedVoltage(m_shooter.m_topFlywheel))/2);
    SmartDashboard.putNumber("Indexer Voltage", getMotorAppliedVoltage(m_indexer.m_IndexerMotor));
    SmartDashboard.putNumber("Intake Voltage", getMotorAppliedVoltage(m_intake.m_intakeMotor));
    
    // Drive motor voltages
    SmartDashboard.putNumber("Drive Front Left Voltage", getMotorAppliedVoltage(m_drivetrain.m_frontLeft.m_drivingSparkMax));
    SmartDashboard.putNumber("Drive Front Right Voltage", getMotorAppliedVoltage(m_drivetrain.m_frontRight.m_drivingSparkMax));
    SmartDashboard.putNumber("Drive Rear Left Voltage", getMotorAppliedVoltage(m_drivetrain.m_rearLeft.m_drivingSparkMax));
    SmartDashboard.putNumber("Drive Rear Right Voltage", getMotorAppliedVoltage(m_drivetrain.m_rearRight.m_drivingSparkMax));

  }
  private double getMotorAppliedVoltage(CANSparkMax cMax) {
    return cMax.getBusVoltage()*cMax.getAppliedOutput();
  }

  public static ControlPanel getInstance(){
    if(controlPanel == null){
      controlPanel = new ControlPanel();
    }
    return controlPanel;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    // Constants.ShooterConstants.kbottomPower = setShooterSpeeds.getDouble(0);
    // Constants.ShooterConstants.ktopPower = setShooterSpeeds.getDouble(0);
    // Constants.ShooterConstants.kPivotFF = SmartDashboard.getNumber("pivot FF", 0.0465);
    Constants.PivotConstants.kPPivotUp= SmartDashboard.getNumber("kPPivot", 0);
    Constants.PivotConstants.kDPivot = SmartDashboard.getNumber("kDPivot", 0);
    Constants.IndexerConstants.kIndexerSpeed = SmartDashboard.getNumber("Indexer Speed", 0.7);
    // SmartDashboard.putData("Scheduler", CommandScheduler.getInstance());
    SmartDashboard.putNumber("Fused Heading",m_drivetrain.m_gyro.getFusedHeading());
    SmartDashboard.putNumber("f_angle",m_drivetrain.m_gyro.getAngle());
  }
}
//~