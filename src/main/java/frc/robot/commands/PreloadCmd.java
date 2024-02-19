package frc.robot.commands;

import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.ArmSubsystem.ArmPosition;
import frc.robot.subsystems.ShooterSubsystem.IntakeState;
import edu.wpi.first.wpilibj2.command.Command;

public class PreloadCmd extends Command {
  private final ShooterSubsystem shooter;
  private final ArmSubsystem arm;
  public boolean isFinished = false;
  ArmPosition target = ArmPosition.Intake;

  public PreloadCmd(ShooterSubsystem shooter, ArmSubsystem arm) {
    this.shooter = shooter;
    this.arm = arm;
    addRequirements(shooter, arm);
  }

  /**
   * Sets the target position of the arm to the Intake position.
   */
  @Override
  public void initialize() {
    arm.unsafeSetPosition(target);
    isFinished = false;
  }

  /**
   */
  @Override
  public void execute() {
    if (arm.leftShoulderMotor.atPosition() && arm.wristMotor.atPosition()) {
      isFinished = true;
    }
    shooter.intakeState = IntakeState.ResetTimer;
  }

  /**
   */
  @Override
  public void end(boolean interrupted) {
    shooter.intakeState = IntakeState.Preload;
    arm.unsafeSetPosition(ArmPosition.Stowed);
  }

  @Override
  public boolean isFinished() {
    return isFinished;
  }
}
