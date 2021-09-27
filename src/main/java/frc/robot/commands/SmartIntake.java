// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.OI;
import frc.robot.subsystems.Lights;
import frc.robot.subsystems.MagIntake;
import frc.robot.subsystems.Lights.LEDMode;
import frc.robot.subsystems.MagIntake.BeamBreakID;

public class SmartIntake extends CommandBase {
    private MagIntake magIntake;
    private Lights lights;

    private enum MagIntakeStates {}

    public SmartIntake(MagIntake magIntake, Lights lights) {
        this.magIntake = magIntake;
        this.lights = lights;
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
    }

    @Override
    public void execute() {
        // magIntake.setMagPercent(0.5, 0.5, 0.5);
        // magIntake.setIntakePercent(-0.65);
        if (!magIntake.getBeamBreak(BeamBreakID.ONE)) {
            magIntake.setMagPercent(0.5, 0.17, 0.5);
            magIntake.setIntakePercent(-0.27);
            lights.setMode(LEDMode.YELLOW);
        } else {
            if(OI.operatorController.getBumper(Hand.kLeft )){
                magIntake.setMagPercent(0.5, 0.17, 0.5);
            }
            else {
                magIntake.setMagPercent(0, 0, 0);
            }
            magIntake.setIntakePercent(-0.70);
            // magIntake.setMagPercent(0.3, 0.1, 0.3);)            lights.setMode(LEDMode.BLUE);
        }
        magIntake.putIntakeCurrentSmartDashboard();
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("Inside end command");
        // magIntake.setMagPercent(0, 0, 0);
        // magIntake.setIntakePercent(0);
        // magIntake.setIntakePistonUp();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
