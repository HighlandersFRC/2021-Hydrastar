package frc.robot.commands.defaultCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.MagIntake;

public class MagIntakeDefault extends CommandBase {
    private MagIntake magIntake;

    public MagIntakeDefault(MagIntake magIntake) {
        this.magIntake = magIntake;
        addRequirements(this.magIntake);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        // System.out.println("Hola om");
        // if (OI.driverController.getTriggerAxis(Hand.kLeft) > 0.25) {
        //     System.out.println("got lt button");
        //     magIntake.setMagPercent(0.5, 0.5, 0.5);
        // } else {
        magIntake.setIntakePercent(0);
        magIntake.setMagPercent(0, 0, 0);
        magIntake.setIntakePistonUp();
        magIntake.putBeamBreaksSmartDashboard();
    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return false;
    }
}
