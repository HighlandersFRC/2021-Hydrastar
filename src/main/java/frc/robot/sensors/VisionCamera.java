// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.sensors;

import edu.wpi.first.wpilibj.SerialPort;

import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class VisionCamera {
    public String debugString;
    private JSONParser parser = new JSONParser();
    private SerialPort port;
    private double lastParseTime;
    private double distance;
    private double angle;
    private double ballDist;
    private double ballAngle;
    private AtomicBoolean shouldStop = new AtomicBoolean(false);
    private ConcurrentLinkedQueue<JSONObject> jsonResults = new ConcurrentLinkedQueue<JSONObject>();
    private ConcurrentLinkedQueue<String> stringResults = new ConcurrentLinkedQueue<String>();

    public VisionCamera(SerialPort jevois) {
        port = jevois;
        Runnable task =
                () -> {
                    String buffer = "";
                    while (!shouldStop.get()) {
                        // System.out.println(port.getBytesReceived());
                        // Gets bytes from serial port
                        // System.out.println("Port: " + port.readString());
                        if (port.getBytesReceived() > 0) {
                            String temp = port.readString();
                            buffer += temp;
                            stringResults.add(temp);
                            // System.out.println("Temp: " + temp);
                        }
                        debugString = buffer;
                        // Consume bytes until the '{'
                        if (buffer.length() > 0) {
                            int index = buffer.indexOf('{', 0);
                            if (index != -1 && index != 0) {
                                buffer = buffer.substring(index);
                            } else if (index == -1) {
                                buffer = "";
                            }
                        }
                        // Search for '}' and parse JSON
                        if (buffer.length() > 0) {
                            int index = buffer.indexOf('}', 0);
                            if (index != -1) {
                                String section = buffer.substring(0, index + 1);
                                try {

                                    JSONObject json = (JSONObject) parser.parse(section);
                                    if (jsonResults.size() > 16) {
                                        jsonResults.poll();
                                    }
                                    jsonResults.add(json);
                                } catch (ParseException e) {
                                    System.err.println(e);
                                }
                                buffer = buffer.substring(index + 1);
                            }
                        }
                    }
                };
        Thread thread = new Thread(task);
        thread.start();
    }

    public void updateVision() {
        // Drain existing objects out of queue and use most recent
        JSONObject json = jsonResults.poll();
        int jsonSize = jsonResults.size();
        for (int i = 0; i < jsonSize; i++) {
            JSONObject temp = jsonResults.poll();
            if (temp != null) {
                json = temp;
            }
        }

        // Use JSON results if present
        if (json != null) {
            Object tempDistance = json.get("Distance");
            if (tempDistance != null) {
                distance = (double) tempDistance;
            } else {
                distance = 0;
            }
            Object tempAngle = json.get("Angle");
            if (tempAngle != null) {
                angle = (double) tempAngle;
            } else {
                angle = 0;
            }
        }
    }

    public void updateBallVision() {
        // Drain existing objects out of queue and use most recent
        JSONObject json = jsonResults.poll();
        int jsonSize = jsonResults.size();
        for (int i = 0; i < jsonSize; i++) {
            JSONObject temp = jsonResults.poll();
            if (temp != null) {
                json = temp;
            }
        }

        // Use JSON results if present
        if (json != null) {
            Object tempDistance = json.get("BallDistance");
            if (tempDistance != null) {
                ballDist = (double) tempDistance;
            }
            Object tempAngle = json.get("BallAngle");
            if (tempAngle != null) {
                ballAngle = (double) tempAngle;
            }
        }
    }

    public double getDistance() {
        return distance;
    }

    public double getAngle() {
        return angle;
    }

    public double getLastParseTime() {
        return lastParseTime;
    }

    public double getCorrectedDistance(double a, double b, double c, double d) {
        updateVision();
        return a * Math.pow(getDistance(), 3)
                + b * Math.pow(getDistance(), 2)
                + c * getDistance()
                + d;
    }
}