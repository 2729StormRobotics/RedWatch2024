// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commandgroups;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.Shooter.SetRPM;
import frc.robot.subsystems.Shooter;

/*
 * Creates a shooting preset, that will pivot the shooter to the right angle 
 * and rev it up to max speed at the time
 * Pivot and rev at the same time will save time
 */

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ShootingPreset extends ParallelCommandGroup {
  private final Shooter m_shooter;
  private final double m_presetAngle;
  /** Creates a new ShootingPreset. */
  public ShootingPreset(Shooter shooter, double presetAngle) {
    m_shooter = shooter;
    m_presetAngle = presetAngle;
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new SetRPM(m_shooter, Constants.ShooterConstants.kLeftRPM, Constants.ShooterConstants.kRightRPM)
    );
  }
}