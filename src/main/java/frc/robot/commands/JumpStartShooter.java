// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.Shooter;

public class JumpStartShooter extends CommandBase {
    private Shooter shooter;
    private double shooterCount = 0;
    /** Creates a new JumpStartShooter. */
    public JumpStartShooter(Shooter shooter) {
        this.shooter = shooter;
        addRequirements(shooter);
        // Use addRequirements() here to declare subsystem dependencies.
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        shooterCount = 0;
        shooter.setShooterRPM(200);
        SmartDashboard.putBoolean("Jumpstart", true);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        shooterCount++;
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        SmartDashboard.putBoolean("Jumpstart", false);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        if (shooter.getShooterRPM() > 200) {
            return true;
        }

        return false;
    }
}
