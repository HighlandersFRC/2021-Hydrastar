package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.sensors.LidarLite;
import frc.robot.subsystems.Hood;
import frc.robot.subsystems.Peripherals;

public class SetHoodPosition extends CommandBase {

    private Hood hood;
    private Peripherals peripherals;
    private double target;
    private LidarLite lidar;
    private double distance;
    private boolean regression; 

    public SetHoodPosition(Hood hood, Peripherals peripherals, double target, double lidarDistance, boolean useRegression) {
        this.hood = hood;
        this.peripherals = peripherals;
        addRequirements(hood);
        this.target = target;
        this.regression = useRegression;
    }

    @Override
    public void initialize() {
        SmartDashboard.putBoolean("Inisde Hood Init", true);
        this.distance = peripherals.getLidarDistance();
        SmartDashboard.putNumber("Hood Lidar", peripherals.getLidarDistance());
        SmartDashboard.putNumber("HDist", distance);
        SmartDashboard.putBoolean("UseRegression", regression);
        if(regression == true) {
            target = (1.469567313 * this.distance) + 0.9674088001;
        SmartDashboard.putNumber("InitTarget", target);}
    }

    @Override
    public void execute() {
        System.out.println(distance);
        SmartDashboard.putNumber("ExecTarget", target);
        hood.setHoodTarget(target);
    }

    @Override
    public void end(boolean interrupted) {
        SmartDashboard.putBoolean("Inisde Hood Init", false);

    }

    @Override
    public boolean isFinished() {
         return Math.abs(target - hood.getHoodPosition()) < 0.5;
       // return true;
    }
}
