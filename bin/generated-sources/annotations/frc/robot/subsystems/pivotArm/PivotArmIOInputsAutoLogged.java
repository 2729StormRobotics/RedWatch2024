package frc.robot.subsystems.pivotArm;

import java.lang.Cloneable;
import java.lang.Override;
import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.inputs.LoggableInputs;

public class PivotArmIOInputsAutoLogged extends PivotArmIO.PivotArmIOInputs implements LoggableInputs, Cloneable {
  @Override
  public void toLog(LogTable table) {
    table.put("AngleRads", angleRads);
    table.put("AngVelocityRadsPerSec", angVelocityRadsPerSec);
    table.put("AppliedVolts", appliedVolts);
    table.put("SetpointAngleRads", setpointAngleRads);
    table.put("CurrentAmps", currentAmps);
    table.put("TempCelsius", tempCelsius);
  }

  @Override
  public void fromLog(LogTable table) {
    angleRads = table.get("AngleRads", angleRads);
    angVelocityRadsPerSec = table.get("AngVelocityRadsPerSec", angVelocityRadsPerSec);
    appliedVolts = table.get("AppliedVolts", appliedVolts);
    setpointAngleRads = table.get("SetpointAngleRads", setpointAngleRads);
    currentAmps = table.get("CurrentAmps", currentAmps);
    tempCelsius = table.get("TempCelsius", tempCelsius);
  }

  public PivotArmIOInputsAutoLogged clone() {
    PivotArmIOInputsAutoLogged copy = new PivotArmIOInputsAutoLogged();
    copy.angleRads = this.angleRads;
    copy.angVelocityRadsPerSec = this.angVelocityRadsPerSec;
    copy.appliedVolts = this.appliedVolts;
    copy.setpointAngleRads = this.setpointAngleRads;
    copy.currentAmps = this.currentAmps.clone();
    copy.tempCelsius = this.tempCelsius.clone();
    return copy;
  }
}
