package frc.robot.commands;

import frc.robot.RobotContainer;
import frc.robot.subsystems.ArmPositionSubsystem;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.FlumperSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.ArmPositionSubsystem.ArmPosition;
import frc.robot.subsystems.ShooterSubsystem.ShootState;
import edu.wpi.first.wpilibj2.command.Command;

public class FloorToShooterCmd extends Command {
  private final FlumperSubsystem flumperSubsystem;
  private final ShooterSubsystem shooterSubsystem;
  private final ArmPositionSubsystem armPosition = RobotContainer.armPosition;

  public FloorToShooterCmd(FlumperSubsystem flumper, ShooterSubsystem shooter) {
    flumperSubsystem = flumper;
    shooterSubsystem = shooter;
    addRequirements(flumper, shooter);
  }

  @Override
  public void initialize() {
    new SetArmPositionCmd(armPosition, ArmPosition.Intake);
    if(armPosition.checkBoth()){
      flumperSubsystem.eat();
      shooterSubsystem.state = ShootState.Intake;
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
