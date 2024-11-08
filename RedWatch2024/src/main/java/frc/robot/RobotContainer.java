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
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commandgroups.AmpScoringSequence;
import frc.robot.commandgroups.FeedAndShoot;
import frc.robot.commandgroups.IntakeThenLoad;
import frc.robot.commandgroups.PivotAndRev;
import frc.robot.commandgroups.ScoringSequence;
import frc.robot.commandgroups.VisionAndPivot;
import frc.robot.commandgroups.AutoCommandGroups.AutoAprilTagAlign;
import frc.robot.commandgroups.AutoCommandGroups.AutoFeedAndShoot;
import frc.robot.commandgroups.AutoCommandGroups.AutoNoteAlign;
import frc.robot.commandgroups.AutoCommandGroups.AutoPivotAndRevNoEnd;
import frc.robot.commandgroups.AutoCommandGroups.AutoRevAndShoot;
import frc.robot.commandgroups.AutoCommandGroups.AutoScoringSequence;
import frc.robot.commands.Meltdown;
import frc.robot.commands.Indexer.Feed;
import frc.robot.commands.Intake.StopIntake;
import frc.robot.commands.Pivot.AutoPivot;
import frc.robot.commands.Shooter.ManualRPMRev;
import frc.robot.commands.Shooter.SetRPM;
import frc.robot.commands.Vision.AprilTagAlign;
import frc.robot.commands.Vision.NoteAlign;
import frc.robot.subsystems.Blinkin;
import frc.robot.subsystems.ControlPanel;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Hanger;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;
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
  private final Blinkin m_lights;
  private final Hanger m_Hanger;

  // Will allow to choose which auto command to run from the shuffleboard
  private final SendableChooser<Command> autoChooser;
  
  private final Joystick m_translator = new Joystick(OperatorConstants.kDriveTranslatorPort);
  private final Joystick m_rotator = new Joystick(OperatorConstants.kDriveRotatorPort);
  private final XboxController m_weaponsController = new XboxController(OperatorConstants.kWeaponsControllerPort);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() { 
    m_indexer = Indexer.getInstance();
    m_intake = Intake.getInstance();
    m_shooter = Shooter.getInstance();
    m_pivot = Pivot.getInstance();
    m_vision = Vision.getInstance();
    m_lights = Blinkin.getInstance();
    m_drivetrain = Drivetrain.getInstance();
    m_controlPanel = ControlPanel.getInstance();

    SmartDashboard.putData("command scheduler", CommandScheduler.getInstance());

    // Configure the trigger bindings
    m_Hanger = Hanger.getInstance();
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
      new RunCommand(() -> m_pivot.setPivotSpeed(Math.copySign(Math.pow(m_weaponsController.getLeftY(), 5), -m_weaponsController.getLeftY()))
        , m_pivot));
    
    //keep steady rpm for the shooter
    m_shooter.setDefaultCommand(new RunCommand(() -> m_shooter.setShooterSpeed(Shooter.passivePower, Shooter.passivePower), m_shooter));

    NamedCommands.registerCommand("FirstShot", new ScoringSequence(47.5, 3000, 3000, Constants.IndexerConstants.kFeedSpeakerSpeed).withTimeout(2));
    NamedCommands.registerCommand("Shoot", new AutoScoringSequence(Constants.ShooterConstants.kBottomPowerSpeaker, Constants.ShooterConstants.kTopPowerSpeaker, Constants.IndexerConstants.kFeedSpeakerSpeed));
    NamedCommands.registerCommand("IntakeItem", new IntakeThenLoad().withTimeout(7));
    NamedCommands.registerCommand("IntakeAngle", new AutoPivot(75, m_pivot, false));
    NamedCommands.registerCommand("StopIntake", new StopIntake());
    NamedCommands.registerCommand("VisionAlign", new AutoAprilTagAlign());
    NamedCommands.registerCommand("SetShooterPower", new InstantCommand(() -> m_shooter.setShooterSpeed(0.85, 0.85)));
    NamedCommands.registerCommand("OffsetGyro60", new InstantCommand(() -> Drivetrain.gyroOffset += -60));
    // NamedCommands.registerCommand("PivotBumperUp", new FastPivot(49, m_pivot).withTimeout(1.1));
    NamedCommands.registerCommand("PivotBumperUp", new AutoPivot(m_vision, m_pivot, true).withTimeout(1.1));
    NamedCommands.registerCommand("PivotBumperUpFirst", new AutoPivot(m_vision, m_pivot, true).withTimeout(0.75));
    NamedCommands.registerCommand("SetShooterPower50", new InstantCommand(() -> m_shooter.setShooterSpeed(0.6, 0.6)));
    NamedCommands.registerCommand("Feed", new InstantCommand(() -> {m_indexer.runIndexer(1);}));
    NamedCommands.registerCommand("NoteAlign", new AutoNoteAlign().withTimeout(0.85));
    NamedCommands.registerCommand("FarShot", new AutoScoringSequence(6000, 6000, Constants.IndexerConstants.kFeedSpeakerSpeed));
    NamedCommands.registerCommand("RevShooter", new SetRPM(6000, 6000));
    NamedCommands.registerCommand("PivotAndRev", new PivotAndRev(6000, 6000));
    NamedCommands.registerCommand("PivotAndRevNoEnd", new AutoPivotAndRevNoEnd(6000, 6000));
    NamedCommands.registerCommand("AutoRevAndShoot", new AutoRevAndShoot());


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
    new JoystickButton(m_rotator, Button.kA.value).whileTrue(new NoteAlign(m_translator));

    // reset gyro
    new JoystickButton(m_rotator, 12).whileTrue(new RunCommand(() -> m_drivetrain.zeroHeading(), m_drivetrain));
   
    // vision align
    // new JoystickButton(m_translator, Button.kA.value).whileTrue(new AprilTagAlign(m_translator));
    new JoystickButton(m_translator, Button.kA.value).whileTrue(new AprilTagAlign(m_translator));
  
  
  /*
  * WEAPONS~
  */
  // PIVOT SOFT STOP
    new Trigger(()-> (m_pivot.getPivotAngle() >=90)).whileTrue(new AutoPivot(90-4, m_pivot, false));

    //MANUAL REV - LT
    new Trigger(() -> (m_weaponsController.getLeftTriggerAxis() > 0.5)).whileTrue(new AutoPivotAndRevNoEnd(100000, 100000));

    //MANUAL SHOOT - A
     new JoystickButton(m_weaponsController, Button.kA.value).onTrue
    // (new AutoPivot(30, m_pivot, false));
     (new FeedAndShoot(6000, 6000, Constants.IndexerConstants.kFeedSpeakerSpeed));

    // //SHOOT SPEAKER - RB
    new JoystickButton(m_weaponsController, Button.kRightBumper.value).onTrue
    (new ScoringSequence(6000, 6000, Constants.IndexerConstants.kFeedSpeakerSpeed).andThen(new InstantCommand(() -> {m_lights.neutral();})));
    new JoystickButton(m_weaponsController, Button.kRightBumper.value).onFalse(new InstantCommand(() -> m_indexer.stop())
    .andThen(new AutoPivot(7, m_pivot, false)).andThen(new InstantCommand(() -> {m_lights.neutral();})));

    // SHOOT AMP - LB
    new JoystickButton(m_weaponsController, Button.kLeftBumper.value).onTrue
    (new PivotAndRev(47.5, 3000, 3000).andThen(new FeedAndShoot(3000, 3000, 1)));

    //INTAKE PIVOT - X ~ 
    new JoystickButton(m_weaponsController, Button.kX.value).whileTrue(new ParallelDeadlineGroup(new WaitCommand(0.5).andThen(new IntakeThenLoad()), new AutoPivot(75,m_pivot, false)).andThen(new AutoPivot( 2, m_pivot, false))
    .andThen(new InstantCommand(() -> {m_lights.neutral();})));
    new JoystickButton(m_weaponsController, Button.kX.value).onFalse(new ParallelCommandGroup(new AutoPivot(7, m_pivot, false), new StopIntake(), new InstantCommand(() -> {m_indexer.stop();}, m_indexer).andThen(new InstantCommand(() -> {m_lights.neutral();}))));

    //MELTDOWN - B
    new JoystickButton(m_weaponsController, Button.kB.value).onTrue(new Meltdown());

    //PIVOT AMP - start
    new JoystickButton(m_weaponsController, Button.kStart.value).onFalse(new AutoPivot(103.5, m_pivot, false));//106

    //PASS - Y
    new JoystickButton(m_weaponsController, Button.kY.value).onTrue(new PivotAndRev(30, 3900, 3900).andThen(new FeedAndShoot(3900, 3900, 1)));
    
    new JoystickButton(m_weaponsController, Button.kBack.value).onTrue
    (new AmpScoringSequence(Constants.ShooterConstants.kBottomPowerAmp, Constants.ShooterConstants.kTopPowerAmp, Constants.IndexerConstants.kFeedAmpSpeed));

 

    new JoystickButton(m_weaponsController, Button.kY.value).onFalse(new InstantCommand(() -> m_indexer.stop())
    .andThen(new AutoPivot(7, m_pivot, false)).andThen(new InstantCommand(() -> {m_lights.neutral();})));

    // Shooter overrides 
    new POVButton(m_weaponsController, 0).onTrue(new RunCommand(() -> {m_shooter.setShooterSpeed(Constants.ShooterConstants.kTopPowerAmp, Constants.ShooterConstants.kBottomPowerAmp);}, m_shooter));
    new POVButton(m_weaponsController, 180).onTrue(new InstantCommand(() -> {m_shooter.stopShooterMotors();}, m_shooter));

    // Intake overrides
    new POVButton(m_weaponsController, 90).onTrue(new RunCommand(() -> {m_intake.ejectItem();}, m_intake));
    new POVButton(m_weaponsController, 270).onTrue(new InstantCommand(() -> {m_intake.stopIntake();}, m_intake));

    // Indexer overrides
    new POVButton(m_weaponsController, 45).onTrue(new RunCommand(() -> {m_indexer.runIndexer(Constants.IndexerConstants.kIndexerSpeed);}, m_indexer));
    new POVButton(m_weaponsController, 225).onTrue(new InstantCommand(() -> {m_indexer.stop();}, m_indexer));

    // LEDs
    new JoystickButton(m_weaponsController, Button.kRightStick.value).onTrue(new InstantCommand(() -> {m_lights.set(-0.99);}));
  }
//~
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    // return new PivotAndRev(47.5, 3000, 3000).andThen(new FeedAndShoot(3000, 3000, 1));
    return autoChooser.getSelected();
  }
}
// {if; buttonx=1 then; :Itanke work
  //work=auto pivot:Encoder=100