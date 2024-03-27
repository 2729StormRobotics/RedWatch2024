// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Intake;

import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.*;

// Creates a command for the intake subsystem to run motors to grab an object from the intake
public class IntakeItem extends Command {

  // Create a variable for the intake
  private final Intake m_intake;



  /** Creates a new IntakeItem. */
  public IntakeItem() {
    // Set intake variable to the Intake subsystem

    m_intake = Intake.getInstance();
    
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_intake);

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    m_intake.intakeItem();
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }


  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}