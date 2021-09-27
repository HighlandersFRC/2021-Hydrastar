package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.Hood;

public class SetHoodPosition extends CommandBase {

    private Hood hood;
    private double target;
    private double distance;

    public SetHoodPosition(Hood hood, double target, double lidarDistance) {
        this.hood = hood;
        this.distance = lidarDistance;
        addRequirements(hood);
        this.target = target;
    }

    @Override
    public void initialize() {
        // if(distance != -1 && distance > 6) {
        //     // target = (-0.0010916867 * Math.pow(distance, 3)) + (0.0518742349 * Math.pow(distance, 2)) + (-0.5535555678 * Math.pow(distance, 1)) + 7.612521649;
        //     target = (0.1857331224 * distance) + 4.647679325;
        // }
        // else if(distance < 6 && distance != -1) {
        //     target = 2.5;
        // }
    }

    @Override
    public void execute() {
        hood.setHoodTarget(target);
    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
         return Math.abs(target - hood.getHoodPosition()) < 0.5;
       // return true;
    }
}
