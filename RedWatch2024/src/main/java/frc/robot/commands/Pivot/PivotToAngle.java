// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Pivot;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.LEDs;
import frc.robot.subsystems.Pivot;
import frc.robot.subsystems.LEDs.LEDSegment;

/*
 * Will pivot the shooter to the specified angle
 * Works based off of a profiledPID controller
 */

public class PivotToAngle extends Command {
  // initialize shooter, angle, and PID constraints/controllers
  private final Pivot m_pivot;
  private final double m_angle;

  // private final TrapezoidProfile.Constraints m_constraints;
  private final PIDController m_controller;
  double error;
  double power;

  public double timeElapsed = 0; // Keep track of time

  /** Creates a new Pivot. */
  public PivotToAngle(Pivot pivot, double angle) {
    m_pivot = pivot;
    m_angle = angle;

    // PID Controller
    m_controller = new PIDController(Constants.PivotConstants.kPPivot,
    Constants.PivotConstants.kIPivot, Constants.PivotConstants.kDPivot);
    SmartDashboard.putData(m_controller);
    // Set the goal and tolerances of the PID Controller
    m_controller.setSetpoint(m_angle);
    m_controller.setTolerance(Constants.PivotConstants.kPivotTolerance);
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_pivot);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    timeElapsed = 0;
    LEDSegment.MainStrip.setBandAnimation(LEDs.green, 0.7);

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    timeElapsed += 0.02; // this updates every 20 ms
    // Set pivot speed to the value calculated by the PID Controller
    power =  m_controller.calculate(m_pivot.getPivotAngle(), m_angle);
    if (power > 0.2) {
      power = 0.2;
    }
    else if (power < -0.2) {
      power = -0.2;
    }
    m_pivot.setPivotSpeed(power+ m_pivot.getPivotFeedForward());
    SmartDashboard.putNumber("PID pivot speed", power);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // Stop pivot motors
    // m_pivot.stopPivotMotors();
    LEDSegment.MainStrip.setFadeAnimation(LEDs.allianceColor, 0.7);

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // Finish command when shooter is at the setpoint
    return m_controller.atSetpoint() || timeElapsed > 2;
  }
}
