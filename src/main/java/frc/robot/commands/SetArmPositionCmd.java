package frc.robot.commands;

import frc.robot.subsystems.ArmPositionSubsystem;
import frc.robot.subsystems.ArmPositionSubsystem.ArmPosition;
import edu.wpi.first.wpilibj2.command.Command;

public class SetArmPositionCmd extends Command {
  private final ArmPositionSubsystem subsystem;
  private ArmPosition target;
  private double threshold = 0;

  public SetArmPositionCmd(ArmPositionSubsystem s, ArmPosition t) {
    subsystem = s;
    target = t;
    addRequirements(s);
  }

  public SetArmPositionCmd(ArmPositionSubsystem s, ArmPosition t, double th){
    subsystem = s;
    target = t;
    threshold = th;
  }

  @Override
  public void initialize() {
    if(threshold == 0){
      subsystem.setTarget(target);
    } else{
      subsystem.setTarget(target, threshold);
    }
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
