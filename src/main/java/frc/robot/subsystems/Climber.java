// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.commands.defaultCommands.ClimberDefault;

public class Climber extends SubsystemBase {
    private final WPI_TalonFX leftClimberMotor = new WPI_TalonFX(6);
    private final WPI_TalonFX rightClimberMotor = new WPI_TalonFX(5);
    private final DoubleSolenoid leftClimberRatchet = new DoubleSolenoid(2, 3);
    // private final DoubleSolenoid rightClimberRatchet = new DoubleSolenoid(4, 5);

    /** Creates a new Climber. */
    public Climber() {}

    public void init() {
        // leftClimberMotor.set(Conr, demand0, demand1Type, demand1);
        resetClimbEncoders();
        leftClimberMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 0);
        rightClimberMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 0);
        setDefaultCommand(new ClimberDefault(this));
    }

    public void setLeftClimber(double percent) {
        leftClimberMotor.set(ControlMode.PercentOutput, percent);
    }

    public void setRightClimber(double percent) {
        rightClimberMotor.set(ControlMode.PercentOutput, percent);
    }

    public void resetClimbEncoders() {
        leftClimberMotor.setSelectedSensorPosition(0);
        rightClimberMotor.setSelectedSensorPosition(0);
    }

    public void setClimber(double leftPercent, double rightPercent) {
        leftClimberMotor.set(ControlMode.PercentOutput, leftPercent);
        rightClimberMotor.set(ControlMode.PercentOutput, rightPercent);
    }

    public void putEncodersSmartDashboard() {
        SmartDashboard.putNumber("Left Climber Encoder", leftClimberMotor.getSelectedSensorPosition());
        SmartDashboard.putNumber("Right Climber Encoder", rightClimberMotor.getSelectedSensorPosition());
    }

    public void setClimberPiston(Value value) {
        leftClimberRatchet.set(value);
    }

    // public void setRightClimberPiston(Value value) {
    //     rightClimberRatchet.set(value);
    // }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }

	public double getLeftClimberCurrent() {
        return leftClimberMotor.getOutputCurrent();
    }
    
    public double getRightClimberCurrent() {
        return rightClimberMotor.getOutputCurrent();
    }

	public double getRightEncoderTics() {
        return rightClimberMotor.getSelectedSensorPosition();
    }

    public double getLeftEncoderTics() {
        return leftClimberMotor.getSelectedSensorPosition();
    }
}
