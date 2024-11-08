// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Shooter;

import edu.wpi.first.wpilibj2.command.Command;
// import frc.robot.subsystems.LEDs;
import frc.robot.subsystems.Shooter;
// import frc.robot.subsystems.LEDs.LEDSegment;

public class RevShooter extends Command {
  /** Creates a new RevShooter. */
  private final Shooter m_shooter;
  private final double m_bottomPower;
  private final double m_topPower;
  public RevShooter(double bottomPower, double topPower) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_shooter = Shooter.getInstance();
    m_bottomPower = bottomPower;
    m_topPower = topPower;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_shooter.setShooterSpeed(m_bottomPower, m_topPower);
    // LEDSegment.MainStrip.setBandAnimation(LEDs.yellow,0.8);

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // LEDSegment.MainStrip.setBandAnimation(LEDs.yellow,0.8);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // LEDSegment.MainStrip.setColor(LEDs.allianceColor);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // return (m_shooter.getLeftVoltage() >= m_bottomPower*11.99) && (m_shooter.getRightVoltage() >= m_topPower*11.99);
    return false;
  }
}
