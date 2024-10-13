package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.Constants.VisionConstants;
import frc.robot.subsystems.LED.BlinkinLEDController;
import frc.robot.subsystems.Vision;
import frc.robot.subsystems.groundIntake.GroundIntake;
import frc.robot.subsystems.indexer.Indexer;
// import frc.robot.subsystems.LEDs.LEDSegment;
import frc.robot.subsystems.pivotArm.PivotArm;
import frc.robot.subsystems.shooter.Shooter;
import frc.robot.util.misc.LinearInterpolationTable;
// import frc.robot.subsystems.LEDs;

public class ShootingCommands {
  private static final LinearInterpolationTable ShooterInterpolationTable =
      new LinearInterpolationTable(Constants.ShootingPoints);

  private static BlinkinLEDController m_blinkin;

  public static Command AutoPivot(Vision vision, PivotArm pivot, boolean IsVision) {
    double m_turnError;
    double m_turnPower;
    double m_setpoint;
    double m_ff;
    double m_angle = 32;
    Vision m_vision;
    double deltaHeight;
    double deltaAngle;
    double dist;
    double timeElapsed = 0;

    if (!IsVision) {
      m_setpoint = m_angle;
    } else {
      deltaAngle = Math.toRadians(VisionConstants.limelightAngle + vision.getY());
      deltaHeight = VisionConstants.speakerTagHeight - VisionConstants.limelightHeight;
      dist = deltaHeight / Math.tan(deltaAngle);
      m_setpoint = ShooterInterpolationTable.getOutput(dist);

      if (m_setpoint < 10) {
        m_setpoint = 10;
      }
      // if (vision.getY() == 0.0) {
      //   m_setpoint = 49;
      // }
    }
    return pivot.PIDCommand(m_setpoint);
  }

  public static SequentialCommandGroup FeedAndShoot(Indexer indexer, Shooter shooter) {
    return new SequentialCommandGroup(
        indexer.OuttakeLoopCommand(5), new WaitCommand(1.5), shooter.runVoltage(0));
  }

  public static Command IntakeThenLoad(Indexer indexer, GroundIntake groundIntake, PivotArm pivot) {

    m_blinkin = BlinkinLEDController.getInstance();
    return new InstantCommand(
            () -> {
              m_blinkin.orangeHeartbeat();
            })
        .andThen(
            indexer.IntakeLoopCommand(5)
                .deadlineWith(groundIntake.manualCommand(() -> 5))
                .deadlineWith(pivot.PIDCommand(75)));
  }
}
