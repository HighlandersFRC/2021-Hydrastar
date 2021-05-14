// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.tools.pathing;

import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Peripherals;

public class Odometry {

    private double left;
    private double currentLeft;
    private double dLeft;
    private double right;
    private double currentRight;
    private double dRight;
    private double startingTheta;
    private double theta;
    private double currentTheta;
    private double dCentre;
    private double x;
    private double currentX;
    private double y;
    private double currentY;
    private Drive drive;
    private Peripherals peripherals;
    private boolean inverted = false;

    public Odometry(Drive drive, Peripherals peripherals) {
        this.drive = drive;
        this.peripherals = peripherals;
    }

    public Odometry(Drive drive, Peripherals peripherals, boolean inverted) {
        this.drive = drive;
        this.peripherals = peripherals;
        this.inverted = inverted;
    }

    public void zero() {
        currentX = 0;
        currentY = 0;
        currentLeft = 0;
        currentRight = 0;
        left = 0;
        right = 0;
        x = 0;
        y = 0;
        theta = 0;
        dLeft = 0;
        dRight = 0;
        dCentre = 0;
    }

    public void setLeft(double left) {
        this.left = left;
    }

    public void setRight(double right) {
        this.right = right;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setTheta(double theta) {
        startingTheta = theta;
    }

    public void setInverted(boolean inverted) {
        this.inverted = inverted;
    }

    public double getX() {
        update();
        return x;
    }

    public double getY() {
        update();
        return y;
    }

    public double getTheta() {
        update();
        return theta;
    }

    public boolean getInverted() {
        return inverted;
    }

    public void update() {
        if (inverted) {
            currentLeft = drive.getLeftPosition();
            currentRight = drive.getRightPosition();
            currentTheta = startingTheta - peripherals.getNavxAngle();
            dLeft = currentLeft - left;
            dRight = currentRight - right;
            dCentre = (dLeft + dRight) / 2;
            currentX = x - dCentre * Math.cos(Math.toRadians(currentTheta));
            currentY = y - dCentre * Math.sin(Math.toRadians(currentTheta));
        } else {
            currentLeft = drive.getLeftPosition();
            currentRight = drive.getRightPosition();
            currentTheta = startingTheta + peripherals.getNavxAngle();
            dLeft = currentLeft - left;
            dRight = currentRight - right;
            dCentre = (dLeft + dRight) / 2;
            currentX = x + dCentre * Math.cos(Math.toRadians(currentTheta));
            currentY = y + dCentre * Math.sin(Math.toRadians(currentTheta));
        }
        x = currentX;
        y = currentY;
        theta = currentTheta;
        left = currentLeft;
        right = currentRight;
    }
}