package frc.robot.commands;

import frc.robot.ExtraMath;
import frc.robot.subsystems.LimelightSubsystem;
import frc.robot.subsystems.PigeonSubsystem;
import java.util.function.DoubleSupplier;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

public class SnapToDegreeCmd extends Command {
  private final PigeonSubsystem pigeonSubsystem;
  double yaw;
  DoubleSupplier target;
  double rotate;
  
  public SnapToDegreeCmd(PigeonSubsystem pigeon, LimelightSubsystem limelight){
    pigeonSubsystem = pigeon;
    try {
      this.target = ()->{
        try{
          return limelight.getTargets()[limelight.resultLargestAreaTarget()].tx;
        } catch(Exception e){return pigeonSubsystem.Y;}
      };
    } catch (Exception e) {
      this.target = ()->pigeonSubsystem.Y;
    }
  }

  public SnapToDegreeCmd(PigeonSubsystem pigeon, double target) {
    pigeonSubsystem = pigeon;
    this.target = ()->target;
  }
  public SnapToDegreeCmd(PigeonSubsystem pigeon, DoubleSupplier target) {
    pigeonSubsystem = pigeon;
    this.target = target;
  }


  @Override
  public void initialize() {}

  @Override
  public void execute() {
    yaw = pigeonSubsystem.Y;
    rotate = ExtraMath.degreeDistance(yaw, target.getAsDouble());
    SmartDashboard.putNumber("Snap Target", target.getAsDouble());
    SmartDashboard.putNumber("Snap Rotation Value", rotate);
    SmartDashboard.putBoolean("Is at Target Rotation?", rotate <=1 && rotate >= -1);
    // Have robot rotate "rotate" degrees
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return rotate <=1 && rotate >= -1;
  }
}
