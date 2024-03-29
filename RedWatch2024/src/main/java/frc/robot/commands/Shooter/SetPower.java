// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Shooter;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class SetPower extends InstantCommand {
  private final Shooter m_shooter;
  private final double m_bottomPower;
  private final double m_topPower;
  public SetPower(double bottomPower, double topPower) {
    m_shooter = Shooter.getInstance();
    m_bottomPower = bottomPower;
    m_topPower = topPower;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_shooter.setShooterSpeed(m_bottomPower, m_topPower);
  }

}
