// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.OI;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.LightRing;
import frc.robot.subsystems.Peripherals;
import frc.robot.tools.controlloops.PID;

public class ClimberUpPID extends CommandBase {

    private PID pid;
    private double kP = 0.0000025;
    private double kI = 0.0;
    private double kD = 0.0;
    private double normalClimberOutput = -0.3;
    private Climber climber;
    private double output;
    private double leftPercent = 0;
    private double rightPercent = 0;

    public ClimberUpPID(
            Climber climberSubsystem) {
        this.climber = climberSubsystem;
        // this.targetTics = targetTics;
        addRequirements(this.climber);
    }

    @Override
    public void initialize() {
        climber.resetClimbEncoders();
        pid = new PID(kP, kI, kD);
        pid.setSetPoint(0);
        pid.setMinOutput(-0.05);
        pid.setMaxOutput(0.05);
    }

    @Override
    public void execute() {
        pid.updatePID(climber.getLeftEncoderTics() - climber.getRightEncoderTics());
        output = pid.getResult();
        leftPercent = normalClimberOutput + output;
        rightPercent = normalClimberOutput - output;
        SmartDashboard.putNumber("Left Climber Power", normalClimberOutput + output);
        SmartDashboard.putNumber("Right Climber Power", normalClimberOutput - output);
        climber.setClimber(leftPercent, rightPercent);
    }

    @Override
    public void end(boolean interrupted) {
        climber.setClimber(0, 0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
