// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.defaultCommands;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.OI;
import frc.robot.subsystems.Climber;

public class ClimberDefault extends CommandBase {
    private Climber climber;
    private WaitCommand wait = new WaitCommand("Wait", 0.1);
    /** Creates a new ClimberDefault. */
    public ClimberDefault(Climber climber) {
        this.climber = climber;

        addRequirements(climber);
        // Use addRequirements() here to declare subsystem dependencies.
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {}

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        if(OI.operatorController.getYButton()) {
            climber.setClimberPiston(Value.kReverse);
            climber.setClimber(0.35, 0.35);
        }
        // else if(OI.operatorController.getAButton()) {
        //     climber.setClimber(-0.3, -0.3);
        // }
        else {
            climber.setClimberPiston(Value.kForward);
            climber.setClimber(0, 0);
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
