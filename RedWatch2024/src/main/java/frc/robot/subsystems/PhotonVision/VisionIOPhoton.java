  // Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

//FIX CAM NAMES *******************************
// ******

package frc.robot.subsystems.PhotonVision;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.PhotonVision.VisionIO.VisionIOInputs;

import static frc.robot.subsystems.PhotonVision.VisionConstants.AMBIGUITY_THRESHOLD;
import static frc.robot.subsystems.PhotonVision.VisionConstants.cam1Name;
import static frc.robot.subsystems.PhotonVision.VisionConstants.cam1RobotToCam;
import static frc.robot.subsystems.PhotonVision.VisionConstants.kTagLayout;

// import org.littletonrobotics.junction.Logger;
// import org.littletonrobotics.junction.networktables.LoggedDashboardBoolean;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonPoseEstimator.PoseStrategy;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.net.PortForwarder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class VisionIOPhoton extends SubsystemBase {
  
  private final PhotonCamera camera1;
  private final PhotonPoseEstimator camera1Estimator;

  private Pose2d lastEstimate = new Pose2d();

  // LoggedDashboardBoolean killSideCams = new LoggedDashboardBoolean("Vision/KillSideCams", false);


  // Initialzes camera with a name and creates a PhotonPoseEstimator to process vision data
  // Error has all correct data types and its prolly tweaking
  public VisionIOPhoton() {
    PortForwarder.add(5800, "photonvision.local", 5800);

    camera1 = new PhotonCamera(cam1Name);
    camera1Estimator = new PhotonPoseEstimator(kTagLayout, PoseStrategy.MULTI_TAG_PNP_ON_COPROCESSOR, camera1, cam1RobotToCam);
    camera1Estimator.setMultiTagFallbackStrategy(PoseStrategy.LOWEST_AMBIGUITY);

    SmartDashboard.putBoolean("KillSideCams", false);
  }

  // Updates inputs for vision processing
  public void updateInputs(VisionIOInputs inputs, Pose2d currentEstimate) {
    lastEstimate = currentEstimate;
    
    PhotonPipelineResult[] results = getAprilTagResults();
    PhotonPoseEstimator[] photonEstimators = getAprilTagEstimators(currentEstimate);

    inputs.estimate = new Pose2d[] { new Pose2d() };

    // add code to check if the closest target is in front or back
    inputs.timestamp = estimateLatestTimestamp(results);

    if (hasEstimate(results)) {
      // inputs.results = results;
      inputs.estimate = getEstimatesArray(results, photonEstimators);
      inputs.hasEstimate = true;

      int[][] cameraTargets = getCameraTargets(results);
      inputs.cameraTargets = cameraTargets[0];

      Pose3d[] tags = getTargetsPositions(results);
      Logger.recordOutput("Vision/Targets3D", tags);
      Logger.recordOutput("Vision/Targets", Pose3dToPose2d(tags));
      Logger.recordOutput("Vision/TagCounts", tagCounts(results));
    } 
    else {
      inputs.timestamp = inputs.timestamp;
      inputs.hasEstimate = false;
    }

  // LITTLETON ROBOTICS STUFF **REPLACE LATER**
  // Logger.recordOutput("Vision/cam1/Connected", camera1.isConnected());
  // Logger.recordOutput("Vision/cam2/Connected", camera2.isConnected());
  // Logger.recordOutput("Vision/cam3/Connected", camera3.isConnected());
  }

  private PhotonPipelineResult[] getAprilTagResults() {
    if (killSideCams.get()) {
        PhotonPipelineResult cam1_result = getLatestResult(camera);

        printStuff("cam", cam_result);

        return new PhotonPipelineResult[] { cam1_result };
    }

    PhotonPipelineResult cam1_result = getLatestResult(camera1);
    PhotonPipelineResult cam2_result = getLatestResult(camera2);
    PhotonPipelineResult cam3_result = getLatestResult(camera3);

    printStuff("cam1", cam1_result);
    printStuff("cam2", cam2_result);
    printStuff("cam3", cam3_result);

    return new PhotonPipelineResult[] { cam1_result, cam2_result, cam3_result };
}

  





  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
