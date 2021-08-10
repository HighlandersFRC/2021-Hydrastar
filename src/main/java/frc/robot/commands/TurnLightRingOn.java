// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.LightRing;

public class TurnLightRingOn extends CommandBase {
    private LightRing lightRing;

    public TurnLightRingOn(LightRing lightRing) {
        this.lightRing = lightRing;
        addRequirements(lightRing);
    }

    @Override
    public void initialize() {
        lightRing.turnVisionOn();
    }

    @Override
    public void execute() {}

    @Override
    public void end(final boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return false;
    }
}