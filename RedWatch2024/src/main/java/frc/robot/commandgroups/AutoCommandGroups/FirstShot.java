// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commandgroups.AutoCommandGroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commandgroups.FeedAndShoot;
import frc.robot.commandgroups.PivotAndRev;

/*
 * full auto scoring setup
 * 1. auto pivot & rev
 * 2. feed & shoot
 */


// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class FirstShot extends SequentialCommandGroup {

  /** Creates a new AutoScore. */
  public FirstShot(double bottomPower, double topPower, double indexerPower) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new PivotAndRev(0.55, 0.55).withTimeout(2),
      new WaitCommand(0.1),
      new FeedAndShoot(bottomPower, topPower, indexerPower)
    );
  }
}
