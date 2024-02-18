package frc.robot.commands;

import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.FlumperSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.ArmSubsystem.ArmPosition;
import frc.robot.subsystems.FlumperSubsystem.FlumperState;
import frc.robot.subsystems.ShooterSubsystem.IntakeState;
import edu.wpi.first.wpilibj2.command.Command;

public class FloorToShooterCmd extends Command {
  private final FlumperSubsystem flumper;
  private final ShooterSubsystem shooter;
  private final ArmSubsystem arm;
  boolean isForwards;
  ArmPosition target = ArmPosition.Intake;

  public FloorToShooterCmd(FlumperSubsystem flumper, ShooterSubsystem shooter, ArmSubsystem arm, boolean isForwards) {
    this.flumper = flumper;
    this.shooter = shooter;
    this.arm = arm;
    this.isForwards = isForwards;
    addRequirements(flumper, shooter, arm);
  }

  /**
   * Sets the target position of the arm to the Intake position.
   */
  @Override
  public void initialize() {
    arm.unsafeSetPosition(target);
  }

  /**
   * Once the robot reaches the Intake position, run the flumper and shooter intake motors to either take in or spit out a note.
   */
  @Override
  public void execute() {
    if (arm.leftShoulderMotor.atPosition() && arm.wristMotor.atPosition()) {
      if (isForwards) {
        flumper.set(FlumperState.Eat);
        shooter.intakeState = IntakeState.Intake;
      } else {
        flumper.set(FlumperState.Spit);
        shooter.intakeState = IntakeState.ReverseIntake;
      }
    }
  }

  /**
   * Once finished, halt flumper, pull back note into a prefire position, and return the arm to the stowed position.
   */
  @Override
  public void end(boolean interrupted) {
    flumper.set(FlumperState.Stop);
    shooter.intakeState = IntakeState.Preload;
    arm.unsafeSetPosition(ArmPosition.Stowed);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
