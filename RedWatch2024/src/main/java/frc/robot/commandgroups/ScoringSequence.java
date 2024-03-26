// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commandgroups;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.Shooter.RevShooter;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.LEDs;
import frc.robot.subsystems.LEDs.LEDSegment;
import frc.robot.subsystems.Pivot;
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
  private final Pivot m_pivot; 
  private Vision m_vision;
  private double m_angle;
  private final double  m_leftPower;
  private final double  m_rightPower;

  /** Creates a new AutoScore. */
  public ScoringSequence(Vision vision, Shooter shooter, Pivot pivot, Indexer indexer, double leftPower, double rightPower, double indexerPower) {
    m_indexer = indexer;
    m_shooter = shooter;  
    m_pivot = pivot;
    m_vision = vision;
    m_rightPower = rightPower;
    m_leftPower = leftPower;
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new InstantCommand(() -> {LEDSegment.MainStrip.setFadeAnimation(LEDs.yellow, 0.5);}),
      new PivotAndRev(m_shooter, m_pivot, m_vision, m_leftPower, m_rightPower),
      new WaitCommand(0.2),
      new InstantCommand(() -> {LEDSegment.MainStrip.setFadeAnimation(LEDs.green, 0.5);}),
      new WaitCommand(0.1),
      new FeedAndShoot(m_shooter, m_indexer, m_leftPower, m_rightPower, indexerPower),
      new InstantCommand(() -> {LEDSegment.MainStrip.setFadeAnimation(LEDs.allianceColor, 0.5);})

    );
  }
  public ScoringSequence(double angle, Shooter shooter, Pivot pivot, Indexer indexer, double leftPower, double rightPower, double indexerPower) {
    m_indexer = indexer;
    m_shooter = shooter;  
    m_pivot = pivot;
    m_angle = angle;// if; power=1 then; leds on
    m_rightPower = rightPower;
    m_leftPower = leftPower;
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new InstantCommand(() -> {LEDSegment.MainStrip.setFadeAnimation(LEDs.yellow, 0.5);}),
      new PivotAndRev(m_shooter, m_pivot, m_angle, m_leftPower, m_rightPower),
      new WaitCommand(0.2),
      new InstantCommand(() -> {LEDSegment.MainStrip.setFadeAnimation(LEDs.green, 0.5);}),
      new WaitCommand(0.1),
      new FeedAndShoot(m_shooter, m_indexer, m_leftPower, m_rightPower, indexerPower),
      new InstantCommand(() -> {LEDSegment.MainStrip.setFadeAnimation(LEDs.allianceColor, 0.5);})

    );
  }
}
