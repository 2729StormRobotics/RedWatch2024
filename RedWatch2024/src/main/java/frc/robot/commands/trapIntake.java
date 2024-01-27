// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.TrapSubsystem;
import frc.robot.Constants;

public class trapIntake extends Command {
    /** Initialize */
    private final TrapSubsystem m_Trap;

    public trapIntake(TrapSubsystem trap) {
        // Use addRequirements() here to declare subsystem dependencies.
        m_Trap = trap;
        addRequirements(m_Trap);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        m_Trap.handIntake(Constants.TrapConstants.kHandMotorSpeed);

    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_Trap.stopHandMotor();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        // Checks to see if the Note is in the intake using the beam break
        if (m_Trap.isNotePresent())
            return true;

        return false;

    }

}
