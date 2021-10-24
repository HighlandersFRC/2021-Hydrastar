// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.sensors;

import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.Constants;

public class BeamBreaks {
 
    private final DigitalInput beamBreak1 = new DigitalInput(Constants.BEAM_BREAK_1_ID);
   // private final DigitalInput beamBreak2 = new DigitalInput(Constants.BEAM_BREAK_2_ID);
    private final DigitalInput beamBreak3 = new DigitalInput(Constants.BEAM_BREAK_3_ID);
    private final DigitalInput beamBreak4 = new DigitalInput(Constants.BEAM_BREAK_4_ID);
    private final DigitalInput beamBreak5 = new DigitalInput(Constants.BEAM_BREAK_5_ID);
    private final DigitalInput beamBreak6 = new DigitalInput(Constants.BEAM_BREAK_6_ID);
  
    public boolean getBeamBreak(int n) {
        switch (n) {
            case 1:
            return beamBreak1.get();
            case 2:
            //return beamBreak2.get();
            case 3:
            return beamBreak3.get();
            case 4:
            return beamBreak4.get();
            case 5:
            return beamBreak5.get();
            case 6:
            return beamBreak6.get();
            default:
            return false;
        }
    }
}