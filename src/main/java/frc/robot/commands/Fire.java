// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.subsystems.MagIntake;
import frc.robot.subsystems.Shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class Fire extends SequentialCommandGroup {
    private MagIntake magIntake;
    private Shooter shooter;
    /** Creates a new Fire. */
    public Fire(MagIntake magIntake, Shooter shooter) {
        addRequirements(magIntake, shooter);
        // Add your commands in the addCommands() call, e.g.
        // addCommands(new FooCommand(), new BarCommand());
        addCommands(new SpinShooter(shooter, 5000), new EjectMagazine(magIntake));
    }
}
