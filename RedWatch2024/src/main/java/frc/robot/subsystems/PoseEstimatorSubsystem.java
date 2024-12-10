package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.util.MathUtil;  
import edu.wpi.first.cameraserver.vision.Pose3d;
import edu.wpi.first.cameraserver.vision.Rotation3d;
import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.Nat;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Twist2d;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.wpilibj.Timer;

import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;

public class PoseEstimatorSubsystem extends SubsystemBase {

  private final PhotonCamera photonCamera;
  private final DrivetrainSubsystem drivetrainSubsystem;

  // Target poses for vision
  private static final List<Pose3d> targetPoses = List.of(
    new Pose3d(3.0, 1.165, 0.287 + 0.165, new Rotation3d(0, 0, Math.toRadians(180.0))),
    new Pose3d(3.0, 0.0, 0.287 + 0.165, new Rotation3d(0, 0, Math.toRadians(180.0)))
  );

  // Define standard deviations for state estimation (example values, adjust as necessary)
  private static final Matrix<N3, N1> stateStdDevs = VecBuilder.fill(0.05, 0.05, Math.toRadians(5));

  private Pose2d basePose = new Pose2d(); 
  private Pose2d latestPose = new Pose2d();
  private final NavigableMap<Double, PoseUpdate> updates = new TreeMap<>();
  private final Matrix<N3, N1> q = new Matrix<>(Nat.N3(), Nat.N1());

  public PoseEstimatorSubsystem(PhotonCamera photonCamera, DrivetrainSubsystem drivetrainSubsystem) {
    this.photonCamera = photonCamera;
    this.drivetrainSubsystem = drivetrainSubsystem;

    // Initialize the PoseEstimator with the state standard deviations
    for (int i = 0; i < 3; ++i) {
      q.set(i, 0, stateStdDevs.get(i, 0) * stateStdDevs.get(i, 0));
    }
  }

  @Override
  public void periodic() {
    // Update pose estimation here
    update();
  }

  // Update robot's pose based on drivetrain and vision data
  private void update() {
    // Process vision data (this is a placeholder; actual vision data needs to be provided)
    List<TimestampedVisionUpdate> visionUpdates = getVisionData();
    for (var visionUpdate : visionUpdates) {
      addVisionData(visionUpdate);
    }

    // Process drivetrain data
    Twist2d driveTwist = drivetrainSubsystem.getTwist();
    addDriveData(Timer.getFPGATimestamp(), driveTwist);

    // Calculate the latest pose
    latestPose = basePose;
    for (var updateEntry : updates.entrySet()) {
      latestPose = updateEntry.getValue().apply(latestPose, q);
    }
  }

  // Method to simulate obtaining vision data (replace with actual vision data logic)
  private List<TimestampedVisionUpdate> getVisionData() {
    // Simulated vision data
    return List.of(new TimestampedVisionUpdate(Timer.getFPGATimestamp(), new Pose2d(3.0, 2.0, new Rotation2d()), stateStdDevs));
  }

  // Method to add drive data (this would come from the drivetrain)
  private void addDriveData(double timestamp, Twist2d twist) {
    updates.put(timestamp, new PoseUpdate(twist, new ArrayList<>()));
  }

  // Method to add vision data (this would come from your vision processing)
  private void addVisionData(TimestampedVisionUpdate visionUpdate) {
    double timestamp = visionUpdate.timestamp();
    var visionData = new VisionUpdate(visionUpdate.pose(), visionUpdate.stdDevs());

    if (updates.containsKey(timestamp)) {
      // Update existing entry
      var oldVisionUpdates = updates.get(timestamp).visionUpdates();
      oldVisionUpdates.add(visionData);
      oldVisionUpdates.sort(VisionUpdate.compareDescStdDev);
    } else {
      // Insert new entry
      updates.put(timestamp, new PoseUpdate(new Twist2d(), List.of(visionData)));
    }
  }

  // Nested classes for pose updates and vision updates
  private static class PoseUpdate {
    private final Twist2d twist;
    private final List<VisionUpdate> visionUpdates;

    public PoseUpdate(Twist2d twist, List<VisionUpdate> visionUpdates) {
      this.twist = twist;
      this.visionUpdates = visionUpdates;
    }

    public Pose2d apply(Pose2d lastPose, Matrix<N3, N1> q) {
      Pose2d pose = lastPose.exp(twist);
      for (var visionUpdate : visionUpdates) {
        // Apply vision updates using Kalman filtering (simplified)
        var visionTwist = pose.log(visionUpdate.pose());
        pose = pose.exp(new Twist2d(visionTwist.dx, visionTwist.dy, visionTwist.dtheta));
      }
      return pose;
    }
  }

  public static record VisionUpdate(Pose2d pose, Matrix<N3, N1> stdDevs) {
    public static final Comparator<VisionUpdate> compareDescStdDev =
        (VisionUpdate a, VisionUpdate b) -> -Double.compare(
            a.stdDevs().get(0, 0) + a.stdDevs().get(1, 0),
            b.stdDevs().get(0, 0) + b.stdDevs().get(1, 0));
  }

  public static record TimestampedVisionUpdate(double timestamp, Pose2d pose, Matrix<N3, N1> stdDevs) {}
}
