package frc.robot.commands.defaultCommands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.OI;
import frc.robot.subsystems.Hood;

public class HoodDefault extends CommandBase {

    private Hood hood;

    public HoodDefault(Hood hood) {
        this.hood = hood;
        addRequirements(hood);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        if (OI.driverController.getXButton()) {
            SmartDashboard.putNumber("hood position", hood.getHoodPosition());
            hood.setHoodTarget(15);
        } else {
            hood.setHoodTarget(0);
        }
    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return false;
    }
}
