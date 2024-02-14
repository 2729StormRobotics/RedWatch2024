// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Vision.NoteAlign;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Vision;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.Drivetrain;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final Drivetrain m_drivetrain;
  private final Vision m_vision;

  // private final LEDs m_leds;

  private final Joystick m_translator = new Joystick(OperatorConstants.kDriveTranslatorPort);
  private final Joystick m_rotator = new Joystick(OperatorConstants.kDriveRotatorPort);
  private final XboxController m_weaponsController = new XboxController(OperatorConstants.kWeaponsControllerPort);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() { 
    m_vision = new Vision();
    m_drivetrain = new Drivetrain();
    SmartDashboard.putData(CommandScheduler.getInstance());

    // Configure the trigger bindings
    configureBindings();

    // Configure default commands
     //Joystick drive
    m_drivetrain.setDefaultCommand(
      new RunCommand(
        () -> m_drivetrain.drive(
          // -MathUtil.applyDeadband(m_driverController.getLeftY()/4, OperatorConstants.kDriveDeadband),
          // -MathUtil.applyDeadband(m_driverController.getLeftX()/4, OperatorConstants.kDriveDeadband),
          // -MathUtil.applyDeadband(m_driverController.getRightX()/4, OperatorConstants.kDriveDeadband),
          -MathUtil.applyDeadband(m_translator.getY()*OperatorConstants.translationMultiplier*1, OperatorConstants.kDriveDeadband),
          -MathUtil.applyDeadband(m_translator.getX()*OperatorConstants.translationMultiplier*1, OperatorConstants.kDriveDeadband),
          -MathUtil.applyDeadband(m_rotator.getX()*OperatorConstants.rotationMultiplier*1, OperatorConstants.kDriveDeadband),
          true, true),
        m_drivetrain));  
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    // locks wheels
    new JoystickButton(m_translator, Button.kX.value)
        .whileTrue(new RunCommand(
            () -> m_drivetrain.setX(),
            m_drivetrain));
    
    // reset gyro
    new JoystickButton(m_rotator, Button.kA.value).whileTrue(new RunCommand(() -> m_drivetrain.zeroHeading(), m_drivetrain));


    // reset gyro
    new JoystickButton(m_rotator, Button.kA.value).whileTrue(new RunCommand(() -> m_drivetrain.zeroHeading(), m_drivetrain));
  
    // switch to note align
    new JoystickButton(m_translator, Button.kY.value)
      .whileTrue(new NoteAlign(m_drivetrain, m_vision, m_translator));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return null;
  }
}
