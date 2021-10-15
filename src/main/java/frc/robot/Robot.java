// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

// random comment

package frc.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.BringDownClimber;
import frc.robot.commands.CancelMagazine;
import frc.robot.commands.Fire;
import frc.robot.commands.LeftClimberDownCurrent;
import frc.robot.commands.Outtake;
import frc.robot.commands.PurePursuit;
import frc.robot.commands.PushClimber;
import frc.robot.commands.SetHoodPosition;
import frc.robot.commands.SmartIntake;
import frc.robot.commands.composite.Autonomous;
import frc.robot.commands.composite.PushClimberUp;
import frc.robot.commands.composite.ThreeBallAuto;
import frc.robot.commands.composite.TwoBallSnatch;
import frc.robot.sensors.LidarLite;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Hood;
import frc.robot.subsystems.LightRing;
import frc.robot.subsystems.Lights;
import frc.robot.subsystems.MagIntake;
import frc.robot.subsystems.Peripherals;
import frc.robot.subsystems.Shooter;
import frc.robot.tools.pathing.Odometry;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
    private final Drive drive = new Drive();
    private final MagIntake magIntake = new MagIntake();
    private final Peripherals peripherals = new Peripherals();
    private final Shooter shooter = new Shooter();
    private final Hood hood = new Hood();
    private final LightRing lightRing = new LightRing();
    private final Climber climber = new Climber();
    private final Lights lights = new Lights();
    private UsbCamera camera;
    // private UsbCamera camera2;
    private VideoSink server;
    private SequentialCommandGroup autoCommand;
    private final Odometry odometry = new Odometry(drive, peripherals);
    Autonomous autonomous = new Autonomous(drive, peripherals, magIntake, hood, shooter, lightRing, lights);
    TwoBallSnatch twoBallSteal = new TwoBallSnatch(drive, peripherals, magIntake, hood, shooter, lightRing, lights);
    ThreeBallAuto threeBallAuto = new ThreeBallAuto(drive, peripherals, magIntake, hood, shooter, lightRing, lights);
    // ThreeBallAuto threeBallAuto = new ThreeBallAuto(drive, peripherals, magIntake, hood, shooter, lightRing);
    private Command m_autonomousCommand;

    private Trajectory autoPart1;
    private Trajectory autoPart2;

    private PurePursuit autoPathPart1;
    private PurePursuit autoPathPart2;

    private RobotContainer m_robotContainer;

    private Boolean cameraBoolean = false;
    private Boolean ableToSwitch = false;

    @Override
    public void robotInit() {
        odometry.zero();
        drive.init();
        shooter.init();
        hood.init();
        shooter.zeroShooterEncoder();
        peripherals.init();
        lightRing.init();
        climber.init();
        magIntake.init();
        lights.init();
        try {
            autoPart1 =
                    TrajectoryUtil.fromPathweaverJson(
                            Paths.get("/home/lvuser/deploy/AutoPart1.json"));
            autoPart2 =
                    TrajectoryUtil.fromPathweaverJson(
                            Paths.get("/home/lvuser/deploy/AutoPart2.json"));
        } catch (IOException e) {
            System.out.println("didn't get trajectory");
            e.printStackTrace();
        }
        // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
        // autonomous chooser on the dashboard.
        m_robotContainer = new RobotContainer();
        camera = CameraServer.getInstance().startAutomaticCapture("VisionCamera1", "/dev/video0");
        camera.setResolution(160, 120);
        camera.setFPS(10);


        server = CameraServer.getInstance().addSwitchedCamera("driverVisionCameras");
        server.setSource(camera);
        Shuffleboard.update();
        SmartDashboard.updateValues();
    }

    @Override
    public void robotPeriodic() {
     
        
        SmartDashboard.putBoolean("Top Switch", hood.getTopLimitSwitch());
        SmartDashboard.putBoolean("Bottom Switch", hood.getBottomLimitSwitch());
        drive.getDriveMeters();
        magIntake.putBeamBreaksSmartDashboard();
         SmartDashboard.putNumber("Lidar Distance", peripherals.getLidarDistance());
         SmartDashboard.putNumber("Camera DISTANCE", peripherals.getCamDistance());
        SmartDashboard.putNumber("navx value", peripherals.getNavxAngle());
        SmartDashboard.putNumber("shooter tics", shooter.getShooterTics());
        SmartDashboard.putNumber("shooter rpm", shooter.getShooterRPM());
        SmartDashboard.putNumber("hood position", hood.getHoodPosition());
        SmartDashboard.putNumber("Drive Encoder Tics", drive.getDriveMeters());
        SmartDashboard.putNumber("navx angle", peripherals.getNavxAngle());

        SmartDashboard.putNumber("Camera angle", peripherals.getCamAngle());

        CommandScheduler.getInstance().run();

        SmartDashboard.putNumber("Left CLimber Tics", climber.getLeftEncoderTics());
        SmartDashboard.putNumber("Right Climber Tics", climber.getRightEncoderTics());

    }

    @Override
    public void disabledInit() {}

    @Override
    public void disabledPeriodic() {}

    /**
     * This autonomous runs the autonomous command selected by your {@link RobotContainer} class.
     */
    @Override
    public void autonomousInit() {
        odometry.zero();
        drive.zeroDriveEncoderTics();
        SmartDashboard.putNumber("Position Y", odometry.getY());
        SmartDashboard.putNumber("Position Theta", odometry.getTheta());
        if(OI.is6BallAuto()) {
            autonomous.schedule();
        }
        else if(OI.isThreeBallAuto()) {
            threeBallAuto.schedule();
        }
        else {
            autonomous.schedule();
        }
    }

    /** This function is called periodically during autonomous. */
    @Override
    public void autonomousPeriodic() {
        SmartDashboard.putNumber("Position X", odometry.getX());
    }

    @Override
    public void teleopInit() {
        drive.teleopInit();
        OI.driverRT.whileHeld(new SmartIntake(magIntake, lights));
        OI.driverLT.whileHeld(new Outtake(magIntake));
        OI.driverB.whenPressed(
                new Fire(
                        magIntake,
                        peripherals,
                        shooter,
                        hood,
                        lightRing,
                        drive,
                        2000,
                        24,
                        -9.0,
                        false,
                        -1,
                        10,
                        lights,
                         2));

        OI.driverA.whenPressed(
                new Fire(
                        magIntake,
                        peripherals,
                        shooter,
                        hood,
                        lightRing,
                        drive,
                        1800,
                        9.75,
                        0.0,
                        false,
                        -1,
                        0,
                        lights,
                        1));
// changed rpm from 2900 to 3100
        OI.driverX.whenPressed(
                new Fire(
                        magIntake,
                        peripherals,
                        shooter,
                        hood,
                        lightRing,
                        drive,
                        3750,
                        32,
                        3.0,
                        true,
                        -1,
                        20,
                        lights,
                        3));

        OI.driverA.whenReleased(new SetHoodPosition(hood, peripherals, 0, 0));
        OI.driverA.whenReleased(new CancelMagazine(magIntake));

        // OI.driverX.whenPressed(new Fire(magIntake, shooter, hood, 2000, 18));
        OI.driverB.whenReleased(new SetHoodPosition(hood, peripherals, 0, 0));
        OI.driverB.whenReleased(new CancelMagazine(magIntake));

        OI.driverX.whenReleased(new SetHoodPosition(hood, peripherals, 0, 0));
        OI.driverX.whenReleased(new CancelMagazine(magIntake));

        OI.operatorX.whenPressed(new PushClimberUp(climber));
        OI.operatorB.whenPressed(new LeftClimberDownCurrent(climber));

        OI.operatorA.whileHeld(new BringDownClimber(climber));

        if (m_autonomousCommand != null) {
            m_autonomousCommand.cancel();
        }
    }

    /** This function is called periodically during operator control. */
    @Override
    public void teleopPeriodic() {}

    @Override
    public void testInit() {

        // Cancels all running commands at the start of test mode.
        CommandScheduler.getInstance().cancelAll();
    }

    /** This function is called periodically during test mode. */
    @Override
    public void testPeriodic() {}
}
