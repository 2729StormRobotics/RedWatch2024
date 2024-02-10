// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commandgroups.FeedAndShoot;
import frc.robot.commandgroups.IntakeThenLoad;
import frc.robot.commandgroups.PivotAndRev;
import frc.robot.commandgroups.ScoringSequence;
import frc.robot.commands.Intake.IntakeItem;
import frc.robot.commands.Intake.StopIntake;
import frc.robot.commands.Shooter.JoystickPivot;
import frc.robot.commands.Vision.AprilTagAlign;
import frc.robot.subsystems.ControlPanel;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.LEDs;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Vision;
/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final Indexer m_indexer;
  private final Intake m_intake;
  private final Shooter m_shooter;
  private final Vision m_vision;
  private final LEDs m_leds;
  private final Drivetrain m_drivetrain;
  private final ControlPanel m_controlpanel;
  
  // Will allow to choose which auto command to run from the shuffleboard
  private final SendableChooser<Command> autoChooser;
  
  private final Joystick m_translator = new Joystick(OperatorConstants.kDriveTranslatorPort);
  private final Joystick m_rotator = new Joystick(OperatorConstants.kDriveRotatorPort);
  private final XboxController m_weaponsController = new XboxController(OperatorConstants.kWeaponsControllerPort);  
  private final XboxController m_driverController = new XboxController(OperatorConstants.kDriveTranslatorPort);  

  //add the joystick here


  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    m_indexer = new Indexer();
    m_intake = new Intake();
    m_shooter = new Shooter();
    m_vision = new Vision();
    m_leds = new LEDs();
    m_drivetrain = new Drivetrain();
    m_controlpanel = new ControlPanel(m_drivetrain, m_indexer, m_leds, m_intake, m_shooter, m_vision);
    SmartDashboard.putData(CommandScheduler.getInstance());

    configureBindings();

     //Joystick drive
    m_drivetrain.setDefaultCommand(
      new RunCommand(
        () -> m_drivetrain.drive(
          -MathUtil.applyDeadband(m_driverController.getLeftY()/4, OperatorConstants.kDriveDeadband),
          -MathUtil.applyDeadband(m_driverController.getLeftX()/4, OperatorConstants.kDriveDeadband),
          -MathUtil.applyDeadband(m_driverController.getRightX()/4, OperatorConstants.kDriveDeadband),
          // -MathUtil.applyDeadband(m_translator.getY()*OperatorConstants.translationMultiplier, OperatorConstants.kDriveDeadband),
          // -MathUtil.applyDeadband(m_translator.getX()*OperatorConstants.translationMultiplier, OperatorConstants.kDriveDeadband),
          // -MathUtil.applyDeadband(m_rotator.getX()*OperatorConstants.rotationMultiplier, OperatorConstants.kDriveDeadband),
          true, true),
        m_drivetrain));

    //manual pivot control
    m_shooter.setDefaultCommand(
      new JoystickPivot((m_weaponsController.getLeftY())*0.6, m_shooter)
    );

    /*
     * Auto
     */
    // Named Commands
    // Shooting
    NamedCommands.registerCommand("Shoot", new ScoringSequence(
      m_shooter.getOptimalAngle(0, m_vision.getStageDistance()), m_shooter, m_indexer)
    );
    NamedCommands.registerCommand("IntakeItem", new IntakeThenLoad(m_intake, m_indexer));
    NamedCommands.registerCommand("StopIntake", new StopIntake(m_intake));
    NamedCommands.registerCommand("VisionAlign", new AprilTagAlign(m_vision, m_drivetrain, m_rotator));

    // Puts auto chooser onto shuffleboard
    autoChooser = AutoBuilder.buildAutoChooser();
    SmartDashboard.putData("Auto Chooser", autoChooser);
    
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

    // feed
    new JoystickButton(m_weaponsController, Button.kA.value).onTrue(new FeedAndShoot(m_shooter, m_indexer));

    // intake
    new JoystickButton(m_weaponsController, Button.kX.value).onTrue(new IntakeThenLoad(m_intake, m_indexer));
    
    // scoring 
    new JoystickButton(m_weaponsController, Button.kY.value).onTrue(new ScoringSequence(50, m_shooter, m_indexer));

    // Pivor and Rev
    new JoystickButton(m_weaponsController, Button.kY.value).onTrue(new PivotAndRev(m_shooter, 50));
    
    // reset gyro
    new JoystickButton(m_driverController, Button.kA.value).whileTrue(new RunCommand(() -> m_drivetrain.zeroHeading(), m_drivetrain));



  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return autoChooser.getSelected();
  }
}
