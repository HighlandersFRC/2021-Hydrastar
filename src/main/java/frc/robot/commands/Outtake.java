// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BallCount;
import frc.robot.subsystems.MagIntake;

public class Outtake extends CommandBase {
    private MagIntake magIntake;
    private BallCount ballCount;
    private double startTime = Timer.getFPGATimestamp();
    private double currentTime = Timer.getFPGATimestamp();
    private boolean tempBoolean = true;

    public Outtake(MagIntake magIntake, BallCount ballCount) {
        this.magIntake = magIntake;
        this.ballCount = ballCount;
        addRequirements(magIntake);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        magIntake.setIntakePercent(0.5);
        magIntake.setMagPercent(-0.7, -0.5, -0.7);

        currentTime = Timer.getFPGATimestamp();

        if (tempBoolean && !ballCount.getBeamBreaks().getBeamBreak(1)) {
            tempBoolean = false;
            startTime = currentTime;
        } else if (!tempBoolean && ballCount.getBeamBreaks().getBeamBreak(1)) {
            ballCount.subtractNum( (int) Math.floor((currentTime - startTime) / 0.2));
            tempBoolean = true;
            startTime = 99999;
        }
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
