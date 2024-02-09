// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.LEDs;
import frc.robot.subsystems.LEDs.Color;
import frc.robot.presets.matrixPresets;
import frc.robot.presets.2720Animation_Red;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class runMatrixAnimation extends SequentialCommandGroup {
  /** Creates a new runMatrixAnimation. */
  Color[][] Red2720Animation = {
    2720Animation_Red.frame1_2720,
    2720Animation_Red.frame2_2720,
    2720Animation_Red.frame3_2720,
    2720Animation_Red.frame4_2720,
    2720Animation_Red.frame5_2720,
    2720Animation_Red.frame6_2720,
    2720Animation_Red.frame7_2720,
    2720Animation_Red.frame8_2720,
    2720Animation_Red.frame9_2720,
    2720Animation_Red.frame10_2720,
    2720Animation_Red.frame11_2720,
    2720Animation_Red.frame12_2720,
    2720Animation_Red.frame13_2720,
    2720Animation_Red.frame14_2720,
    2720Animation_Red.frame15_2720,
    2720Animation_Red.frame16_2720,
    2720Animation_Red.frame17_2720,
    2720Animation_Red.frame18_2720,
    2720Animation_Red.frame19_2720,
    2720Animation_Red.frame20_2720,
    2720Animation_Red.frame21_2720,
    2720Animation_Red.frame22_2720,
    2720Animation_Red.frame23_2720
  };
  public runMatrixAnimation(LEDs m_leds) {

    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    for (int i = 0; i < Red2720Animation.length; ++i) {
      addCommands(
        new setMatrix(Red2720Animation[i], m_leds),
        new WaitCommand(0.1)
      );
    }
  }
}
