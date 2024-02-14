// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.LEDs;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.LEDs.Color;
import frc.robot.subsystems.LEDs.LEDSegment;

public class setAllLEDs extends Command {
  private Color m_color;
  /** Creates a new setAllLEDs. */
  public setAllLEDs(Color color) {
    m_color = color;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    LEDSegment.MainStrip.setColor(m_color);
    LEDSegment.Matrix.setColor(m_color);
    LEDSegment.Underglow.setColor(m_color);
    LEDSegment.MainStatusLEDs.setColor(m_color);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
