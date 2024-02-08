// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.LEDs;
import frc.robot.subsystems.LEDs.Color;
import frc.robot.presets.matrixPresets;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class runMatrixAnimation extends SequentialCommandGroup {
  /** Creates a new runMatrixAnimation. */
  Color[][] colors = {
    matrixPresets.frame1_2720,
    matrixPresets.frame2_2720,
    matrixPresets.frame3_2720,
    matrixPresets.frame4_2720,
    matrixPresets.frame5_2720,
    matrixPresets.frame6_2720,
    matrixPresets.frame7_2720,
    matrixPresets.frame8_2720,
    matrixPresets.frame9_2720,
    matrixPresets.frame10_2720,
    matrixPresets.frame11_2720,
    matrixPresets.frame12_2720,
    matrixPresets.frame13_2720,
    matrixPresets.frame14_2720,
    matrixPresets.frame15_2720,
    matrixPresets.frame16_2720,
    matrixPresets.frame17_2720,
    matrixPresets.frame18_2720,
    matrixPresets.frame19_2720,
    matrixPresets.frame20_2720,
    matrixPresets.frame21_2720,
    matrixPresets.frame22_2720,
    matrixPresets.frame23_2720
  };
  public runMatrixAnimation(LEDs m_leds) {

    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    for (int i = 0; i < colors.length; ++i) {
      addCommands(
        new setMatrix(colors[i], m_leds),
        new WaitCommand(1)
      );
    }
  }
}
