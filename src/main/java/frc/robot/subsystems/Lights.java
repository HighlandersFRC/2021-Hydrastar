package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Spark;

// import frc.robot.commands.defaultCommands.AutoLightsDefault;
import frc.robot.commands.defaultCommands.LightsDefault;
import frc.robot.sensors.BeamBreaks;

public class Lights extends SubsytemBaseEnhanced {
    private Spark ledPWM;
    private double currentLedMode;
    private BallCount ballCount;
    private BeamBreaks beamBreaks;

    private boolean beamBreakTemp = true;

    public Lights(BallCount ballCount) {
        ledPWM = new Spark(0);
        currentLedMode = LEDMode.BLUE.value;
        this.ballCount = ballCount;
    }
    // setMode method uses constants from the LEDMode enum
    public void setMode(LEDMode mode) {
        currentLedMode = mode.value;
    }

    public int getBallCount() {
        return ballCount.getBallCount();
      }
      

    public enum LEDMode {
        BLUE(0.87),
        RED(0.61),
        GREEN(0.77),
        YELLOW(0.67),
        RAINBOW(-0.97),
        ORANGE(0.65),
        PURPLE(0.91),
        AQUA(0.81),
        SKYBLUE(0.83),
        STROBERED(-0.11),
        RAINBOWWAVE(-0.45),
        OFF(0.99),
        ZEROBALLS(-0.31),
        ONEBALL(0.81),
        TWOBALLS(0.93),
        THREEBALLS(0.73),
        FOURBALLS(0.57),
        FIVEBALLS(0.67);

        public final double value;

        private LEDMode(double value) {
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
}