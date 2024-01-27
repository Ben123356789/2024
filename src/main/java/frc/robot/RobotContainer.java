package frc.robot;

import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.LimelightSubsystem;
import frc.robot.subsystems.PigeonSubsystem;

public class RobotContainer {
  //public Subsystem pigeonSubsystem;
  public LEDSubsystem ledSubsystem;
  public LimelightSubsystem limelight1;
  public PowerDistribution pdp;

  public RobotContainer() {
    configureBindings();
    //pigeonSubsystem = new PigeonSubsystem();
    limelight1 = new LimelightSubsystem();
    pdp = new PowerDistribution(1, ModuleType.kCTRE);
    ledSubsystem = new LEDSubsystem(limelight1, pdp);
  }

  private void configureBindings() {}

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}