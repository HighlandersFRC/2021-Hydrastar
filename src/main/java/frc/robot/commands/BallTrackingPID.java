// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.OI;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.LightRing;
import frc.robot.subsystems.Lights;
import frc.robot.subsystems.MagIntake;
import frc.robot.subsystems.Peripherals;
import frc.robot.subsystems.Lights.LEDMode;
import frc.robot.subsystems.MagIntake.BeamBreakID;
import frc.robot.tools.controlloops.PID;

public class BallTrackingPID extends CommandBase {

    private LightRing lightRing;
    private Drive drive;
    private Peripherals peripherals;

    private PID pid;
    private PID backPID;
    private double kP = 0.0075;
    private double kI = 0.0001;
    private double kD = 0.005;
    private double backP = 0.018;
    private double backI = 0.0;
    private double backD = 0.0;
    private int counter = 0;
    private double angleOffset = 0;
    private boolean isBack = false;
    private double output = 0;
    private double distance = 0;
    private Lights lights;
    private MagIntake magIntake;

    public BallTrackingPID(
            LightRing lightRing,
            Drive drive,
            MagIntake magIntake,
            Peripherals peripherals,
            Double offset,
            boolean back,
            double distance,
            Lights lights) {
        this.drive = drive;
        this.lightRing = lightRing;
        this.peripherals = peripherals;
        angleOffset = offset;
        isBack = back;
        this.distance = distance;
        this.lights = lights;
        this.magIntake = magIntake;
        addRequirements(this.drive, this.lightRing, this.magIntake);
    }

    @Override
    public void initialize() {
        SmartDashboard.putBoolean("finished vision", false);
        counter = 0;
        pid = new PID(kP, kI, kD);
        backPID = new PID(backP, backI, backD);
        pid.setSetPoint(0);
        pid.setMinOutput(-0.1);
        pid.setMaxOutput(0.1);
    }

    @Override
    public void execute() {
        counter++;
        magIntake.setIntakePercent(-0.7);
        if(magIntake.getBeamBreak(BeamBreakID.ONE)) {
          magIntake.setMagPercent(0.5, 0.2, 0.5);
        }
        else {
          magIntake.setMagPercent(0.0, 0.0, 0.0);
        }
        SmartDashboard.putNumber("PID Output", pid.getResult());
        drive.setRightPercent(output - 0.3);
        drive.setLeftPercent(-output - 0.3);
        if (peripherals.getCamAngle() == 0) {
            lights.setMode(LEDMode.STROBERED);
        } else {
            lights.setMode(LEDMode.GREEN);
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
        if(!magIntake.getBeamBreak(BeamBreakID.THREE)) {
          return true;
        }
        return false;
        
    }
}
