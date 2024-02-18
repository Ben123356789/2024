package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.FlumperSubsystem;
import frc.robot.subsystems.FlumperSubsystem.FlumperState;

public class FlumperCmd extends Command {
  private final FlumperSubsystem flumper;
  FlumperState state;

  public FlumperCmd(FlumperSubsystem flumper, FlumperState state) {
    this.flumper = flumper;
    this.state = state;
    addRequirements(flumper);
  }

  @Override
  public void initialize() {
    flumper.set(state);
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {
    flumper.set(FlumperState.Stop);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
