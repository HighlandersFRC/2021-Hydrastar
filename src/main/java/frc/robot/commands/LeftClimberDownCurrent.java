package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;

public class LeftClimberDownCurrent extends CommandBase {
  /** Creates a new LeftClimberDownCurrent. */
  private Climber climber;

  private boolean rightDone = false;
  private boolean leftDone = false;

  public LeftClimberDownCurrent(Climber climber) {
    this.climber = climber;
    addRequirements(climber);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    leftDone = false;
    rightDone = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(climber.getLeftClimberCurrent() > 20) {
        leftDone = true;
    }
    if(climber.getRightClimberCurrent() > 20) {
        rightDone = true;
    }
    if(rightDone) {
        climber.setClimber(-0.3, 0);
    }
    else if(leftDone) {
        climber.setClimber(0, -0.3);
    }
    else {
        climber.setClimber(-0.3, -0.3);
    }
    SmartDashboard.putNumber("Output current", climber.getLeftClimberCurrent());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    climber.setClimber(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(rightDone && leftDone) {
      return true;
    }
    return false;
  }
}
