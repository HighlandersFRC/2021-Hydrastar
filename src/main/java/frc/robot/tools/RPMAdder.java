package frc.robot.tools;

public class RPMAdder {

    private double rpmAdder = 0;

    public RPMAdder() {
    }
    public void increaseRPM() {
        if(rpmAdder > 500) {
            rpmAdder = 500;
        }
        else {
            rpmAdder = rpmAdder + 10;
        }
    }

    public void decreaseRPM() {
        rpmAdder = rpmAdder - 10;
    }

    public double getRPMAdder() {
        return rpmAdder;
    }

}
