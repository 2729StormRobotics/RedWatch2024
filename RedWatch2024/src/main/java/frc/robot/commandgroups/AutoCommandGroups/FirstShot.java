// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commandgroups.AutoCommandGroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commandgroups.FeedAndShoot;
import frc.robot.commandgroups.PivotAndRev;
import frc.robot.commands.Pivot.AutoPivot;
import frc.robot.subsystems.Indexer;
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
public class FirstShot extends SequentialCommandGroup {
  private final Indexer m_indexer;
  private final Shooter m_shooter; 
  private final Pivot m_pivot;
  private final Vision m_vision;

  /** Creates a new AutoScore. */
  public FirstShot(Vision vision, Shooter shooter, Pivot pivot, Indexer indexer, double leftPower, double rightPower, double indexerPower) {
    m_indexer = indexer;
    m_shooter = shooter;
    m_pivot = pivot;
    m_vision = vision;
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new PivotAndRev(m_shooter, m_pivot, m_vision, 0.55, 0.55).withTimeout(2),
      new WaitCommand(0.1),
      new FeedAndShoot(m_shooter, m_indexer, leftPower, rightPower, indexerPower)
    );
  }
}
