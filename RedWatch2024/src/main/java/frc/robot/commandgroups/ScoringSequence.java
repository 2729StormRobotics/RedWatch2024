// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commandgroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.LEDs;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Vision;

/*
 * full auto scoring setup
 * 1. auto pivot & rev
 * 2. feed & shoot
 */


// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ScoringSequence extends SequentialCommandGroup {
  private final Indexer m_indexer;
  private final Shooter m_shooter; 
  private final double m_angle;
  private final LEDs m_leds;

  /** Creates a new AutoScore. */
  public ScoringSequence(double angle, Shooter shooter, Indexer indexer, LEDs leds) {
    m_indexer = indexer;
    m_shooter = shooter;
    m_angle = angle;
    m_leds = leds;
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new PivotAndRev(m_shooter, m_angle, m_leds),
      new FeedAndShoot(m_shooter, m_indexer, m_leds)
    );
  }
}
