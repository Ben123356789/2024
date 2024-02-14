package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.FloorToShooterCmd;
import frc.robot.commands.SetArmPositionCmd;
import frc.robot.commands.SnapToDegreeCmd;
import frc.robot.input.AnalogTrigger;
import frc.robot.input.DPadButton;
import frc.robot.input.Keybind;
import frc.robot.input.AnalogTrigger.Axis;
import frc.robot.input.DPadButton.DPad;
import frc.robot.input.Keybind.Button;
import frc.robot.subsystems.ClimberSubsystem;
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
  public FlumperSubsystem flumper;
  public ShooterSubsystem shooter;
  public ArmSubsystem arm;
  private final CommandXboxController driverController = new CommandXboxController(Constants.DRIVER_CONTROLLER_PORT);
  private final CommandXboxController codriverController = new CommandXboxController(Constants.CODRIVER_CONTROLLER_PORT);

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

  // driver keybinds
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

  // codriver keybinds
  DPadButton climberUpKeybind;
  DPadButton climberDownKeybind;
  /**Amp & Trap*/
  Keybind altScoringKeybind;
  /**Intake & Source*/
  Keybind recievingKeybind;
  /**Speaker High & Low, Podium High & Low*/
  Keybind shootKeybind;

  Trigger modifyArm;
  Trigger fixedArm;


  // void makeShift(Trigger trigger, Command main, Command alt) {
  //   trigger.and(shiftTrigger.negate()).whileTrue(main);
  //   trigger.and(shiftTrigger).whileTrue(alt);
  // }

  private void configureBindings() {
    modifyArm = codriverController.leftBumper();
    fixedArm = codriverController.rightBumper();

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

    // map keys
    snapTo0Keybind = new Keybind(driverController.getHID(), Button.Y);
    snapTo180Keybind = new Keybind(driverController.getHID(), Button.A);
    snapToAmpKeybind = new Keybind(driverController.getHID(), Button.X);
    snapToSLKeybind = new DPadButton(driverController.getHID(), DPad.Left);
    snapToSRKeybind = new DPadButton(driverController.getHID(), DPad.Right);
    intakeKeybind = new AnalogTrigger(driverController.getHID(), Axis.RT, 0.5);
    snapToNoteKeybind = new Keybind(driverController.getHID(), Button.B);

    climberUpKeybind = new DPadButton(codriverController.getHID(), DPad.Up);
    altScoringKeybind = new Keybind(codriverController.getHID(), Button.X);
    recievingKeybind = new Keybind(codriverController.getHID(), Button.A);
    shootKeybind = new Keybind(codriverController.getHID(), Button.Y);

    // bind controls to commands
    snapTo0Keybind.trigger().whileTrue(new SnapToDegreeCmd(pigeon, 0));
    snapTo180Keybind.trigger().whileTrue(new SnapToDegreeCmd(pigeon, 180));
    snapToAmpKeybind.trigger().whileTrue(new SnapToDegreeCmd(pigeon, () -> DriverStation.getAlliance().orElse(Alliance.Red) == Alliance.Red ? 90 : -90));
    snapToSLKeybind.trigger().whileTrue(new SnapToDegreeCmd(pigeon, -45));
    snapToSRKeybind.trigger().whileTrue(new SnapToDegreeCmd(pigeon, 45));
    snapToNoteKeybind.trigger().whileTrue(new SnapToDegreeCmd(pigeon, limelight1));


    // co driver

    // climberUpKeybind.trigger().whileTrue(new ClimberCommand(climber, Climber.Up));
    // climberDownKeybind.trigger().whileTrue(new ClimberCommand(climber, Climber.Up));
    // you can ignore fixed position as it doesn't modify these
    altScoringKeybind.trigger().and(modifyArm).whileTrue(new SetArmPositionCmd(arm, ArmPosition.Trap));
    altScoringKeybind.trigger().and(modifyArm.negate()).whileTrue(new SetArmPositionCmd(arm, ArmPosition.Amp));
    recievingKeybind.trigger().and(modifyArm).whileTrue(new SetArmPositionCmd(arm, ArmPosition.Source));
    recievingKeybind.trigger().and(modifyArm.negate()).whileTrue(new FloorToShooterCmd(flumper, shooter, arm));

    // fixed does matter
    shootKeybind.trigger().and(modifyArm.negate()).and(fixedArm.negate()).whileTrue(new SetArmPositionCmd(arm, ArmPosition.SpeakerLow));
    shootKeybind.trigger().and(modifyArm).and(fixedArm.negate()).whileTrue(new SetArmPositionCmd(arm, ArmPosition.SpeakerHigh));

    // we dont have podium positions


    //intakeKeybind.trigger().whileTrue(new FloorToShooterCmd(flumper, shooter, arm));
    // leftKeybind.trigger().whileTrue(new KeybindTestCmd(led, 4));
    // rightKeybind.trigger().whileTrue(new KeybindTestCmd(led, 1));
    // leftKeybind.trigger().and(rightKeybind).whileTrue(new KeybindTestCmd(led, 3));
    // leftKeybind.trigger().and(rightKeybind).whileTrue(new KeybindTestCmd(led, 2));

  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}