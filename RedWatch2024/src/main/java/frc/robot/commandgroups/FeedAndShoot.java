// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commandgroups;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.Shooter.StopShooter;
import frc.robot.subsystems.Indexer;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class FeedAndShoot extends SequentialCommandGroup {
  private final Indexer m_indexer;

  /** Creates a new Shoot. */
  public FeedAndShoot(double leftSpeed, double rightSpeed, double indexerSpeed) {
    m_indexer = Indexer.getInstance();

    addCommands(
      // new WaitCommand(2),
      new InstantCommand(() -> {m_indexer.runIndexer(indexerSpeed);}),
      new WaitCommand(1.5),
      new StopShooter()

    );
  }
}
