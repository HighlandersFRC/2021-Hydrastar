package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.commands.defaultCommands.LightsDefault;
import frc.robot.sensors.BeamBreaks;



public class Lights extends SubsystemBaseEnhanced {
  private Spark ledPWM;
  private double currentLedMode;
  private BallCount ballCount;
  private BeamBreaks beamBreaks;
  
  
  private boolean beamBreakTemp = true;

  public Lights(BeamBreaks beamBreaks, BallCount ballCount) {
    ledPWM = new Spark(0);
    currentLedMode = LEDMode.BLUE.value;
    this.beamBreaks = beamBreaks;
    this.ballCount = ballCount;
  }

  public void setMode(LEDMode mode){
    currentLedMode = mode.value;
    
  }

  public BallCount getBallCount() {
    return ballCount;
  }
  
  public enum LEDMode{
    BLUE(0.87), 
    RED(0.61), 
    GREEN(0.73), 
    YELLOW(0.67), 
    RAINBOW(-0.97), 
    OFF(0.99),
    STROBERED(-0.11),
    ZEROBALLS(-0.31),
    ONEBALL(0.61),
    TWOBALLS(0.93),
    THREEBALLS(0.73),
    FOURBALLS(0.57),
    FIVEBALLS(0.67)
    ;

    public final double value;
    private LEDMode(double value){
        this.value = value;
    }
  }

  

  @Override
  public void periodic() {
    ledPWM.set(currentLedMode);
    //System.out.println("Current led mode: " + currentLedMode);
  }

  public void tempTrue() {
    beamBreakTemp = true;
  }

  public void tempFalse() {
    beamBreakTemp = false;
  }

  public boolean getTemp() {
    return beamBreakTemp;
  }

  public BeamBreaks getBeamBreaks() {
    return this.beamBreaks;
  }

  @Override
  public void init() {
      // TODO Auto-generated method stub
    setDefaultCommand(new LightsDefault(this));
  }

  @Override
  public void autoInit() {
      // TODO Auto-generated method stub

  }

  @Override
  public void teleopInit() {
      // TODO Auto-generated method stub
    
  }

  // public int getBallCount() {
  //     return ballCount;
  // }
  // public void incrementBallCount() {
  //   ballCount++;
  //   if (ballCount > 5) {
  //     ballCount = 5;
  //   }
  //   //System.out.println("INCREMENT BALL COUNT");
  // }
  // public void resetBallCount() {
  //     ballCount = 0;
  //     //System.out.println("RESET BALL COUNT");
  // }

  // public void setBallCount(int n) {
  //   ballCount = n;
  //   //System.out.println("BALL COUNT SET TO " + n);
  // }
}