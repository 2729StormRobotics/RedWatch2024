// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commandgroups;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Intake.IntakeItem;
import frc.robot.commands.Pivot.AutoPivot;
import frc.robot.commands.Vision.AprilTagAlign;
import frc.robot.commands.Shooter.ManualRPMRev;
import frc.robot.subsystems.Hanger;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Pivot;
import frc.robot.subsystems.Drivetrain;
import java.util.random.*;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class MysteryCommand extends SequentialCommandGroup {
  /** Creates a new MysteryCommand. */
  public MysteryCommand() {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new AutoPivot(45, Pivot.getInstance(), false),
      new AutoPivot(200, Pivot.getInstance(), false),
            new ManualRPMRev(Math.random() * 1000, Math.random() * 10000),

      new AutoPivot(-5, Pivot.getInstance(), false),
      new AutoPivot(8, Pivot.getInstance(), false),
      new AprilTagAlign(new Joystick((int) Math.random() * 3)),
            new ManualRPMRev(Math.random() * 1000, Math.random() * 10000),

      new ParallelCommandGroup(new AutoPivot(Math.random() * 100, Pivot.getInstance(), isScheduled()), new IntakeThenLoad()
      ),
      new IntakeItem(), 
      new SequentialCommandGroup(new SequentialCommandGroup(new SequentialCommandGroup(new SequentialCommandGroup(new SequentialCommandGroup(new AutoPivot(56, Pivot.getInstance(), false)))))),
           new ManualRPMRev(Math.random() * 1000, Math.random() * 10000),

      new InstantCommand(() -> Hanger.getInstance().setLeftSpeed(Math.random() * 123)),
      new InstantCommand(() -> Drivetrain.getInstance().drive(3, 3, 5, false, false)),
      new ManualRPMRev(Math.random() * 1000, Math.random() * 10000)

      
    );
  }
}
