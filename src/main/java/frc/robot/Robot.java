// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.commands.CancelMagazine;
import frc.robot.commands.DriveBackwards1;
import frc.robot.commands.Fire;
import frc.robot.commands.Outtake;
import frc.robot.commands.PurePursuit;
import frc.robot.commands.SetHoodPosition;
import frc.robot.commands.SmartIntake;
import frc.robot.commands.composite.Autonomous;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Hood;
import frc.robot.subsystems.LightRing;
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
    private SequentialCommandGroup autoCommand;
    private final Odometry odometry = new Odometry(drive, peripherals);
    Autonomous autonomous = new Autonomous(drive, peripherals, magIntake, hood, shooter, lightRing);
    private Command m_autonomousCommand;

    private Trajectory autoPart1;
    private Trajectory autoPart2;

    private PurePursuit autoPathPart1;
    private PurePursuit autoPathPart2;

    private RobotContainer m_robotContainer;

    /**
     * This function is run when the robot is first started up and should be used for any
     * initialization code.
     */
    @Override
    public void robotInit() {
        odometry.zero();
        drive.init();
        shooter.init();
        hood.init();
        shooter.zeroShooterEncoder();
        peripherals.init();
        lightRing.init();
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
    }

    /**
     * This function is called every robot packet, no matter the mode. Use this for items like
     * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
     *
     * <p>This runs after the mode specific periodic functions, but before LiveWindow and
     * SmartDashboard integrated updating.
     */
    @Override
    public void robotPeriodic() {
        SmartDashboard.putBoolean("Top Switch", hood.getTopLimitSwitch());
        SmartDashboard.putBoolean("Bottom Switch", hood.getBottomLimitSwitch());
        drive.getDriveMeters();
        magIntake.putBeamBreaksSmartDashboard();
        SmartDashboard.putNumber("navx value", peripherals.getNavxAngle());
        SmartDashboard.putNumber("shooter tics", shooter.getShooterTics());
        SmartDashboard.putNumber("shooter rpm", shooter.getShooterRPM());
        SmartDashboard.putNumber("hood position", hood.getHoodPosition());
        SmartDashboard.putNumber("Drive Encoder Tics", drive.getDriveMeters());
        SmartDashboard.putNumber("navx angle", peripherals.getNavxAngle());

        SmartDashboard.putNumber("Camera angle", peripherals.getCamAngle());

        // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
        // commands, running already-scheduled commands, removing finished or interrupted commands,
        // and running subsystem periodic() methods.  This must be called from the robot's periodic
        // block in order for anything in the Command-based framework to work.
        CommandScheduler.getInstance().run();
    }

    /** This function is called once each time the robot enters Disabled mode. */
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
        autonomous.schedule();

        // try {
        //     autoPathPart1 = new PurePursuit(drive, odometry, autoPart1, 1, 5.0, true);
        //     autoPathPart2 = new PurePursuit(drive, odometry, autoPart2, 18, 5.0, false);
        //     autoCommand =
        //             new SequentialCommandGroup(

        //                     new ParallelRaceGroup(autoPathPart1, new SmartIntake(magIntake)));
        //     //autoCommand.schedule();
        // } catch (Exception e) {
        //     System.out.println("Inside Catch");
        // }

        // m_autonomousCommand = m_robotContainer.getAutonomousCommand();

        // // schedule the autonomous command (example)
        // if (m_autonomousCommand != null) {
        //   m_autonomousCommand.schedule();
        // }
    }

    /** This function is called periodically during autonomous. */
    @Override
    public void autonomousPeriodic() {
        SmartDashboard.putNumber("Position X", odometry.getX());
    }

    @Override
    public void teleopInit() {
        drive.teleopInit();
        OI.driverRT.whileHeld(new SmartIntake(magIntake));
        OI.driverLT.whileHeld(new Outtake(magIntake));
        OI.driverLT.whenReleased(new CancelMagazine(magIntake));
        OI.driverA.whenPressed(
                new Fire(magIntake, peripherals, shooter, hood, lightRing, drive, 2000, 24, -9.0));

        // OI.driverA.whenPressed(new Fire(magIntake, shooter, hood, 3500, 19));

        // OI.driverA.whenPressed(new ParallelRaceGroup(new DriveBackwards1(drive, 10), new
        // SmartIntake(magIntake)));
        OI.driverB.whenPressed(
                new Fire(magIntake, peripherals, shooter, hood, lightRing, drive, 3000, 27, -2.0));
        OI.driverY.whenPressed(new DriveBackwards1(drive, 7));

        OI.driverA.whenReleased(new SetHoodPosition(hood, 0));
        OI.driverA.whenReleased(new CancelMagazine(magIntake));

        // OI.driverX.whenPressed(new Fire(magIntake, shooter, hood, 2000, 18));
        OI.driverB.whenReleased(new SetHoodPosition(hood, 0));
        OI.driverB.whenReleased(new CancelMagazine(magIntake));

        OI.driverX.whenReleased(new SetHoodPosition(hood, 0));
        OI.driverX.whenReleased(new CancelMagazine(magIntake));

        // OI.driverA.whileHeld(new Fire(magIntake, shooter));

        // OI.driverA.whenPressed(new SpinShooter(shooter, 1500));
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
