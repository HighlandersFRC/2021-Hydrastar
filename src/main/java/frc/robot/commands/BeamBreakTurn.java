// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Peripherals;
import frc.robot.tools.controlloops.PID;

public class BeamBreakTurn extends CommandBase {
    private Peripherals peripherals;
    private Drive drive;
    private PID pid;
    private double kP = 0.035;
    private double kI = 0;
    private double kD = 0.25;
    private double target;
    private double averageValue;
    private int averageCount = 0;

    private int finishTrue = 0;

    private double ultraSonicDifference = 0;
    private double normalAngle = 0;

    private double backUSDist = 0;
    private double frontUSDist = 0;

    private double USToUS = 18.5;

    /** Creates a new BeamBreakTurn. */
    public BeamBreakTurn(Peripherals peripherals, Drive drive, double target) {
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
        finishTrue = 0;
        pid = new PID(kP, kI, kD);
        pid.setSetPoint(90);
        pid.setMinOutput(-0.2);
        pid.setMaxOutput(0.2);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        backUSDist = peripherals.getBackUltraSonicDist();
        frontUSDist = peripherals.getUltraSonicDist();

        ultraSonicDifference = (backUSDist - frontUSDist);
        SmartDashboard.putNumber("US Diff", ultraSonicDifference);
        normalAngle = Math.atan2(USToUS, ultraSonicDifference);
        normalAngle = normalAngle * 180;
        normalAngle = normalAngle / Math.PI;

        
        
        if(Math.abs(averageValue - normalAngle) < 7) {
            pid.updatePID(normalAngle);
            if(Math.abs(normalAngle - 90) < 3) {
                finishTrue++;
            }
            else { 
                finishTrue = 0;
            }
            drive.setLeftPercent(-pid.getResult());
            drive.setRightPercent(pid.getResult());
            if(averageCount < 10) {
                averageValue = (averageValue + normalAngle)/averageCount;
            }
            else {
                averageCount = 0;
            }
        }
        else {
            drive.setLeftPercent(0.0);
            drive.setRightPercent(0.0);
        }

        SmartDashboard.putNumber("US pid output", pid.getResult());
        SmartDashboard.putNumber("Angle", normalAngle);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        drive.setLeftPercent(0);
        drive.setRightPercent(0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return (finishTrue > 10);
    }
}