// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.TrapSubsystem;
import frc.robot.Constants;

public class moveArm extends Command {
    /** Initialize */
    private final TrapSubsystem m_Trap;
    private final double potDistance;
    private double potDifference;


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

        // Moves arm to height
        if (m_Trap.getPotValue() < (potDistance - Constants.TrapConstants.kTolerance)) {
            m_Trap.setArmSpeed(Constants.TrapConstants.kArmMotorSpeed);
        } 
        else if (m_Trap.getPotValue() > (potDistance + Constants.TrapConstants.kTolerance)) {
            m_Trap.setArmSpeed(-1 * Constants.TrapConstants.kArmMotorSpeed);
        }
        

    }
    

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_Trap.stopArmMotor();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        // Ends until the arm reaches the height +- the tolerance (0.5")
        if (potDifference <= Constants.TrapConstants.kTolerance){
            return true;
        }
        return false;
    }
}
