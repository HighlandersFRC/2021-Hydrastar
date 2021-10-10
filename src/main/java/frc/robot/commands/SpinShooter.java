// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Peripherals;
import frc.robot.subsystems.Shooter;

public class SpinShooter extends CommandBase {
    private Shooter shooter;
    private Peripherals peripherals;
    private double rpm;
    private double distance;
    private int zone;
    private double counter = 0;
    /** Creates a new SpinShooter. */
    public SpinShooter(Shooter shooter, Peripherals peripherals, double rpm, int shootingZone) {
        this.shooter = shooter;
        this.peripherals = peripherals;
        this.rpm = rpm;
        this.zone = shootingZone;
        addRequirements(shooter);

        // Use addRequirements() here to declare subsystem dependencies.
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        this.distance = peripherals.getLidarDistance();
        if(zone == 3){
            rpm = (29.84041751 * this.distance) + 1008.993061;

        }
        shooter.setShooterRPM(rpm);
        counter = 0;
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        // shooter.setShooterRPM(rpm);
        counter++;
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {}

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return Math.abs(shooter.getShooterRPM() - rpm) < 50 || (counter > 100);
        // return true;
    }
}
