package frc.robot.commands;

import frc.robot.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

public class ShootCmd extends Command {
  private final ShooterSubsystem shooter;

  public ShootCmd(ShooterSubsystem subsystem) {
    shooter = subsystem;
    addRequirements(subsystem);
  }

  @Override
  public void initialize() {
    shooter.shootWhenReady = true;
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {
    shooter.shootWhenReady = false;
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
