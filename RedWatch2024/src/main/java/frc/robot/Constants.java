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

  public static final class HangerConstants {
		public static final int kHangerLeftPivotFollowerPort = 9;
		public static final int kHangerRightPivotPort = 10;

    // pivoting gearbox = 1:125 
    public static final double kPivotingGearRatio = 1.0 / 125.0;
    public static final double kAnglePerRevolution = kPivotingGearRatio * 3.14;
		public static final double kHangerRightSize = 0;
    public static final double kEncoderOffset = 0;

    //If needed.
    public static double kP = 0;
    public static double kI = 0;
    public static double kD = 0;

    public static final int kCurrentLimit = 0;
    public static final int kStallLimit = 0;

    public static final double kPivotArmSpeed = 0; //Assign Speed
    //Sets the position for the arm when neutral
    public static final double kLeftPivotArmNeutral = 0; //Arms neutral position
    public static final double kRightPivotArmNeutral = 0;
	}
}
