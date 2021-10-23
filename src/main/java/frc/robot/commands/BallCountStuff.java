// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BallCount;
import frc.robot.subsystems.Lights;

public class BallCountStuff extends CommandBase {
  private BallCount ballCount;
  private String command;
  public BallCountStuff(BallCount ballCount, String command) {
    this.ballCount = ballCount;
    addRequirements();
    this.command = command;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    switch (command) {
      case "add":
      System.out.println("add ball");
      ballCount.incrementBallCount();
      break;
      case "reset":
      System.out.println("reset balls");
      ballCount.resetBallCount();
      break;
    }
  }


  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    //System.out.println("ball count stuff END");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
