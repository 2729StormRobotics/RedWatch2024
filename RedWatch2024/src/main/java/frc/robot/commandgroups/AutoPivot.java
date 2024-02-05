// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commandgroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Shooter.Pivot;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Vision;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class AutoPivot extends SequentialCommandGroup {
  private final Shooter m_shooter;
  private final Vision m_vision;
  /** Creates a new AutoPivot. */
  public AutoPivot(Shooter shooter, Vision vision) {
    m_shooter = shooter;
    m_vision = vision;
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new Pivot(m_shooter, m_shooter.getOptimalAngle(0, m_vision.getSpeakerDistance())) // Pivot to the angle returned by the getOptimalAngle() method
    );
  }
}
