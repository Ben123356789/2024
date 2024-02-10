package frc.robot.commands;

import frc.robot.subsystems.PigeonSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

public class SnapToDegreeCmd extends Command {
  private final PigeonSubsystem pigeonSubsystem;
  double yaw;
  int target;

  public SnapToDegreeCmd(PigeonSubsystem pigeon, int target) {
    pigeonSubsystem = pigeon;
    this.target = target;
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    yaw = pigeonSubsystem.Y;


  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }

  public double angleFinder(double c, double t){
  
  }
}
