// // Copyright (c) FIRST and other WPILib contributors.
// // Open Source Software; you can modify and/or share it under the terms of
// // the WPILib BSD license file in the root directory of this project.

// package frc.robot.commands;

// import edu.wpi.first.math.controller.PIDController;
// import edu.wpi.first.wpilibj2.command.Command;
// import frc.robot.Constants;
// import frc.robot.subsystems.Shooter;

// /*
//  * Spins the flywheels to the set RPM
//  * Works based off of a feedforward + PID
//  */

// public class SetRPM extends Command {
//   private final Shooter m_shooter;
//   private final PIDController m_controller;
//   public double m_targetRPM; // target rpm
//   public double m_ff; // feedforward

//   /** Creates a new SetRPM. */
//   public SetRPM(Shooter shooter, double rpm) {
//     m_shooter = shooter;
//     m_controller = new PIDController(Constants.ShooterConstants.kPShoot, 0, 0); // only uses P
//     m_controller.setTolerance(Constants.ShooterConstants.kRPMTolerance); // sets tolerance for PID controller to end
//     m_targetRPM = rpm;
//     m_ff = m_targetRPM/Constants.ShooterConstants.kMaxRPM; // turn RPM into a value between 0-1

//     // Use addRequirements() here to declare subsystem dependencies.
//     addRequirements(m_shooter);
//   }

//   // Called when the command is initially scheduled.
//   @Override
//   public void initialize() {}

//   // Called every time the scheduler runs while the command is scheduled.
//   @Override
//   public void execute() {
//     // feedforward should get the rpm decently close
//     // PID will make it more accurate (hopefully)
//     m_shooter.setShooterSpeed(m_ff + m_controller.calculate(m_shooter.getAverageRPM(), m_targetRPM));
//   }

//   // Called once the command ends or is interrupted.
//   @Override
//   public void end(boolean interrupted) {}

//   // Returns true when the command should end.
//   @Override
//   public boolean isFinished() {
//     return m_controller.atSetpoint();
//   }
// }
