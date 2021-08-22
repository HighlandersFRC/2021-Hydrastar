package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.Spark;
import frc.robot.subsystems.SubsystemBaseEnhanced;


public class Lights extends SubsystemBaseEnhanced {
  private Spark ledPWM;
  private double currentLedMode;

  public Lights() {
    ledPWM = new Spark(1);
    currentLedMode = LEDMode.BLUE.value;
  }

  public void setMode(LEDMode mode){
    currentLedMode = mode.value;
  }
  
  public enum LEDMode{
    BLUE(0.87), RED(0.61), GREEN(0.77), YELLOW(0.69), RAINBOW(-0.97), OFF(0.99), REDFLASHING(-0.11), RAINBOWSPARKLE(-0.89), WHITEFLASH(-0.81),
    ORANGE(0.63);

    public final double value;
    private LEDMode(double value){
        this.value = value;
    }
  }

  
  public void periodic() {
    ledPWM.set(currentLedMode);
  }
  public void init() {

  }

  public void autoInit() {

  }

  public void teleopInit() {

  }
}