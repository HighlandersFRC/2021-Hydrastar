// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.commands.defaultCommands.ClimberDefault;

public class Climber extends SubsystemBase {
    private final WPI_TalonFX leftClimberMotor = new WPI_TalonFX(6);
    private final WPI_TalonFX rightClimberMotor = new WPI_TalonFX(5);
    private final DoubleSolenoid leftClimberRatchet = new DoubleSolenoid(2, 3);
    private final DoubleSolenoid rightClimberRatchet = new DoubleSolenoid(4, 5);

    /** Creates a new Climber. */
    public Climber() {}

    public void init() {
        // leftClimberMotor.set(Conr, demand0, demand1Type, demand1);
        setDefaultCommand(new ClimberDefault(this));
    }

    public void setLeftClimber(double percent) {
        leftClimberMotor.set(ControlMode.PercentOutput, percent);
    }

    public void setRightClimber(double percent) {
        rightClimberMotor.set(ControlMode.PercentOutput, percent);
    }

    public void setClimber(double leftPercent, double rightPercent) {
        leftClimberMotor.set(ControlMode.PercentOutput, leftPercent);
        rightClimberMotor.set(ControlMode.PercentOutput, rightPercent);
    }

    public void setLeftClimberPiston(Value value) {
        leftClimberRatchet.set(value);
    }

    public void setRightClimberPiston(Value value) {
        rightClimberRatchet.set(value);
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }
}
