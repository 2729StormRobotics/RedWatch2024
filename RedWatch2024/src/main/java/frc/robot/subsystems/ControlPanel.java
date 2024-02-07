// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ControlPanelConstants;
import frc.robot.commands.Shooter.Pivot;

import java.util.Map;

import com.kauailabs.navx.frc.AHRS;

public class ControlPanel extends SubsystemBase {

  private final ShuffleboardTab m_controlpanelTab;

  private final ShuffleboardLayout m_drivetrainStatus;
  private final ShuffleboardLayout m_indexerStatus;
  private final ShuffleboardLayout m_lightsStatus;
  private final ShuffleboardLayout m_intakeStatus;
  private final ShuffleboardLayout m_shooterStatus;


  private final GenericEntry setPivotEncoder;

  private final Drivetrain m_drivetrain;
  private final Indexer m_indexer;
  private final Intake m_intake;
  private final Shooter m_shooter;
  private final Vision m_vision;


  /** Creates a new ControlPanel. */
  public ControlPanel(Drivetrain drivetrain, Indexer indexer, Intake intake, Shooter shooter, Vision vision) {
    m_drivetrain = drivetrain;
    m_indexer = indexer;
    m_intake = intake;
    m_shooter = shooter;
    m_vision = vision;

    m_controlpanelTab = Shuffleboard.getTab(ControlPanelConstants.kShuffleboardTab);

    m_drivetrainStatus = m_controlpanelTab.getLayout("Drivetrain Status", BuiltInLayouts.kList)
      .withProperties(Map.of("Label position", "TOP"))
      .withPosition(0, 0)
      .withSize(2, 4);
      
    m_indexerStatus = m_controlpanelTab.getLayout("indexer Status", BuiltInLayouts.kList)
      .withProperties(Map.of("Label position", "TOP"))
      .withPosition(2, 0)
      .withSize(2, 2);

    m_lightsStatus = m_controlpanelTab.getLayout("Light Status", BuiltInLayouts.kList)
      .withProperties(Map.of("Label position", "TOP"))
      .withPosition(4, 0)
      .withSize(2, 3);

    m_intakeStatus = m_controlpanelTab.getLayout("Pivot Arm Status", BuiltInLayouts.kList)
      .withProperties(Map.of("Label position", "TOP"))
      .withPosition(8, 0)
      .withSize(1, 2);
    
    m_shooterStatus = m_controlpanelTab.getLayout("Telescoping Arm Status", BuiltInLayouts.kList)
      .withProperties(Map.of("Label position", "TOP"))
      .withPosition(10, 0)
      .withSize(1, 2);


    m_drivetrainStatus.addDouble("Average Speed", () -> m_drivetrain.getTurnRate()); // How fast the robot is
    m_drivetrainStatus.addNumber("Robot Heading", () -> m_drivetrain.getHeading()); // How far the robot is
    m_drivetrainStatus.addDouble("Pitch", () -> m_drivetrain.m_gyro.getPitch()); // Pitch of robot
    m_drivetrainStatus.addDouble("Yaw", () -> m_drivetrain.m_gyro.getYaw());// Yaw of robot
 
    m_indexerStatus.addDouble("Indexer Velocity", () -> m_indexer.getIndexerRPM()); // How fast the indexer is
    m_indexerStatus.addBoolean("Beam break status", () -> m_indexer.isNotePresent());

    m_shooterStatus.addNumber("Pivot Encoder", () -> m_shooter.getPivotAngle()); // Angle of shooter
    setPivotEncoder = m_intakeStatus.add("Pivot Encoder Input", m_shooter.getPivotAngle()).getEntry();
    m_shooterStatus.add(new Pivot(m_shooter, setPivotEncoder.get().getDouble()));  
    m_shooterStatus.addNumber("Flywheel RPM", () -> m_shooter.getAverageRPM());

    m_intakeStatus.addNumber("Intake RPM", () -> m_intake.getVelocity());
    
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}