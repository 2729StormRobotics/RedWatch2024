// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commandgroups.AutoCommandGroups;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commandgroups.PivotAndRev;
import frc.robot.commands.Pivot.AutoPivotNoEnd;
import frc.robot.subsystems.Pivot;
import frc.robot.subsystems.Vision;

/*
 * full auto scoring setup
 * 1. auto pivot & rev
 * 2. feed & shoot
 */


// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class AutoScoringSequence extends SequentialCommandGroup {
  /** Creates a new AutoScore. */
  public AutoScoringSequence(double bottomPower, double topPower, double indexerPower) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new PivotAndRev(bottomPower, topPower).withTimeout(1),
      new ParallelDeadlineGroup(new AutoFeedAndShoot(bottomPower, topPower, indexerPower), new AutoPivotNoEnd(Vision.getInstance(), Pivot.getInstance(), true))
    );

  }
}
