// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.subsystems;

import java.util.ResourceBundle.Control;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.commands.defaultCommands.DriveDefault;
import frc.robot.commands.defaultCommands.MagIntakeDefault;
import frc.robot.sensors.BeamBreaks;

public class MagIntake extends SubsytemBaseEnhanced {

    public enum BeamBreakID {
        ONE,
        TWO,
        THREE,
        FOUR,
        FIVE,
        SIX
    }

    private final CANSparkMax lowMag = new CANSparkMax(Constants.BOTTOM_MAG_ID, MotorType.kBrushless);
    private final WPI_VictorSPX middleMag = new WPI_VictorSPX(Constants.MIDDLE_MAG_ID);
    private final CANSparkMax highMag = new CANSparkMax(Constants.HIGH_MAG_ID, MotorType.kBrushless);

    private final WPI_TalonFX intakeMotor = new WPI_TalonFX(Constants.INTAKE_MOTOR_ID);

    private final DoubleSolenoid intakePiston = new DoubleSolenoid(0,1);

 
    // private final DigitalInput beamBreak6 = new DigitalInput(6);
    // private final DigitalInput beamBreak6 = new DigitalInput(Constants.BEAM_BREAK_6_ID);
    private BeamBreaks beamBreak;

    public MagIntake(BeamBreaks beamBreak) {
        this.beamBreak = beamBreak;
    }

    public void init() {
        setDefaultCommand(new MagIntakeDefault(this));
        lowMag.setSmartCurrentLimit(10);
        highMag.setSmartCurrentLimit(10);
    }

    @Override
    public void autoInit() {
    }

    @Override
    public void teleopInit() {
    }

    
    public BeamBreaks getBeamBreaks(){
        return this.beamBreak;
    }

    public void setIntakePercent(double percent) {
        intakeMotor.set(ControlMode.PercentOutput, percent);
    }

    public void setLowMagPercent(double percent) {
        lowMag.set(percent);
    }

    public void setMidMagPercent(double percent) {
        middleMag.set(ControlMode.PercentOutput, percent);
    }

    public void setHighMagPercent(double percent) {
        highMag.set(percent);
    }

    public void setIntakePistonDown() {
        intakePiston.set(Value.kForward);
    }

    public void setIntakePistonUp() {
        intakePiston.set(Value.kReverse);
    }

  
    public void putIntakeCurrentSmartDashboard() {
        SmartDashboard.putNumber("Intake Current", intakeMotor.getOutputCurrent());
    }

    public void setMagPercent(double lowPercent, double midPercent, double highPercent) {
        setLowMagPercent(lowPercent);
        setMidMagPercent(midPercent);
        setHighMagPercent(highPercent);
    }

    @Override
    public void periodic() {}
}
