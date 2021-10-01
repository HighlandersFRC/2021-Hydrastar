// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.sensors;

import edu.wpi.first.wpilibj.I2C;

import frc.robot.commands.Wait;

public class LidarLite {
    /** Creates a new LidarLite. */
    private byte[] buffer;

    private I2C i2c;

    public LidarLite() {
        buffer = new byte[2];

        i2c = new I2C(I2C.Port.kOnboard, 0x62);
    }

    // black is gnd
    // yellow is scl
    // blue is sda
    // red is 3.3v

    // public int lidarDistance(boolean biasCorrection) {
    //     if (biasCorrection) {
    //         i2c.write(0x00, 0x04);
    //     } else {
    //         i2c.write(0x00, 0x03);
    //     }
    //     distanceArray = new byte[2];
    //     i2c.read(0x10, 2, distanceArray);
    //     int distance = (distanceArray[0] << 8) + distanceArray[1];
    //     return distance;
    // }

    private void update() {
        i2c.write(0x00, 0x04);
        i2c.write(0x00, 0x03);
        new Wait(0.005);
        i2c.read(0x10, 2, buffer);
        new Wait(0.005);
    }

    public double getDistanceIn() {
        update();
        return ((buffer[0] + buffer[1]) * 0.394);
    }

    // public int getDistanceIn() { // I made this function better. It used to be part of a PID
    // system. We didn't need a PID system.
    //  update();
    // return (int) getDistance(); // inches cuz Merica.
}
