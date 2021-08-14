package frc.robot.commands;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.Constants;
import frc.robot.subsystems.Drive;
import frc.robot.tools.math.Point;
import frc.robot.tools.math.Vector;
import frc.robot.tools.pathing.Odometry;

public class PurePursuit extends CommandBase {
    private Trajectory trajectory;
    private int closestSegment;
    private Point lookaheadPoint;
    private Point lastLookaheadPoint;
    private int startingNumber;
    private double dx;
    private double dy;
    private double distToPoint;
    private double minDistToPoint;
    private Point closestPoint;
    private double lookaheadDistance;
    private double desiredRobotCurvature;
    private Point startingPointOfLineSegment;
    private boolean firstLookaheadFound;
    private int startingNumberLA;
    private Vector lineSegVector;
    private Point endPointOfLineSegment;
    private Point robotPos;
    private Vector robotPosVector;
    private double lookaheadIndexT1;
    private double lookaheadIndexT2;
    private double partialPointIndex;
    private double lastPointIndex;
    private Vector distToEndVector;
    private double curveAdjustedVelocity;
    private double k;
    public double endThetaError;
    private Odometry odometry;
    private boolean inverted;
    private Drive drive;
    private Notifier pathNotifier;

    public PurePursuit(
            Drive drive,
            Odometry odometry,
            Trajectory trajectory,
            double lookAhead,
            double kValue,
            boolean direction) {
        this.drive = drive;
        this.trajectory = trajectory;
        this.lookaheadDistance = lookAhead;
        this.k = kValue;
        this.odometry = odometry;
        this.inverted = direction;
        addRequirements(this.drive);
    }

    private class PathRunnable implements Runnable {
        public void run() {
            algorithm();
        }
    }

    private void setWheelVelocities(double targetVelocity, double curvature) {
        double leftVelocity;
        double rightVelocity;
        double v;
        if (closestSegment < 3) {
            v = 0.6 + targetVelocity;
        } else {
            v = targetVelocity;
        }
        double c = curvature;
        c = -c;
        leftVelocity = v * (2 + (c * Constants.DRIVE_WHEEL_BASE)) / 2;
        rightVelocity = v * (2 - (c * Constants.DRIVE_WHEEL_BASE)) / 2;
        drive.setLeftSpeed(leftVelocity);
        drive.setRightSpeed(rightVelocity);
        // System.out.println(leftVelocity);
    }

    private void findRobotCurvature() {
        double a = -Math.tan(Math.toRadians(odometry.getTheta()));
        double b = 1;
        double c = Math.tan(Math.toRadians(odometry.getTheta())) * odometry.getX() - odometry.getY();
        double x = Math.abs(a * lookaheadPoint.getX() + b * lookaheadPoint.getY() + c)/Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
        double side =
                Math.signum(
                        Math.sin(Math.toRadians(odometry.getTheta()))
                                        * (lookaheadPoint.getX() - odometry.getX())
                                - Math.cos(Math.toRadians(odometry.getTheta()))
                                        * (lookaheadPoint.getY() - odometry.getY()));
        double curvature = ((2 * x) / Math.pow(lookaheadDistance, 2)) * side;
        desiredRobotCurvature = curvature;
    }

    public double findDiscriminant(double endX, double endY, double changeX, double changeY) {
        lineSegVector.setI(endX);
        lineSegVector.setJ(endY);
        robotPosVector.setI(changeX);
        robotPosVector.setJ(changeY);
        double a = lineSegVector.dot(lineSegVector);
        double b = 2 * robotPosVector.dot(lineSegVector);
        double c = robotPosVector.dot(robotPosVector) - lookaheadDistance * lookaheadDistance;
        double discriminant = b * b - 4 * a * c;
        return discriminant;
    }

