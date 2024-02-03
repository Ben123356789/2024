package frc.robot;

import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.SetArmPositionCmd;
import frc.robot.subsystems.ArmPositionSubsystem;
import frc.robot.subsystems.ArmPositionSubsystem.ArmPosition;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.FlumperSubsystem;
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.LimelightSubsystem;
import frc.robot.subsystems.PigeonSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.ShoulderSubsystem;
import frc.robot.subsystems.WristSubsystem;

public class RobotContainer {
  public static ArmPositionSubsystem armPosition;
  public PigeonSubsystem pigeon;
  public LEDSubsystem led;
  public LimelightSubsystem limelight1;
  public ClimberSubsystem climber;
  public PowerDistribution pdp;
  public ElevatorSubsystem elevator;
  public FlumperSubsystem flumper;
  public ShooterSubsystem shooter;
  public ShoulderSubsystem shoulder;
  public WristSubsystem wrist;
  public ArmPositionSubsystem armposition;
  private final CommandXboxController controller = new CommandXboxController(Constants.DRIVER_CONTROLLER_PORT);

  public RobotContainer() {
    configureBindings();
    pigeon = new PigeonSubsystem();
    limelight1 = new LimelightSubsystem();
    pdp = new PowerDistribution(Constants.PDP_ID, ModuleType.kCTRE);
    led = new LEDSubsystem(limelight1, pdp);
    armposition = new ArmPositionSubsystem();
    // elevator = new ElevatorSubsystem();
    // flumper = new FlumperSubsystem();
    // shooter = new ShooterSubsystem();
    // climber = new ClimberSubsystem();
    // shoulder = new ShoulderSubsystem();
    // wrist = new WristSubsystem();
  }

  private void configureBindings() {
    // The following are examples of possible bindings
    controller.a().onTrue(new SetArmPositionCmd(armposition, ArmPosition.Stowed));
    controller.b().onTrue(new SetArmPositionCmd(armposition, ArmPosition.SpeakerHigh));
    controller.x().onTrue(new SetArmPositionCmd(armposition, ArmPosition.Trap));
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}