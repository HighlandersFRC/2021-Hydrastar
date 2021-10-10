package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import frc.robot.Constants;
import frc.robot.commands.defaultCommands.ShooterDefault;

public class Shooter extends SubsytemBaseEnhanced {

    private final WPI_TalonFX shooterMaster = new WPI_TalonFX(9);
    private final WPI_TalonFX shooterFollower = new WPI_TalonFX(10);

    // private final WPI_TalonFX[] shooterMotors = {shooterMaster, shooterFollower};

    public void init() {
        shooterMaster.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 0);
        shooterMaster.configPeakOutputForward(0.7);
        shooterMaster.configClosedLoopPeakOutput(0, 0.7);
        shooterMaster.configPeakOutputReverse(-0.7);
        shooterMaster.configVoltageCompSaturation(11.7);
        shooterMaster.enableVoltageCompensation(true);
        shooterMaster.setSensorPhase(true);
        shooterMaster.selectProfileSlot(0, 0);
        // kF was previously 0.007
        shooterMaster.config_kF(0, 0.0095);
        shooterMaster.config_kP(0, 0.05);
        shooterMaster.config_kI(0, 0.000001);
        shooterMaster.config_kD(0, 0.12);
        setDefaultCommand(new ShooterDefault(this));
        shooterFollower.set(ControlMode.Follower, 9);
        shooterMaster.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 80, 0, 0));
    }

    public void autoInit() {}

    public void teleopInit() {}

    public double getShooterTics() {
        return (shooterMaster.getSelectedSensorPosition());
    }

    public void zeroShooterEncoder() {
        shooterMaster.setSelectedSensorPosition(0);
        shooterFollower.setSelectedSensorPosition(0);
    }

    public void setShooterPercent(double percent) {
        shooterMaster.set(ControlMode.PercentOutput, percent);
    }

    public void setShooterRPM(double rpm) {
        double adjustedRPM = rpm * 1.66;
        shooterMaster.set(ControlMode.Velocity, Constants.shooterRPMToUnitsPer100MS(adjustedRPM));
    }

    public double getShooterRPM() {
        return Constants.unitsPer100MsToRPM(shooterMaster.getSelectedSensorVelocity());
    }

    public void periodic() {}
}
