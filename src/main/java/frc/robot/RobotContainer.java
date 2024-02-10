package frc.robot;

import java.security.Key;

import edu.wpi.first.hal.AllianceStationID;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.ChangeKeybindCmd;
import frc.robot.commands.FloorToShooterCmd;
import frc.robot.commands.KeybindTestCmd;
import frc.robot.commands.SetArmPositionCmd;
import frc.robot.commands.SnapToDegreeCmd;
import frc.robot.input.AnalogTrigger;
import frc.robot.input.DPadButton;
import frc.robot.input.Keybind;
import frc.robot.input.AnalogTrigger.Axis;
import frc.robot.input.DPadButton.DPad;
import frc.robot.input.Keybind.Button;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.FlumperSubsystem;
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.LimelightSubsystem;
import frc.robot.subsystems.PigeonSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.ArmSubsystem.ArmPosition;
import frc.robot.subsystems.ArmSubsystem;

public class RobotContainer {
  public PigeonSubsystem pigeon;
  public LEDSubsystem led;
  public LimelightSubsystem limelight1;
  public ClimberSubsystem climber;
  public PowerDistribution pdp;
  public ElevatorSubsystem elevator;
  public FlumperSubsystem flumper;
  public ShooterSubsystem shooter;
  public ArmSubsystem arm;
  private final CommandXboxController controller = new CommandXboxController(Constants.DRIVER_CONTROLLER_PORT);

  public RobotContainer() {
    pigeon = new PigeonSubsystem();
    limelight1 = new LimelightSubsystem();
    pdp = new PowerDistribution(Constants.PDP_ID, ModuleType.kCTRE);
    led = new LEDSubsystem(limelight1, pdp);
    // arm = new ArmSubsystem();
    // elevator = new ElevatorSubsystem();
    // flumper = new FlumperSubsystem();
    // shooter = new ShooterSubsystem();
    // climber = new ClimberSubsystem();
    configureBindings();
  }

  Keybind armStowKeybind;

  Keybind ledKeybind;
  AnalogTrigger leftKeybind;
  AnalogTrigger rightKeybind;
  AnalogTrigger intakeKeybind;
  Keybind snapTo0Keybind;
  Keybind snapTo180Keybind;
  Keybind snapToAmpKeybind;
  /**SL = Stage Left*/
  DPadButton snapToSLKeybind;
  /**SR = Stage Right*/
  DPadButton snapToSRKeybind;
  Keybind snapToNoteKeybind;

  private void configureBindings() {
    // armStowKeybind = new Keybind(controller.m_hid, Button.A);
    // // The following are examples of possible bindings
    // armStowKeybind.trigger().onTrue(new SetArmPositionCmd(arm, ArmPosition.Stowed));
    // controller.b().onTrue(new SetArmPositionCmd(arm, ArmPosition.SpeakerHigh));
    // controller.x().onTrue(new SetArmPositionCmd(arm, ArmPosition.Trap));

    // controller.a().whileTrue(new KeybindTestCmd(led, 4));
    // controller.b().whileTrue(new KeybindTestCmd(led, 1));
    // controller.x().whileTrue(new KeybindTestCmd(led, 2));
    // controller.y().whileTrue(new KeybindTestCmd(led, 3));

    // ledKeybind = new Keybind(controller.getHID(), Button.A);
    // ledKeybind.trigger().whileTrue(new KeybindTestCmd(led, 4));

    // controller.x().onTrue(new ChangeKeybindCmd(ledKeybind, Button.Y));
    // controller.b().onTrue(new ChangeKeybindCmd(ledKeybind, Button.A));

    // leftKeybind = new AnalogTrigger(controller.getHID(), Axis.LT, 0.5);
    // rightKeybind = new AnalogTrigger(controller.getHID(), Axis.RT, 0.5);
    snapTo0Keybind = new Keybind(controller.getHID(), Button.Y);
    snapTo180Keybind = new Keybind(controller.getHID(), Button.A);
    snapToAmpKeybind = new Keybind(controller.getHID(), Button.X);
    snapToSLKeybind = new DPadButton(controller.getHID(), DPad.Left);
    snapToSRKeybind = new DPadButton(controller.getHID(), DPad.Right);
    intakeKeybind = new AnalogTrigger(controller.getHID(), Axis.RT, 0.5);
    snapToNoteKeybind = new Keybind(controller.getHID(), Button.B);

    snapTo0Keybind.trigger().whileTrue(new SnapToDegreeCmd(pigeon, 0));
    snapTo180Keybind.trigger().whileTrue(new SnapToDegreeCmd(pigeon, 180));
    snapToAmpKeybind.trigger().whileTrue(new SnapToDegreeCmd(pigeon, () -> DriverStation.getAlliance().orElse(Alliance.Red) == Alliance.Red ? 90 : -90));
    snapToSLKeybind.trigger().whileTrue(new SnapToDegreeCmd(pigeon, -45));
    snapToSRKeybind.trigger().whileTrue(new SnapToDegreeCmd(pigeon, 45));
    snapToNoteKeybind.trigger().whileTrue(new SnapToDegreeCmd(pigeon, limelight1));
    //intakeKeybind.trigger().whileTrue(new FloorToShooterCmd(flumper, shooter, arm));
    // leftKeybind.trigger().whileTrue(new KeybindTestCmd(led, 4));
    // rightKeybind.trigger().whileTrue(new KeybindTestCmd(led, 1));
    // leftKeybind.trigger().and(rightKeybind).whileTrue(new KeybindTestCmd(led, 3));
    // leftKeybind.trigger().and(rightKeybind).whileTrue(new KeybindTestCmd(led, 2));



    // double dpadDegrees;
    // dpadDegrees = controller.getHID().getPOV(0);
    // if(dpadDegrees == 270){

    // }

  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}