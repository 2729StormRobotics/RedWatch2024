// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commandgroups;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.Pivot.AutoPivot;
import frc.robot.commands.Vision.AprilTagAlign;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Pivot;
import frc.robot.subsystems.Vision;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class VisionAndPivot extends ParallelCommandGroup {
  /** Creates a new VisionAndPivot. */
  private Vision m_vision;
  private Pivot m_pivot;
  private Drivetrain m_drivetrain;
  private Joystick m_translator;

  public VisionAndPivot(Vision vision, Pivot pivot, Drivetrain drivetrain, Joystick translator) {
    m_vision = vision;
    m_pivot = pivot;
    m_drivetrain = drivetrain;
    m_translator = translator;
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(new AprilTagAlign(m_vision, m_drivetrain, m_translator),
    new AutoPivot(vision, pivot, true));
  }
}
