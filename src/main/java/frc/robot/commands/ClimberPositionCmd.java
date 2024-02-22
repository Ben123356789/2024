package frc.robot.commands;

import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.subsystems.ArmSubsystem.ArmPosition;
import frc.robot.subsystems.ClimberSubsystem.ClimbState;
import edu.wpi.first.wpilibj2.command.Command;

public class ClimberPositionCmd extends Command {
  private final ClimberSubsystem climber;
  private final ArmSubsystem arm;
  public ClimbState state;
  public boolean isFinished = false;

  public ClimberPositionCmd(ClimberSubsystem climber, ArmSubsystem arm, ClimbState state) {
    this.climber = climber;
    this.arm = arm;
    this.state = state;
    addRequirements(climber, arm);
  }

  @Override
  public void initialize() {
    climber.setHeight(state.height());
    if(state == ClimbState.Max){
      arm.unsafeSetPosition(ArmPosition.ClimberUp);
    } else if(state == ClimbState.Mid){
      arm.unsafeSetPosition(ArmPosition.ClimberMid);
    } else if(state == ClimbState.Min){
      arm.unsafeSetPosition(ArmPosition.ClimberLow);
    }
    isFinished = true;
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return isFinished;
  }
}
