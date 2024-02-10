package frc.robot.commands;

import frc.robot.subsystems.LEDSubsystem;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;

public class KeybindTestCmd extends Command {
  private final LEDSubsystem ledSubsystem;

  public int stripNum;

  public KeybindTestCmd(LEDSubsystem subsystem, int num) {
    ledSubsystem = subsystem;
    stripNum = num;
    // addRequirements(subsystem);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    switch(stripNum){
      case 1: ledSubsystem.setRed(); break;
      case 2: ledSubsystem.setBlue(); break;
      case 3: ledSubsystem.setYellow(); break;
      case 4: ledSubsystem.setGreen(); break;
    }
  }

  @Override
  public void end(boolean interrupted) {
    switch(stripNum){
      case 1: ledSubsystem.setColour(Color.kBlack, ledSubsystem.showingBuffer, ledSubsystem.quadStrip1); break;
      case 2: ledSubsystem.setColour(Color.kBlack, ledSubsystem.showingBuffer, ledSubsystem.quadStrip2); break;
      case 3: ledSubsystem.setColour(Color.kBlack, ledSubsystem.showingBuffer, ledSubsystem.quadStrip3); break;
      case 4: ledSubsystem.setColour(Color.kBlack, ledSubsystem.showingBuffer, ledSubsystem.quadStrip4); break;
    }
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
