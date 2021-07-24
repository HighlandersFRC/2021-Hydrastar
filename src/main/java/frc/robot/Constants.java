// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;

public final class Constants {
    public static final int LEFT_DRIVE_LEAD_ID = 4;
    public static final int RIGHT_DRIVE_LEAD_ID = 2;
    public static final int LEFT_DRIVE_FOLLOWER_ID = 3;
    public static final int RIGHT_DRIVE_FOLLOWER_ID = 1;
    public static final int BOTTOM_MAG_ID = 5;
    public static final int MIDDLE_MAG_ID = 7;
    public static final int HIGH_MAG_ID = 6;
    public static final int INTAKE_MOTOR_ID = 8;
    public static final int BEAM_BREAK_1_ID = 0;
    public static final int BEAM_BREAK_2_ID = 3;
    public static final int BEAM_BREAK_3_ID = 2;
    public static final int BEAM_BREAK_4_ID = 1;
    public static final int BEAM_BREAK_5_ID = 4;
    public static final int BEAM_BREAK_6_ID = 6;

    // Drive constants
    public static final double DRIVE_MAX_VOLTAGE = 11.7;
    public static final double DRIVE_CURRENT_PEAK_THRESHOLD = 40;
    public static final double DRIVE_CURRENT_PEAK_TIME = 10;
    public static final double DRIVE_MAX_CURRENT = 39;
    public static final double DRIVE_TICKS_PER_ROTATION = 28087;
    public static final double DRIVE_WHEEL_DIAMETER = 0.15875;
    public static final double DRIVE_WHEEL_CIRCUMFERENCE = DRIVE_WHEEL_DIAMETER * Math.PI;
    public static final double DRIVE_WHEEL_BASE = 0.6604;
    public static final double SHOOTER_TICKS_PER_ROTATION = 1250;

    public static final SupplyCurrentLimitConfiguration currentLimitEnabled =
            new SupplyCurrentLimitConfiguration(
                    true, DRIVE_MAX_CURRENT, DRIVE_CURRENT_PEAK_THRESHOLD, DRIVE_CURRENT_PEAK_TIME);

    public static double driveUnitsToMeters(double ticks) {
        return ticks / DRIVE_TICKS_PER_ROTATION * DRIVE_WHEEL_CIRCUMFERENCE;
    }

    public static double driveMetersToUnits(double feet) {
        return feet * DRIVE_TICKS_PER_ROTATION / DRIVE_WHEEL_CIRCUMFERENCE;
    }

    public static double driveUnitsPer100MSToFPS(double velocity) {
        return driveUnitsToMeters(velocity) * 10;
    }

    public static double driveFPSToUnitsPer100MS(double fps) {
        return driveMetersToUnits(fps) / 10;
    }

    public static int shooterRPMToUnitsPer100MS(double rpm) {
        return (int) Math.round((rpm / 600) * Constants.SHOOTER_TICKS_PER_ROTATION);
    }

    public static double unitsPer100MsToRPM(double units) {
        return (units * 600) / Constants.SHOOTER_TICKS_PER_ROTATION;
    }
}
