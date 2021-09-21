// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;
import frc.robot.tools.controlloops.PID;

public class ClimbRobot extends CommandBase {
  /** Creates a new ClimbRobot. */
  private PID pid;
  private double kP = 0.000005;
  private double kI = 0.0;
  private double kD = 0.0;
  
  private Climber climber;

  private double output = 0.0;

  public ClimbRobot(Climber climber) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.climber = climber;
    addRequirements(this.climber);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    pid = new PID(kP, kI, kD);
    pid.setSetPoint(0);
    pid.setMinOutput(-0.05);
    pid.setMaxOutput(0.05);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    pid.updatePID(climber.getLeftEncoderTics() - climber.getRightEncoderTics());
    output = pid.getResult();
    SmartDashboard.putNumber("Left Climb Tics", climber.getLeftEncoderTics());
    SmartDashboard.putNumber("Right Climb Tics", climber.getRightEncoderTics());
    SmartDashboard.putNumber("Climber PID Output", output);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    climber.setClimber(0.0, 0.0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if((climber.getLeftEncoderTics() + climber.getRightEncoderTics()/2) < 170000) {
      return true;
    }
    return false;
  }
}
