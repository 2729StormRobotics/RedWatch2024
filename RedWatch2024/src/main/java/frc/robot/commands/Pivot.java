// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Shooter;

/*
 * Will pivot the shooter to the specified angle
 * Works based off of a profiledPID controller
 */

public class Pivot extends Command {
  // initialize shooter, angle, and PID constraints/controllers
  private final Shooter m_shooter;
  private final double m_angle;

  private final TrapezoidProfile.Constraints m_constraints;
  private final ProfiledPIDController m_controller;

  public double timeElapsed = 0; // Keep track of time

  /** Creates a new Pivot. */
  public Pivot(Shooter shooter, double angle) {
    m_shooter = shooter;
    m_angle = angle;

    // ProfiledPID Constraints
    m_constraints = new TrapezoidProfile.Constraints(
      Constants.ShooterConstants.kMaxPivotVelocity,
      Constants.ShooterConstants.kMaxPivotAcceleration);

    // PID Controller
    m_controller = new ProfiledPIDController(Constants.ShooterConstants.kPPivot,
    Constants.ShooterConstants.kIPivot, Constants.ShooterConstants.kDPivot, m_constraints);

    // Set the goal and tolerances of the PID Controller
    m_controller.setGoal(m_angle);
    m_controller.setTolerance(Constants.ShooterConstants.kPivotTolerance);
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
    timeElapsed += 0.02; // this updates every 20 ms
    // Set pivot speed to the value calculated by the PID Controller
    m_shooter.setPivotSpeed(m_controller.calculate(m_shooter.getPivotAngle(), m_angle));
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // Stop pivot motors
    m_shooter.stopPivotMotors();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // Finish command when shooter is at the setpoint
    return m_controller.atSetpoint();
    // return false;
  }
}
