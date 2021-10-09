// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Peripherals;
import frc.robot.tools.controlloops.PID;

public class NavxTurnOneSide extends CommandBase {
    private Peripherals peripherals;
    private Drive drive;
    private PID pid;
    private double kP = 0.015;
    private double kI = 0.003;
    private double kD = 0.16;
    private double target;

    /** Creates a new NavxTurn. */
    public NavxTurnOneSide(Peripherals peripherals, Drive drive, double target) {
        this.peripherals = peripherals;
        this.drive = drive;
        this.target = target;
        addRequirements(peripherals, drive);
        // Use addRequirements() here to declare subsystem dependencies.
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        // peripherals.zeroNavx();
        SmartDashboard.putBoolean("finished navxturn", false);
        pid = new PID(kP, kI, kD);
        pid.setSetPoint(target);
        pid.setMinOutput(-0.2);
        pid.setMaxOutput(0.2);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        System.out.println("Inside navx turn");
        pid.updatePID(peripherals.getNavxAngle());
        SmartDashboard.putNumber("navx pid output", pid.getResult());
        drive.setLeftPercent(0);
        drive.setRightPercent(-pid.getResult());
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        SmartDashboard.putBoolean("finished navxturn", true);
        drive.setLeftPercent(0);
        drive.setRightPercent(0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return (Math.abs(target - peripherals.getNavxAngle()) < 1);
    }
}