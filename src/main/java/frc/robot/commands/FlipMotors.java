// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;
import frc.robot.tools.pathing.Odometry;

public class FlipMotors extends CommandBase {
  /** Creates a new FlipMotors. */

  private Drive drive;
  private Odometry odometry;
  private boolean inversion = false;

  public FlipMotors(Drive drive, boolean inversion, Odometry odometry) {
    this.drive = drive;
    this.odometry = odometry;
    addRequirements(drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    drive.inverseDriveMotors(inversion);
    odometry.zero();
    drive.zeroDriveEncoderTics();
    odometry.setInverted(true);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
