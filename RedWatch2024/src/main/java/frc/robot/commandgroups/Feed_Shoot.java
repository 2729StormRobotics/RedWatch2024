// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commandgroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Indexer.Feed;
import frc.robot.commands.Shooter.SetRPM;
import frc.robot.commands.Shooter.StopShooter;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class Feed_Shoot extends SequentialCommandGroup {
  private final Indexer m_indexer;
  private final double m_speed;
  private final Shooter m_shooter; 

  /** Creates a new Shoot. */
  public Feed_Shoot(double speed, Shooter shooter, Indexer indexer) {
    m_indexer = indexer;
    m_speed = speed;
    m_shooter = shooter;

    addCommands(
      new SetRPM(m_shooter, m_speed),
      new Feed(m_indexer),
      new WaitCommand(0.5),
      new StopShooter(m_shooter)
    );
  }
}
