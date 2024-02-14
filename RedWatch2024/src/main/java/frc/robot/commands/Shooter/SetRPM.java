// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Shooter;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Shooter;

/*
 * Spins the flywheels to the set RPM
 * Works based off of a feedforward + PID
 */

public class SetRPM extends Command {
  private final Shooter m_shooter;
  private final PIDController m_RightController;
  private final PIDController m_LeftController;
  public double m_targetLeftRPM; // target rpm
  public double m_targetRightRPM; // target rpm
  public double m_LeftFF; // feedforward
  public double m_RightFF; // feedforward

  /** Creates a new SetRPM. */
  public SetRPM(Shooter shooter, double leftRPM, double rightRPM) {
    m_shooter = shooter;
    m_LeftController = new PIDController(Constants.ShooterConstants.kPShoot, 0, 0); // only uses P
    m_LeftController.setTolerance(Constants.ShooterConstants.kRPMTolerance); // sets tolerance for PID controller to end
    m_RightController = new PIDController(Constants.ShooterConstants.kPShoot, 0, 0);
    m_RightController.setTolerance(Constants.ShooterConstants.kRPMTolerance);
    m_targetLeftRPM = leftRPM;
    m_targetRightRPM = rightRPM;
    m_LeftFF = m_targetLeftRPM/Constants.ShooterConstants.kMaxRPM; // turn RPM into a value between 0-1
    m_RightFF = m_targetRightRPM/Constants.ShooterConstants.kMaxRPM; // turn RPM into a value between 0-1

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // feedforward should get the rpm decently close
    // PID will make it more accurate (hopefully)
    m_shooter.setShooterSpeed(m_LeftFF + m_LeftController.calculate(m_shooter.getLeftRPM(), m_targetLeftRPM), 
    m_RightFF + m_RightController.calculate(m_shooter.getRightRPM(), m_targetRightRPM));
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_LeftController.atSetpoint() && m_RightController.atSetpoint();
  }
}
