package frc.robot.commands;

import frc.robot.input.Keybind;
import frc.robot.input.Keybind.Button;
import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

public class ChangeKeybindCmd extends Command {
  Keybind binding;
  Button newButton;
  boolean done = false;

  public ChangeKeybindCmd(Keybind binding, Button newButton) {
    this.binding = binding;
    this.newButton = newButton;
  }

  @Override
  public void initialize() {
    done = false;
  }

  @Override
  public void execute() {
    binding.button = newButton;
    done = true;
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return done;
  }
}
