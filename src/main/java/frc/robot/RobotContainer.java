package frc.robot;

import com.ctre.phoenix6.mechanisms.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.FloorToShooterCmd;
import frc.robot.commands.FlumperCmd;
import frc.robot.commands.SetArmPositionCmd;
import frc.robot.commands.ShootCmd;
import frc.robot.commands.SnapToDegreeCmd;
import frc.robot.drive.CommandSwerveDrivetrain;
import frc.robot.drive.Telemetry;
import frc.robot.drive.TunerConstants;
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
import frc.robot.subsystems.FlumperSubsystem.FlumperState;
import frc.robot.subsystems.ArmSubsystem;

public class RobotContainer {
  // The following is swerve auto-generated code
  private double MaxSpeed = TunerConstants.kSpeedAt12VoltsMps; // kSpeedAt12VoltsMps desired top speed
  private double MaxAngularRate = 1.5 * Math.PI; // 3/4 of a rotation per second max angular velocity

  /* Setting up bindings for necessary control of the swerve drive platform */
  private final CommandSwerveDrivetrain drivetrain = TunerConstants.DriveTrain; // My drivetrain

  private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
      .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
      .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // I want field-centric
                                                               // driving in open loop
  private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
  private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();
  private final Telemetry logger = new Telemetry(MaxSpeed);

  public PigeonSubsystem pigeon;
  public LEDSubsystem led;
  public LimelightSubsystem limelight1;
  public ClimberSubsystem climber;
  public PowerDistribution pdp;
  public FlumperSubsystem flumper;
  public ShooterSubsystem shooter;
  public ArmSubsystem arm;
  private final CommandXboxController driverController = new CommandXboxController(Constants.DRIVER_CONTROLLER_PORT);
  private final CommandXboxController codriverController = new CommandXboxController(
      Constants.CODRIVER_CONTROLLER_PORT);

  public RobotContainer() {
    // pigeon = new PigeonSubsystem();
    // limelight1 = new LimelightSubsystem();
    // pdp = new PowerDistribution(Constants.PDP_ID, ModuleType.kCTRE);
    // led = new LEDSubsystem(limelight1, pdp);
    arm = new ArmSubsystem();
    flumper = new FlumperSubsystem();
    shooter = new ShooterSubsystem();
    // climber = new ClimberSubsystem();
    configureBindings();
  }

  Keybind armStowKeybind;

  // driver keybinds
  Keybind ledKeybind;
  AnalogTrigger leftKeybind;
  AnalogTrigger rightKeybind;
  AnalogTrigger intakeDriverKeybind;
  Keybind snapTo0Keybind;
  Keybind snapTo180Keybind;
  Keybind snapToAmpKeybind;
  /** SL = Stage Left */
  DPadButton snapToSLKeybind;
  /** SR = Stage Right */
  DPadButton snapToSRKeybind;
  Keybind snapToNoteKeybind;
  Keybind shootTestKeybind;

  // codriver keybinds
  DPadButton climberUpKeybind;
  DPadButton climberDownKeybind;
  /** Amp & Trap */
  Keybind altScoringKeybind;
  /** Intake & Source */
  Keybind recievingKeybind;
  /** Speaker High & Low, Podium High & Low */
  Keybind shootPositionKeybind;
  Keybind subwooferKeybind;
  AnalogTrigger shootTrigger;
  AnalogTrigger spitTrigger;
  // Modifiers
  Trigger modifyArm;
  Trigger fixedArm;

