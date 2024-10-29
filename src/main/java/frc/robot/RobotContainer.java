// Copyright 2021-2024 FRC 6328
// http://github.com/Mechanical-Advantage
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// version 3 as published by the Free Software Foundation or
// available in the root directory of this project.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.

package frc.robot;

import static frc.robot.util.drive.DriveControls.*;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.util.PathPlannerLogging;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import frc.robot.commandgroups.*;
import frc.robot.commands.AutoPivotNoEnd;
import frc.robot.commands.DriveCommands;
import frc.robot.commands.ShootingCommands;
import frc.robot.subsystems.LED.BlinkinLEDController;
import frc.robot.subsystems.Vision;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.drive.GyroIO;
import frc.robot.subsystems.drive.GyroIOReal;
import frc.robot.subsystems.drive.ModuleIO;
import frc.robot.subsystems.drive.ModuleIOSim;
import frc.robot.subsystems.drive.ModuleIOSparkMax;
import frc.robot.subsystems.groundIntake.GroundIntake;
import frc.robot.subsystems.groundIntake.GroundIntakeIO;
import frc.robot.subsystems.groundIntake.GroundIntakeIOSim;
import frc.robot.subsystems.groundIntake.GroundIntakeIOSparkMax;
import frc.robot.subsystems.indexer.Indexer;
import frc.robot.subsystems.indexer.IndexerIO;
import frc.robot.subsystems.indexer.IndexerIOSim;
import frc.robot.subsystems.indexer.IndexerIOSparkMax;
import frc.robot.subsystems.pivotArm.PivotArm;
import frc.robot.subsystems.pivotArm.PivotArmIO;
import frc.robot.subsystems.pivotArm.PivotArmIOSim;
import frc.robot.subsystems.pivotArm.PivotArmIOSparkMax;
import frc.robot.subsystems.shooter.Shooter;
import frc.robot.subsystems.shooter.ShooterConstants;
import frc.robot.subsystems.shooter.ShooterIO;
import frc.robot.subsystems.shooter.ShooterIOSim;
import frc.robot.subsystems.shooter.ShooterIOSparkMax;
import frc.robot.util.drive.DriveControls;
import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.networktables.LoggedDashboardBoolean;
import org.littletonrobotics.junction.networktables.LoggedDashboardChooser;
import org.littletonrobotics.junction.networktables.LoggedDashboardNumber;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // Subsystems
  private final Drive drive;
  // private final Flywheel flywheel;
  private final GroundIntake groundIntake;
  private final Shooter shooter;
  private final PivotArm pivot;
  private final Indexer indexer;

  private final Vision vision;

  private boolean brakeMode = true;

  // LEDs
  private final BlinkinLEDController ledController = BlinkinLEDController.getInstance();

  // Controller
  private final CommandJoystick m_translator = new CommandJoystick(1);
  private final CommandJoystick m_rotator = new CommandJoystick(2);
  private final CommandXboxController m_weaponsController = new CommandXboxController(0);

  //   private final CommandXboxController controller = new CommandXboxController(0);

  // Dashboard inputs
  private LoggedDashboardChooser<Command> autoChooser;
  // private final LoggedDashboardNumber flywheelSpeedInput =
  //     new LoggedDashboardNumber("Flywheel Speed", 1500.0);
  // crete mode changers
  private LoggedDashboardNumber autoWait = new LoggedDashboardNumber("AutoWait", 0);
  private LoggedDashboardNumber rightShooterVolts =
      new LoggedDashboardNumber("RightShooter", ShooterConstants.SHOOTER_FULL_VOLTAGE);
  private LoggedDashboardNumber leftShooterVolts =
      new LoggedDashboardNumber("LeftShooter", ShooterConstants.SHOOTER_FULL_VOLTAGE);
  private LoggedDashboardBoolean brakeModeDashboard =
      new LoggedDashboardBoolean("Brake Mode", true);
  private LoggedDashboardBoolean setStartPosition =
      new LoggedDashboardBoolean("Set Start Position", false);

  // Field
  private final Field2d field;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    switch (Constants.currentMode) {
      case REAL:
        // Real robot, instantiate hardware IO implementations
        drive =
            new Drive(
                new GyroIOReal(),
                new ModuleIOSparkMax(0),
                new ModuleIOSparkMax(1),
                new ModuleIOSparkMax(2),
                new ModuleIOSparkMax(3));
        // flywheel = new Flywheel(new FlywheelIOSparkMax());
        shooter = new Shooter(new ShooterIOSparkMax());
        groundIntake = new GroundIntake(new GroundIntakeIOSparkMax());
        pivot = new PivotArm(new PivotArmIOSparkMax());
        indexer = new Indexer(new IndexerIOSparkMax());
        vision = new Vision();
        // drive = new Drive(
        // new GyroIOPigeon2(true),
        // new ModuleIOTalonFX(0),
        // new ModuleIOTalonFX(1),
        // new ModuleIOTalonFX(2),
        // new ModuleIOTalonFX(3));
        // flywheel = new Flywheel(new FlywheelIOTalonFX());
        break;

      case SIM:
        // Sim robot, instantiate physics sim IO implementations
        drive =
            new Drive(
                new GyroIO() {},
                new ModuleIOSim(),
                new ModuleIOSim(),
                new ModuleIOSim(),
                new ModuleIOSim());
        // flywheel = new Flywheel(new FlywheelIOSim());
        shooter = new Shooter(new ShooterIOSim());
        groundIntake = new GroundIntake(new GroundIntakeIOSim() {});
        pivot = new PivotArm(new PivotArmIOSim());
        indexer = new Indexer(new IndexerIOSim());
        vision = new Vision();

        break;

      default:
        // Replayed robot, disable IO implementations
        drive =
            new Drive(
                new GyroIO() {},
                new ModuleIO() {},
                new ModuleIO() {},
                new ModuleIO() {},
                new ModuleIO() {});
        // flywheel = new Flywheel(new FlywheelIO() {});
        shooter = new Shooter(new ShooterIO() {});
        groundIntake = new GroundIntake(new GroundIntakeIO() {});
        pivot = new PivotArm(new PivotArmIO() {});
        indexer = new Indexer(new IndexerIO() {});
        vision = new Vision();

        break;
    }

    field = new Field2d();
    SmartDashboard.putData("Field", field);

    System.out.println("[Init] Setting up Path Planner Logging");

    // Logging callback for current robot pose
    PathPlannerLogging.setLogCurrentPoseCallback(
        (pose) -> {
          // Do whatever you want with the pose here
          field.setRobotPose(pose);
          Logger.recordOutput("PathPlanner/RobotPose", pose);
        });

    // Logging callback for target robot pose
    PathPlannerLogging.setLogTargetPoseCallback(
        (pose) -> {
          // Do whatever you want with the pose here
          field.getObject("target pose").setPose(pose);
          Logger.recordOutput("PathPlanner/TargetPose", pose);
        });

    // Logging callback for the active path, this is sent as a list of poses
    PathPlannerLogging.setLogActivePathCallback(
        (poses) -> {
          // Do whatever you want with the poses here
          field.getObject("path").setPoses(poses);
          Logger.recordOutput("PathPlanner/ActivePath", poses.toArray(new Pose2d[0]));
        });

    // Set up auto routines
    // NamedCommands.registerCommand(
    //     "Run Flywheel",
    //     Commands.startEnd(
    //             () -> flywheel.runVelocity(flywheelSpeedInput.get()), flywheel::stop, flywheel)
    //         .withTimeout(5.0));
    autoChooser = new LoggedDashboardChooser<>("Auto Choices", AutoBuilder.buildAutoChooser());

    // Set up SysId routines
    autoChooser.addOption(
        "Drive SysId (Quasistatic Forward)",
        drive.sysIdQuasistatic(SysIdRoutine.Direction.kForward));
    autoChooser.addOption(
        "Drive SysId (Quasistatic Reverse)",
        drive.sysIdQuasistatic(SysIdRoutine.Direction.kReverse));
    autoChooser.addOption(
        "Drive SysId (Dynamic Forward)", drive.sysIdDynamic(SysIdRoutine.Direction.kForward));
    autoChooser.addOption(
        "Drive SysId (Dynamic Reverse)", drive.sysIdDynamic(SysIdRoutine.Direction.kReverse));
    // Configure the button bindings
    configureButtonBindings();

    // Set up auto routines
    System.out.println("[Init] Setting up Logged Auto Chooser");
    autoChooser = new LoggedDashboardChooser<>("Auto Choices", AutoBuilder.buildAutoChooser());
  }
  // zero gyro
  public void reset() {
    drive.resetYaw();
  }
  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    // default subsystem commands
    DriveControls.configureControls();
    drive.setDefaultCommand(
        DriveCommands.joystickDrive(drive, DRIVE_FORWARD, DRIVE_STRAFE, DRIVE_ROTATE));

    shooter.setDefaultCommand(shooter.runVoltage(SHOOTER_SPEED));
    pivot.setDefaultCommand(pivot.ManualCommand(PIVOT_ROTATE));

    INTAKE_THEN_LOAD.onTrue(ShootingCommands.IntakeThenLoad(indexer, groundIntake, pivot));
    PIVOT_TO_SPEAKER.whileTrue(new AutoPivotNoEnd(vision, pivot, true));
    PIVOT_AND_REV.whileTrue(
        new ParallelCommandGroup(new AutoPivotNoEnd(vision, pivot, true), shooter.runRPM(4800.0)));

    // Drive setting commands
    // DRIVE_SLOW.onTrue(new InstantCommand(DriveCommands::toggleSlowMode));

    // DRIVE_STOP.onTrue(
    //     new InstantCommand(
    //         () -> {
    //           drive.stopWithX();
    //           drive.resetYaw();
    //         },
    //         drive));

    // DRIVE_HOLD_STOP.onTrue(
    //     new InstantCommand(
    //         () -> {
    //           drive.stopWithX();
    //         },
    //         drive));

    // // Drive Modes

    DRIVE_SPEAKER_AIM.whileTrue(
        DriveCommands.joystickSpeakerPoint(drive, DRIVE_FORWARD, DRIVE_STRAFE));

    // Drive Angle Locks
    // LOCK_BACK.whileTrue(
    //     DriveCommands.joystickAnglePoint(
    //         drive,
    //         DRIVE_FORWARD,
    //         DRIVE_STRAFE,
    //         () -> {
    //           return AllianceFlipUtil.apply(new Rotation2d());
    //         }));
    // LOCK_PICKUP.whileTrue(
    //     DriveCommands.joystickAnglePoint(
    //         drive,
    //         DRIVE_FORWARD,
    //         DRIVE_STRAFE,
    //         () -> {
    //           return AllianceFlipUtil.apply(Rotation2d.fromDegrees(-45));
    //         }));
    // LOCK_PASS.whileTrue(DriveCommands.joystickPasserPoint(drive, DRIVE_FORWARD, DRIVE_STRAFE));

    // Pivot Commands
    // PIVOT_AMP.whileTrue(pivot.PIDCommandForever(PivotArmConstants.PIVOT_AMP_ANGLE));
    // PIVOT_ZERO.whileTrue(pivot.PIDCommandForever(PivotArmConstants.PIVOT_ARM_INTAKE_ANGLE));
    // PIVOT_TO_SPEAKER.whileTrue(pivot.PIDCommandForever(PivotArmConstants.PIVOT_SUBWOOFER_ANGLE));
    // PIVOT_PODIUM.whileTrue(pivot.PIDCommandForever(PivotArmConstants.PIVOT_PODIUM_ANGLE));
    // PIVOT_ANYWHERE.whileTrue(pivot.PIDCommandForever(this::getAngle));
    // PIVOT_HOLD.whileTrue(pivot.PIDHoldCommand());

    // Intake Commands
    // INTAKE_IN.whileTrue(indexer.manualCommand(IndexerConstants.INDEXER_IN_VOLTAGE));
    // INTAKE_OUT.whileTrue(indexer.manualCommand(IndexerConstants.INDEXER_OUT_VOLTAGE));
    // INTAKE_UNTIL_INTAKED.onTrue(intakeUntilIntaked());

    // Ground Intake Commands
    // GROUND_INTAKE_IN.whileTrue(
    //     groundIntake.manualCommand(GroundIntakeConstants.GROUND_INTAKE_IN_VOLTAGE));
    // GROUND_INTAKE_OUT.whileTrue(
    //     groundIntake.manualCommand(GroundIntakeConstants.GROUND_INTAKE_OUT_VOLTAGE));

    // // Shooter Commands
    // SHOOTER_FULL_SEND.whileTrue(
    //     shooter.runVoltageBoth(rightShooterVolts::get, leftShooterVolts::get));
    // SHOOTER_FULL_SEND_INTAKE.whileTrue(shootNote());
    // Shimmy shimmy
    // SHOOTER_UNJAM.whileTrue(
    //     (indexer.manualCommand(IndexerConstants.INDEXER_OUT_VOLTAGE / 2)
    //         .alongWith(shooter.runVoltage(ShooterConstants.SHOOTER_UNJAM_VOLTAGE))));
    // SHOOTER_PREPARE_THEN_SHOOT.whileTrue(shooter.runVoltage(ShooterConstants.SHOOTER_FULL_VOLTAGE));
    // SHOOTER_PREPARE_THEN_SHOOT.onFalse(new WaitCommand(1)
    //
    // .deadlineWith(shooter.runVoltage(ShooterConstants.SHOOTER_FULL_VOLTAGE))
    //
    // .deadlineWith(indexer.manualCommand(IndexerConstants.INDEXER_IN_VOLTAGE)));

    // add extra commands in tuning mode
    // if (Constants.tuningMode) {
    //   SmartDashboard.putData("Pivot Sysid",
    //     new SequentialCommandGroup(
    //       pivot.quasistaticForward(),
    //       pivot.quasistaticBack(),
    //       pivot.dynamicForward(),
    //       pivot.dynamicBack()
    //     )
    //   );
    // }
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return autoChooser.get();
  }
}
