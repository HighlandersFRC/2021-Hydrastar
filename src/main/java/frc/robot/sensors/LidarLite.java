// // Copyright (c) FIRST and other WPILib contributors.
// // Open Source Software; you can modify and/or share it under the terms of
// // the WPILib BSD license file in the root directory of this project.

package frc.robot.sensors;

import edu.wpi.first.wpilibj.Counter;

public class LidarLite {
    private Counter counter;

    public LidarLite(Counter lidarCounter) {
        counter = lidarCounter;
        counter.setMaxPeriod(1.0);
        counter.setSemiPeriodMode(true);
        counter.reset();
    }

    public double getDistance() {
        double cmDist;
        double inDist;
        double countDist;
        if (counter.get() < 1) {
            return -1;
        }
        // countDist = counter.getPeriod() * 100000;// * 30000.0;
        
        cmDist = (counter.getPeriod() * 10000000.0 / 1000.0);
     
        return cmDist;
        
    }
}
