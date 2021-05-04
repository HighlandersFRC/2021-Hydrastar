// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.MagIntake;

public class Outtake extends CommandBase {
    private MagIntake magIntake;

    public Outtake(MagIntake magIntake) {
        this.magIntake = magIntake;
        addRequirements(magIntake);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        magIntake.setIntakePercent(0.4);
        magIntake.setMagPercent(-0.5, 0.5, -0.5);
    }

    @Override
    public void end(boolean interrupted) {
        magIntake.setIntakePercent(0);
        magIntake.setMagPercent(0, 0, 0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
