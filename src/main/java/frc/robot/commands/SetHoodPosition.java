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
    private int regression; 

    public SetHoodPosition(Hood hood, Peripherals peripherals, double target, int useRegression) {
        this.hood = hood;
        this.peripherals = peripherals;
        addRequirements(hood);
        this.target = target;
        this.regression = useRegression;
    }

    @Override
    public void initialize() {
        this.distance = peripherals.getLidarDistance();
        SmartDashboard.putNumber("Hood Lidar", peripherals.getLidarDistance());
        SmartDashboard.putNumber("UseRegression", regression);
        if(regression == 0) {
            target = (1.469567313 * this.distance) + 0.9674088001;
        SmartDashboard.putNumber("InitTarget", target);
    }
        // if(regression == 1){
        //     target = 24;
        // }
        // if(regression == 2){
        //     target = 33;
        // }
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
