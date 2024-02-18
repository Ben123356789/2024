package frc.robot.commands;

import frc.robot.LimelightHelpers.LimelightTarget_Fiducial;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.LimelightSubsystem;
import frc.robot.subsystems.ArmSubsystem.ArmPosition;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

public class LowLimelightShotCmd extends Command {
    ArmSubsystem arm;
    double targetShoulderPosition;
    double targetWristPosition;
    double elevatorPosition;
    LimelightSubsystem limelight;

    boolean shoulderSetCheck = false;
    boolean wristSetCheck = false;
    boolean elevatorSetCheck;

    double[][] wristPosition = {
            { 7.5, 23 },
            { 14, 37 },
            { 33, 45 }
    };
    LinearInterpolation wrist;
    LimelightTarget_Fiducial tag;


    public LowLimelightShotCmd(ArmSubsystem arm, LimelightSubsystem limelight) {
        this.arm = arm;
        this.limelight = limelight;
        addRequirements(arm);
    }

    @Override
    public void initialize() {
        // System.out.println("init");
        wrist = new LinearInterpolation(wristPosition);
    }

    @Override
    public void execute() {
        tag = limelight.getDataForId(7);
        if(tag == null){

            tag = limelight.getDataForId(4);
        }
        if(tag != null){
            SmartDashboard.putNumber("Calculated Wrist Position:", wrist.interpolate(tag.ty));
            // arm.wristMotorset now!!!
        }
    }

    @Override
    public void end(boolean interrupted) {
        // arm.unsafeSetPosition(ArmPosition.Stowed);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}