package frc.robot.tools;

public class RPMAdder {

    private double rpmAdder = 0;

    public RPMAdder() {
    }
    public void increaseRPM() {
        rpmAdder = rpmAdder + 1;
    }

    public void decreaseRPM() {
        rpmAdder = rpmAdder - 1;
    }

    public double getRPMAdder() {
        return rpmAdder;
    }

}
