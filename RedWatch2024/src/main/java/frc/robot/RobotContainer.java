// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.HangerControl;
import frc.robot.subsystems.Hanger;
// import frc.robot.subsystems.LEDs.LEDSegment;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commandgroups.AmpScoringSequence;
import frc.robot.commandgroups.FeedAndShoot;
import frc.robot.commandgroups.IntakeThenLoad;
import frc.robot.commandgroups.PodiumScoringSequence;
import frc.robot.commandgroups.ScoringSequence;
import frc.robot.commandgroups.AutoCommandGroups.AutoFeedAndShoot;
import frc.robot.commandgroups.AutoCommandGroups.AutoScoringSequence;
import frc.robot.commandgroups.AutoCommandGroups.FirstShot;
import frc.robot.commands.Meltdown;
import frc.robot.commands.Intake.StopIntake;
// import frc.robot.commands.LEDs.PartyMode;
import frc.robot.commands.Pivot.AutoPivot;
import frc.robot.commands.Pivot.PivotBumperUp;
import frc.robot.commands.Pivot.PivotToAngle;
import frc.robot.commands.Shooter.RevShooter;
import frc.robot.commands.Shooter.SetPower;
import frc.robot.commands.Vision.AprilTagAlign;
import frc.robot.commands.Vision.NoteAlign;
import frc.robot.subsystems.ControlPanel;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;
// import frc.robot.subsystems.LEDs;
import frc.robot.subsystems.Pivot;
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
  private final Drivetrain m_drivetrain;
  private final Vision m_vision;
  private final Indexer m_indexer;
  private final Intake m_intake;
  private final Shooter m_shooter;
  private final Pivot m_pivot;
  private final ControlPanel m_controlPanel;
  // private final LEDs m_leds;
  private final Hanger m_Hanger;

  // Will allow to choose which auto command to run from the shuffleboard
  private final SendableChooser<Command> autoChooser;
  
  private final Joystick m_translator = new Joystick(OperatorConstants.kDriveTranslatorPort);
  private final Joystick m_rotator = new Joystick(OperatorConstants.kDriveRotatorPort);
  private final XboxController m_weaponsController = new XboxController(OperatorConstants.kWeaponsControllerPort);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() { 
    m_indexer = new Indexer();
    m_intake = new Intake();
    m_shooter = new Shooter();
    m_pivot = new Pivot();
    m_vision = new Vision();
    // m_leds = new LEDs();
    m_drivetrain = new Drivetrain();
    m_controlPanel = new ControlPanel(m_drivetrain, m_indexer, m_intake, m_shooter, m_pivot, m_vision);

    SmartDashboard.putData("command scheduler", CommandScheduler.getInstance());

    // Configure the trigger bindings
    m_Hanger = new Hanger();
    // m_Hanger.setDefaultCommand(new HangerControl(m_weaponsController.getLeftY()*0.5, m_weaponsController.getLeftY()*0.5, m_Hanger));
    m_Hanger.setDefaultCommand(new RunCommand(() -> m_Hanger.setSpeed(MathUtil.applyDeadband(m_weaponsController.getRightY(), 0.1)), m_Hanger));
    
    SmartDashboard.putData(CommandScheduler.getInstance());
    configureBindings();

    // Configure default commands
     //Joystick drive
    m_drivetrain.setDefaultCommand(
      new RunCommand(
        () -> m_drivetrain.drive(
          // -MathUtil.applyDeadband(m_driverController.getLeftY()/4, OperatorConstants.kDriveDeadband),
          // -MathUtil.applyDeadband(m_driverController.getLeftX()/4, OperatorConstants.kDriveDeadband),
          // -MathUtil.applyDeadband(m_driverController.getRightX()/4, OperatorConstants.kDriveDeadband),
          -MathUtil.applyDeadband(m_translator.getY()*OperatorConstants.translationMultiplier, OperatorConstants.kDriveDeadband),
          -MathUtil.applyDeadband(m_translator.getX()*OperatorConstants.translationMultiplier, OperatorConstants.kDriveDeadband),
          -MathUtil.applyDeadband(m_rotator.getX()*OperatorConstants.rotationMultiplier, OperatorConstants.kDriveDeadband),
          true, true),
        m_drivetrain));

    //manual pivot control
     m_pivot.setDefaultCommand(
      new RunCommand(() -> m_pivot.setPivotSpeed(-m_weaponsController.getLeftY() * 0.05 + m_pivot.getPivotFeedForward()), m_pivot));
    
    //keep steady rpm for the shooter
    m_shooter.setDefaultCommand(new RunCommand(() -> m_shooter.setShooterSpeed(Shooter.passivePower, Shooter.passivePower), m_shooter));

    NamedCommands.registerCommand("FirstShot", new AutoScoringSequence(m_vision, m_shooter, m_pivot, m_indexer, 0.6, 0.6, Constants.IndexerConstants.kFeedSpeakerSpeed));
    NamedCommands.registerCommand("Shoot", new AutoScoringSequence(m_vision, m_shooter, m_pivot, m_indexer, Constants.ShooterConstants.kLeftPowerSpeaker, Constants.ShooterConstants.kRightPowerSpeaker, Constants.IndexerConstants.kFeedSpeakerSpeed));
    NamedCommands.registerCommand("IntakeItem", new IntakeThenLoad(m_intake, m_indexer).withTimeout(3.5));
    NamedCommands.registerCommand("IntakeAngle", new PivotToAngle(m_pivot, 75));
    NamedCommands.registerCommand("StopIntake", new StopIntake(m_intake));
    NamedCommands.registerCommand("VisionAlign", new AprilTagAlign(m_vision, m_drivetrain, m_rotator).withTimeout(1));
    NamedCommands.registerCommand("SetShooterPower", new InstantCommand(() -> m_shooter.setShooterSpeed(0.85, 0.85)));
    NamedCommands.registerCommand("OffsetGyro60", new InstantCommand(() -> Drivetrain.gyroOffset += -60));
    NamedCommands.registerCommand("PivotBumperUp", new PivotBumperUp(m_pivot).withTimeout(0.5));
    NamedCommands.registerCommand("PivotBumperUpFirst", new PivotBumperUp(m_pivot).withTimeout(0.8));
    NamedCommands.registerCommand("SetShooterPower50", new InstantCommand(() -> m_shooter.setShooterSpeed(0.6, 0.6)));
    NamedCommands.registerCommand("Feed", new AutoFeedAndShoot(m_shooter, m_indexer, 0.6, 0.6, Constants.IndexerConstants.kFeedSpeakerSpeed));
  

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

    /*
     * Driver
     */
    // locks wheels
    new JoystickButton(m_translator, Button.kX.value)
        .whileTrue(new RunCommand(
            () -> m_drivetrain.setX(),
            m_drivetrain));
    new JoystickButton(m_rotator, Button.kA.value).whileTrue(new NoteAlign(m_drivetrain, m_vision, m_translator));

    // reset gyro
    new JoystickButton(m_rotator, 12).whileTrue(new RunCommand(() -> m_drivetrain.zeroHeading(), m_drivetrain));
   
    // vision align
    new JoystickButton(m_translator, Button.kA.value).whileTrue(new AprilTagAlign(m_vision, m_drivetrain, m_translator));
  
  
  /*
  * WEAPONS
  */
    //MANUAL SHOOT - A
    new JoystickButton(m_weaponsController, Button.kA.value).onTrue((new RevShooter(m_shooter, 0.85, 0.85).withTimeout(2)).andThen(new FeedAndShoot(m_shooter, m_indexer, Constants.ShooterConstants.kLeftPowerSpeaker, Constants.ShooterConstants.kRightPowerSpeaker, 1)));
    
    // //SHOOT SPEAKER - RB
    new JoystickButton(m_weaponsController, Button.kRightBumper.value).onTrue
    (new ScoringSequence(m_vision, m_shooter, m_pivot, m_indexer, Constants.ShooterConstants.kLeftPowerSpeaker, Constants.ShooterConstants.kRightPowerSpeaker, Constants.IndexerConstants.kFeedSpeakerSpeed));
    new JoystickButton(m_weaponsController, Button.kRightBumper.value).onFalse(new InstantCommand(() -> m_indexer.stop()).andThen(new PivotToAngle(m_pivot, 2)));

    // SHOOT AMP - LB
    new JoystickButton(m_weaponsController, Button.kLeftBumper.value).onTrue
    (new AmpScoringSequence(m_shooter, m_pivot, m_indexer, Constants.ShooterConstants.kLeftPowerAmp, Constants.ShooterConstants.kRightPowerAmp, Constants.IndexerConstants.kFeedAmpSpeed));


    //INTAKE PIVOT - X 
    new JoystickButton(m_weaponsController, Button.kX.value).whileTrue(new ParallelDeadlineGroup(new WaitCommand(0.25).andThen(new IntakeThenLoad(m_intake, m_indexer)), new PivotToAngle(m_pivot, 75)).andThen(new PivotToAngle(m_pivot, 2))); //37.5 at .55
    new JoystickButton(m_weaponsController, Button.kX.value).onFalse(new ParallelCommandGroup(new PivotToAngle(m_pivot, 2), new StopIntake(m_intake), new InstantCommand(() -> {m_indexer.stop();}, m_indexer)));

    //MELTDOWN - B
    new JoystickButton(m_weaponsController, Button.kB.value).onTrue(new Meltdown(m_shooter, m_pivot, m_intake, m_drivetrain, m_indexer));

    //PIVOT AMP - start
    new JoystickButton(m_weaponsController, Button.kStart.value).onFalse(new PivotToAngle(m_pivot, 103));

    //BUMPER UP SHOT - Y
     new JoystickButton(m_weaponsController, Button.kY.value).onTrue
  (new PodiumScoringSequence(m_vision, m_shooter, m_pivot, m_indexer, Constants.ShooterConstants.kLeftPowerSpeaker, Constants.ShooterConstants.kRightPowerSpeaker, Constants.IndexerConstants.kFeedSpeakerSpeed));
    new JoystickButton(m_weaponsController, Button.kY.value).onFalse(new InstantCommand(() -> m_indexer.stop()).andThen(new PivotToAngle(m_pivot, 2)));


    // Shooter overrides 
    new POVButton(m_weaponsController, 0).onTrue(new RunCommand(() -> {m_shooter.setShooterSpeed(Constants.ShooterConstants.kLeftPowerAmp, Constants.ShooterConstants.kLeftPowerAmp);}, m_shooter));
    new POVButton(m_weaponsController, 180).onTrue(new InstantCommand(() -> {m_shooter.stopShooterMotors();}, m_shooter));

    // Intake overrides
    new POVButton(m_weaponsController, 90).onTrue(new RunCommand(() -> {m_intake.ejectItem();}, m_intake));
    new POVButton(m_weaponsController, 270).onTrue(new InstantCommand(() -> {m_intake.stopIntake();}, m_intake));

    // Indexer overrides
    new POVButton(m_weaponsController, 45).onTrue(new RunCommand(() -> {m_indexer.runIndexer(Constants.IndexerConstants.kIndexerSpeed);}, m_indexer));
    new POVButton(m_weaponsController, 225).onTrue(new InstantCommand(() -> {m_indexer.stop();}, m_indexer));

    // LEDs
    // new JoystickButton(m_weaponsController, Button.kRightStick.value).onTrue(new PartyMode(m_leds));
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
