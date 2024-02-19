package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.ExtraMath;
import frc.robot.LimelightHelpers;
import frc.robot.LimelightHelpers.LimelightTarget_Fiducial;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.LimelightSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.ArmSubsystem.ArmPosition;
import frc.robot.subsystems.ShooterSubsystem.ShooterState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

public class LowLimelightShotCmd extends Command {
    ArmSubsystem arm;
    double targetShoulderPosition;
    double targetWristPosition;
    double elevatorPosition;
    LimelightSubsystem limelight;
    ShooterSubsystem shooter;
    boolean okToShoot;

    boolean shoulderSetCheck = false;
    boolean wristSetCheck = false;
    boolean elevatorSetCheck;

    double[][] wristPosition = {
            { 7.5, 23 },
            { 14, 37 },
            { 33, 45 }
    };

    double[][] shooterSpeed = {
            { 7.5, 7000 },
            { 14, 6000 },
            { 33, 5000 }     
    };
    LinearInterpolation wrist;
    LinearInterpolation shooterRPM;
    LimelightTarget_Fiducial tag;


    public LowLimelightShotCmd(ArmSubsystem arm, ShooterSubsystem shooter, LimelightSubsystem limelight) {
        this.arm = arm;
        this.shooter = shooter;
        this.limelight = limelight;
        addRequirements(arm, shooter);
    }

    @Override
    public void initialize() {
        // System.out.println("init");
        wrist = new LinearInterpolation(wristPosition);
        shooterRPM = new LinearInterpolation(shooterSpeed);
        limelight.setPipeline(1);
        // arm.safeManualLimelightSetPosition(0, wrist.interpolate(tag.ty), 0, true);
    }

    @Override
    public void execute() {
        tag = limelight.getDataForId(7);
        if(tag == null){
            tag = limelight.getDataForId(4);
        }
        if(tag != null){
            SmartDashboard.putNumber("Calculated Wrist Position:", wrist.interpolate(tag.ty));
            arm.safeManualLimelightSetPosition(0, wrist.interpolate(tag.ty), 0, false);
            shooter.shooterV = shooterRPM.interpolate(tag.ty);
            shooter.shooterState = ShooterState.SpinLimelight;
            if(ExtraMath.within(tag.tx, 0, Constants.SHOOTER_ALLOWED_X_OFFSET)){
                shooter.okToShoot = true;
            } else{
                shooter.okToShoot = false;
            }
        }
    }

    @Override
    public void end(boolean interrupted) {
        shooter.okToShoot = true;
        shooter.shooterState = ShooterState.Idle;
        arm.isTrapezoidal = true;
        arm.unsafeSetPosition(ArmPosition.Stowed);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}