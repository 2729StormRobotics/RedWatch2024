// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.List;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class PoseEstimatorSubsystem extends SubsystemBase {
  
  private final PhotonCamera photonCamera;
  private final DrivetrainSubsystem drivetrainSubsystem;

  private static final List<Pose3d> targetPoses = Collections.unmodifiableList(List.of(
      new Pose3d(x: 3.0, y: 1.165, 0.287 + 0.165, new Rotation3d(roll: 0, pitch: 0, degreesToRadians(degrees: 180.0))),
      new Pose3d(x: 3.0, y: 0.0, 0.287 + 0.165, new Rotation3d(roll: 0, pitch: 0, degreesToRadians(degrees: 180.0)))));
  
  
  /** Creates a new PoseEstimatorSubsystem. */
  public PoseEstimatorSubsystem() {}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
