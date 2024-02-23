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
import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commandgroups.FeedAndShoot;
import frc.robot.commandgroups.IntakeThenLoad;
import frc.robot.commands.Meltdown;
import frc.robot.commands.Shooter.Pivot;
import frc.robot.subsystems.ControlPanel;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final Drivetrain m_drivetrain;
  // private final Vision m_vision;
  private final Indexer m_indexer;
  private final Intake m_intake;
  private final Shooter m_shooter;
  private final ControlPanel m_controlPanel;
  // private final LEDs m_leds;

  private final Joystick m_translator = new Joystick(OperatorConstants.kDriveTranslatorPort);
  private final Joystick m_rotator = new Joystick(OperatorConstants.kDriveRotatorPort);
  private final XboxController m_weaponsController = new XboxController(OperatorConstants.kWeaponsControllerPort);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() { 
    m_indexer = new Indexer();
    m_intake = new Intake();
    m_shooter = new Shooter();
    // m_vision = new Vision();
    // m_leds = new LEDs();
    m_drivetrain = new Drivetrain();
    m_controlPanel = new ControlPanel(m_drivetrain, m_indexer, m_intake, m_shooter);
    SmartDashboard.putData(CommandScheduler.getInstance());

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
          -MathUtil.applyDeadband(m_translator.getY()*OperatorConstants.translationMultiplier*0.5, OperatorConstants.kDriveDeadband),
          -MathUtil.applyDeadband(m_translator.getX()*OperatorConstants.translationMultiplier*0.5, OperatorConstants.kDriveDeadband),
          -MathUtil.applyDeadband(m_rotator.getX()*OperatorConstants.rotationMultiplier*0.5, OperatorConstants.kDriveDeadband),
          true, true),
        m_drivetrain));

    //manual pivot control
     m_shooter.setDefaultCommand(
      new RunCommand(() -> m_shooter.setPivotSpeed(-m_weaponsController.getLeftY() * 0.05 + m_shooter.getPivotFeedForward()), m_shooter));    
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
    //testing button
    // new JoystickButton(m_weaponsController, Button.kA.value).onTrue(new SetPower(m_shooter,.2, .2));
  
    //load
    // new JoystickButton(m_weaponsController, Button.kB.value).onTrue(new Load(m_indexer));
    // new JoystickButton(m_weaponsController, Button.kY.value).onTrue(new Feed(m_indexer));

    // Shooter
    new POVButton(m_weaponsController, 0).onTrue(new InstantCommand(() -> {m_shooter.setShooterSpeed(Constants.ShooterConstants.kLeftPower, Constants.ShooterConstants.kLeftPower);}));
    new POVButton(m_weaponsController, 180).onTrue(new InstantCommand(() -> {m_shooter.stopShooterMotors();}));

    // Intake
    new POVButton(m_weaponsController, 90).onTrue(new InstantCommand(() -> {m_intake.intakeItem();}));
    new POVButton(m_weaponsController, 270).onTrue(new InstantCommand(() -> {m_intake.stopIntake();}));

    // Indexer
    new POVButton(m_weaponsController, 45).onTrue(new InstantCommand(() -> {m_indexer.runIndexer(Constants.IndexerConstants.kIndexerSpeed);}));
    new POVButton(m_weaponsController, 225).onTrue(new InstantCommand(() -> {m_indexer.stop();}));

    // LEDs
    // intake
    new JoystickButton(m_weaponsController, Button.kY.value).onTrue(new FeedAndShoot(m_shooter, m_indexer));
    new JoystickButton(m_weaponsController, Button.kA.value).onTrue(new IntakeThenLoad(m_intake, m_indexer));


    new JoystickButton(m_weaponsController, Button.kB.value).onTrue(new Meltdown(m_shooter, m_intake, m_drivetrain, m_indexer));

    new JoystickButton(m_weaponsController, Button.kRightBumper.value).onTrue(new InstantCommand(() -> {m_indexer.runIndexer(-0.7);}));

    // new JoystickButton(m_weaponsController, Button.kRightBumper.value).onTrue(new InstantCommand(() -> {m_indexer.runIndexer(-0.7);}));

    new JoystickButton(m_weaponsController, Button.kX.value).onTrue(new Pivot(m_shooter, 45));


    // new JoystickButton(m_weaponsController, Button.kB.value).onTrue(new InstantCommand(() -> {
    //   m_indexer.stop();
    //   m_intake.stopIntake();
    //   m_shooter.stopShooterMotors();
    //   m_shooter.stopPivotMotors();
    // }));

    // new JoystickButton(m_weaponsController, Button.kA.value).onTrue(new InstantCommand(() -> m_shooter.setPivotSpeed(0)));

    // // // scoring 
    // // new JoystickButton(m_weaponsController, Button.kY.value).onTrue(new ScoringSequence(50, m_shooter, m_indexer));

    // // // Pivor and Rev
    // new JoystickButton(m_weaponsController, Button.kY.value).onTrue(new PivotAndRev(m_shooter, 50));
    
    // reset gyro
    new JoystickButton(m_rotator, Button.kA.value).whileTrue(new RunCommand(() -> m_drivetrain.zeroHeading(), m_drivetrain));


    // reset gyro
    new JoystickButton(m_rotator, Button.kA.value).whileTrue(new RunCommand(() -> m_drivetrain.zeroHeading(), m_drivetrain));
  
    // switch to note align
    // new JoystickButton(m_translator, Button.kY.value)
    //   .whileTrue(new NoteAlign(m_drivetrain, m_vision, m_translator));
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
