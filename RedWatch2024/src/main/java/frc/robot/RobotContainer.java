// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Vision.AprilTagAlign;
import frc.robot.commands.Vision.NoteAlign;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Vision;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.networktables.NetworkTableInstance;
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
import frc.robot.commands.LEDs.PartyMode;
import frc.robot.commands.Shooter.AutoPivot;
import frc.robot.commands.Shooter.Pivot;
import frc.robot.subsystems.ControlPanel;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.LEDs;
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
  private final Vision m_vision;
  private final Indexer m_indexer;
  private final Intake m_intake;
  final Shooter m_shooter;
  private final ControlPanel m_controlPanel;
  private final LEDs m_leds;

  private final Joystick m_translator = new Joystick(OperatorConstants.kDriveTranslatorPort);
  private final Joystick m_rotator = new Joystick(OperatorConstants.kDriveRotatorPort);
  private final XboxController m_weaponsController = new XboxController(OperatorConstants.kWeaponsControllerPort);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() { 
    m_indexer = new Indexer();
    m_intake = new Intake();
    m_shooter = new Shooter();
    m_vision = new Vision();
    m_leds = new LEDs();
    m_drivetrain = new Drivetrain();
    m_controlPanel = new ControlPanel(m_drivetrain, m_indexer, m_intake, m_shooter, m_leds);

    SmartDashboard.putData("command scheduler", CommandScheduler.getInstance());

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

    /*
     * Driver
     */
    // locks wheels
    new JoystickButton(m_translator, Button.kX.value)
        .whileTrue(new RunCommand(
            () -> m_drivetrain.setX(),
            m_drivetrain));

    // reset gyro
    new JoystickButton(m_rotator, Button.kA.value).whileTrue(new RunCommand(() -> m_drivetrain.zeroHeading(), m_drivetrain));
   
    // vision align
    new JoystickButton(m_translator, Button.kA.value).toggleOnTrue(new AprilTagAlign(m_vision, m_drivetrain, m_translator));
  
  
  /*
  * WEAPONS
  */
    //SHOOT SPEAKER - RB
    new JoystickButton(m_weaponsController, Button.kRightBumper.value).onTrue
    (new FeedAndShoot(m_shooter, m_indexer, Constants.ShooterConstants.kLeftPowerSpeaker, Constants.ShooterConstants.kRightPowerSpeaker, Constants.IndexerConstants.kFeedSpeakerSpeed));
    
    // SHOOT AMP - LB
    new JoystickButton(m_weaponsController, Button.kLeftBumper.value).onTrue
    (new FeedAndShoot(m_shooter, m_indexer, Constants.ShooterConstants.kLeftPowerAmp, Constants.ShooterConstants.kRightPowerAmp, Constants.IndexerConstants.kFeedAmpSpeed));
    
    //RUN INTAKE - A
    new JoystickButton(m_weaponsController, Button.kA.value).onTrue(new IntakeThenLoad(m_intake, m_indexer));

    //MELTDOWN - B
    new JoystickButton(m_weaponsController, Button.kB.value).onTrue(new Meltdown(m_shooter, m_intake, m_drivetrain, m_indexer));
    
    //INTAKE PIVOT - X 
    new JoystickButton(m_weaponsController, Button.kX.value).onTrue(new Pivot(m_shooter, 75)); //37.5 at .55

    //PIVOT SPEAKER - Y
    new JoystickButton(m_weaponsController, Button.kY.value).onTrue(new Pivot(
      m_shooter, 
      m_shooter.getOptimalAngle(m_vision.getSpeakerDistance())));
    SmartDashboard.putNumber("Vision Speaker distance", m_vision.getSpeakerDistance());
    SmartDashboard.putNumber("Limelight y angle", m_vision.getY());
    SmartDashboard.putNumber("Limelight table y", NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(27));


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
    new JoystickButton(m_weaponsController, Button.kRightStick.value).onTrue(new PartyMode(m_leds));
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
