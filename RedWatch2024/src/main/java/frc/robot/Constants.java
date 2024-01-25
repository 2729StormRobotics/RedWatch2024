// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }

  public static class ShooterConstants {
    // Motor ID/initialization valuess
    public static final int kLeftPivotID = 999;
    public static final int kRightPivotID = 999;
    public static final int kLeftFlywheelID = 999;
    public static final int kRightFlywheelID = 999;
    public static final int kCurrentLimit = 45;
    public static final boolean kLeftFlywheelInverted = false;
    public static final boolean kRightFlywheelInverted = !kLeftFlywheelInverted;
    public static final boolean kLeftPivot = false;
    public static final boolean kRightPivot = !kLeftPivot;
    public static final double kLeftPivotOffset = 0;
    public static final double kRightPivotOffset = 0;

    // Pivot PID values
    public static final double kPPivot = 0;
    public static final double kIPivot = 0;
    public static final double kDPivot = 0;
    public static final double kMaxPivotVelocity = 0; // Measured in degrees/s
    public static final double kMaxPivotAcceleration = 0; // Measured in degrees/s^2
    public static final double kPivotTolerance = 2; // degrees

    // Flywheel PID values
    public static final double kMaxRPM = 5000;
    public static final double kPShoot = 0;
    public static final double kRPMTolerance = 50;
  }
}
