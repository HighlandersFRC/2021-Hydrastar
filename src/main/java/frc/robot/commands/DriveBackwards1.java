// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Peripherals;
import frc.robot.tools.controlloops.PID;

public class DriveBackwards1 extends CommandBase {
  private Drive drive;
  private Peripherals peripherals;
  private PID pid;
  private PID usPID;
  private double usP = 0.1;
  private double usI = 0.0;
  private double usD = 0.0;
  private double kP = 0.4;
  private double kI = 0.0;
  private double kD = 0.01;
  private double target;
  private double minOutput;
  private double maxOutput;
  private boolean isForwards;
  private double turnOffset = 0.0;
  private double desiredAngle = 0;
  private double ultraSonicDistance;
  private boolean useUS = false;
  
  /** Creates a new DriveBackwards1. */
  public DriveBackwards1(Drive drive, Peripherals peripherals, double target, double minMaxOutput, boolean trueForwards, double wantedAngle, boolean useUltra) {
    this.drive = drive;
    this.peripherals = peripherals;
    this.target = target * 0.0254;
    addRequirements(drive);
    minOutput = -minMaxOutput;
    maxOutput = minMaxOutput;
    isForwards = trueForwards;
    this.desiredAngle = wantedAngle;
    this.useUS = useUltra;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    ultraSonicDistance = peripherals.getUltraSonicDist();
    SmartDashboard.putNumber("US Distance", ultraSonicDistance);
    drive.zeroDriveEncoderTics();
    SmartDashboard.putBoolean("finished drivebackwards 1", false);
    pid = new PID(kP, kI, kD);
    usPID = new PID(usP, usI, usD);
    pid.setSetPoint(target);
    usPID.setSetPoint(22);
    pid.setMinOutput(minOutput);
    pid.setMaxOutput(maxOutput);
    usPID.setMaxOutput(0.05);
    usPID.setMinOutput(-0.05);
    drive.setDriveBrake();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    pid.updatePID(drive.getDriveMeters());
    if(useUS) {
      turnOffset = usPID.updatePID(peripherals.getUltraSonicDist());
    }
    if(Math.abs(peripherals.getNavxAngle() - desiredAngle) > 1.5) {
        turnOffset = 0.01;
    }
    if(isForwards) {
      drive.setLeftPercent(pid.getResult() - turnOffset);
      drive.setRightPercent(pid.getResult() + turnOffset);
      // SmartDashboard.putNumber("Distance", drive.getDriveMeters() - target);
    }
    else {
      drive.setLeftPercent(-pid.getResult() + turnOffset);
      drive.setRightPercent(-pid.getResult() - turnOffset);
      // SmartDashboard.putNumber("Distance", drive.getDriveMeters() + target);
    }
    SmartDashboard.putNumber("drivebackwards 1 output", pid.getResult());
  }

  @Override
  public void end(boolean interrupted) {
    drive.setLeftPercent(0);
    drive.setRightPercent(0);
    SmartDashboard.putBoolean("finished drivebackwards 1", true);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(isForwards) {
      if(Math.abs(drive.getDriveMeters() - target) < 0.5){
        return true;
      }  
    }
    else {
      if(Math.abs(drive.getDriveMeters() + target) < 0.2){
        return true;
      }
    }     
    
    return false;
  }
}
