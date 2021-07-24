package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.Hood;

public class SetHoodPosition extends CommandBase {

    private Hood hood;
    private double target;

    public SetHoodPosition(Hood hood, double target) {
        this.hood = hood;
        addRequirements(hood);
        this.target = target;
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        hood.setHoodTarget(target);
    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return Math.abs(target - hood.getHoodPosition()) < 0.1;
    }
}
