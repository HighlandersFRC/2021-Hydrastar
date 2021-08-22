
package frc.robot.subsystems;


import frc.robot.commands.defaultCommands.LightsDefault;
import edu.wpi.first.wpilibj.Spark;


public class Lights extends SubsytemBaseEnhanced {
  private Spark ledPWM;
  private double currentLedMode;

  public Lights() {
    ledPWM = new Spark(1);
    currentLedMode = LEDMode.BLUE.value;
  }
  // setMode method uses constants from the LEDMode enum
  public void setMode(LEDMode mode){
    currentLedMode = mode.value;
    
  }
  
  public enum LEDMode{
    BLUE(0.87), RED(0.61), GREEN(0.73), YELLOW(0.67), RAINBOW(-0.97), OFF(0.99);

    public final double value;
    private LEDMode(double value){
        this.value = value;
    }
  }

  @Override
  public void periodic() {
    ledPWM.set(currentLedMode);
  }
  
  public void init() {
    setDefaultCommand(new LightsDefault(this));
  }

  public void autoInit() {

  }

  public void teleopInit() {
    setDefaultCommand(new LightsDefault(this));
  }
}

