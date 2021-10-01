package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.sensors.LidarLite;
import frc.robot.subsystems.Hood;

public class SetHoodPosition extends CommandBase {

    private Hood hood;
    private double target;
    private LidarLite lidar;
    private double distance;

    public SetHoodPosition(Hood hood, double target, double lidarDistance) {
        this.distance = lidarDistance;
        this.hood = hood;
        addRequirements(hood);
        this.target = target;
    }

    @Override
    public void initialize() {
        if (distance != -1 && distance > 5) {
            target = (0.23 * distance) + 6;
        } else if (distance < 5 && distance != -1) {
            target = 1;
        } else if (distance != -1 && ((distance > 3) && (distance < 13))) {
            target = (0.15 * distance) + 2;
        }
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
