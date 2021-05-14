// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.sensors;

import com.kauailabs.navx.frc.AHRS;

public class Navx {
    private double originalAngle;
    private double originalYaw;
    private AHRS imu;

    public Navx(AHRS navx) {
        imu = navx;
        originalAngle = imu.getAngle();
        originalYaw = imu.getYaw();
    }

    public Navx(AHRS navx, Double startAngle) {
        imu = navx;
        originalAngle = startAngle;
        originalYaw = imu.getYaw();
    }

    public double currentAngle() {
        return imu.getAngle() - originalAngle;
    }

    public double currentPitch() {
        return imu.getPitch();
    }

    public double currentRoll() {
        return imu.getRoll();
    }

    public double currentYaw() {
        return ((imu.getYaw()) - originalYaw);
    }

    public boolean isMoving() {
        return imu.isMoving();
    }

    public double currentAccelerometerX() {
        return imu.getWorldLinearAccelX();
    }

    public double currentAccelerometerY() {
        return imu.getWorldLinearAccelY();
    }

    public double currentAccelerometerZ() {
        return imu.getWorldLinearAccelZ();
    }

    public boolean isOn() {
        return imu.isConnected();
    }

    public boolean isMagCalibrated() {
        return imu.isMagnetometerCalibrated();
    }

    public boolean isAutoCalibrating() {
        return imu.isCalibrating();
    }

    public boolean isMagInerference() {
        return imu.isMagneticDisturbance();
    }

    public void softResetAngle() {
        originalAngle = imu.getAngle();
    }

    public void softResetYaw() {
        originalYaw = imu.getYaw();
    }
}