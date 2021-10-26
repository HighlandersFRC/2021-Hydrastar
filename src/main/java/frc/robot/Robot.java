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
import frc.robot.sensors.BeamBreaks;
import frc.robot.sensors.LidarLite;
import frc.robot.subsystems.BallCount;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Hood;
import frc.robot.subsystems.LightRing;
import frc.robot.subsystems.Lights;
import frc.robot.subsystems.MagIntake;
import frc.robot.subsystems.Peripherals;
import frc.robot.subsystems.Shooter;
import frc.robot.tools.RPMAdder;
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
    private final Peripherals peripherals = new Peripherals();
    private final Shooter shooter = new Shooter();
    private final Hood hood = new Hood();
    private final LightRing lightRing = new LightRing();
    private final Climber climber = new Climber();
    private final BeamBreaks beamBreaks = new BeamBreaks();
    private final MagIntake magIntake = new MagIntake(beamBreaks);
    private final BallCount ballCount = new BallCount(beamBreaks);
    private final Lights lights = new Lights(ballCount);
    
 
    private UsbCamera camera;
    private UsbCamera camera2;
    private VideoSink server;
    private SequentialCommandGroup autoCommand;
    private final Odometry odometry = new Odometry(drive, peripherals);
    Autonomous autonomous = new Autonomous(drive, peripherals, magIntake, hood, shooter, lightRing, lights, ballCount);
    TwoBallSnatch twoBallSteal = new TwoBallSnatch(drive, peripherals, magIntake, hood, shooter, lightRing, lights, ballCount);
    // ThreeBallAuto threeBallAuto = new ThreeBallAuto(drive, peripherals, magIntake, hood, shooter, lightRing);
    private Command m_autonomousCommand;

    private Trajectory autoPart1;
    private Trajectory autoPart2;

    private PurePursuit autoPathPart1;
    private PurePursuit autoPathPart2;

    private RobotContainer m_robotContainer;

    private Boolean cameraBoolean = false;
    private Boolean ableToSwitch = false;

    private RPMAdder rpmAdder = new RPMAdder();

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

     
    }

    @Override
    public void robotPeriodic() {
     
        // SmartDashboard.putNumber("Back Ultra Sonic Distance", peripherals.getBackUltraSonicDist());
        // SmartDashboard.putNumber("Front UltraSonic Distance", peripherals.getUltraSonicDist());
        SmartDashboard.putBoolean("Top Switch", hood.getTopLimitSwitch());
        SmartDashboard.putBoolean("Bottom Switch", hood.getBottomLimitSwitch());
        drive.getDriveMeters();
        SmartDashboard.putBoolean("BeamBreak 1", magIntake.getBeamBreaks().getBeamBreak(1));
        SmartDashboard.putNumber("shooter rpm", shooter.getShooterRPM());
        SmartDashboard.putNumber("hood position", hood.getHoodPosition());
        SmartDashboard.putNumber("Camera angle", peripherals.getCamAngle());
        SmartDashboard.putNumber("UltraSonic Distance", peripherals.getUltraSonicDist());
        //SmartDashboard.putNumber("Lidar", peripherals.getLidarDistance());
        // System.out.println(peripherals.getLidarDistance());
        CommandScheduler.getInstance().run();

        // SmartDashboard.putNumber("Left CLimber Tics", climber.getLeftEncoderTics());
        // SmartDashboard.putNumber("Right Climber Tics", climber.getRightEncoderTics());
        // SmartDashboard.putNumber("POV", OI.getPOV());
        SmartDashboard.putNumber("NAVX", peripherals.getNavxAngle());
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
            // threeBallAuto.schedule();
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
        ballCount.teleopInit();
        OI.driverRT.whileHeld(new SmartIntake(magIntake, lights, ballCount));
        OI.driverLT.whileHeld(new Outtake(magIntake, ballCount));
        OI.driverB.whenPressed(
                new Fire(
                        magIntake,
                        peripherals,
                        shooter,
                        hood,
                        lightRing,
                        drive,
                        2000,
                        26,
                        -9.0,
                        false,
                        -1,
                        lights,
                        -1, 
                        2,
                        rpmAdder,
                        ballCount));

        OI.driverA.whenPressed(
                new Fire(
                        magIntake,
                        peripherals,
                        shooter,
                        hood,
                        lightRing,
                        drive,
                        1500,
                        7.95,
                        0.0,
                        false,
                        -1,
                        lights,
                        peripherals.getUltraSonicDist(), 
                        1,
                        rpmAdder,
                        ballCount));

        OI.driverX.whenPressed(
                new Fire(
                        magIntake,
                        peripherals,
                        shooter,
                        hood,
                        lightRing,
                        drive,
                        2900,
                        32,
                        3.0,
                        true,
                        -1,
                        lights,
                        -1, 
                        3,
                        rpmAdder,
                        ballCount));     
                        
        // OI.driverY.whenPressed(new BeamBreakTurn(peripherals, drive, 0));
        // OI.driverY.whenPressed(new BallTrackingPID(lightRing, drive, magIntake, peripherals, 0.0, false, -1, lights));

        OI.driverA.whenReleased(new SetHoodPosition(hood, peripherals, 0,  0));
        OI.driverA.whenReleased(new CancelMagazine(magIntake));

        // OI.driverX.whenPressed(new Fire(magIntake, shooter, hood, 2000, 18));
        OI.driverB.whenReleased(new SetHoodPosition(hood, peripherals,  0,  0));
        OI.driverB.whenReleased(new CancelMagazine(magIntake));

        OI.driverX.whenReleased(new SetHoodPosition(hood, peripherals, 0,  0));
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
    public void teleopPeriodic() {
        if(OI.getPOV() == 0) {
            rpmAdder.increaseRPM();
        }
        else if(OI.getPOV() == 180) {
            rpmAdder.decreaseRPM();
        }
        // SmartDashboard.putNumber("Adder", rpmAdder);
    }

    @Override
    public void testInit() {

        // Cancels all running commands at the start of test mode.
        CommandScheduler.getInstance().cancelAll();
    }

    /** This function is called periodically during test mode. */
    @Override
    public void testPeriodic() {}
}
