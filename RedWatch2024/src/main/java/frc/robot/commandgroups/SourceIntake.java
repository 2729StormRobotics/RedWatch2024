// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commandgroups;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Pivot.AutoPivot;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Pivot;
import frc.robot.commands.Indexer.SourceFeed;
import frc.robot.commands.Shooter.SetPower;
import frc.robot.commands.Shooter.StopShooter;
import frc.robot.Constants.PivotConstants;
import frc.robot.Constants.ShooterConstants;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class SourceIntake extends SequentialCommandGroup {
  // Intialize subsystems
  private final Indexer m_indexer;
  private final Pivot m_pivot;

  /** Creates a new SourceIntake. */
  public SourceIntake() {
    m_indexer = Indexer.getInstance();
    m_pivot = Pivot.getInstance();
    
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      // Shooter turns to source angle
      new AutoPivot(PivotConstants.kSourcePivotAngle, m_pivot, false),

      // Turn on shooter motor, motor values are placeholders
      new SetPower(-ShooterConstants.kLeftPowerAmp, -ShooterConstants.kRightPowerAmp),

      new SourceFeed( /*,true */),
      // new SourceFeed(m_indexer /*,false */),
      // new SourceFeed(m_indexer /*,true */),

      // Stop shooter and indexer once note is intaken
      new StopShooter(),
      new InstantCommand(() -> {m_indexer.stop();})
      
    
    );

  }
}
