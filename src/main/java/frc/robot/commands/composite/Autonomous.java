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
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Hood;
import frc.robot.subsystems.LightRing;
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
            LightRing lightRing) {
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
                        2000,
                        24,
                        -9.0,
                        false,
                        -1),
                new DriveBackwards1(drive, 46, 0.7, false),
                new NavxTurn(peripherals, drive, 16),
                // changed from 19 degrees on NavxTurn
                new ParallelRaceGroup(
                        new DriveBackwards1(drive, 140, 0.27, false), new SmartIntake(magIntake)));
        // new ParallelRaceGroup(
        //         new DriveBackwards1(drive, 120, 0.8, true), new SmartIntake(magIntake)),
        // new Fire(
        //         magIntake,
        //         peripherals,
        //         shooter,
        //         hood,
        //         lightRing,
        //         drive,
        //         2900,
        //         31,
        //         -6.0,
        //         true,
        //         65));
    }
}
