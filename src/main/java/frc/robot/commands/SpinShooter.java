// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.Shooter;
import frc.robot.tools.RPMAdder;

public class SpinShooter extends CommandBase {
    private Shooter shooter;
    private double rpm;
    private double counter = 0;
    private double addition = 0;

    private RPMAdder rpmAdder;
    /** Creates a new SpinShooter. */
    public SpinShooter(Shooter shooter, double rpm, RPMAdder adder) {
        this.shooter = shooter;
        this.rpm = rpm;
        this.rpmAdder = adder;
        this.addition = 0;
        addRequirements(shooter);

        // Use addRequirements() here to declare subsystem dependencies.
    }

    public SpinShooter(Shooter shooter, double rpm) {
        this.shooter = shooter;
        this.rpm = rpm;
        this.rpmAdder = new RPMAdder();
        this.addition = 0;
        addRequirements(shooter);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        addition = 0;
        addition = rpmAdder.getRPMAdder();
        // rpm = rpm + addition;
        shooter.setShooterRPM(rpm + addition);
        counter = 0;
        SmartDashboard.putNumber("RPM ADDER", addition);
        SmartDashboard.putNumber("WantRPM", rpm + addition);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        // shooter.setShooterRPM(rpm);
        counter++;
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {}

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return Math.abs(shooter.getShooterRPM() - rpm) < 25 || (counter > 100);
        // return true;
    }
}
