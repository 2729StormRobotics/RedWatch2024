package frc.robot.subsystems.hanger;

import frc.robot.Constants;

public final class HangerConstants {
  public static class HangerSimConstants {
    public static final int kMotorPort = 0;
    public static final int kBreakBeamSensorChannel = 0;
    public static final int kJoystickPort = 0;

    public static final double kHangerP = 0.001;
    public static final double kHangerI = 0.0;
    public static final double kHangerD = 0.0;

    public static final double kHangerS = 0.0;
    public static final double kHangerG = 0.0;
    public static final double kHangerV = 0.0;
    public static final double kHangerA = 0.0;
    // Not sure what these three are or if they're needed
    public static final double kHangerGearing = 0.0;
    public static final double kHangerDrumRadius = 0.03;
    public static final double kCarriageMass = 0.15; // Mass in Kg
    public static final double kMomentOfInertia =
        0.5
            * kCarriageMass
            * kHangerDrumRadius
            * kHangerDrumRadius; // Moment of inertia represents how resistant to force something
    // is

    // distance per pulse = (distance per revolution) / (pulses per revolution)
    // = (Pi * D) / ppr
    public static final double kHangerEncoderDistPerPulse =
        2.0 * Math.PI * kHangerDrumRadius / 4096;
  }

  public static class HangerPhysicalConstants {
    // Not sure if these two necessary
    public static final double GROUND_STOP_BUFFER = 0.0;
    public static final double GROUND_TOLERANCE = 0.0;

    public static final double kIndexerP = 0.001;
    public static final double kIndexerI = 0.0;
    public static final double kIndexerD = 0.0;
  }

  // do these values need to be adjusted??
  public static final double HANGER_IN_VOLTAGE = 8;
  public static final double HANGER_IN_VOLTAGE_WEAK = 4.2;
  public static final double HANGER_OUT_VOLTAGE = -2;
  public static final double HANGER_TOLERANCE = 1;

  public static final double SHOOTER_UNJAM_TIME = 0.2;

  //do we need this??
  public static double getIntakeLoopMaxTime() {
    switch (Constants.getRobotMode()) {
      case REAL:
        return 5.0;
      case SIM:
      default:
        return 1.0;
    }
  }
}
