package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.FloorIntakeSubsystem;
import frc.robot.subsystems.FloorIntakeSubsystem.FloorIntakeState;

public class FloorIntakeCmd extends Command {
  private final FloorIntakeSubsystem floorIntake;
  FloorIntakeState state;

  public FloorIntakeCmd(FloorIntakeSubsystem floorIntake, FloorIntakeState state) {
    this.floorIntake = floorIntake;
    this.state = state;
    addRequirements(floorIntake);
  }

  @Override
  public void initialize() {
    floorIntake.set(state);
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {
    floorIntake.set(FloorIntakeState.Stop);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
