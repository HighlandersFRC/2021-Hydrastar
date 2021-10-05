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
        SmartDashboard.putNumber("HDist", distance);
        SmartDashboard.putBoolean("UseRegression", regression);
        if(regression == true) {
            if (this.distance != -1 && this.distance > 13) {
                SmartDashboard.putBoolean("1True", true);
                SmartDashboard.putBoolean("2True", false);
                SmartDashboard.putBoolean("3True", false);
                target = (0.23 * this.distance) + 6;
            } else if (this.distance < 5 && this.distance != -1) {
                SmartDashboard.putBoolean("1True", false);
                SmartDashboard.putBoolean("2True", true);
                SmartDashboard.putBoolean("3True", false);
                target = 4;
            } else if (this.distance != -1 && ((this.distance > 3) && (this.distance < 13))) {
                SmartDashboard.putBoolean("1True", false);
                SmartDashboard.putBoolean("2True", false);
                SmartDashboard.putBoolean("3True", true);
                target = (0.15 * this.distance) + 2;
            }
        } else {
            SmartDashboard.putBoolean("1True", false);
            SmartDashboard.putBoolean("2True", false);
            SmartDashboard.putBoolean("3True", false);
        }
        SmartDashboard.putNumber("InitTarget", target);
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
