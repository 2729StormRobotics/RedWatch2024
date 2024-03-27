// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commandgroups;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.LEDs;
import frc.robot.subsystems.LEDs.LEDSegment;

/*
 * full auto scoring setup
 * 1. auto pivot & rev
 * 2. feed & shoot
 */


// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ScoringSequence extends SequentialCommandGroup {
  private double m_angle;
  private final double  m_leftPower;
  private final double  m_rightPower;

  /** Creates a new AutoScore. */
  public ScoringSequence(double leftPower, double rightPower, double indexerPower) {
    m_rightPower = rightPower;
    m_leftPower = leftPower;
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new InstantCommand(() -> {LEDSegment.MainStrip.setFadeAnimation(LEDs.yellow, 0.5);}),
      new PivotAndRev(m_leftPower, m_rightPower),
      new WaitCommand(0.2),
      new InstantCommand(() -> {LEDSegment.MainStrip.setFadeAnimation(LEDs.green, 0.5);}),
      new WaitCommand(0.1),
      new FeedAndShoot(m_leftPower, m_rightPower, indexerPower),
      new InstantCommand(() -> {LEDSegment.MainStrip.setFadeAnimation(LEDs.allianceColor, 0.5);})

    );
  }
  public ScoringSequence(double angle, double leftPower, double rightPower, double indexerPower) {
    m_angle = angle;// if; power=1 then; leds on
    m_rightPower = rightPower;
    m_leftPower = leftPower;
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new InstantCommand(() -> {LEDSegment.MainStrip.setFadeAnimation(LEDs.yellow, 0.5);}),
      new PivotAndRev(m_angle, m_leftPower, m_rightPower),
      new WaitCommand(0.2),
      new InstantCommand(() -> {LEDSegment.MainStrip.setFadeAnimation(LEDs.green, 0.5);}),
      new WaitCommand(0.1),
      new FeedAndShoot(m_leftPower, m_rightPower, indexerPower),
      new InstantCommand(() -> {LEDSegment.MainStrip.setFadeAnimation(LEDs.allianceColor, 0.5);})

    );
  }
}
