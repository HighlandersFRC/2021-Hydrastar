// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.commands.defaultCommands.DriveDefault;
import frc.robot.commands.defaultCommands.MagIntakeDefault;

public class MagIntake extends SubsytemBaseEnhanced {

    //private final TalonFX lowMag = new TalonFX(Constants.BOTTOM_MAG_ID);
    // private final TalonFX middleMag = new TalonFX(Constants.MIDDLE_MAG_ID);
    // private final TalonFX highMag = new TalonFX(Constants.HIGH_MAG_ID);

    public MagIntake() {}

    public void init() {
        setDefaultCommand(new MagIntakeDefault(this));
    }

    @Override
    public void autoInit() {
    }

    @Override
    public void teleopInit() {
    }

    public void setLowMagPercent(double percent) {
        //lowMag.set(ControlMode.PercentOutput, percent);
    }

    public void setMidMagPercent(double percent) {
        //middleMag.set(ControlMode.PercentOutput, percent);
    }

    public void setHighMagPercent(double percent) {
        //highMag.set(ControlMode.PercentOutput, percent);
    }


    public void setMagPercent(double lowPercent, double midPercent, double highPercent) {
        setLowMagPercent(lowPercent);
        setMidMagPercent(midPercent);
        setHighMagPercent(highPercent);
    }

    @Override
    public void periodic() {}
}
