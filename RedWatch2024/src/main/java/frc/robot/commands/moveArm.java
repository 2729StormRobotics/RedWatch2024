// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.motor;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class moveArm extends CommandBase {
    /** Initialize */
    private final TrapSubsystem m_Trap;
    private final double potDistance;
    private final double potDifference;


    public moveArm(double distance, TrapSubsystem trap) {
        // Use addRequirements() here to declare subsystem dependencies.
        m_Trap = trap;
        potDistance = distance;
        addRequirements(m_Trap);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {}

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        potDifference = m_Trap.getPotDifference(potDistance);

        // 
        // Speed values are placeholders
        if (m_Trap.getPotValue() < potDistance) {
            m_Trap.setSpeed(1);
        } 
        else if (m_Trap.getPotValue() > potDistance) {
            m_Trap.setSpeed(-1);
        }

    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_Trap.stopMotor();
    }

    // Returns true when the command should end.
    // Get units for the arm
    @Override
    public boolean isFinished() {
        if (potDifference <= potDistance){
            return true;
        }
        return false;
    }
}
