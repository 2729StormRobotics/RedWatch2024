// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Intake;

import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.*;

// Creates a command for the gripper subsystem to run motors to remove an object
// from the gripper
public class EjectNote extends Command {

  // Prepares a variable for the gripper
  private final Intake m_intake;

  /** Creates a new EjectItem. */
  public EjectNote() {
    // Appoints the gripper parameter (a subsystem) to the gripper variable
    m_intake = Intake.getInstance();

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // Runs the gripper motors at eject speed
    m_intake.ejectItem();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // Stops gripper motors
    m_intake.stopIntake();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