    private void algorithm() {
        for (int i = startingNumber; i < trajectory.getStates().size() - 1; i++) {
            dx = trajectory.getStates().get(i).poseMeters.getTranslation().getX() - odometry.getX();
            dy = trajectory.getStates().get(i).poseMeters.getTranslation().getY() - odometry.getY();
            distToPoint = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
            // System.out.println(distToPoint);
            if (distToPoint < minDistToPoint) {
                minDistToPoint = distToPoint;
                closestSegment = i;
                closestPoint.setLocation(
                        trajectory.getStates().get(i).poseMeters.getTranslation().getX(),
                        trajectory.getStates().get(i).poseMeters.getTranslation().getY());
            }
        }
        startingNumber = closestSegment;
        minDistToPoint = 1000;
        firstLookaheadFound = false;
        for (int i = startingNumberLA; i < trajectory.getStates().size() - 1; i++) {
            startingPointOfLineSegment.setLocation(
                    trajectory.getStates().get(i).poseMeters.getTranslation().getX(),
                    trajectory.getStates().get(i).poseMeters.getTranslation().getY());
            endPointOfLineSegment.setLocation(
                    trajectory.getStates().get(i + 1).poseMeters.getTranslation().getX(),
                    trajectory.getStates().get(i + 1).poseMeters.getTranslation().getY());
            robotPos.setLocation(odometry.getX(), odometry.getY());
            double endPointX = endPointOfLineSegment.getX();
            double endPointY = endPointOfLineSegment.getY();
            double changeX = startingPointOfLineSegment.getX() - robotPos.getX();
            double changeY = startingPointOfLineSegment.getY() - robotPos.getY();
            double discriminant = findDiscriminant(endPointX, endPointY, changeX, changeY);
            if (discriminant < 0) {
                lookaheadPoint.setLocation(lastLookaheadPoint.getX(), lastLookaheadPoint.getY());
            } else {
                discriminant = Math.sqrt(discriminant);
                firstLookaheadFound = true;
            }
            if (firstLookaheadFound) {
                i = trajectory.getStates().size();
            } else if (!firstLookaheadFound && i == trajectory.getStates().size() - 1) {
                lookaheadPoint.setLocation(lastLookaheadPoint.getX(), lastLookaheadPoint.getY());
            }
        }
        lastLookaheadPoint.setLocation(lookaheadPoint.getX(), lookaheadPoint.getY());
        if (partialPointIndex > lastPointIndex) {
            lastPointIndex = partialPointIndex;
        }
        distToEndVector.setI(trajectory.getStates().get(trajectory.getStates().size() - 1).poseMeters.getTranslation().getX() - odometry.getX());
        distToEndVector.setJ(trajectory.getStates().get(trajectory.getStates().size() - 1).poseMeters.getTranslation().getY() - odometry.getY());
        startingNumberLA = (int) partialPointIndex;
        lastLookaheadPoint = lookaheadPoint;
        findRobotCurvature();
        // System.out.println(trajectory.getStates().get(closestSegment).velocityMetersPerSecond);
        curveAdjustedVelocity = Math.min(Math.abs(k/trajectory.getStates().get(closestSegment).curvatureRadPerMeter), trajectory.getStates().get(closestSegment).velocityMetersPerSecond);
        setWheelVelocities(curveAdjustedVelocity, desiredRobotCurvature);
    }

    @Override
    public void initialize() {
        odometry.setX(trajectory.getStates().get(0).poseMeters.getTranslation().getX());
        odometry.setY(trajectory.getStates().get(0).poseMeters.getTranslation().getY());
        odometry.setInverted(inverted);
        distToEndVector = new Vector(12, 12);
        lookaheadPoint = new Point(0, 0);
        closestPoint = new Point(0, 0);
        robotPos = new Point(0, 0);
        endPointOfLineSegment = new Point(0, 0);
        startingPointOfLineSegment = new Point(0, 0);
        lineSegVector = new Vector(0, 0);
        robotPosVector = new Vector(0, 0);
        lastLookaheadPoint = new Point(0, 0);
        closestPoint = new Point(0, 0);
        closestSegment = 0;
        minDistToPoint = 10000;
        startingNumber = 0;
        startingNumberLA = 0;
        lastPointIndex = 0;
        partialPointIndex = 0;
        lookaheadIndexT1 = 0;
        lookaheadIndexT2 = 0;
        desiredRobotCurvature = 0;
        minDistToPoint = 0;
        distToPoint = 0;
        dx = 0;
        dy = 0;
        curveAdjustedVelocity = 0;
        pathNotifier = new Notifier(new PathRunnable());
        pathNotifier.startPeriodic(0.02);
    }

    @Override
    public void execute() {
    }

    @Override
    public boolean isFinished() {
        return trajectory.getStates().size() - closestSegment < 3;
    }

    @Override
    public void end(boolean interrupted) {
        pathNotifier.stop();
        drive.setLeftSpeed(0);
        drive.setRightSpeed(0);
    }
}