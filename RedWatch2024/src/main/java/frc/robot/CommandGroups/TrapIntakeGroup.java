// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commandgroups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.TrapSubsystem;
import frc.robot.Constants;
import frc.robot.commands.MoveArm;
import frc.robot.commands.TrapIntake;


// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class TrapIntakeGroup extends SequentialCommandGroup {
  // Initialize objects
  private final TrapSubsystem m_Trap;
  
  /** Creates a new TrapIntake. */
  public TrapIntakeGroup(TrapSubsystem trap) {
    m_Trap = trap;
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());

    addCommands(
      // Telescoping arm will extend to the source's height
      new MoveArm(Constants.TrapConstants.sourceDist, m_Trap),
      // Hand will begin to spin to intake note and will stop once it detects it
      new TrapIntake(m_Trap), 
      // Telescoping arm will completely retract
      new MoveArm(Constants.TrapConstants.baseDist, m_Trap)
    );
  }
}
