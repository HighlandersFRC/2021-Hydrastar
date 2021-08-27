// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;
import frc.robot.tools.controlloops.PID;

public class DriveBackwards1 extends CommandBase {
  private Drive drive;
  private PID pid;
  private double kP = 0.2;
  private double kI = 0.0;
  private double kD = 0.01;
  private double target;
  
  /** Creates a new DriveBackwards1. */
  public DriveBackwards1(Drive drive, double target) {
    this.drive = drive;
    this.target = target * 0.0254;
    addRequirements(drive);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    drive.zeroDriveEncoderTics();
    SmartDashboard.putBoolean("finished drivebackwards 1", false);
    pid = new PID(kP, kI, kD);
    pid.setSetPoint(target);
    pid.setMinOutput(-0.5);
    pid.setMaxOutput(0.5);
    drive.setDriveBrake();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    pid.updatePID(drive.getDriveMeters());
    drive.setLeftPercent(-pid.getResult());
    drive.setRightPercent(-pid.getResult());
    SmartDashboard.putNumber("drivebackwards 1 output", pid.getResult());
    SmartDashboard.putNumber("Distance", drive.getDriveMeters() + target);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drive.setLeftPercent(0);
    drive.setRightPercent(0);
    SmartDashboard.putBoolean("finished drivebackwards 1", true);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(Math.abs(drive.getDriveMeters() + target) < 0.2){
      return true;
    }
      
    
    return false;
  }
}