  private void configureBindings() {
    // drivetrain.setDefaultCommand( // Drivetrain will execute this command periodically
    //     drivetrain.applyRequest(() -> drive.withVelocityX(-driverController.getLeftY() * MaxSpeed) // Drive forward with
    //                                                                                        // negative Y (forward)
    //         .withVelocityY(-driverController.getLeftX() * MaxSpeed) // Drive left with negative X (left)
    //         .withRotationalRate(-driverController.getRightX() * MaxAngularRate) // Drive counterclockwise with negative X (left)
    //     ));

    // driverController.back().whileTrue(drivetrain.applyRequest(() -> brake));
    // driverController.b().whileTrue(drivetrain
    //     .applyRequest(() -> point.withModuleDirection(new Rotation2d(-driverController.getLeftY(), -driverController.getLeftX()))));

    // // reset the field-centric heading on left bumper press
    // driverController.leftBumper().onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldRelative()));

    // drivetrain.registerTelemetry(logger::telemeterize);

    modifyArm = codriverController.leftBumper();
    fixedArm = codriverController.rightBumper();

    // initialize keybinds - driver controller
    // snapTo0Keybind = new Keybind(driverController.getHID(), Button.Y);
    // snapTo180Keybind = new Keybind(driverController.getHID(), Button.A);
    // snapToAmpKeybind = new Keybind(driverController.getHID(), Button.X);
    // snapToSLKeybind = new DPadButton(driverController.getHID(), DPad.Left);
    // snapToSRKeybind = new DPadButton(driverController.getHID(), DPad.Right);
    // intakeDriverKeybind = new AnalogTrigger(driverController.getHID(), Axis.RT, 0.5);
    // snapToNoteKeybind = new Keybind(driverController.getHID(), Button.B);

    // initialize keybinds - codriver controller
    // climberUpKeybind = new DPadButton(codriverController.getHID(), DPad.Up);
    altScoringKeybind = new Keybind(codriverController.getHID(), Button.X);
    recievingKeybind = new Keybind(codriverController.getHID(), Button.A);
    // shootPositionKeybind = new Keybind(codriverController.getHID(), Button.Y);
    // subwooferKeybind = new Keybind(codriverController.getHID(), Button.B);
    // shootTrigger = new AnalogTrigger(codriverController.getHID(), Axis.RT, 0.5);
    spitTrigger = new AnalogTrigger(codriverController.getHID(), Axis.LT, 0.5);

    // // bind driver controls to commands
    // snapTo0Keybind.trigger().whileTrue(new SnapToDegreeCmd(pigeon, 0));
    // snapTo180Keybind.trigger().whileTrue(new SnapToDegreeCmd(pigeon, 180));
    // snapToAmpKeybind.trigger().whileTrue(
    // new SnapToDegreeCmd(pigeon, () -> DriverStation.getAlliance().orElse(Alliance.Red) == Alliance.Red ? 90 : -90));
    // snapToSLKeybind.trigger().whileTrue(new SnapToDegreeCmd(pigeon, -45));
    // snapToSRKeybind.trigger().whileTrue(new SnapToDegreeCmd(pigeon, 45));
    // //snapToNoteKeybind.trigger().whileTrue(new SnapToDegreeCmd(pigeon, limelight1));
    // intakeDriverKeybind.trigger().whileTrue(new FloorToShooterCmd(flumper, shooter, arm, true));

    // // bind codriver controls to commands
    // subwooferKeybind.trigger().whileTrue(new SetArmPositionCmd(arm, ArmPosition.SubWoofer));
    // // you can ignore fixed position as it doesn't modify these
    altScoringKeybind.trigger().and(modifyArm).whileTrue(new SetArmPositionCmd(arm, ArmPosition.Trap));
    altScoringKeybind.trigger().and(modifyArm.negate()).whileTrue(new SetArmPositionCmd(arm, ArmPosition.Amp));
    // recievingKeybind.trigger().and(modifyArm).whileTrue(new SetArmPositionCmd(arm, ArmPosition.Source));
    recievingKeybind.trigger()./*and(modifyArm.negate()).*/whileTrue(new FloorToShooterCmd(flumper, shooter, arm, true));

    // // y button!
    // shootPositionKeybind.trigger().and(modifyArm.negate()).and(fixedArm.negate())
    //     .whileTrue(new SetArmPositionCmd(arm, ArmPosition.SpeakerLow));
    // shootPositionKeybind.trigger().and(modifyArm).and(fixedArm.negate())
    //     .whileTrue(new SetArmPositionCmd(arm, ArmPosition.SpeakerHigh));
    // shootPositionKeybind.trigger().and(modifyArm.negate()).and(fixedArm)
    //     .whileTrue(new SetArmPositionCmd(arm, ArmPosition.PodiumLow));
    // shootPositionKeybind.trigger().and(modifyArm).and(fixedArm)
    //     .whileTrue(new SetArmPositionCmd(arm, ArmPosition.PodiumHigh));

    // non positional
    // shootTrigger.trigger().whileTrue(new ShootCmd(shooter));
     spitTrigger.trigger()./**and(modifyArm.negate()).*/whileTrue(new FlumperCmd(flumper, FlumperState.Spit));
    // spitTrigger.trigger().and(modifyArm).whileTrue(new FloorToShooterCmd(flumper, shooter, arm, false));

    // climberUpKeybind.trigger().whileTrue(new ClimberCommand(climber,
    // Climber.Up));
    // climberDownKeybind.trigger().whileTrue(new ClimberCommand(climber,
    // Climber.Down));

    // we dont have podium positions
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}