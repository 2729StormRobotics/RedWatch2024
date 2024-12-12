// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.PhotonVision;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.subsystems.PhotonVision.VisionConstants.kMultiTagStdDevs;
import static frc.robot.subsystems.PhotonVision.VisionConstants.kSingleTagStdDevs;
import static frc.robot.subsystems.PhotonVision.VisionConstants.kTagLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// import org.littletonrobotics.junction.AutoLog;
// import org.littletonrobotics.junction.Logger;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;


 public interface VisionIO {

    // Updates arrays with information
    public static class VisionIOInputs {
      public Pose2d[] estimate = new Pose2d[0];
      public double timestamp = 0;
      public double[] timestampArray = new double[0];

      public int[] cameraTargets = new int[0];

      public boolean hasEstimate = false;

      public byte[] results;
    }

    // Updates the inputs above
    public default void updateInputs(VisionIOInputs inputs, Pose2d estimate) {
    }

    // Find a replacement of getLatestResult()
    public default PhotonPipelineResult getLatestResult(PhotonCamera camera) {
      return camera.getLatestResult();
    }

    public default boolean goodResult(PhotonPipelineResult result) {
      return result.hasTargets();
    }
    // FIX THE METHODS AND FIND WHERE THEY ARE (INITIALIZING)
    // public default Optional<Pose2d>[] getEstimates(PhotonPipelineResult[] results,
    //   PhotonPoseEstimator[] photonEstimator) {
    //     ArrayList<Optional<Pose2d>> estimates = new ArrayList<>();
    //     for (int i = 0; i < results.length; i++) {
    //       PhotonPipelineResult result = results[i];
    //       if (result.hasTargets()) {
    //         var est = photonEstimator[i].update();
    //         if (est.isPresent() && goodResult(result)) {
    //           estimates.add(Optional.of(est.get().estimatedPose.toPose2d()));
    //         } else {
    //           estimates.add(Optional.empty());
    //         }
    //       } else {
    //         estimates.add(Optional.empty());
    //       }
    //   }

    // Optional<Pose2d>[] estimatesArray = estimates.toArray(new Optional[0]);
    // return estimatesArray;
    // }
      
    // Cleans up the data for pose estimation
    public default Pose2d[] getEstimatesArray(PhotonPipelineResult[] results, PhotonPoseEstimator[] photonEstimator) {
      Optional<Pose2d>[] estimates = getEstimates(results, photonEstimator);
      Pose2d[] estimatesArray = new Pose2d[estimates.length];
      for (int i = 0; i < estimates.length; i++) {
        if (estimates[i].isPresent() && estimates[i].get() != null) {
          estimatesArray[i] = estimates[i].get();
        }
      }

      int count = 0;
      for (int i = 0; i < estimatesArray.length; i++) {
        if (estimatesArray[i] != null) {
          count++;
        }
      }

      Pose2d[] finalEstimates = new Pose2d[count];
      int index = 0;
      for (int i = 0; i < estimatesArray.length; i++) {
        if (estimatesArray[i] != null) {
          finalEstimates[index] = estimatesArray[i];
          index++;
        }
      }

      return finalEstimates;
    }

    // Standard deviations calculations for cameras estimated pose
    public default List<Matrix<N3, N1>> getStdArray(VisionIOInputs inputs, Pose2d currentPose) {
      List<Matrix<N3, N1>> stdsArray = new ArrayList<Matrix<N3, N1>>();
  
      for (int i = 0; i < getCameraTargets(inputs).length; i++) {
        if (getCameraTargets(inputs)[i].length != 0) {
          stdsArray.add(getEstimationStdDevs(inputs, currentPose, i));
        }
      }
  
      return stdsArray;
    }

    // Retrieve IDs of detected April Tags
    public default int[][] getCameraTargets(VisionIOInputs inputs) {
      return new int[][] { inputs.cameraTargets};
    }

    




}


// public class VisionIO extends SubsystemBase {
//   /** Creates a new VisionIO. */
 

//   @Override
//   public void periodic() {
//     // This method will be called once per scheduler run
//   }
// }
