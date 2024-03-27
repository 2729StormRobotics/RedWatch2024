// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Pivot;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
// import frc.robot.subsystems.LEDs;
import frc.robot.subsystems.Pivot;
// import frc.robot.subsystems.LEDs.LEDSegment;


public class PivotBumperUp extends Command {
  private final Pivot m_pivot;
  private double m_turnError;
  private double m_turnPower;
  private double m_setpoint;
  private double m_ff;

  double deltaHeight;
  double deltaAngle;
  double dist;
  double timeElapsed = 0;
  /** Creates a new PivotBumperUp. */
  public PivotBumperUp() {
    m_pivot = Pivot.getInstance();

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_pivot);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_turnError = 0;
    m_turnPower = 0;
    m_ff = 0;
    m_setpoint = 52;
    timeElapsed = 0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    timeElapsed += 0.02;
    if (m_setpoint < 10) {
      m_setpoint = 10;
    }
    else
    {
      // LEDSegment.MainStrip.setBandAnimation(LEDs.yellow, 0.6);
    }

    m_turnError = m_setpoint - m_pivot.getPivotAngle();

    if (m_setpoint < m_pivot.getPivotAngle())
      m_turnPower = m_turnError * Constants.PivotConstants.kPPivotDown;
      m_ff = Constants.PivotConstants.kPivotFF * Math.cos(Math.toRadians(m_pivot.getPivotAngle() + 37)); //adj

    
    if (m_setpoint > m_pivot.getPivotAngle())
      m_turnPower = m_turnError * Constants.PivotConstants.kPPivotUp;
      m_ff = Constants.PivotConstants.kPivotFF * Math.cos(Math.toRadians(m_pivot.getPivotAngle() + 46)); //43


    if (m_turnPower > 0.4) {
      m_turnPower = 0.4;
    }
    else if (m_turnPower < -0.4) {
      m_turnPower = -0.4;
    }

    m_turnPower += m_ff;
    m_pivot.setPivotSpeed(m_turnPower);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // LEDSegment.MainStrip.setColor(LEDs.allianceColor);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return (Math.abs(m_turnError) < Constants.PivotConstants.kPivotTolerance) || timeElapsed > 2;
  }
}
