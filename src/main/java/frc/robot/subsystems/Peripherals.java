// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.sensors.LidarLite;
import frc.robot.sensors.Navx;
import frc.robot.sensors.VisionCamera;

public class Peripherals extends SubsytemBaseEnhanced {
    private final AHRS ahrs = new AHRS(Port.kMXP);

    private final Navx navx = new Navx(ahrs);
   // private final Counter backUltraSonic = new Counter(8);
    private final Counter ultraSonic = new Counter(7);
    private VisionCamera visionCam;
    // private final Counter lidarPort = new Counter(7);

    // private final LidarLite lidar = new LidarLite(lidarPort);

    @Override
    public void init() {
        ultraSonic.setSemiPeriodMode(true);
        //backUltraSonic.setSemiPeriodMode(true);
        SerialPort jevois = null;
        try {
            jevois = new SerialPort(115200, SerialPort.Port.kMXP);
            System.out.println("Hola om");
            SmartDashboard.putBoolean("Got Camera", true);
        } catch (final Exception e) {
            SmartDashboard.putBoolean("Got Camera", false);
            System.out.println("CV cam's serial port failed to connect. Reason: " + e);
        }

        visionCam = new VisionCamera(jevois);
        try {
            visionCam.getAngle();
        } catch (final Exception e) {
            System.err.println("TestCamera could not get angle. Reason: " + e);
        }
        zeroNavx();
    }

    public Peripherals() {}

    // public double getLidarDistance() {
    //     return lidar.getDistance();
    // }

    // public double getBackUltraSonicDist() {
    //     // return 0.0;
    //    // return (backUltraSonic.getPeriod() * 33000) - 7;
    // }

    public double getUltraSonicDist() {
        return (ultraSonic.getPeriod() * 37000);
    }

    // public double getAverageUltraSonicDist() {
    //     return (((getBackUltraSonicDist()) + getUltraSonicDist())/2);
    // }
    
    public double getCamAngle() {
        visionCam.updateVision();
        return visionCam.getAngle();
    }

    public double getCamDistance() {
        visionCam.updateVision();
        return visionCam.getDistance();
    }

    public double getNavxAngle() {
        return navx.currentAngle();
    }

    public void zeroNavx() {
        navx.softResetAngle();
    }

    @Override
    public void autoInit() {
        // TODO Auto-generated method stub

    }

    @Override
    public void teleopInit() {
        // TODO Auto-generated method stub

    }
}
