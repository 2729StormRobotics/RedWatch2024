package frc.robot.subsystems.hanger;

import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
import frc.robot.Constants.ElectricalLayout;

/** Need to import Constants files/classes */
//

public class HangerIOSparkMax implements HangerIO {

  private CANSparkMax leader;
  private CANSparkMax follower;
  private SparkPIDController velocityPID;

  private double desiredSpeed;

  private RelativeEncoder encoder_left;
  private RelativeEncoder encoder_right;

  public HangerIOSparkMax() {
    /** ID needs to be assigned from constants */
    // setPIDConstants(kIntakeP, kIntakeI, kIntakeD);

    leader = new CANSparkMax(ElectricalLayout.HANGER_MOTOR_LEFT, CANSparkMax.MotorType.kBrushless);
    follower =
        new CANSparkMax(ElectricalLayout.HANGER_MOTOR_RIGHT, CANSparkMax.MotorType.kBrushless);

    encoder_left = leader.getEncoder();
    encoder_right = follower.getEncoder();

    leader.restoreFactoryDefaults();
    follower.restoreFactoryDefaults();

    leader.setCANTimeout(250);
    follower.setCANTimeout(250);

    leader.setInverted(false);
    follower.follow(leader, false);

    leader.enableVoltageCompensation(12.0);
    leader.setSmartCurrentLimit(30);

    leader.burnFlash();
    follower.burnFlash();

    velocityPID = leader.getPIDController();
  }

  /** updates inputs from robot */
  @Override
  public void updateInputs(HangerIOInputs inputs) {
    inputs.appliedVoltage = leader.getAppliedOutput() * leader.getBusVoltage();
    inputs.currentAmps = new double[] {leader.getOutputCurrent()};
    inputs.tempCelcius = new double[] {leader.getMotorTemperature()};
    inputs.velocityRadsPerSec = encoder_left.getVelocity();
    inputs.speedSetpoint = desiredSpeed;
  }

  /** sets voltage to run leader if necessary */
  @Override
  public void setVoltage(double voltage) {
    leader.setVoltage(voltage * 10);
  }

  /** sets brake mode to stop */
  @Override
  public void setBrake(boolean brake) {
    leader.setIdleMode(brake ? IdleMode.kBrake : IdleMode.kCoast);
  }

  @Override
  public boolean isHanging() {
    return false;
  }

  @Override
  public void setSpeed(double speed) {
    desiredSpeed = speed;
    velocityPID.setReference(speed, ControlType.kVelocity);
  }

  @Override
  public double getSpeed() {
    return encoder_left.getVelocity();
  }

  @Override
  public void setP(double p) {
    velocityPID.setP(p);
  }

  @Override
  public void setI(double i) {
    velocityPID.setI(i);
  }

  @Override
  public void setD(double d) {
    velocityPID.setD(d);
  }

  @Override
  public double getP() {
    return velocityPID.getP();
  }

  @Override
  public double getI() {
    return velocityPID.getI();
  }

  @Override
  public double getD() {
    return velocityPID.getD();
  }

  @Override
  public void stop() {
    setVoltage(0.0);
  }

  @Override
  public void setPIDConstants(double p, double i, double d) {
    velocityPID.setP(p);
    velocityPID.setI(i);
    velocityPID.setD(d);
  }
}
