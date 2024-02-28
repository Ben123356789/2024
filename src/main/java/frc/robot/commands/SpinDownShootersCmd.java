package frc.robot.commands;

import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.ShooterSubsystem.IntakeState;
import frc.robot.subsystems.ShooterSubsystem.ShooterState;
import edu.wpi.first.wpilibj2.command.Command;

public class SpinDownShootersCmd extends Command {
  private final ShooterSubsystem shooter;
  public double speed;
  public boolean isAmp;

  public SpinDownShootersCmd(ShooterSubsystem shooter) {
    this.shooter = shooter;
    addRequirements(shooter);
  }

  @Override
  public void initialize() {
    shooter.intakeState = IntakeState.Idle;
    shooter.shooterV = 0;
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {
    shooter.shooterState = ShooterState.Idle;
    // shooter.shooterV = 0;
    shooter.spinDownShooters();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
