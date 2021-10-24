// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BallCount;
import frc.robot.subsystems.MagIntake;

public class Outtake extends CommandBase {
    private MagIntake magIntake;
    private boolean tempBoolean = true;
    private BallCount ballCount;
    private double startTime = Timer.getFPGATimestamp();
    private double currentTime = Timer.getFPGATimestamp();

    public Outtake(MagIntake magIntake, BallCount ballCount) {
        this.magIntake = magIntake;
        this.ballCount = ballCount;
        addRequirements(magIntake);
    }

    @Override
    public void initialize() {
        
    }

    @Override
    public void execute() {
        magIntake.setIntakePercent(0.5);
        magIntake.setMagPercent(-0.7, -0.5, -0.7);

        // if (tempBoolean && !ballCount.getBeamBreaks().getBeamBreak(1)) {
        //     tempBoolean = false;
        //     startTime = Timer.getFPGATimestamp();
        // } else if (ballCount.getBeamBreaks().getBeamBreak(1)) {
        //     System.out.println("broken duration: " + (Timer.getFPGATimestamp() - startTime));
        //     if (!tempBoolean) {
        //         ballCount.subtractNum( (int) Math.floor( (Timer.getFPGATimestamp() - startTime) / 0.2) );
        //         System.out.println( (int) Math.floor( (Timer.getFPGATimestamp() - startTime) / 0.2) );
        //     }
        //     tempBoolean = true;
        //     startTime = 99999;
        // }
        currentTime = Timer.getFPGATimestamp();

        System.out.println("Outtaking!");

        if (tempBoolean && !ballCount.getBeamBreaks().getBeamBreak(1)) {

            tempBoolean = false;
            startTime = currentTime;

        } else if (!tempBoolean && ballCount.getBeamBreaks().getBeamBreak(1)) {

            System.out.println("broken duration: " + (currentTime - startTime));
            System.out.println("had " + ballCount.getBallCount() + " balls");

            ballCount.subtractNum( (int) Math.floor( (currentTime - startTime) / 0.2) );

            System.out.println("now have " + ballCount.getBallCount() + " balls");

            tempBoolean = true;
            startTime = 99999;
        }
    }

    @Override
    public void end(boolean interrupted) {
        // if (!ballCount.getBeamBreaks().getBeamBreak(1)) {
        //     ballCount.incrementBallCount();
        //     System.out.println("inside end condition");
        //     System.out.println(interrupted);
        // }
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
