package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.PigeonSubsystem;

public class RobotContainer {
  public Subsystem pigeonSubsystem;

  public RobotContainer() {
    configureBindings();
    pigeonSubsystem = new PigeonSubsystem();
  }

  private void configureBindings() {}

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}