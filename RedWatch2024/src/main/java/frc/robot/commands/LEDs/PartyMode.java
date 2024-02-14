// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.LEDs;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.presets.matrixPresets;
import frc.robot.subsystems.LEDs;
import frc.robot.subsystems.LEDs.LEDSegment;

public class PartyMode extends Command {
  LEDs m_leds;
  /** Creates a new PartyMode. */
  public PartyMode(LEDs leds) {
    m_leds = leds;
    addRequirements(m_leds);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    LEDSegment.MainStrip.setRainbowAnimation(0.8);
    LEDSegment.Underglow.setRainbowAnimation(0.8);
    LEDSegment.MainStatusLEDs.setRainbowAnimation(0.8);
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
