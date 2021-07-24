package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.ControlType;

import frc.robot.commands.defaultCommands.HoodDefault;

public class Hood extends SubsytemBaseEnhanced {

    private double hoodTarget = 0.0;
    private double kf = 0.0;
    private double kp = 0;
    private double ki = 0;
    private double kd = 0;
    private float maxpoint = 22;
    private float minpoint = 0;
    private final CANSparkMax hoodMotor = new CANSparkMax(0, MotorType.kBrushless);
    private final CANPIDController pidController;
    private final CANEncoder hoodEncoder;

    public Hood() {
        pidController = hoodMotor.getPIDController();
        hoodEncoder = hoodMotor.getEncoder();
    }

    public void init() {
        hoodMotor.getEncoder().setPosition(0);
        hoodMotor.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, true);
        hoodMotor.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, true);
        hoodMotor.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, maxpoint);
        hoodMotor.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, minpoint);
        hoodMotor.enableVoltageCompensation(11.3);
        pidController.setFF(kf);
        pidController.setP(kp);
        pidController.setI(ki);
        pidController.setD(kd);
        pidController.setIZone(.2);
        pidController.setOutputRange(-0.5, 0.5);
        pidController.setSmartMotionMaxVelocity(200, 0);
        pidController.setSmartMotionMinOutputVelocity(-200, 0);
        pidController.setSmartMotionMaxAccel(200, 0);
        pidController.setSmartMotionAllowedClosedLoopError(.1, 0);
        hoodMotor.setInverted(true);
        hoodMotor.enableVoltageCompensation(11.3);
        hoodMotor.setInverted(true);
        setDefaultCommand(new HoodDefault(this));
    }

    public void setHoodPercent(double power) {
        hoodMotor.set(power);
    }

    public void setHoodTarget(double target) {
        hoodTarget = target;
        pidController.setReference(hoodTarget, ControlType.kPosition);
    }

    public double getHoodPosition() {
        return hoodEncoder.getPosition();
    }

    public void autoInit() {}

    public void teleopInit() {
        setDefaultCommand(new HoodDefault(this));
    }

    public void periodic() {}
}