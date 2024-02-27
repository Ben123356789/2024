package frc.robot.commands;

import frc.robot.drive.CommandSwerveDrivetrain;
import edu.wpi.first.wpilibj2.command.Command;

public class ChangeDegreeHeadingCmd extends Command {
  double angle;
  private final CommandSwerveDrivetrain drivetrain;

  public ChangeDegreeHeadingCmd(CommandSwerveDrivetrain drivetrain, double angle) {
    this.angle = angle;
    this.drivetrain = drivetrain;
  }

  @Override
  public void initialize() {
    drivetrain.currentAngleTarget = angle;
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return true;
  }
}
