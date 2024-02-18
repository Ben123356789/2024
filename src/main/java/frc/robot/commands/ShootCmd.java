package frc.robot.commands;

import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.ShooterSubsystem.IntakeState;
import edu.wpi.first.wpilibj2.command.Command;

public class ShootCmd extends Command {
  private final ShooterSubsystem shooter;

  public ShootCmd(ShooterSubsystem shooter) {
    this.shooter = shooter;
    //addRequirements(shooter);
  }

  @Override
  public void initialize() {
    shooter.intakeState = IntakeState.ShootNow;
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {
    shooter.intakeState = IntakeState.Idle;
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
