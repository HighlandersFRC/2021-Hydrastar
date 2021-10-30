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
    private int zone;
    private double avgDistComplete = 0;

    private int averageDone = 1;

    private double averageDist = 0;

    private double oldValue = 0;


    public SetHoodPosition(Hood hood, Peripherals peripherals, double target, int shootingZone) {
        this.hood = hood;
        this.peripherals = peripherals;
        addRequirements(hood);
        this.target = target;
        this.zone = shootingZone;
        
    }

    @Override
    public void initialize() {
        averageDone = 0;
        averageDist = 0;
        avgDistComplete = 0;
        // SmartDashboard.putNumber("Hood Lidar", this.distance);
        // SmartDashboard.putNumber("Zone Number", zone);
    }

    @Override
    public void execute() {
        if(zone == 1) {
            if(averageDone < 20) {
                if(oldValue == 0) { 
                    oldValue = peripherals.getUltraSonicDist();
                }
                else {
                    this.distance = peripherals.getUltraSonicDist();
                    averageDist = (averageDist + this.distance);
                    // avgDistComplete = averageDist * 0.0833 + (0.083 * oldValue);
                    // oldValue = avgDistComplete;
                    SmartDashboard.putNumber("avg", averageDist);
                    averageDone++;
                }
                
            }
            else {
                avgDistComplete = averageDist/20;
                SmartDashboard.putNumber("averageDist", avgDistComplete);
                if(zone == 1){
                    if(avgDistComplete < 12 || avgDistComplete == -1){
                         target = 6;
                    }
                    else {
                        target = (.2889226759 * avgDistComplete) + 4.927324066;
                    }
                }
                hood.setHoodTarget(target);
            }
        }
        else{
            hood.setHoodTarget(target);
        }
        

    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
         return Math.abs(target - hood.getHoodPosition()) < 0.5;
       // return true;
    }
}
