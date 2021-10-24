// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.sensors.BeamBreaks;

/** Add your docs here. */
public class BallCount extends SubsystemBaseEnhanced {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private int ballCount;
  private BeamBreaks beamBreaks;

  public BallCount(BeamBreaks beamBreaks) {
    this.beamBreaks = beamBreaks;
  }

  public BeamBreaks getBeamBreaks() {
    return this.beamBreaks;
  }
  
  public int getBallCount() {
    return ballCount;
  }

  public void setBallCount(int n) {
    ballCount = n;
  }

  public void incrementBallCount() {
    ballCount ++;
  }

  public void decrementBallCount() {
    ballCount --;
    if (ballCount < 0) {
      ballCount = 0;
    }
  }

  public void addNum(int n) {
    ballCount +=n;
  }

  public void subtractNum(int n) {
    ballCount -= n;
    if (ballCount < 0) {
      ballCount = 0;
    }
    System.out.println("subtracted " + n + " balls");
  }

  public void resetBallCount() {
    ballCount = 0;
  }

  @Override
  public void init() {
    ballCount = 0;

  }

  @Override
  public void autoInit() {
    

  }

  @Override
  public void teleopInit() {
    ballCount = 0;
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("BALL COUNT", ballCount);
  }
}
