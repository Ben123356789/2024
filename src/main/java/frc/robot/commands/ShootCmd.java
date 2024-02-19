package frc.robot.commands;

import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.ArmSubsystem.ArmPosition;
import frc.robot.subsystems.ShooterSubsystem.IntakeState;
import edu.wpi.first.wpilibj2.command.Command;

public class ShootCmd extends Command {
  private final ShooterSubsystem shooter;
  private final ArmSubsystem arm;
  public boolean isForwards = true;

  public ShootCmd(ArmSubsystem arm, ShooterSubsystem shooter, boolean isForwards) {
    this.arm = arm;
    this.shooter = shooter;
    this.isForwards = isForwards;
    // addRequirements(shooter);
  }

  @Override
  public void initialize() {
  }
  
  @Override
  public void execute() {
    if (shooter.okToShoot) {
      if(arm.target == ArmPosition.Amp2 || !isForwards){
        shooter.intakeState = IntakeState.ReverseIntake;
      } else{
        shooter.intakeState = IntakeState.ShootNow;
      }
    } else{
      shooter.intakeState = IntakeState.Idle;
    }
  }

  @Override
  public void end(boolean interrupted) {
    shooter.intakeState = IntakeState.Idle;
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
