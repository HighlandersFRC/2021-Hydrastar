// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.subsystems.Hood;
import frc.robot.subsystems.MagIntake;
import frc.robot.subsystems.Shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class Fire extends SequentialCommandGroup {

    /** Creates a new Fire. */
    public Fire(MagIntake magIntake, Shooter shooter, Hood hood, double rpm, double hoodPosition) {
        addRequirements(magIntake, shooter, hood);
        // Add your commands in the addCommands() call, e.g.
        // addCommands(new FooCommand(), new BarCommand());
        addCommands(
                new ParallelCommandGroup(
                        new SpinShooter(shooter, rpm), new SetHoodPosition(hood, hoodPosition)),
                new EjectMagazine(magIntake));
    }
}
