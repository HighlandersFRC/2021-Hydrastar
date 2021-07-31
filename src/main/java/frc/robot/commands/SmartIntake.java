// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.MagIntake;
import frc.robot.subsystems.MagIntake.BeamBreakID;

public class SmartIntake extends CommandBase {
    private MagIntake magIntake;

    private enum MagIntakeStates {}

    public SmartIntake(MagIntake magIntake) {
        this.magIntake = magIntake;
        addRequirements(magIntake);
    }

    public SmartIntake(MagIntake magIntake, double duration) {
        this.magIntake = magIntake;
        addRequirements(magIntake);
    }

    @Override
    public void initialize() {
        magIntake.setIntakePistonDown();
    }

    @Override
    public void execute() {
        // magIntake.setMagPercent(0.5, 0.5, 0.5);
        // magIntake.setIntakePercent(-0.65);
        if (!magIntake.getBeamBreak(BeamBreakID.ONE)) {
            magIntake.setMagPercent(0.5, 0.15, 0.5);
            magIntake.setIntakePercent(-0.15);
        } else {
            magIntake.setIntakePercent(-0.65);
            magIntake.setMagPercent(0, 0, 0);
        }
    }

    @Override
    public void end(boolean interrupted) {
        magIntake.setMagPercent(0, 0, 0);
        magIntake.setIntakePercent(0);
        magIntake.setIntakePistonUp();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
