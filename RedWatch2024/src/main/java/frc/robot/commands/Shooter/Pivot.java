// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Shooter;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.LEDs;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.LEDs.LEDSegment;

/*
 * Will pivot the shooter to the specified angle
 * Works based off of a profiledPID controller
 */

public class Pivot extends Command {
  // initialize shooter, angle, and PID constraints/controllers
  private final Shooter m_shooter;
  private final double m_angle;

  // private final TrapezoidProfile.Constraints m_constraints;
  private final PIDController m_controller;
  double error;
  double power;

  public double timeElapsed = 0; // Keep track of time

  /** Creates a new Pivot. */
  public Pivot(Shooter shooter, double angle) {
    m_shooter = shooter;
    m_angle = angle;

    // ProfiledPID Constraints
    // m_constraints = new TrapezoidProfile.Constraints(
    //   Constants.ShooterConstants.kMaxPivotVelocity,
    //   Constants.ShooterConstants.kMaxPivotAcceleration);

    // PID Controller
    m_controller = new PIDController(Constants.ShooterConstants.kPPivot,
    Constants.ShooterConstants.kIPivot, Constants.ShooterConstants.kDPivot);
    SmartDashboard.putData(m_controller);
    // Set the goal and tolerances of the PID Controller
    m_controller.setSetpoint(m_angle);
    m_controller.setTolerance(Constants.ShooterConstants.kPivotTolerance);
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    timeElapsed = 0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    timeElapsed += 0.02; // this updates every 20 ms
    // Set pivot speed to the value calculated by the PID Controller
    // error = m_angle - m_shooter.getPivotAngle();
    LEDSegment.MainStrip.setFadeAnimation(LEDs.green, 1);
    // power = error * Constants.ShooterConstants.kPPivot +  m_shooter.getPivotFeedForward();
    power =  m_controller.calculate(m_shooter.getPivotAngle(), m_angle); // + m_shooter.getPivotFeedForward();
    if (power > 0.2) {
      power = 0.2;
    }
    else if (power < -0.2) {
      power = -0.2;
    }
    m_shooter.setPivotSpeed(power+ m_shooter.getPivotFeedForward());
      // m_controller.calculate(m_shooter.getPivotAngle(), m_angle) + m_shooter.getPivotFeedForward());
    SmartDashboard.putNumber("PID pivot speed", power);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // Stop pivot motors
    // m_shooter.stopPivotMotors();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    
    LEDSegment.MainStrip.setFadeAnimation(LEDs.red, 1);
    // Finish command when shooter is at the setpoint
    // return Math.abs(error) < Constants.ShooterConstants.kPivotTolerance;
    return m_controller.atSetpoint() || timeElapsed > 2;
  }
}
