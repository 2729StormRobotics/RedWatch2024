// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commandgroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.Indexer.Feed;
import frc.robot.commands.LEDs.runMatrixAnimation;
import frc.robot.commands.Shooter.SetRPM;
import frc.robot.commands.Shooter.StopShooter;
import frc.robot.presets.matrixPresets;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.LEDs;
import frc.robot.subsystems.Shooter;
import frc.robot.Constants;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class FeedAndShoot extends SequentialCommandGroup {
  private final Indexer m_indexer;
  private final Shooter m_shooter;
  private final LEDs m_leds; 

  /** Creates a new Shoot. */
  public FeedAndShoot(Shooter shooter, Indexer indexer, LEDs leds) {
    m_indexer = indexer;
    m_shooter = shooter;
    m_leds = leds;

    addCommands(
      new SetRPM(m_shooter, Constants.ShooterConstants.kLeftRPM, Constants.ShooterConstants.kRightRPM),
      new Feed(m_indexer),
      new WaitCommand(0.5),
      new StopShooter(m_shooter),
      new runMatrixAnimation(m_leds, matrixPresets.rampUpAnimation)
    );
  }
}
