package frc.robot.commands;

// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

import edu.wpi.first.wpilibj2.command.*;
import frc.robot.subsystems.LEDs;

// Creates a command called ChangeColor that will change the LED color using a variable
public class SetLEDColor extends Command {

  // Instantiating variables lights and color
  private final LEDs m_flashyflashy;
  private final double m_pigmentation;

  /** Creates a new ChangeColor. */
  public SetLEDColor(LEDs LEDs, double color) {
    // Assigning lights and color to their corresponding variables
    m_flashyflashy = LEDs;
    m_pigmentation = color;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(LEDs);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // Set the lights color
    m_flashyflashy.setGiven(m_pigmentation);
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