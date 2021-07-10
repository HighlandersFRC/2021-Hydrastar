package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import frc.robot.commands.defaultCommands.ShooterDefault;

public class Shooter extends SubsytemBaseEnhanced {

    private final WPI_TalonFX shooterMaster = new WPI_TalonFX(9);
    private final WPI_TalonFX shooterFollower = new WPI_TalonFX(10);

    private final WPI_TalonFX[] shooterMotors = {shooterMaster, shooterFollower};

    public void init() {
        setDefaultCommand(new ShooterDefault(this));
        shooterFollower.set(ControlMode.Follower, 9);
    }

    public void autoInit() {}

    public void teleopInit() {}

    public void setShooterPercent(Double percent) {
        shooterMaster.set(ControlMode.PercentOutput, percent);
    }

    public void periodic() {}
}
