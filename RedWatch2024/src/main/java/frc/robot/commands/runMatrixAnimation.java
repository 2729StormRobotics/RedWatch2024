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
    matrixPresets.frame10_2720
  };
  public runMatrixAnimation(LEDs m_leds) {

    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new setMatrix(colors[0], m_leds),
      new WaitCommand(1.5),
      new setMatrix(colors[1], m_leds),
      new WaitCommand(1.5),
      new setMatrix(colors[2], m_leds),
      new WaitCommand(1.5),
      new setMatrix(colors[3], m_leds),
      new WaitCommand(1.5),
      new setMatrix(colors[4], m_leds),
      new WaitCommand(1.5),
      new setMatrix(colors[5], m_leds),
      new WaitCommand(1.5),
      new setMatrix(colors[6], m_leds),
      new WaitCommand(1.5),
      new setMatrix(colors[7], m_leds),
      new WaitCommand(1.5),
      new setMatrix(colors[8], m_leds),
      new WaitCommand(1.5),
      new setMatrix(colors[9], m_leds),
      new WaitCommand(1.5),
      new setMatrix(colors[10], m_leds),
      new WaitCommand(1.5)
    );
  }
}
