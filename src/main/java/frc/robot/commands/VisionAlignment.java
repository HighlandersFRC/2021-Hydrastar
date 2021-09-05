// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.OI;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.LightRing;
import frc.robot.subsystems.Peripherals;
import frc.robot.tools.controlloops.PID;

public class VisionAlignment extends CommandBase {

    private LightRing lightRing;
    private Drive drive;
    private Peripherals peripherals;

    private PID pid;
    private PID backPID;
    private double kP = 0.0075;
    private double kI = 0.0001;
    private double kD = 0.005;
    private double backP = 0.02;
    private double backI = 0.0;
    private double backD = 0.0;
    private int counter = 0;
    private double angleOffset = 0;
    private boolean isBack = false;
    private double output = 0;
    private double distance = 0;

    public VisionAlignment(
            LightRing lightRing,
            Drive drive,
            Peripherals peripherals,
            Double offset,
            boolean back,
            double distance) {
        this.drive = drive;
        this.lightRing = lightRing;
        this.peripherals = peripherals;
        angleOffset = offset;
        isBack = back;
        this.distance = distance;

        addRequirements(this.drive, this.lightRing);
    }

    @Override
    public void initialize() {
        SmartDashboard.putBoolean("finished vision", false);
        counter = 0;
        pid = new PID(kP, kI, kD);
        backPID = new PID(backP, backI, backD);
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
        if (isBack) {
            output = backPID.updatePID(peripherals.getCamAngle() + angleOffset);
        } else {
            output = pid.updatePID(peripherals.getCamAngle() + angleOffset);
        }
        SmartDashboard.putNumber("PID Output", pid.getResult());
        SmartDashboard.putNumber("Counter", counter);
        if(OI.driverController.getBumper(Hand.kRight)) {
            drive.setLeftPercent(0.1);
            drive.setRightPercent(-0.1);
        }
        else if(OI.driverController.getBumper(Hand.kLeft)) {
            drive.setLeftPercent(-0.1);
            drive.setRightPercent(0.1);
        }
        else {
            drive.setRightPercent(output);
            drive.setLeftPercent(-output);
        }
        
    }

    @Override
    public void end(boolean interrupted) {
        drive.setRightPercent(0);
        drive.setLeftPercent(0);
        SmartDashboard.putBoolean("finished vision", true);
        lightRing.turnVisionOff();
    }

    @Override
    public boolean isFinished() {
        if(isBack) {
            return Math.abs(peripherals.getCamAngle() - angleOffset) <= 0.5
                        && Math.abs(pid.getResult()) < 0.1
                        && peripherals.getCamAngle() != angleOffset
                || counter > 75;
        }
        else {
            return Math.abs(peripherals.getCamAngle() - angleOffset) <= 2
                        && Math.abs(pid.getResult()) < 0.05
                        && peripherals.getCamAngle() != angleOffset
                || counter > 45;
        }
        // return false;
        
    }
}
