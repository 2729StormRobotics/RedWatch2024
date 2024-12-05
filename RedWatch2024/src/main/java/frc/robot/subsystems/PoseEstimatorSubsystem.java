package frc.robot.subsystems;

import java.util.List;
import java.util.Collections;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.util.MathUtil;  // Assuming you need MathUtil for degrees to radians conversion
import edu.wpi.first.cameraserver.vision.Pose3d; // Import the Pose3d class, adjust as per your project's dependencies
import edu.wpi.first.cameraserver.vision.Rotation3d; // Import Rotation3d class, adjust as necessary

public class PoseEstimatorSubsystem extends SubsystemBase {

  private final PhotonCamera photonCamera; // Assuming PhotonCamera is a class you're using for vision
  private final DrivetrainSubsystem drivetrainSubsystem;

  // Define target poses, make sure the Pose3d and Rotation3d classes are correctly imported
  private static final List<Pose3d> targetPoses = Collections.unmodifiableList(List.of(
      new Pose3d(3.0, 1.165, 0.287 + 0.165, new Rotation3d(0, 0, Math.toRadians(180.0))),
      new Pose3d(3.0, 0.0, 0.287 + 0.165, new Rotation3d(0, 0, Math.toRadians(180.0)))));

  private static final Vector<N7> stateStdDevs = VecBuilder.fill(n1: 0.05, n2: 0.05, Units.degreesToRadians(degrees: 5), n4: 0.05, n5: 0.05)

  private static final Vector<N5> localMeasurementStdDevs = VecBuilder.fill()
    /** Creates a new PoseEstimatorSubsystem. */
  public PoseEstimatorSubsystem(PhotonCamera photonCamera, DrivetrainSubsystem drivetrainSubsystem) {
    this.photonCamera = photonCamera;
    this.drivetrainSubsystem = drivetrainSubsystem;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    // Perform any logic needed for pose estimation here
  }
}
