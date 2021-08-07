// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.defaultCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.LightRing;

public class LightRingDefault extends CommandBase {

    private LightRing lightRing;

    public LightRingDefault(LightRing lightRing) {
        this.lightRing = lightRing;
        addRequirements(lightRing);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        lightRing.turnVisionOff();
    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return false;
    }
}