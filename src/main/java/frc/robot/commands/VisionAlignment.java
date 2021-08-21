// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.Drive;
import frc.robot.subsystems.LightRing;
import frc.robot.subsystems.Peripherals;
import frc.robot.tools.controlloops.PID;

public class VisionAlignment extends CommandBase {

    private LightRing lightRing;
    private Drive drive;
    private Peripherals peripherals;

    private PID pid;
    private double kP = 0.0075;
    private double kI = 0.00001;
    private double kD = 0.;
    private int counter = 0;
    private double angleOffset = 0;

    public VisionAlignment(
            LightRing lightRing, Drive drive, Peripherals peripherals, Double offset) {
        this.drive = drive;
        this.lightRing = lightRing;
        this.peripherals = peripherals;
        angleOffset = offset;

        addRequirements(this.drive, this.lightRing);
    }

    @Override
    public void initialize() {
        SmartDashboard.putBoolean("finished vision", false);
        counter = 0;
        pid = new PID(kP, kI, kD);
        pid.setSetPoint(0);
        pid.setMinOutput(-0.5);
        pid.setMaxOutput(0.5);
    }

    @Override
    public void execute() {
        counter++;
        lightRing.turnVisionOn();
        // SmartDashboard.putNumber("vision Angle", peripherals.getCamAngle());
        // System.out.println(peripherals.getCamAngle());
        pid.updatePID(peripherals.getCamAngle() + angleOffset);
        SmartDashboard.putNumber("PID Output", pid.getResult());
        SmartDashboard.putNumber("Counter", counter);
        drive.setRightPercent(pid.getResult());
        drive.setLeftPercent(-pid.getResult());
    }

    @Override
    public void end(boolean interrupted) {
        drive.setRightPercent(0);
        drive.setLeftPercent(0);
        SmartDashboard.putBoolean("finished vision", true);
        // lightRing.turnOff();
    }

    @Override
    public boolean isFinished() {
        return Math.abs(peripherals.getCamAngle()) <= 0.5
                        && Math.abs(pid.getResult()) < 0.05
                        && peripherals.getCamAngle() != angleOffset
                || counter > 45;
    }
}
