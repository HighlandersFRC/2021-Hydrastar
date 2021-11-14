// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.composite;

import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.BackDriveClimber;
import frc.robot.commands.DisengageClimberPiston;
import frc.robot.commands.PushClimber;
import frc.robot.commands.Wait;
import frc.robot.subsystems.Climber;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class PushClimberUp extends SequentialCommandGroup {
  /** Creates a new PushClimberUp. */
  public PushClimberUp(Climber climber) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(new ParallelRaceGroup(new DisengageClimberPiston(climber), new Wait(0.25)), new ParallelRaceGroup(new BackDriveClimber(climber), new Wait(0.5)),
    new PushClimber(climber, 180000));
  }
}
