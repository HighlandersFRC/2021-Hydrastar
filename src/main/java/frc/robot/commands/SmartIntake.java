// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.OI;
import frc.robot.subsystems.BallCount;
import frc.robot.subsystems.Lights;
import frc.robot.subsystems.MagIntake;
import frc.robot.subsystems.Lights.LEDMode;


public class SmartIntake extends CommandBase {
    private MagIntake magIntake;
    private Lights lights;
    private BallCount ballCount;
    private boolean tempBoolean = false;
    private boolean tempBoolean2 = false;
    private double startTime = 99999;

    private enum MagIntakeStates {}

    public SmartIntake(MagIntake magIntake, Lights lights, BallCount ballCount) {
        this.magIntake = magIntake;
        this.lights = lights;
        this.ballCount = ballCount;
        addRequirements(magIntake);
    }

    public SmartIntake(MagIntake magIntake, double duration, Lights lights) {
        this.magIntake = magIntake;
        this.lights = lights;
        addRequirements(magIntake, lights);
    }

    @Override
    public void initialize() {
        magIntake.setIntakePistonDown();
        System.out.println("inside intake init");
    }

    @Override
    public void execute() {
        // magIntake.setMagPercent(0.5, 0.5, 0.5);
        // magIntake.setIntakePercent(-0.65);
        if (!magIntake.getBeamBreaks().getBeamBreak(1)) {
            magIntake.setMagPercent(0.5, 0.17, 0.5);
            magIntake.setIntakePercent(-0.70);
            lights.setMode(LEDMode.YELLOW);
        } else {
            if(OI.operatorController.getBumper(Hand.kLeft)){
                magIntake.setMagPercent(0.5, 0.17, 0.5);
            }
            else {
                magIntake.setMagPercent(0, 0, 0);
            }
            magIntake.setIntakePercent(-0.70);
            // magIntake.setMagPercent(0.3, 0.1, 0.3);)            lights.setMode(LEDMode.BLUE);
        }
        magIntake.putIntakeCurrentSmartDashboard();

        if (ballCount.getBeamBreaks().getBeamBreak(1)) {
            tempBoolean2 = true;
            System.out.println("1");
        } else if (tempBoolean2 && !ballCount.getBeamBreaks().getBeamBreak(1)) {
            tempBoolean = true;
            //System.out.println("2");
        }
        if (tempBoolean && tempBoolean2 && ballCount.getBeamBreaks().getBeamBreak(1)) {
            ballCount.incrementBallCount();
            tempBoolean = false;
            tempBoolean2 = false;
            //System.out.println("3");
        }
    }

    @Override
    public void end(boolean interrupted) {
        //System.out.println("Inside end command");
        // magIntake.setMagPercent(0, 0, 0);
        // magIntake.setIntakePercent(0);
        // magIntake.setIntakePistonUp();
        // if (!ballCount.getBeamBreaks().getBeamBreak(1)) {
        //     ballCount.decrementBallCount();
        // }
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
