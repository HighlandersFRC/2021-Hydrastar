package frc.robot.commands.defaultCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.OI;
import frc.robot.subsystems.Drive;

public class DriveDefault extends CommandBase {
    private Drive drive;

    public DriveDefault(Drive drive) {
        this.drive = drive;
        addRequirements(this.drive);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        //System.out.println("Hola om");
        drive.arcadeDrive(OI.getDriverLeftY(), OI.getDriverRightX());
    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return false;
    }
}
