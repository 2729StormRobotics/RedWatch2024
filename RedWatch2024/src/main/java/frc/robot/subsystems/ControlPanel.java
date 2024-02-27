// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.Map;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.ControlPanelConstants;
import frc.robot.Constants.IndexerConstants;
import frc.robot.Constants.IntakeConstants;
import frc.robot.Constants.ShooterConstants;
import frc.robot.commands.LEDs.PartyMode;
import frc.robot.commands.LEDs.setAllLEDs;
import frc.robot.commands.LEDs.setDefault;
import frc.robot.commands.Shooter.Pivot;


public class ControlPanel extends SubsystemBase {

  private final Drivetrain m_drivetrain;
  private final Indexer m_indexer;
  // private final LEDs m_leds;
  private final Intake m_intake;
  private final Shooter m_shooter;
  private final Vision m_vision;


  /** Creates a new ControlPanel. */
  public ControlPanel(Drivetrain drivetrain, Indexer indexer, Intake intake, Shooter shooter, Vision vision) {
    m_drivetrain = drivetrain;
    m_indexer = indexer;
    // m_leds = leds;
    m_intake = intake;
    m_shooter = shooter;
    m_vision = vision;

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
    

    // voltage
    SmartDashboard.putNumber("Total Voltage", m_PD.getVoltage());
    SmartDashboard.putNumber("Temp", m_PD.getTemperature());
    SmartDashboard.putData("PDH", m_PD);
    SmartDashboard.putData("Gyro", m_drivetrain.m_gyro);
    SmartDashboard.putData("BuiltInAccelerometer", BiA);

    //  Indexer
    SmartDashboard.putNumber("Indexer Velocity", m_indexer.getIndexerRPM()); // How fast the indexer is
    SmartDashboard.putBoolean("Beam break status", m_indexer.isNotePresent());
    // Shooter
    SmartDashboard.putNumber("Pivot Encoder", m_shooter.getPivotAngle()); // Angle of shooter
    SmartDashboard.putNumber("Flywheel RPM", m_shooter.getAverageRPM());
    // setkPPivot = m_shooterStatus.add("Pivot PID", m_shooter.pivot)
    // setShooterSpeeds = m_shooterStatus.add("Shooter Speed", 1)
    // .withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 1)).getEntry();
    // setPivotSpeeds = m_shooterStatus.add("Pivot Speed", 1)
    // .withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 1)).getEntry();
    SmartDashboard.putNumber("Shooter Speeds", 0.75);
    // SmartDashboard.putNumber("pivot Speeds", 0.1);
    SmartDashboard.putNumber("kPPivot", ShooterConstants.kPPivot);
    SmartDashboard.putNumber("kDPivot", ShooterConstants.kDPivot);


    // Intake a
    SmartDashboard.putNumber("Intake RPM", m_intake.getVelocity());

    // Lights
    // m_lightsStatus.add("Set LEDs to Red", new setAllLEDs(LEDs.red));  
    // m_lightsStatus.add("Set to Default",new setDefault());  
    // m_lightsStatus.add("Party Mode", new PartyMode(m_leds));  

    
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    // Constants.ShooterConstants.kLeftPower = setShooterSpeeds.getDouble(0);
    // Constants.ShooterConstants.kRightPower = setShooterSpeeds.getDouble(0);
    
    Constants.ShooterConstants.kLeftPowerSpeaker = SmartDashboard.getNumber("Shooter Speeds", 0.75);
    Constants.ShooterConstants.kRightPowerSpeaker = SmartDashboard.getNumber("Shooter Speeds", 0.75);
    // Constants.ShooterConstants.kPivotFF = SmartDashboard.getNumber("pivot FF", 0.0465);
    Constants.ShooterConstants.kPPivot = SmartDashboard.getNumber("kPPivot", 0);
    Constants.ShooterConstants.kDPivot = SmartDashboard.getNumber("kDPivot", 0);
    SmartDashboard.putNumber("Auto angle", m_shooter.getOptimalAngle(m_vision.getSpeakerDistance()));
    SmartDashboard.putData("Scheduler", CommandScheduler.getInstance());
  }
}
