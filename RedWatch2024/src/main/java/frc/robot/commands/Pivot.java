// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Shooter;

public class Pivot extends Command {
  private final Shooter m_shooter;
  private final double m_angle;
  public double timeElapsed = 0;

  private final TrapezoidProfile.Constraints m_constraints;
  private final ProfiledPIDController m_controller;

  /** Creates a new Pivot. */
  public Pivot(Shooter shooter, double angle) {
    m_shooter = shooter;
    m_angle = angle;

    m_constraints = new TrapezoidProfile.Constraints(
      Constants.ShooterConstants.kMaxPivotVelocity,
      Constants.ShooterConstants.kMaxPivotAcceleration);
    m_controller = new ProfiledPIDController(Constants.ShooterConstants.kPPivot,
    Constants.ShooterConstants.kIPivot, Constants.ShooterConstants.kDPivot, m_constraints);
    m_controller.setGoal(m_angle);
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    timeElapsed += 0.02;
    m_shooter.setPivotSpeed(m_controller.calculate(m_shooter.getAverageAngle()));
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_shooter.stopPivotMotors();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
