// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

import frc.robot.tools.extrabuttons.TriggerButton;

public class OI {
    public static XboxController driverController = new XboxController(0);
    public static XboxController operatorController = new XboxController(1);

    public static TriggerButton driverRT = new TriggerButton(driverController, 3);
    public static TriggerButton driverLT = new TriggerButton(driverController, 2);
    public static JoystickButton driverX = new JoystickButton(driverController, 3);
    public static JoystickButton driverY = new JoystickButton(driverController, 4);

    public static double getDriverLeftX() {
        return driverController.getX(Hand.kLeft);
    }

    public static double getDriverLeftY() {
        return driverController.getY(Hand.kLeft);
    }

    public static double getDriverRightX() {
        return driverController.getX(Hand.kRight);
    }

    public static double getDriverRightY() {
        return driverController.getY(Hand.kRight);
    }
}
