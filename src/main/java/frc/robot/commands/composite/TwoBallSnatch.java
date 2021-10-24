// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.composite;

import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.commands.DriveBackwards1;
import frc.robot.commands.Fire;
import frc.robot.commands.NavxTurn;
import frc.robot.commands.NavxTurnOneSide;
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
public class TwoBallSnatch extends SequentialCommandGroup {
    /** Creates a new Autonomous. */
    public TwoBallSnatch(
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
                new ParallelRaceGroup(
                        new DriveBackwards1(drive, peripherals, 116, 0.35, false, 19, false), new SmartIntake(magIntake, lights, ballCount)),
                new ParallelRaceGroup(new NavxTurnOneSide(peripherals, drive, 45), new SmartIntake(magIntake, lights, ballCount)),
                new ParallelRaceGroup(
                        new DriveBackwards1(drive, peripherals, 7, 0.35, false, 45, false), new SmartIntake(magIntake, lights, ballCount)),
                new DriveBackwards1(drive, peripherals, 150, 0.7, true, 45, false));
    }
}
