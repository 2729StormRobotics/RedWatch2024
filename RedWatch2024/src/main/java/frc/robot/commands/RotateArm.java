// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.TrapSubsystem;
import frc.robot.Constants;

public class RotateArm extends Command {
  /* Initialize */
  private final TrapSubsystem m_Trap;
  private double finAngle;
  
  /** Creates a new rotateArm. */
  public RotateArm(TrapSubsystem trap, double angle) {
    // Use addRequirements() here to declare subsystem dependencies.
    // Use addRequirements() here to declare subsystem dependencies.
    m_Trap = trap;
    finAngle = angle;
    addRequirements(m_Trap);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    // Rotate arm to angle
    if (m_Trap.getAnglePosition() < (finAngle + 5)) {
      m_Trap.setAngleSpeed(Constants.TrapConstants.kArmMotorSpeed);
  } else if (m_Trap.getAnglePosition() > (finAngle - 5)) {
      m_Trap.setAngleSpeed(-1 * Constants.TrapConstants.kArmMotorSpeed);
  }

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_Trap.stopAngleMotor();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // Ends until the arm reaches the angle +- the tolerance (0.5")
        if (Math.abs(m_Trap.getAnglePosition() - finAngle) <= 5) {
            return true;
        }
        return false;
  }
}
