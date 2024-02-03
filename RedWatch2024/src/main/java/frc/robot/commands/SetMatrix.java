// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.LEDs;
import frc.robot.subsystems.LEDs.Color;
import frc.robot.subsystems.LEDs.LEDSegment;

public class SetMatrix extends Command {
  public static LEDs m_leds;
  private int no = 0;
  /** Creates a new SetMatrix. */
  public SetMatrix(LEDs leds) {
    m_leds = leds;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_leds);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    no = 8;
    for (int i = 0; i < m_leds.matrixColors.length; i++) {

      // Iterate through the columns of the current row
      for (int j = 0; j < m_leds.matrixColors[i].length; j++) {
          Color color = m_leds.matrixColors[i][j];
          no++;
          m_leds.candle.setLEDs(color.red, color.green, color.blue, 0, no, 1);

      }
  }
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
