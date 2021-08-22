// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.defaultCommands;

import frc.robot.subsystems.Lights;
import frc.robot.subsystems.Lights.LEDMode;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class LightsDefault extends CommandBase {
  private Lights lights;

/** Creates a new LightsDefault. */
  public LightsDefault(Lights lights) {
      this.lights = lights;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(this.lights);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    lights.setMode(LEDMode.BLUE);
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