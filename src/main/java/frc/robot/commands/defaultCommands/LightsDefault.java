// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.defaultCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.Lights;
import frc.robot.subsystems.Lights.LEDMode;

public class LightsDefault extends CommandBase {
    private Lights lights;
    private double startTime;

    public LightsDefault(Lights lights) {
        this.lights = lights;
        addRequirements(this.lights);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {}

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        //lights.setMode(LEDMode.BLUE);
        switch(lights.getBallCount()) {
            case 0: lights.setMode(LEDMode.ZEROBALLS);
              break;
            case 1: lights.setMode(LEDMode.ONEBALL);
              break;
            case 2: lights.setMode(LEDMode.TWOBALLS);
              break;
            case 3: lights.setMode(LEDMode.THREEBALLS);
              break;
            case 4: lights.setMode(LEDMode.FOURBALLS);
              break;
            case 5: lights.setMode(LEDMode.FIVEBALLS);
              break;
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