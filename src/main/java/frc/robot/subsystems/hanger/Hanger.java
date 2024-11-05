package frc.robot.subsystems.hanger;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.util.drive.DashboardValues;
import java.util.function.DoubleSupplier;
import org.littletonrobotics.junction.AutoLogOutput;
import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.networktables.LoggedDashboardNumber;

public class Hanger extends SubsystemBase {
  private final HangerIO io;
  HangerIOInputsAutoLogged inputs = new HangerIOInputsAutoLogged();
  private LoggedDashboardNumber logP;
  private LoggedDashboardNumber logI;
  private LoggedDashboardNumber logD;
  // State of the note in the intake
  enum NoteState {
    NOT_ENOUGH, // Not far enough in the intake or not in there at all
    GOLDILOCKS, // just the right position in intake
    MIDDLE, //
    OVERSHOOT
  }

  private NoteState noteState = NoteState.GOLDILOCKS;
  private double currentVoltage = 0;
  private double timeInIntake = 0;
  private final double desiredTimeInIntake = 0.5;

  public Hanger(HangerIO io) {
    this.io = io;
    SmartDashboard.putData(getName(), this);
    logP = new LoggedDashboardNumber("Hanger/P", io.getP());
    logI = new LoggedDashboardNumber("Hanger/I", io.getI());
    logD = new LoggedDashboardNumber("Hanger/D", io.getD());
  }

  @AutoLogOutput(key = "Hanger/hanging")
  public boolean isVoltageClose(double setVoltage) {
    double voltageDifference = Math.abs(setVoltage - inputs.appliedVoltage);
    return voltageDifference <= HangerConstants.HANGER_TOLERANCE;
  }

  public void periodic() {
    io.updateInputs(inputs);
    // Update PID constants to ensure they are up to date

    Logger.processInputs("Hanger", inputs);
    Logger.recordOutput("Hanger/State", noteState.name());
    Logger.recordOutput("Hanger/HangerMotorConnected", inputs.velocityRadsPerSec != 0);
  }

  public void setVoltage(double voltage) {
    if (DashboardValues.turboMode.get()) {
      io.setVoltage(0);
    } else {
      io.setVoltage(voltage);
    }
    isVoltageClose(voltage);
  }

  public void setBrake(boolean brake) {
    io.setBrake(brake);
  }

  public boolean isHanging() {
    return io.isHanging();
  }

  // Checks if note has been in the intake for 0.5 seconds
  public boolean isHangingForEnoughTime() {
    return timeInIntake >= desiredTimeInIntake;
  }
  /**
   * Uses input from controller to set speed of the flywheel and is used as the default command for
   * the hanger
   */
  public Command speedCommand(DoubleSupplier speed) {
    return new FunctionalCommand(
        () -> {}, () -> io.setSpeed(speed.getAsDouble()), (stop) -> io.stop(), () -> false, this);
  }
  // Allows manual command of the flywheel for testing
  public Command manualCommand(DoubleSupplier voltage) {
    return new FunctionalCommand(
        () -> {},
        () -> io.setVoltage(voltage.getAsDouble()),
        (stop) -> io.stop(),
        () -> false,
        this);
  }

  public Command manualCommand(double voltage) {
    return new FunctionalCommand(
        () -> {}, () -> io.setVoltage(voltage), (stop) -> io.stop(), () -> false, this);
  }

  public Command stop() {
    return new FunctionalCommand(
        () -> {}, () -> io.setVoltage(0), (stop) -> io.stop(), () -> false, this);
  }
}