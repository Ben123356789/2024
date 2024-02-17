package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.ArmSubsystem.ArmPosition;
import edu.wpi.first.wpilibj2.command.Command;

public class SetArmPositionCmd extends Command {
  ArmSubsystem arm;
  double targetShoulderPosition;
  double targetWristPosition;
  double elevatorPosition;
  ArmPosition target;
  double shoulderThreshold = Constants.SHOULDER_SAFE_ANGLE;
  double wristThreshold = Constants.WRIST_SAFE_ANGLE;

  boolean shoulderSetCheck = false;
  boolean wristSetCheck = false;
  boolean elevatorSetCheck;

  public SetArmPositionCmd(ArmSubsystem arm, ArmPosition p) {
    this.arm = arm;
    target = p;
    addRequirements(arm);
  }

  @Override
  public void initialize() {
    arm.unsafeSetPosition(target);
  }

  @Override
  public void execute() {
  }

  @Override
  public void end(boolean interrupted) {
    arm.unsafeSetPosition(ArmPosition.Stowed);
  }

  @Override
  public boolean isFinished() {
    return false;//arm.checkShoulderPosition() && arm.checkWristPosition() && arm.checkElevatorPosition();
  }
}
