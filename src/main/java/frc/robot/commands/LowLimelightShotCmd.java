package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.ExtraMath;
import frc.robot.RobotContainer;
import frc.robot.LimelightHelpers.LimelightTarget_Fiducial;
import frc.robot.drive.Telemetry;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.LimelightSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.ArmSubsystem.ArmPosition;
import frc.robot.subsystems.ShooterSubsystem.ShooterState;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

public class LowLimelightShotCmd extends Command {
    ArmSubsystem arm;
    double targetShoulderPosition;
    double targetWristPosition;
    double elevatorPosition;
    LimelightSubsystem limelight;
    ShooterSubsystem shooter;
    Telemetry logger;

    boolean shoulderSetCheck = false;
    boolean wristSetCheck = false;
    boolean elevatorSetCheck;

    double[][] wristPosition = {
            { 8.3, 22 },
            { 17, 28.5 },
            { 33, 37 }
    };

    double[][] shooterSpeed = {
            { 8.3, 10000 },
            { 17, 9500 },
            { 33, 9000 }     
    };
    LinearInterpolation wrist;
    LinearInterpolation shooterRPM;

    public LowLimelightShotCmd(ArmSubsystem arm, ShooterSubsystem shooter, LimelightSubsystem limelight, Telemetry logger) {
        this.arm = arm;
        this.shooter = shooter;
        this.limelight = limelight;
        this.logger = logger;
        addRequirements(arm, shooter);

    }

    @Override
    public void initialize() {
        wrist = new LinearInterpolation(wristPosition);
        shooterRPM = new LinearInterpolation(shooterSpeed);
        limelight.setPipeline(0);
        shooter.okToShoot = false;
        RobotContainer.speedMultiplier = 0.55;
        // arm.safeManualLimelightSetPosition(0, wrist.interpolate(tag.ty), 0, true);
    }

    @Override
    public void execute() {
        limelight.limelightRotation = limelight.tagTv;
        if(limelight.limelightRotation){
            double x = wrist.interpolate(limelight.tagTy);
            x = x + -1.5*logger.getVelocityX();
            SmartDashboard.putNumber("vel y", logger.getVelocityY());
            // SmartDashboard.putNumber("Calculated Wrist Position:", wrist.interpolate(tag.ty));
            arm.safeManualLimelightSetPosition(0, x, 0, false);
            shooter.shooterV = shooterRPM.interpolate(limelight.tagTy);
            shooter.shooterState = ShooterState.SpinLimelight;
            if(ExtraMath.within(limelight.tagTx, 10*logger.getVelocityY(), Constants.SHOOTER_ALLOWED_X_OFFSET)){
                // limelight.limelightRotation = false;
                shooter.okToShoot = true;
            } else {
                shooter.okToShoot = false;
            }
            // SmartDashboard.putNumber("Tag X", tag.tx);
        }
    }

    @Override
    public void end(boolean interrupted) {
        RobotContainer.speedMultiplier = 1;
        limelight.limelightRotation = false;
        shooter.okToShoot = true;
        shooter.shooterState = ShooterState.Idle;
        shooter.spinDownShooters();
        arm.isTrapezoidal = true;
        arm.unsafeSetPosition(ArmPosition.Stowed);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}