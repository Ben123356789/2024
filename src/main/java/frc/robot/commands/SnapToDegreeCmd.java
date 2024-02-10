package frc.robot.commands;

import frc.robot.ExtraMath;
import frc.robot.subsystems.PigeonSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

public class SnapToDegreeCmd extends Command {
  private final PigeonSubsystem pigeonSubsystem;
  double yaw;
  int target;
  double rotate;

  public SnapToDegreeCmd(PigeonSubsystem pigeon, int target) {
    pigeonSubsystem = pigeon;
    this.target = target;
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    yaw = pigeonSubsystem.Y;
    rotate = ExtraMath.degreeDistance(yaw, target);
    SmartDashboard.putNumber("Snap Rotation Value", rotate);
    // Have robot rotate "rotate" degrees
    SmartDashboard.putBoolean("Is at Target Rotation?", rotate <=1 && rotate >= -1);

  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return rotate <=1 && rotate >= -1;
  }
}
