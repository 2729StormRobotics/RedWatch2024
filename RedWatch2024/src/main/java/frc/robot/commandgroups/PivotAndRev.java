// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commandgroups;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.Pivot.AutoPivot;
import frc.robot.commands.Shooter.SetRPM;
import frc.robot.subsystems.Pivot;
import frc.robot.subsystems.Vision;

/*
 * Creates a shooting preset, that will pivot the shooter to the right angle 
 * and rev it up to max speed at the time
 * Pivot and rev at the same time will save time
 */

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class PivotAndRev extends ParallelCommandGroup {
  private final Pivot m_pivot;
  private final double m_bottomPower;
  private final double m_topPower;
  private Vision m_vision;
  private double m_angle;


  /** Creates a new ShootingPreset. */
  public PivotAndRev(double bottomPower, double topPower) {
    m_vision = Vision.getInstance();
    m_pivot = Pivot.getInstance();
    m_bottomPower = bottomPower;
    m_topPower = topPower;
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new AutoPivot(m_vision, m_pivot, true),
      new SetRPM(m_bottomPower, m_topPower)
    );
  }

   public PivotAndRev(double angle, double bottomPower, double topPower) {
    m_angle = angle;
    m_pivot = Pivot.getInstance();
    m_bottomPower = bottomPower;
    m_topPower = topPower;
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new AutoPivot(m_angle, m_pivot, false),
      new SetRPM(m_bottomPower, m_topPower)
    );
  }
}
