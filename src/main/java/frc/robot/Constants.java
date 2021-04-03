// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final int LEFT_DRIVE_LEAD_ID = 4;
    public static final int RIGHT_DRIVE_LEAD_ID = 2;
    public static final int LEFT_DRIVE_FOLLOWER_ID = 3;
    public static final int RIGHT_DRIVE_FOLLOWER_ID = 1;
    public static final int BOTTOM_MAG_ID = 5;
    public static final int MIDDLE_MAG_ID = 6;
    public static final int HIGH_MAG_ID = 7;
    public static final int BEAM_BREAK_1_ID = 1;
    public static final int BEAM_BREAK_2_ID = 2;
    public static final int BEAM_BREAK_3_ID = 3;
    public static final int BEAM_BREAK_4_ID = 4;
    public static final int BEAM_BREAK_5_ID = 5;
    public static final int BEAM_BREAK_6_ID = 6;

    // Drive constants
    public static final double DRIVE_MAX_VOLTAGE = 11.7;
    public static final double DRIVE_CURRENT_PEAK_THRESHOLD = 40;
    public static final double DRIVE_CURRENT_PEAK_TIME = 10;
    public static final double DRIVE_MAX_CURRENT = 39;
    public static final double DRIVE_TICKS_PER_ROTATION = 28672;
    public static final double DRIVE_WHEEL_DIAMETER = 0.5;
    public static final double DRIVE_WHEEL_CIRCUMFERENCE = DRIVE_WHEEL_DIAMETER * Math.PI;
    public static final double DRIVE_WHEEL_BASE = 2.1;
 
    public static final SupplyCurrentLimitConfiguration currentLimitEnabled =
            new SupplyCurrentLimitConfiguration(
                    true, DRIVE_MAX_CURRENT, DRIVE_CURRENT_PEAK_THRESHOLD, DRIVE_CURRENT_PEAK_TIME);

                    public static double driveUnitsToFeet(double ticks) {
                        return ticks / DRIVE_TICKS_PER_ROTATION * DRIVE_WHEEL_CIRCUMFERENCE;
                    }
                
                    public static double driveFeetToUnits(double feet) {
                        return feet * DRIVE_TICKS_PER_ROTATION / DRIVE_WHEEL_CIRCUMFERENCE;
                    }
                
                    public static double driveUnitsPer100MSToFPS(double velocity) {
                        return driveUnitsToFeet(velocity) * 10;
                    }
                
                    public static double driveFPSToUnitsPer100MS(double fps) {
                        return driveFeetToUnits(fps) / 10;
                    }
}
