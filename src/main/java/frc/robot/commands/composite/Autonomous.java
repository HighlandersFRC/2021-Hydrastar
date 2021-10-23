// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.composite;

import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.commands.DriveBackwards1;
import frc.robot.commands.Fire;
import frc.robot.commands.NavxTurn;
import frc.robot.commands.SmartIntake;
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
public class Autonomous extends SequentialCommandGroup {
    /** Creates a new Autonomous. */
    public Autonomous(
            Drive drive,
            Peripherals peripherals,
            MagIntake magIntake,
            Hood hood,
            Shooter shooter,
            LightRing lightRing,
            Lights lights,
            BallCount ballCount) {
        // Add your commands in the addCommands() call, e.g.
        // addCommands(new FooCommand(), new BarCommand());
        addCommands(
                new Fire(
                        magIntake,
                        peripherals,
                        shooter,
                        hood,
                        lightRing,
                        drive,
                        2100,
                        23,
                        -9.0,
                        false,
                        47,
                        10,
                        lights,
                        ballCount,
                        -1,
                        2),
                new DriveBackwards1(drive, peripherals, 38, 0.7, false, 0),
                new NavxTurn(peripherals, drive, 9),
                // changed from 15 degrees on NavxTurn
                new ParallelRaceGroup(
                        new DriveBackwards1(drive, peripherals, 130, 0.25, false, 19), new SmartIntake(magIntake, lights, ballCount)),
                new NavxTurn(peripherals, drive, 12),
                new ParallelRaceGroup(
                        new DriveBackwards1(drive, peripherals, 90, 0.6, true, 3), new SmartIntake(magIntake, lights, ballCount)));
                // // new Fire(
                //         magIntake,
                //         peripherals,
                //         shooter,
                //         hood,
                //         lightRing,
                //         drive,
                //         2900,
                //         31,
                //         -3.0,
                //         true,
                //         -1,
                //         20,
                //         lights,
                //         -1));
    }
}
