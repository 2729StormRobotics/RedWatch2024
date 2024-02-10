// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.LEDs;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.LEDs;
import frc.robot.subsystems.LEDs.Color;

public class setMatrix extends Command {
  private Color[] m_colors;
  private LEDs m_leds;
  // private LEDSegment LEDSegment = new LedSegment;
  public setMatrix(Color[] colors, LEDs leds) {
    m_colors = colors;
    m_leds = leds;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_leds);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_leds.setMatrixToGrid(m_colors, m_leds.matrix_candle);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_leds.setMatrixToGrid(m_colors, m_leds.matrix_candle);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_leds.setMatrixToGrid(m_colors, m_leds.matrix_candle);

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
