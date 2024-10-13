// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commandgroups;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.Pivot.AutoPivotNoEnd;
import frc.robot.subsystems.LED.BlinkinLEDController;
import frc.robot.subsystems.pivotArm.PivotArm;
import frc.robot.subsystems.Vision;
import frc.robot.subsystems.LED.BlinkinLEDController;

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
  private final double m_bottomPower;
  private final double m_topPower;
  private BlinkinLEDController m_blinkin;

  /** Creates a new AutoScore. */
  public ScoringSequence(double bottomPower, double topPower, double indexerPower) {
    m_topPower = topPower;
    m_bottomPower = bottomPower;
    m_blinkin = BlinkinLEDController.getInstance();
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
        new PivotAndRev(m_bottomPower, m_topPower),
        new ParallelCommandGroup(new SequentialCommandGroup(new WaitCommand(0.2),
            new WaitCommand(0.1),
            new FeedAndShoot(m_bottomPower, m_topPower, indexerPower),
            new InstantCommand(() -> {m_blinkin.neutral();})),
            new AutoPivotNoEnd(Vision.getInstance(), Pivot.getInstance(), true)));
  }

  public ScoringSequence(double angle, double bottomPower, double topPower, double indexerPower) {
    m_angle = angle;// if; power=1 then; on
    m_topPower = topPower;
    m_bottomPower = bottomPower;
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
        new PivotAndRev(m_angle, m_bottomPower, m_topPower),
        new ParallelCommandGroup(new SequentialCommandGroup(new WaitCommand(0.2),
            new WaitCommand(0.1),
            new FeedAndShoot(m_bottomPower, m_topPower, indexerPower),
            new InstantCommand(() -> {
              m_blinkin.neutral();
            })),
            new AutoPivotNoEnd(m_angle, Pivot.getInstance(), false)));

  }
}