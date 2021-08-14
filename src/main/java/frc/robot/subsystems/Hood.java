package frc.robot.subsystems;

import com.revrobotics.CANDigitalInput;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.ControlType;

import frc.robot.commands.defaultCommands.HoodDefault;

public class Hood extends SubsytemBaseEnhanced {

    private double hoodTarget = 0.0;
    private double kf = 0.0;
    private double kp = 0.3;
    private double ki = 0;
    private double kd = 0.75;
    private float maxpoint = 32;
    private float minpoint = 0;
    private final CANSparkMax hoodMotor = new CANSparkMax(13, MotorType.kBrushless);
    private final CANDigitalInput bottomSwitch =
            hoodMotor.getReverseLimitSwitch(CANDigitalInput.LimitSwitchPolarity.kNormallyOpen);
    private final CANDigitalInput topSwitch =
            hoodMotor.getForwardLimitSwitch(CANDigitalInput.LimitSwitchPolarity.kNormallyOpen);
    private final CANPIDController pidController;
    private final CANEncoder hoodEncoder;

    //hood gear ratio is 24:52

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
        pidController.setOutputRange(-0.3, 0.3);
        pidController.setSmartMotionMaxVelocity(200, 0);
        pidController.setSmartMotionMinOutputVelocity(-200, 0);
        pidController.setSmartMotionMaxAccel(200, 0);
        pidController.setSmartMotionAllowedClosedLoopError(.1, 0);
        hoodMotor.setInverted(true);
        hoodMotor.enableVoltageCompensation(11.3);
        hoodMotor.setIdleMode(IdleMode.kCoast);
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

    public boolean getTopLimitSwitch() {
        return topSwitch.get();
    }

    public boolean getBottomLimitSwitch() {
        return bottomSwitch.get();
    }

    public void autoInit() {}

    public void teleopInit() {}

    public void periodic() {
        if (bottomSwitch.get()) {
            hoodEncoder.setPosition(0);
        } else if (topSwitch.get()) {
            hoodEncoder.setPosition(32);
        }
    }
}
