// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public abstract class SubsystemBaseEnhanced extends SubsystemBase {

    public abstract void init();

    public abstract void autoInit();

    public abstract void teleopInit();
}