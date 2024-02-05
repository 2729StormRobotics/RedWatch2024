// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;



import com.revrobotics.CANSparkBase.IdleMode;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.util.Units;

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
    //ports
    public static final int kDriveTranslatorPort = 1;
    public static final int kDriveRotatorPort = 2;
    public static final int kWeaponsControllerPort = 0;

    //joystick settings
    public static final double kDriveDeadband = 0.1;
    public static final double translationMultiplier = 0.6;
    public static double rotationMultiplier = 1;

  }
  


  public static final class IndexerConstants {
    // Assigns Indexer motor to port 4
    public static final int kIndexMotorPort = 4;
    // Assigns Beam break sensor to port 1
    public static final int kBeamBreakPort = 1;
    // Sets the indexer motor to 50% power
    public static final double kIndexerSpeed = 0.5;
    // Sets the indexer motor stall limit to 45 amps
    public static final int kStallLimit = 45;
    // Sets the indexer motor current limit to 60 amps
    public static final int kCurrentLimit = 60;
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

    public static final int kDrivingMotorCurrentLimit = 50; // amps
    public static final int kTurningMotorCurrentLimit = 20; // amps
  }

  public static final class NeoMotorConstants {
    public static final double kFreeSpeedRpm = 5676;
  }

  
  public static class IntakeConstants {
    public static final int kIntakeMotor = 9;
    public static final double kIntakeMotorSpeed = 0.8;
    public static final double kEjectMotorSpeed = -0.8;
  }
  
  public static class ShooterConstants {
    // Motor ID/initialization values
    public static final int kLeftPivotID = 999;
    public static final int kRightPivotID = 999;
    public static final int kLeftFlywheelID = 999;
    public static final int kRightFlywheelID = 999;
    public static final int kCurrentLimit = 45;

    //manual control speed limiters 
    public static final double kPivotSpeedLimiter = 1; 

    // Make sure that the two pivot motors and two shooting motors rotate in opposite directions
    public static final boolean kLeftFlywheelInverted = false;
    public static final boolean kRightFlywheelInverted = !kLeftFlywheelInverted;
    public static final boolean kLeftPivot = false;
    public static final boolean kRightPivot = !kLeftPivot;

    // Absolute encoder offsets
    public static final double kLeftPivotOffset = 0;
    public static final double kRightPivotOffset = 0;

    // Pivot PID values
    public static final double kPPivot = 0.005;
    public static final double kIPivot = 0;
    public static final double kDPivot = 0;
    public static final double kMaxPivotVelocity = 45; // Measured in degrees/s
    public static final double kMaxPivotAcceleration = 0; // Measured in degrees/s^2
    public static final double kPivotTolerance = 1; // degrees

    // Flywheel PID values
    public static final double kMaxRPM = 6000;
    public static final double kPShoot = 0;
    public static final double kRPMTolerance = 50;

    // Flywheel Shooting values
    public static final double kLeftRPM = 0;
    public static final double kRightRPM = 0;
    public static final double kLeftPower = 0;
    public static final double kRightPower = 0;

    // Field and Robot Measurements
    // All units are in meters
    public static final double goalHeight = Units.inchesToMeters(78.129);
    public static final double shooterLength = Units.inchesToMeters(12.01);
    public static final double exitVelocity = 15;
    public static final double g = 9.81;
    public static final double k = -g/(2*Math.pow(exitVelocity, 2)); // substitution used in angle calculation

    
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
    public static final double kPTurn = 0;
    public static final double kSTurn = 0;
    
    // Heights for detecting distance away from apriltag
    public static final double limelightHeight = Units.inchesToMeters(20);
    public static final double limelightAngle = 20; // degrees
    public static final double apriltagWidth = Units.inchesToMeters(6.5);
    public static final double speakerTagHeight = Units.inchesToMeters(53.875) + Units.inchesToMeters(apriltagWidth / 2);
    public static final double ampTagHeight = Units.inchesToMeters(53.875) + Units.inchesToMeters(apriltagWidth / 2);
    public static final double stageTagHeight = Units.inchesToMeters(53.875) + Units.inchesToMeters(apriltagWidth / 2);
    public static final double aprilTagAlignTolerance = 1;

  }
}

