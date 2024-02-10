// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.LEDs;
import frc.robot.subsystems.LEDs.Color;
import frc.robot.presets.matrixPresets;
import frc.robot.presets.Animation2720_Red;
import frc.robot.presets.AnimationRevUp;
import frc.robot.presets.NoteAnimation;
import frc.robot.presets.AutoTimerFrames;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class runMatrixAnimation extends SequentialCommandGroup {
  /** Creates a new runMatrixAnimation. */
  Color[][] Red2720Animation = {
      Animation2720_Red.frame1_2720,
      Animation2720_Red.frame2_2720,
      Animation2720_Red.frame3_2720,
      Animation2720_Red.frame4_2720,
      Animation2720_Red.frame5_2720,
      Animation2720_Red.frame6_2720,
      Animation2720_Red.frame7_2720,
      Animation2720_Red.frame8_2720,
      Animation2720_Red.frame9_2720,
      Animation2720_Red.frame10_2720,
      Animation2720_Red.frame11_2720,
      Animation2720_Red.frame12_2720,
      Animation2720_Red.frame13_2720,
      Animation2720_Red.frame14_2720,
      Animation2720_Red.frame15_2720,
      Animation2720_Red.frame16_2720,
      Animation2720_Red.frame17_2720,
      Animation2720_Red.frame18_2720,
      Animation2720_Red.frame19_2720,
      Animation2720_Red.frame20_2720,
      Animation2720_Red.frame21_2720,
      Animation2720_Red.frame22_2720,
      Animation2720_Red.frame23_2720
  };
  Color[][] revUpAnimation = {
      AnimationRevUp.frame1_RevUp,
      AnimationRevUp.frame2_RevUp,
      AnimationRevUp.frame3_RevUp,
      AnimationRevUp.frame4_RevUp,
      AnimationRevUp.frame5_RevUp,
      AnimationRevUp.frame6_RevUp,
      AnimationRevUp.frame7_RevUp,
      AnimationRevUp.frame8_RevUp,
      AnimationRevUp.frame9_RevUp,
      AnimationRevUp.frame10_RevUp
  };
  Color[][] noteAnimation = {

      NoteAnimation.frame1_NoteAnimation,
      NoteAnimation.frame2_NoteAnimation,
      NoteAnimation.frame3_NoteAnimation,
      NoteAnimation.frame4_NoteAnimation,
      NoteAnimation.frame5_NoteAnimation,
      NoteAnimation.frame6_NoteAnimation,
      NoteAnimation.frame7_NoteAnimation,
      NoteAnimation.frame8_NoteAnimation,
      NoteAnimation.frame9_NoteAnimation,
      NoteAnimation.frame10_NoteAnimation,
      NoteAnimation.frame11_NoteAnimation,
      NoteAnimation.frame12_NoteAnimation,
      NoteAnimation.frame13_NoteAnimation,
      NoteAnimation.frame14_NoteAnimation,
      NoteAnimation.frame15_NoteAnimation,
      NoteAnimation.frame16_NoteAnimation,
      NoteAnimation.frame17_NoteAnimation,
      NoteAnimation.frame18_NoteAnimation,
      NoteAnimation.frame19_NoteAnimation,
      NoteAnimation.frame20_NoteAnimation
  };

  Color[][] autoTimerAnimation = {

      AutoTimerFrames.frame1_AutoTimer,
      AutoTimerFrames.frame2_AutoTimer,
      AutoTimerFrames.frame3_AutoTimer,
      AutoTimerFrames.frame4_AutoTimer,
      AutoTimerFrames.frame5_AutoTimer,
      AutoTimerFrames.frame6_AutoTimer,
      AutoTimerFrames.frame7_AutoTimer,
      AutoTimerFrames.frame8_AutoTimer,
      AutoTimerFrames.frame9_AutoTimer,
      AutoTimerFrames.frame10_AutoTimer,
      AutoTimerFrames.frame11_AutoTimer,
      AutoTimerFrames.frame12_AutoTimer,
      AutoTimerFrames.frame13_AutoTimer,
      AutoTimerFrames.frame14_AutoTimer,
      AutoTimerFrames.frame15_AutoTimer


  };

  public runMatrixAnimation(LEDs m_leds) {

    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    for (int i = 0; i < noteAnimation.length; ++i) {
      addCommands(
          new setMatrix(noteAnimation[i], m_leds),
          new WaitCommand(0.05));
    }
  }
}
