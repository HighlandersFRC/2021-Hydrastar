// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Peripherals;

public class WallFollower extends CommandBase {
  /** Creates a new WallFollower. */

  private Drive drive;
  private Peripherals peripherals;

  private double backDist = 0;
  private double frontDist = 0;

  public WallFollower(Drive driveSubsystem, Peripherals peripheralSubsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.drive = driveSubsystem;
    this.peripherals = peripheralSubsystem;
    addRequirements(this.drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    backDist = peripherals.getBackUltraSonicDist();
    frontDist = peripherals.getUltraSonicDist();

    if(((Math.abs(backDist - frontDist) < 2)) && (Math.abs(backDist - 23) < 2)) {
      drive.setLeftPercent(-0.4);
      drive.setRightPercent(-0.4);
    }
    else if(((Math.abs(backDist - frontDist) < 2)) && ((backDist - 23) > 2)) {
      drive.setLeftPercent(-0.45);
      drive.setRightPercent(-0.4);
    }
    else if(((Math.abs(backDist - frontDist) < 2)) && ((backDist - 23) < -2)) {
      drive.setLeftPercent(-0.4);
      drive.setRightPercent(-0.45);
    }
    else if(backDist < frontDist && ((backDist - 23) > 2)) {
      drive.setRightPercent(-0.3);
      drive.setLeftPercent(-0.3);
    }
    else if(backDist < frontDist && ((Math.abs(backDist - 23)) < 2)) {
      drive.setLeftPercent(-0.4);
      drive.setRightPercent(-0.45);
    }
    else if(backDist < frontDist && ((backDist - 23) < 2)) {
      drive.setLeftPercent(-0.35);
      drive.setRightPercent(-0.45);
    }
    else if(frontDist < backDist && ((frontDist - 23) > 2)) {
      drive.setRightPercent(-0.35);
      drive.setLeftPercent(-0.45);
    }
    else if(frontDist < backDist && (Math.abs(frontDist - 23) < 2)) {
      drive.setLeftPercent(-0.45);
      drive.setRightPercent(-0.4);
    }
    else if(frontDist < backDist && ((frontDist - 23) < 2)) {
      drive.setRightPercent(-0.3);
      drive.setLeftPercent(-0.3);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
