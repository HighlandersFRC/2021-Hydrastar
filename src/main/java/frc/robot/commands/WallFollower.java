// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Peripherals;
import frc.robot.tools.controlloops.PID;

public class WallFollower extends CommandBase {
  /** Creates a new WallFollower. */

  private Drive drive;
  private Peripherals peripherals;

  private double backDist = 0;

  private double backUSDist = 0;
  private double frontUSDist = 0;

  private double USToUS = 17.5;
  private double backToUS = 11;

  private double ultraSonicDifference = 0;
  private double normalAngle = 0;
  private double d4 = 0;
  private double theta2 = 0;
  private double theta3 = 0;
  private double d3 = 0;

  private PID pid;
  private double kP = 0.015;
  private double kI = 0.003;
  private double kD = 0.16;
  private double target;

  public WallFollower(Drive driveSubsystem, Peripherals peripheralSubsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.drive = driveSubsystem;
    this.peripherals = peripheralSubsystem;
    addRequirements(this.drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    pid = new PID(kP, kI, kD);
    pid.setSetPoint(12);
    pid.setMinOutput(-0.05);
    pid.setMaxOutput(0.05);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    backUSDist = peripherals.getBackUltraSonicDist();
    frontUSDist = peripherals.getUltraSonicDist();

    ultraSonicDifference = (backUSDist - frontUSDist);
    normalAngle = Math.atan2(USToUS, ultraSonicDifference);
    // normalAngle = (normalAngle * 180)/Math.PI;
    SmartDashboard.putNumber("Normal", ((normalAngle) * 180)/Math.PI);
    d4 = backUSDist * Math.sin(normalAngle);
    SmartDashboard.putNumber("Sin Normal", Math.sin(normalAngle));
    theta2 = 90 - normalAngle;
    theta3 = 90 - theta2;
    SmartDashboard.putNumber("d4", d4);
    SmartDashboard.putNumber("Cos theta3", backToUS * Math.cos(normalAngle));
    d3 = d4 - (backToUS * Math.cos(normalAngle));
    SmartDashboard.putNumber("d3", d3);

    backDist = d3;

    pid.updatePID(backDist);

    // drive.setLeftPercent(-pid.getResult() - 0.3);
    // drive.setRightPercent(pid.getResult() - 0.3);

    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drive.setLeftPercent(0.0);
    drive.setRightPercent(0.0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
