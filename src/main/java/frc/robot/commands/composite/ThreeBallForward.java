package frc.robot.commands.composite;

import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.commands.DriveBackwards1;
import frc.robot.commands.Fire;
import frc.robot.commands.FireNoVision;
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
public class ThreeBallForward extends SequentialCommandGroup {
    /** Creates a new Autonomous. */
    public ThreeBallForward(
            Drive drive,
            Peripherals peripherals,
            MagIntake magIntake,
            Hood hood,
            Shooter shooter,
            LightRing lightRing,
            BallCount ballCount,
            Lights lights) {
        // Add your commands in the addCommands() call, e.g.
        // addCommands(new FooCommand(), new BarCommand());
        addCommands(
                new FireNoVision(
                        magIntake,
                        peripherals,
                        shooter,
                        hood,
                        lightRing,
                        drive,
                        2100,
                        24,
                        -9.0,
                        false,
                        55,
                        lights,
                        ballCount,
                        -1, 
                        2),
                new DriveBackwards1(drive, peripherals, 65, 0.7, true, 0, false));
                // new NavxTurn(peripherals, drive, 12),
                // // changed from 15 degrees on NavxTurn
                // new ParallelRaceGroup(
                //         new DriveBackwards1(drive, peripherals, 160, 0.25, false, 19), new SmartIntake(magIntake)),
                // new NavxTurn(peripherals, drive, 11),
                // new ParallelRaceGroup(
                //         new DriveBackwards1(drive, peripherals, 120, 0.6, true, 3), new SmartIntake(magIntake)),
                // new Fire(
                //         magIntake,
                //         peripherals,
                //         shooter,
                //         hood,
                //         lightRing,
                //         drive,
                //         2900,
                //         31,
                //         0,
                //         true,
                //         100,
                //         20));
    }
}