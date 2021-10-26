// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;

public class PushClimber extends CommandBase {
  /** Creates a new PushClimber. */
  private Climber climber;
  private double target;

  private Boolean rightDone = false;
  private Boolean leftDone = false;


  public PushClimber(Climber climber, double targetTics) {
    this.climber = climber;
    this.target = targetTics;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    climber.setClimberPiston(Value.kReverse);
    // climber.resetClimbEncoders();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    climber.putEncodersSmartDashboard();
    // if(Math.abs(target - climber.getLeftEncoderTics()) < 1000) {
    //     leftDone = true;
    // }    

    // if(Math.abs(target - climber.getRightEncoderTics()) < 1000) {
    //     rightDone = true;
    // }

    
    if(climber.getRightEncoderTics() > target) {
      rightDone = true;
    }
    if(climber.getLeftEncoderTics() > target) {
      leftDone = true;
    }

    if(rightDone) {
        climber.setClimber(0.4, 0);
    }
    else if(leftDone) {
        climber.setClimber(0, 0.4);
    }
    else {
        climber.setClimber(0.4, 0.4);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    climber.setClimber(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(rightDone && leftDone) {
      return true;
    }
    return false;
  }
}
