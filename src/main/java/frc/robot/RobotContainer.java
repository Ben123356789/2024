package frc.robot;

import com.ctre.phoenix6.mechanisms.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.mechanisms.swerve.SwerveModule.SteerRequestType;
import com.ctre.phoenix6.mechanisms.swerve.utility.PhoenixPIDController;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;
import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.ClimberLockCmd;
import frc.robot.commands.ClimberPositionCmd;
import frc.robot.commands.FloorToShooterCmd;
import frc.robot.commands.PreloadCmd;
import frc.robot.commands.FloorIntakeCmd;
import frc.robot.commands.LowLimelightShotCmd;
import frc.robot.commands.SetArmPositionCmd;
import frc.robot.commands.ShootCmd;
import frc.robot.commands.SnapToDegreeCmd;
import frc.robot.commands.SpinUpShooterCmd;
import frc.robot.commands.IntakeFromSourceCmd;
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
// import frc.robot.subsystems.DashboardSubsystem;
import frc.robot.subsystems.DigitalIOSubsystem;
import frc.robot.subsystems.FloorIntakeSubsystem;
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.LimelightSubsystem;
import frc.robot.subsystems.PigeonSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.ArmSubsystem.ArmPosition;
import frc.robot.subsystems.ClimberSubsystem.ClimbState;
import frc.robot.subsystems.FloorIntakeSubsystem.FloorIntakeState;
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
    private final SwerveRequest.FieldCentricFacingAngle look = new SwerveRequest.FieldCentricFacingAngle()
            .withDeadband(MaxSpeed * 0.1)// .withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // I want field-centric
                                                                     // driving in open loop

    final Telemetry logger = new Telemetry(MaxSpeed);

    public PigeonSubsystem pigeon;
    public LEDSubsystem led;
    public LimelightSubsystem backLimelight;
    public LimelightSubsystem leftLimelight;
    public LimelightSubsystem rightLimelight;
    public ClimberSubsystem climber;
    public PowerDistribution pdp;
    public FloorIntakeSubsystem floorIntake;
    public ShooterSubsystem shooter;
    public ArmSubsystem arm;
    public DigitalIOSubsystem digitalio;
    // public DashboardSubsystem dashboard;
    public final CommandXboxController driverController = new CommandXboxController(Constants.DRIVER_CONTROLLER_PORT);
    private final CommandXboxController codriverController = new CommandXboxController(
            Constants.CODRIVER_CONTROLLER_PORT);

    private final SendableChooser<Command> autoChooser;

    public RobotContainer() {
        pigeon = new PigeonSubsystem();
        // pdp = new PowerDistribution(Constants.PDP_ID, ModuleType.kCTRE);
        // led = new LEDSubsystem(limelight1, pdp);
        backLimelight = new LimelightSubsystem("limelight-back");
        leftLimelight = new LimelightSubsystem("limelight-left");
        rightLimelight = new LimelightSubsystem("limelight-right");
        arm = new ArmSubsystem();
        floorIntake = new FloorIntakeSubsystem();
        shooter = new ShooterSubsystem();
        climber = new ClimberSubsystem();
        digitalio = new DigitalIOSubsystem(arm, shooter, floorIntake, climber);
        // dashboard = new DashboardSubsystem(arm, shooter, climber, floorIntake);

        look.HeadingController = new PhoenixPIDController(3.699, 0.00, 0.2);
        look.HeadingController.enableContinuousInput(-Math.PI, Math.PI);
        drivetrain.seedFieldRelative();

        NamedCommands.registerCommand("limelightShot", new LowLimelightShotCmd(arm, shooter, backLimelight, logger));
        NamedCommands.registerCommand("intake", new FloorToShooterCmd(floorIntake, shooter, arm, true));
        NamedCommands.registerCommand("shoot", new ShootCmd(arm, shooter, true));
        NamedCommands.registerCommand("preload", new PreloadCmd(shooter, arm));

        configureBindings();
        
        autoChooser = AutoBuilder.buildAutoChooser();

        // autoChooser = AutoBuilder.buildAutoChooser("My Default Auto");

        SmartDashboard.putData("Auto Chooser", autoChooser);
    }

    /** Right Trigger */
    AnalogTrigger intakeDriverKeybind;

    /** Left Bumper */
    Keybind resetFieldCentricKeybind;

    /** Dpad Up */
    Keybind snapTo0Keybind;
    /** Dpad Left */
    Keybind snapTo90Keybind;
    /** Dpad Down */
    Keybind snapTo180Keybind;
    /** Dpad Right */
    Keybind snapTo270Keybind;

    /** B Button */
    Keybind snapToNoteKeybind;
    double angleFromNote = 0;

    // codriver keybinds
    /** Dpad Up */
    DPadButton climberMaxKeybind;
    /** Dpad Down */
    DPadButton climberMinKeybind;
    /** Dpad Left */
    DPadButton climberMidKeybind;
    /** Dpad Right */
    DPadButton climberToggleLockKeybind;

    /** Amp & Trap - X */
    Keybind altScoringKeybind;

    /** Intake & Source - A */
    Keybind recievingKeybind;

    /** Speaker High & Low, Podium High & Low - Y */
    Keybind shootPositionKeybind;

    /** B Button */
    Keybind subwooferKeybind;

    /** Right Trigger */
    AnalogTrigger shootTrigger;

    /** Left Trigger */
    AnalogTrigger spitTrigger;

    // Modifiers
    /** Left Bumper */
    Trigger modifyArm;
    /** Right Bumper */
    Trigger fixedArm;

    Keybind dubiousSpit;

    AnalogTrigger secretShoot;
    Keybind secretAim;

    private void configureBindings() {
        modifyArm = codriverController.leftBumper();
        fixedArm = codriverController.rightBumper();

        // initialize keybinds - driver controller
        snapTo0Keybind = new Keybind(driverController.getHID(), Button.Y);
        snapTo90Keybind = new Keybind(driverController.getHID(), Button.X);
        snapTo180Keybind = new Keybind(driverController.getHID(), Button.A);
        snapTo270Keybind = new Keybind(driverController.getHID(), Button.B);

        // snapToNoteKeybind = new Keybind(driverController.getHID(),
        // Button.RightBumper);

        resetFieldCentricKeybind = new Keybind(driverController.getHID(), Button.LeftBumper);
        intakeDriverKeybind = new AnalogTrigger(driverController.getHID(), Axis.RT, 0.5);
        secretShoot = new AnalogTrigger(driverController.getHID(), Axis.LT, 0.5);
        // secretAim = new Keybind(driverController.getHID(), Button.RightBumper);
        dubiousSpit = new Keybind(driverController.getHID(), Button.RightBumper);

        // initialize keybinds - codriver controller
        climberMaxKeybind = new DPadButton(codriverController.getHID(), DPad.Up);
        climberMinKeybind = new DPadButton(codriverController.getHID(), DPad.Down);
        climberMidKeybind = new DPadButton(codriverController.getHID(), DPad.Left);
        climberToggleLockKeybind = new DPadButton(codriverController.getHID(), DPad.Right);
        altScoringKeybind = new Keybind(codriverController.getHID(), Button.X);
        recievingKeybind = new Keybind(codriverController.getHID(), Button.A);
        shootPositionKeybind = new Keybind(codriverController.getHID(), Button.Y);
        subwooferKeybind = new Keybind(codriverController.getHID(), Button.B);
        shootTrigger = new AnalogTrigger(codriverController.getHID(), Axis.RT, 0.5);
        spitTrigger = new AnalogTrigger(codriverController.getHID(), Axis.LT, 0.5);
        // bind driver controls to commands
        drivetrain.setDefaultCommand( // Drivetrain will execute this command periodically
                drivetrain.applyRequest(() -> {
                    double rate;
                    if (backLimelight.limelightRotation) {
                        rate = -0.026 * MaxAngularRate * backLimelight.limelightRotationMagnitude;
                    } else if (pigeon.rotateToDegree) {
                        rate = MaxAngularRate * pigeon.magnitudeToAngle;
                    } else {
                        rate = -driverController.getRightX() * MaxAngularRate;
                    }
                    return drive
                            .withVelocityX(-driverController.getLeftY() * MaxSpeed) // Drive forward with// negative
                                                                                    // Y (forward)
                            .withVelocityY(-driverController.getLeftX() * MaxSpeed) // Drive left with negative X (left)
                            .withRotationalRate(rate); // Drive counterclockwise with negative X (left)
                }));

        snapTo0Keybind.trigger().whileTrue(drivetrain.applyRequest(() -> {
            return look
                    .withTargetDirection(Rotation2d.fromDegrees(0))
                    .withVelocityX(-driverController.getLeftY() * MaxSpeed) // Drive forward with negative Y (forward)
                    .withVelocityY(-driverController.getLeftX() * MaxSpeed); // Drive left with negative X (left)
        }));
        snapTo90Keybind.trigger().whileTrue(drivetrain.applyRequest(() -> {
            return look
                    .withTargetDirection(Rotation2d.fromDegrees(90))
                    .withVelocityX(-driverController.getLeftY() * MaxSpeed) // Drive forward with negative Y (forward)
                    .withVelocityY(-driverController.getLeftX() * MaxSpeed); // Drive left with negative X (left)
        }));
        snapTo180Keybind.trigger().whileTrue(drivetrain.applyRequest(() -> {
            return look
                    .withTargetDirection(Rotation2d.fromDegrees(180))
                    .withVelocityX(-driverController.getLeftY() * MaxSpeed) // Drive forward with negative Y (forward)
                    .withVelocityY(-driverController.getLeftX() * MaxSpeed); // Drive left with negative X (left)
        }));
        snapTo270Keybind.trigger().whileTrue(drivetrain.applyRequest(() -> {
            return look
                    .withTargetDirection(Rotation2d.fromDegrees(-90))
                    .withVelocityX(-driverController.getLeftY() * MaxSpeed) // Drive forward with negative Y (forward)
                    .withVelocityY(-driverController.getLeftX() * MaxSpeed); // Drive left with negative X (left)
        }));

        // snapToNoteKeybind.trigger().whileTrue(drivetrain.applyRequest(() -> {

        // if(ang)
        // return look
        // .withTargetDirection(Rotation2d.fromDegrees(angleFromNote))
        // .withVelocityX(-driverController.getLeftY() * MaxSpeed) // Drive forward with
        // negative Y (forward)
        // .withVelocityY(-driverController.getLeftX() * MaxSpeed); // Drive left with
        // negative X (left)
        // }));

        // not sure how brake will be toggled...
        driverController.back().whileTrue(drivetrain.applyRequest(() -> brake));

        // reset the field-centric heading on left bumper press
        resetFieldCentricKeybind.trigger().onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldRelative()));

        drivetrain.registerTelemetry(logger::telemeterize);

        // snapTo0Keybind.trigger().whileTrue(new SnapToDegreeCmd(pigeon, 0));
        // snapTo180Keybind.trigger().whileTrue(new SnapToDegreeCmd(pigeon, 180));
        // snapToAmpKeybind.trigger().whileTrue(
        // new SnapToDegreeCmd(pigeon, () ->
        // DriverStation.getAlliance().orElse(Alliance.Red) == Alliance.Red ? 90 :
        // -90));
        // snapToSLKeybind.trigger().whileTrue(new SnapToDegreeCmd(pigeon, -45));
        // snapToSRKeybind.trigger().whileTrue(new SnapToDegreeCmd(pigeon, 45));
        // snapToNoteKeybind.trigger().whileTrue(new SnapToDegreeCmd(pigeon,
        // limelight1));

        // secretShoot.trigger().whileTrue(new ShootCmd(arm, shooter, true));
        // secretAim.trigger().whileTrue(new LowLimelightShotCmd(arm, shooter,
        // limelight1, logger));
        dubiousSpit.trigger().whileTrue(new FloorIntakeCmd(floorIntake, FloorIntakeState.Spit));

        intakeDriverKeybind.trigger().whileTrue(new FloorToShooterCmd(floorIntake, shooter, arm, true));
        intakeDriverKeybind.trigger().onFalse(new PreloadCmd(shooter, arm));

        // bind codriver controls to commands
        subwooferKeybind.trigger().whileTrue(new SetArmPositionCmd(arm, ArmPosition.SubWoofer));
        subwooferKeybind.trigger().whileTrue(new SpinUpShooterCmd(shooter, Constants.SUBWOOFER_SHOOT_SPEED, false));

        altScoringKeybind.trigger().and(modifyArm).whileTrue(new SetArmPositionCmd(arm, ArmPosition.Amp2));
        altScoringKeybind.trigger().and(modifyArm.negate()).whileTrue(new SetArmPositionCmd(arm, ArmPosition.Amp));
        altScoringKeybind.trigger().and(modifyArm.negate())
                .whileTrue(new SpinUpShooterCmd(shooter, Constants.AMP_SHOOT_SPEED, true));

        recievingKeybind.trigger().and(modifyArm)
                .whileTrue(new IntakeFromSourceCmd(arm, shooter, Constants.SOURCE_INTAKE_SPEED));
        recievingKeybind.trigger().onFalse(new PreloadCmd(shooter, arm));
        recievingKeybind.trigger().and(modifyArm.negate())
                .whileTrue(new FloorToShooterCmd(floorIntake, shooter, arm, true));

        // y button! (main speaker shot)
        shootPositionKeybind.trigger().and(modifyArm.negate()).and(fixedArm.negate())
                .whileTrue(new LowLimelightShotCmd(arm, shooter, backLimelight, logger));
        // shootPositionKeybind.trigger().and(modifyArm).and(fixedArm.negate())
        // .whileTrue(new SetArmPositionCmd(arm, ArmPosition.SpeakerHigh));
        shootPositionKeybind.trigger().and(modifyArm.negate()).and(fixedArm)
                .whileTrue(new SetArmPositionCmd(arm, ArmPosition.PodiumLow));
        shootPositionKeybind.trigger().and(modifyArm.negate()).and(fixedArm)
                .whileTrue(new SpinUpShooterCmd(shooter, Constants.PODIUM_LOW_SPEED, false));
        shootPositionKeybind.trigger().and(modifyArm).and(fixedArm)
                .whileTrue(new SetArmPositionCmd(arm, ArmPosition.PodiumHigh));
        shootPositionKeybind.trigger().and(modifyArm).and(fixedArm)
                .whileTrue(new SpinUpShooterCmd(shooter, Constants.PODIUM_HIGH_SPEED, false));

        // non positional
        shootTrigger.trigger().whileTrue(new ShootCmd(arm, shooter, true));
        spitTrigger.trigger().and(modifyArm.negate()).whileTrue(new FloorIntakeCmd(floorIntake, FloorIntakeState.Spit));
        spitTrigger.trigger().and(modifyArm).whileTrue(new ShootCmd(arm, shooter, false));

        climberMaxKeybind.trigger().onTrue(new ClimberPositionCmd(climber, arm, ClimbState.Max));
        climberMinKeybind.trigger().onTrue(new ClimberPositionCmd(climber, arm, ClimbState.Min));
        climberMidKeybind.trigger().onTrue(new ClimberPositionCmd(climber, arm, ClimbState.Mid));
        climberToggleLockKeybind.trigger().onTrue(new ClimberLockCmd(climber));

    }

    public Command getAutonomousCommand() {
        return autoChooser.getSelected();
    }

}