// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.OI;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.MagIntake;

public class EjectMagazine extends CommandBase {
    private MagIntake magIntake;
    private Drive drive;

    private int counter = 0;
    private int countsToEnd;

    /** Creates a new EjectMagazine. */
    public EjectMagazine(MagIntake magIntake, Drive drive, int timeToEnd) {
        this.magIntake = magIntake;
        this.drive = drive;
        addRequirements(magIntake);
        countsToEnd = timeToEnd;
        // Use addRequirements() here to declare subsystem dependencies.
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        counter = 0;
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        magIntake.setMagPercent(1, 0.75, 1);
        if(OI.driverController.getBumper(Hand.kRight)) {
            drive.setLeftPercent(0.1);
            drive.setRightPercent(-0.1);
        }
        else if(OI.driverController.getBumper(Hand.kLeft)) {
            drive.setLeftPercent(-0.1);
            drive.setRightPercent(0.1);
        }
        else {
            drive.setRightPercent(0);
            drive.setLeftPercent(0);
        }
        counter++;
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {}

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        if(countsToEnd > 0) {
            if(counter > countsToEnd) {
                return true;
            }
        }
        return false;
    }
}
