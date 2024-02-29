package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.ExtraMath;
import frc.robot.RobotContainer;
import frc.robot.LimelightHelpers.LimelightTarget_Fiducial;
import frc.robot.drive.CommandSwerveDrivetrain;
import frc.robot.drive.Telemetry;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.LimelightSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.ArmSubsystem.ArmPosition;
import frc.robot.subsystems.ShooterSubsystem.IntakeState;
import frc.robot.subsystems.ShooterSubsystem.ShooterState;

import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

public class LimelightAutoCmd extends Command {
    ArmSubsystem arm;
    double targetShoulderPosition;
    double targetWristPosition;
    double elevatorPosition;
    LimelightSubsystem limelight;
    ShooterSubsystem shooter;
    Telemetry logger;
    CommandSwerveDrivetrain drivetrain;
    private final SwerveRequest.FieldCentric drive;

    boolean shoulderSetCheck = false;
    boolean wristSetCheck = false;
    boolean elevatorSetCheck;

    boolean isDone;
    Timer shooterTimer;

    double[][] wristPosition = {
            { 8.3, 22 },
            { 17, 28.5 },
            { 33, 37 }
    };

    double[][] shooterSpeed = {
            { 8.3, 10000 },
            { 17, 9000 },
            { 33, 8000 }     
    };
    LinearInterpolation wrist;
    LinearInterpolation shooterRPM;
    LimelightTarget_Fiducial tag;


    public LimelightAutoCmd(ArmSubsystem arm, ShooterSubsystem shooter, LimelightSubsystem limelight, Telemetry logger, CommandSwerveDrivetrain drivetrain, SwerveRequest.FieldCentric drive) {
        this.arm = arm;
        this.shooter = shooter;
        this.limelight = limelight;
        this.logger = logger;
        this.drivetrain = drivetrain;
        this.drive = drive;
        addRequirements(arm, shooter);
    }

    @Override
    public void initialize() {
        // System.out.println("init");
        wrist = new LinearInterpolation(wristPosition);
        shooterRPM = new LinearInterpolation(shooterSpeed);
        limelight.setPipeline(0);
        shooterTimer = new Timer();
        isDone = false;
        drivetrain.registerTelemetry(logger::telemeterize);

        // arm.safeManualLimelightSetPosition(0, wrist.interpolate(tag.ty), 0, true);
    }

    @Override
    public void execute() {
        tag = limelight.getDataForId(7);
        if(tag == null){
            tag = limelight.getDataForId(4);
        }
        if(tag != null){
            limelight.limelightRotation = true;
            double x = wrist.interpolate(tag.ty);
            x = x + -1.5*logger.getVelocityX();
            // SmartDashboard.putNumber("vel x", logger.getVelocityX());
            // SmartDashboard.putNumber("Calculated Wrist Position:", wrist.interpolate(tag.ty));
            arm.safeManualLimelightSetPosition(0, x, 0, false);
            shooter.shooterV = shooterRPM.interpolate(tag.ty);
            shooter.shooterState = ShooterState.SpinLimelight;

            if(ExtraMath.within(tag.tx, 0, 8) && shooterTimer.get() == 0 && shooter.isShooterAtVelocity()){
                limelight.limelightRotation = false;

                shooterTimer.restart();
               
            }
             if(shooterTimer.get() > 0.25 && shooterTimer.get() < 0.7    ){
             shooter.intakeState = IntakeState.ShootNow;
                System.out.println("Shot");
             }
            if(shooterTimer.get() > 0.8){
                isDone = true;
            }
            limelight.limelightRotationMagnitude = tag.tx-logger.getVelocityY()*10;
            // SmartDashboard.putNumber("Tag X", tag.tx);

            drivetrain.applyRequest(() -> {
                double rate = 0;
                System.out.println("got here");
                if (limelight.limelightRotation) {
                    rate = -0.026 * RobotContainer.MaxAngularRate * limelight.limelightRotationMagnitude;
                   // SmartDashboard.putNumber( "Rate:" , rate);
                   
                }
                return drive
                        .withRotationalRate(rate); // Drive counterclockwise with negative X (left)
            });
        }
    }

    @Override
    public void end(boolean interrupted) {
        limelight.limelightRotation = false;
        shooter.shooterState = ShooterState.Idle;
        shooter.spinDownShooters();
        arm.isTrapezoidal = true;
        arm.unsafeSetPosition(ArmPosition.Stowed);

        shooter.intakeState = IntakeState.Idle;
    }

    @Override
    public boolean isFinished() {
        return isDone;
    }
}