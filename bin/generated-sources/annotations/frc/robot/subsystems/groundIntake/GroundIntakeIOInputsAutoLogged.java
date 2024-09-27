package frc.robot.subsystems.groundIntake;

import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.inputs.LoggableInputs;

public class GroundIntakeIOInputsAutoLogged extends GroundIntakeIO.GroundIntakeIOInputs
    implements LoggableInputs, Cloneable {
  @Override
  public void toLog(LogTable table) {
    table.put("VelocityRadsPerSec", velocityRadsPerSec);
    table.put("AppliedVoltage", appliedVoltage);
    table.put("SpeedSetpoint", speedSetpoint);
    table.put("CurrentAmps", currentAmps);
    table.put("TempCelcius", tempCelcius);
  }

  @Override
  public void fromLog(LogTable table) {
    velocityRadsPerSec = table.get("VelocityRadsPerSec", velocityRadsPerSec);
    appliedVoltage = table.get("AppliedVoltage", appliedVoltage);
    speedSetpoint = table.get("SpeedSetpoint", speedSetpoint);
    currentAmps = table.get("CurrentAmps", currentAmps);
    tempCelcius = table.get("TempCelcius", tempCelcius);
  }

  public GroundIntakeIOInputsAutoLogged clone() {
    GroundIntakeIOInputsAutoLogged copy = new GroundIntakeIOInputsAutoLogged();
    copy.velocityRadsPerSec = this.velocityRadsPerSec;
    copy.appliedVoltage = this.appliedVoltage;
    copy.speedSetpoint = this.speedSetpoint;
    copy.currentAmps = this.currentAmps.clone();
    copy.tempCelcius = this.tempCelcius.clone();
    return copy;
  }
}
