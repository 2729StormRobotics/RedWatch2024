package frc.robot.subsystems.hanger;

import org.littletonrobotics.junction.AutoLog;

/**
 * The HangerIO interface represents the input/output interface for the hanger subsystem. It
 * provides methods to update inputs from the robot, control the hanger motor, and retrieve PID
 * constants for velocity control.
 */
public interface HangerIO {
  /**
   * The HangerIOInputs class represents the input values for the hanger subsystem. It contains
   * fields for velocity, applied voltage, speed setpoint, break beam status, current amps, and
   * temperature in Celsius.
   */
  @AutoLog
  public static class HangerIOInputs {
    /** The velocity of the hanger motor in radians per second. */
    public double velocityRadsPerSec = 0.0;

    /** The applied voltage to run the hanger motor. */
    public double appliedVoltage = 0.0;

    /** The speed setpoint for hanger control. */
    public double speedSetpoint = 0.0;

    /** The status of the break beam sensor. */     //we dont need this
    public boolean breakBeam = false;

    /** The array of current values in amps for each motor. */
    public double[] currentAmps = new double[] {};

    /** The array of temperature values in Celsius for each motor. */
    public double[] tempCelcius = new double[] {};
  }

  /**
   * Updates the input values from the robot.
   *
   * @param inputs the HangerIOInputs object containing the updated input values
   */
  public default void updateInputs(HangerIOInputs inputs) {}

  /**
   * Sets the voltage to run the hanger motor if necessary.
   *
   * @param voltage the voltage to be applied to the motor
   */
  public default void setVoltage(double voltage) {}

  /**
   * Sets the PID constants for velocity control.
   *
   * @param p the proportional constant
   * @param i the integral constant
   * @param d the derivative constant
   */
  public default void setPIDConstants(double p, double i, double d) {}

  /** Stops the hanger motor by setting the voltage to 0. */
  public default void stop() {
    setVoltage(0.0);
  }

  /**
   * Sets the brake mode for the hanger motor.
   *
   * @param brake true to enable brake mode, false otherwise
   */
  public default void setBrake(boolean brake) {}

  /**
   * Checks if the robot is hanging.
   *
   * @return true if the robot is hanging, false otherwise
   */
  public default boolean isHanging() {
    return true;
  }

  /**
   * Sets the speed of the hanger motor.
   *
   * @param speed the speed of the hanger motor
   */
  public default void setSpeed(double speed) {}

  // Returns the current speed of the hanger motor
  public default double getSpeed() {
    return 0;
  }

  /**
   * Sets the proportional constant for velocity control.
   *
   * @param p the proportional constant
   */
  public default void setP(double p) {}

  /**
   * Sets the integral constant for velocity control.
   *
   * @param i the integral constant
   */
  public default void setI(double i) {}

  /**
   * Sets the derivative constant for velocity control.
   *
   * @param d the derivative constant
   */
  public default void setD(double d) {}

  /**
   * Retrieves the proportional constant for velocity control.
   *
   * @return the proportional constant
   */
  public default double getP() {
    return 0.0;
  }

  /**
   * Retrieves the integral constant for velocity control.
   *
   * @return the integral constant
   */
  public default double getI() {
    return 0.0;
  }

  /**
   * Retrieves the derivative constant for velocity control.
   *
   * @return the derivative constant
   */
  public default double getD() {
    return 0.0;
  }
}
