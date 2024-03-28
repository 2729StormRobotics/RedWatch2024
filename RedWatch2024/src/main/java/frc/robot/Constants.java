// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.CANSparkBase.IdleMode;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;
import frc.robot.lib.LinearInterpolationTable;
import java.awt.geom.Point2D;


/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

  public static class OperatorConstants {
    // ports
    public static final int kDriveTranslatorPort = 1;
    public static final int kDriveRotatorPort = 2;
    public static final int kWeaponsControllerPort = 0;

    // joystick settings
    public static final double kDriveDeadband = 0.025;
    public static final double translationMultiplier = 1;
    public static final double rotationMultiplier = 1;
  }

  public static final class IndexerConstants {
    public static final double kFeedSpeakerSpeed = 1;
    public static final double kFeedAmpSpeed = 0.75;

    // Assigns Indexer moftor to port
    public static final int kIndexMotorPort = 12;
    // Assigns Beam break sensor to port 1
    public static final int kBeamBreakPort = 4;
    // Sets the indexer motor to 50% power
    public static double kIndexerSpeed = 0.7;
    // Sets the indexer motor to 50% power in the opposite direction
    public static double kSourceIndexerSpeed = -0.7;
    // Sets the indexer motor current limit to 60 amps
    public static final int kCurrentLimit = 35;
  }

  public static final class HangerConstants {
		public static final int kLeftHanger = 16;
		public static final int kRightHanger = 15;

    public static final int kCurrentLimit = 35;
    public static final double kSpeedLimiter = 1;
	}
  public static final class DriveConstants {
    // Driving Parameters - Note that these are not the maximum capable speeds of
    // the robot, rather the allowed maximum speeds
    public static final double kMaxSpeedMetersPerSecond = 4.8;
    public static final double kMaxAngularSpeed = 2 * Math.PI; // radians per second

    public static final double kDirectionSlewRate = 1.2; // radians per second
    public static final double kMagnitudeSlewRate = 1.8; // percent per second (1 = 100%)
    public static final double kRotationalSlewRate = 2.0; // percent per second (1 = 100%)

    // Chassis configuration
    public static final double kTrackWidth = Units.inchesToMeters(26.5);
    // Distance between centers of right and left wheels on robot
    public static final double kWheelBase = Units.inchesToMeters(26.5);
    // Distance between front and back wheels on robot

    public static final double kRobotRadius = DriveConstants.kTrackWidth * Math.sqrt(2) / 2; // robot radius

    public static final SwerveDriveKinematics kDriveKinematics = new SwerveDriveKinematics(
        new Translation2d(kWheelBase / 2, kTrackWidth / 2),
        new Translation2d(kWheelBase / 2, -kTrackWidth / 2),
        new Translation2d(-kWheelBase / 2, kTrackWidth / 2),
        new Translation2d(-kWheelBase / 2, -kTrackWidth / 2));

    // Angular offsets of the modules relative to the chassis in radians
    public static final double kFrontLeftChassisAngularOffset = Math.PI / 2;
    public static final double kFrontRightChassisAngularOffset = 0;
    public static final double kBackLeftChassisAngularOffset = Math.PI;
    public static final double kBackRightChassisAngularOffset = -Math.PI / 2;

    // SPARK MAX CAN IDs
    public static final int kFrontLeftDrivingCanId = 4;
    public static final int kRearLeftDrivingCanId = 2;
    public static final int kFrontRightDrivingCanId = 6;
    public static final int kRearRightDrivingCanId = 8;

    public static final int kFrontLeftTurningCanId = 3;
    public static final int kRearLeftTurningCanId = 1;
    public static final int kFrontRightTurningCanId = 5;
    public static final int kRearRightTurningCanId = 7;

    public static final boolean kGyroReversed = false;
    public static final double kPhysicalMaxAngularSpeedRadiansPerSecond = 2 * 2 * Math.PI;
  }

  public static class AutoConstants {
    public static final double kMaxSpeedMetersPerSecond = 4.8;
    public static final double kMaxAngularSpeedRadiansPerSecond = Math.toRadians(540);
    public static final double kMaxAcceletrationMetersPerSecondSquared = 3;
    public static final double kMaxAngularAccelerationRadiansPerSecondSquared = Math.toRadians(720);
    public static final double kPXController = 1.5;
    public static final double kPYController = 1.5;
    public static final double kPThetaController = 3;

    public static final TrapezoidProfile.Constraints kThetaControllerConstraints = //
    new TrapezoidProfile.Constraints(
            kMaxAngularSpeedRadiansPerSecond,
            kMaxAngularAccelerationRadiansPerSecondSquared);

  }



  public static final class ModuleConstants {
    // The MAXSwerve module can be configured with one of three pinion gears: 12T,
    // 13T, or 14T.
    // This changes the drive speed of the module (a pinion gear with more teeth
    // will result in a
    // robot that drives faster).
    public static final int kDrivingMotorPinionTeeth = 14;

    // Invert the turning encoder, since the output shaft rotates in the opposite
    // direction of
    // the steering motor in the MAXSwerve Module.
    public static final boolean kTurningEncoderInverted = true;

    // Calculations required for driving motor conversion factors and feed forward
    public static final double kDrivingMotorFreeSpeedRps = NeoMotorConstants.kFreeSpeedRpm / 60;
    public static final double kWheelDiameterMeters = 0.0762;
    public static final double kWheelCircumferenceMeters = kWheelDiameterMeters * Math.PI;
    // 45 teeth on the wheel's bevel gear, 22 teeth on the first-stage spur gear, 15
    // teeth on the bevel pinion
    public static final double kDrivingMotorReduction = (45.0 * 22) / (kDrivingMotorPinionTeeth * 15);
    public static final double kDriveWheelFreeSpeedRps = (kDrivingMotorFreeSpeedRps * kWheelCircumferenceMeters)
        / kDrivingMotorReduction;

    public static final double kDrivingEncoderPositionFactor = (kWheelDiameterMeters * Math.PI)
        / kDrivingMotorReduction; // meters
    public static final double kDrivingEncoderVelocityFactor = ((kWheelDiameterMeters * Math.PI)
        / kDrivingMotorReduction) / 60.0; // meters per second

    public static final double kTurningEncoderPositionFactor = (2 * Math.PI); // radians
    public static final double kTurningEncoderVelocityFactor = (2 * Math.PI) / 60.0; // radians per second

    public static final double kTurningEncoderPositionPIDMinInput = 0; // radians
    public static final double kTurningEncoderPositionPIDMaxInput = kTurningEncoderPositionFactor; // radians

    public static final double kDrivingP = 0.04;
    public static final double kDrivingI = 0;
    public static final double kDrivingD = 0;
    public static final double kDrivingFF = 1 / kDriveWheelFreeSpeedRps;
    public static final double kDrivingMinOutput = -1;
    public static final double kDrivingMaxOutput = 1;

    public static final double kTurningP = 1;
    public static final double kTurningI = 0;
    public static final double kTurningD = 0;
    public static final double kTurningFF = 0;
    public static final double kTurningMinOutput = -1;
    public static final double kTurningMaxOutput = 1;

    public static final IdleMode kDrivingMotorIdleMode = IdleMode.kBrake;
    public static final IdleMode kTurningMotorIdleMode = IdleMode.kBrake;

    public static final int kDrivingMotorCurrentLimit = 35; // amps
    public static final int kTurningMotorCurrentLimit = 20; // amps
  }

  public static final class NeoMotorConstants {
    public static final double kFreeSpeedRpm = 5676;
  }

  public static class IntakeConstants {
    public static final int kIntakeMotor = 13;
    public static final int kCurrentLimit = 35;
    public static double kIntakeMotorSpeed = 0.8;
    public static double kEjectMotorSpeed = -0.8;
  }

  public static class ShooterConstants {
    // Motor ID/initialization values

    public static final int kLeftFlywheelID = 10;
    public static final int kRightFlywheelID = 14;
    public static final int kCurrentLimit = 35;


    // Make sure that the two pivot motors and two shooting motors rotate in
    // opposite directions
    public static final boolean kLeftFlywheelInverted = false;
    public static final boolean kRightFlywheelInverted = !kLeftFlywheelInverted;

    // Flywheel PID values
    public static final double kMaxRPM = 5300;
    public static final double kPShoot = 0; //0.00002
    public static final double kRPMTolerance = 50;

    
    // Flywheel Shooting values
    public static double kLeftPowerAmp = .165; //
    public static double kRightPowerAmp = .165; // 0.17 speed for backward amp shot
    //.153
    public static double kLeftPowerSpeaker = 0.85;
    public static double kRightPowerSpeaker = 0.85; //75
    public static final double steadyPower = 0; // 0.15 // 0.25

  }

  public static class PivotConstants {
    // motor configuration
    public static final int kRightPivotID = 9;
    public static final int kCurrentLimit = 35;
    public static final double kPivotSpeedLimiter = 1;
    public static final boolean kRightPivotInverted = false;

    // Pivot PID values
    public static double kPivotFF = 0.047; //0.049 //0.05
    public static double kPPivotUp = 0.0265;//GOOD ONE
    public static double kPPivotDown = 0.025;//GOOD ONE

    public static final double kIPivot = 0;
    public static double kDPivot = 0;//0.0001;
    public static double kMaxPivotVelocity = 10; // Measured in degrees/s
    public static final double kMaxPivotAcceleration = 45; // Measured in degrees/s^2
    public static final double kPivotTolerance = 0; // degrees
    public static double pivotPower;

    // Setpositions
    public static final double kSourcePivotAngle = 0; // Angle of pivot for source
    public static final double kIntakeAngle = 75;

    // Interpolation table for getting shooting angle based off distance
    public static final Point2D[] ShootingPoints = new Point2D[]{ // array of exp determined data points of (dist, angle)
      new Point2D.Double(-1, 51),
      new Point2D.Double(0.94, 51),
      new Point2D.Double(1.25, 46.5),
      new Point2D.Double(1.5, 44),  
      new Point2D.Double(1.9, 36),
      new Point2D.Double(2.3, 33.3),
      new Point2D.Double(2.73, 29.25),
      new Point2D.Double(3, 28.75),
      new Point2D.Double(3.4, 27.75)};
  }

  public static class VisionConstants {
    // Camera configuration
    public static final double kAprilTagPipeline = 1;
    public static final double kLightOffValue = 0;

    // PID values for driving with vision
    public static final double kDistanceTolerance = 0;
    public static final double kPX = 0;
    public static final double kSDrive = 0;
    public static final double kPY = 0;
    public static final double kTolerance = 0;
    public static final double kPTurn = 0.008;//0.008
    public static final double kITurn = 0;
    public static final double kDTurn = 0.0015; //0.001
    public static final double kSTurn = .02;

    // Heights for detecting distance away from apriltag
    public static final double limelightHeight = Units.inchesToMeters(11.5);
    public static final double limelightAngle = 30.5; // degrees
    public static final double apriltagWidth = Units.inchesToMeters(6.5);
    public static final double speakerTagHeight = Units.inchesToMeters(54) + Units.inchesToMeters(apriltagWidth / 2);
    public static final double ampTagHeight = Units.inchesToMeters(53.875) + Units.inchesToMeters(apriltagWidth / 2);
    public static final double stageTagHeight = Units.inchesToMeters(53.875) + Units.inchesToMeters(apriltagWidth / 2);
    public static final double aprilTagAlignTolerance = 0;

    public static final double kNoteTolerance = 2.0;
    public static final double kPNoteTurn = 0.008;
  }

  public static class ControlPanelConstants {
    public static final String kShuffleboardTab = "Control Panel";
  }

  public static final class LightsConstants {
    public static final int CANDLE_PORT  = 12;
  }
}