package frc.robot.subsystems.shooter;

import java.lang.Cloneable;
import java.lang.Override;
import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.inputs.LoggableInputs;

public class ShooterIOInputsAutoLogged extends ShooterIO.ShooterIOInputs implements LoggableInputs, Cloneable {
  @Override
  public void toLog(LogTable table) {
    table.put("LeftShooterPositionRotations", leftShooterPositionRotations);
    table.put("LeftFlywheelVelocityRPM", leftFlywheelVelocityRPM);
    table.put("LeftFlywheelAppliedVolts", leftFlywheelAppliedVolts);
    table.put("LeftFlywheelOutputCurrent", leftFlywheelOutputCurrent);
    table.put("RightFlywheelPositionRotations", rightFlywheelPositionRotations);
    table.put("RightFlywheelVelocityRPM", rightFlywheelVelocityRPM);
    table.put("RightFlywheelAppliedVolts", rightFlywheelAppliedVolts);
    table.put("RightFlywheelOutputCurrent", rightFlywheelOutputCurrent);
  }

  @Override
  public void fromLog(LogTable table) {
    leftShooterPositionRotations = table.get("LeftShooterPositionRotations", leftShooterPositionRotations);
    leftFlywheelVelocityRPM = table.get("LeftFlywheelVelocityRPM", leftFlywheelVelocityRPM);
    leftFlywheelAppliedVolts = table.get("LeftFlywheelAppliedVolts", leftFlywheelAppliedVolts);
    leftFlywheelOutputCurrent = table.get("LeftFlywheelOutputCurrent", leftFlywheelOutputCurrent);
    rightFlywheelPositionRotations = table.get("RightFlywheelPositionRotations", rightFlywheelPositionRotations);
    rightFlywheelVelocityRPM = table.get("RightFlywheelVelocityRPM", rightFlywheelVelocityRPM);
    rightFlywheelAppliedVolts = table.get("RightFlywheelAppliedVolts", rightFlywheelAppliedVolts);
    rightFlywheelOutputCurrent = table.get("RightFlywheelOutputCurrent", rightFlywheelOutputCurrent);
  }

  public ShooterIOInputsAutoLogged clone() {
    ShooterIOInputsAutoLogged copy = new ShooterIOInputsAutoLogged();
    copy.leftShooterPositionRotations = this.leftShooterPositionRotations;
    copy.leftFlywheelVelocityRPM = this.leftFlywheelVelocityRPM;
    copy.leftFlywheelAppliedVolts = this.leftFlywheelAppliedVolts;
    copy.leftFlywheelOutputCurrent = this.leftFlywheelOutputCurrent;
    copy.rightFlywheelPositionRotations = this.rightFlywheelPositionRotations;
    copy.rightFlywheelVelocityRPM = this.rightFlywheelVelocityRPM;
    copy.rightFlywheelAppliedVolts = this.rightFlywheelAppliedVolts;
    copy.rightFlywheelOutputCurrent = this.rightFlywheelOutputCurrent;
    return copy;
  }
}
