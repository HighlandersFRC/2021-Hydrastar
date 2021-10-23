// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.BallCount;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Hood;
import frc.robot.subsystems.LightRing;
import frc.robot.subsystems.Lights;
import frc.robot.subsystems.MagIntake;
import frc.robot.subsystems.Peripherals;
import frc.robot.subsystems.Shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class Fire extends SequentialCommandGroup {

    private MagIntake magIntake;
	private Peripherals peripherals;
	private Shooter shooter;
	private Hood hood;
	private LightRing lightRing;
	private Drive drive;
	private double rpm;
	private double hoodPosition;
	private double visionOffset;
	private boolean isBack;
	private int timeToEnd;
	private double distance;
	private Lights lights;
	private BallCount ballCount;
	private double lidarDistance;

	/** Creates a new Fire. */
    public Fire(
            MagIntake magIntake,
            Peripherals peripherals,
            Shooter shooter,
            Hood hood,
            LightRing lightRing,
            Drive drive,
            double rpm,
            double hoodPosition,
            double visionOffset,
            boolean isBack,
            int timeToEnd,
            double distance,
            Lights lights,
            BallCount ballCount,
            double lidarDistance,
            int shootingZone) {
                this.magIntake = magIntake;
				this.peripherals = peripherals;
				this.shooter = shooter;
				this.hood = hood;
				this.lightRing = lightRing;
				this.drive = drive;
				this.rpm = rpm;
				this.hoodPosition = hoodPosition;
				this.visionOffset = visionOffset;
				this.isBack = isBack;
				this.timeToEnd = timeToEnd;
				this.distance = distance;
				this.lights = lights;
				this.ballCount = ballCount;
                this.lidarDistance = lidarDistance;
                                this.ballCount = ballCount;
		addRequirements(magIntake, shooter, hood);
        // Add your commands in the addCommands() call, e.g.
        // addCommands(new FooCommand(), new BarCommand());
        addCommands(
                new ParallelCommandGroup(
                        new VisionAlignment(lightRing, drive, peripherals, visionOffset, isBack, distance, lights),
                        new SpinShooter(shooter, rpm),
                        new SetHoodPosition(hood, peripherals, hoodPosition, shootingZone)),
                new EjectMagazine(magIntake, drive, ballCount, timeToEnd),
                // new SetHoodPosition(hood, 0),
                new CancelMagazine(magIntake));
    }
}
