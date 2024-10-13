package frc.robot.subsystems.indexer;

import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.inputs.LoggableInputs;

public class IndexerIOInputsAutoLogged extends IndexerIO.IndexerIOInputs
    implements LoggableInputs, Cloneable {
  @Override
  public void toLog(LogTable table) {
    table.put("VelocityRadsPerSec", velocityRadsPerSec);
    table.put("AppliedVoltage", appliedVoltage);
    table.put("SpeedSetpoint", speedSetpoint);
    table.put("BreakBeam", breakBeam);
    table.put("CurrentAmps", currentAmps);
    table.put("TempCelcius", tempCelcius);
  }

  @Override
  public void fromLog(LogTable table) {
    velocityRadsPerSec = table.get("VelocityRadsPerSec", velocityRadsPerSec);
    appliedVoltage = table.get("AppliedVoltage", appliedVoltage);
    speedSetpoint = table.get("SpeedSetpoint", speedSetpoint);
    breakBeam = table.get("BreakBeam", breakBeam);
    currentAmps = table.get("CurrentAmps", currentAmps);
    tempCelcius = table.get("TempCelcius", tempCelcius);
  }

  public IndexerIOInputsAutoLogged clone() {
    IndexerIOInputsAutoLogged copy = new IndexerIOInputsAutoLogged();
    copy.velocityRadsPerSec = this.velocityRadsPerSec;
    copy.appliedVoltage = this.appliedVoltage;
    copy.speedSetpoint = this.speedSetpoint;
    copy.breakBeam = this.breakBeam;
    copy.currentAmps = this.currentAmps.clone();
    copy.tempCelcius = this.tempCelcius.clone();
    return copy;
  }
}
