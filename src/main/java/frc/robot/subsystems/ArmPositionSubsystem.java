package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.ExtraMath;

public class ArmPositionSubsystem extends SubsystemBase {
  ShoulderSubsystem shoulder;
  WristSubsystem wrist;
  double targetShoulderPosition;
  double targetWristPosition;
  ArmPosition target;

  public enum ArmPosition {
    Stowed, Intake, Source, SpeakerHigh, SpeakerLow, Amp, Trap;

    public double shoulderPosition() {
      switch (this) {
        case Stowed:
          return Constants.SHOULDER_STOWED_POSITION;
        case Intake:
          return Constants.SHOULDER_INTAKE_POSITION;
        case Source:
          return Constants.SHOULDER_SOURCE_POSITION;
        case SpeakerHigh:
          return Constants.SHOULDER_SPEAKER_HIGH_POSITION;
        case SpeakerLow:
          return Constants.SHOULDER_SPEAKER_LOW_POSITION;
        case Amp:
          return Constants.SHOULDER_AMP_POSITION;
        case Trap:
          return Constants.SHOULDER_TRAP_POSITION;
        default:
          return Constants.SHOULDER_STOWED_POSITION;
      }
    }

    public double wristPosition() {
      switch (this) {
        case Stowed:
          return Constants.WRIST_STOWED_POSITION;
        case Intake:
          return Constants.WRIST_INTAKE_POSITION;
        case Source:
          return Constants.WRIST_SOURCE_POSITION;
        case SpeakerHigh:
          return Constants.WRIST_SPEAKER_HIGH_POSITION;
        case SpeakerLow:
          return Constants.WRIST_SPEAKER_LOW_POSITION;
        case Amp:
          return Constants.WRIST_AMP_POSITION;
        case Trap:
          return Constants.WRIST_TRAP_POSITION;
        default:
          return Constants.WRIST_STOWED_POSITION;
      }
    }
  }

  public ArmPositionSubsystem() {
  }

  @Override
  public void periodic() {
    printDashboard(checkBoth());
  }

  public void setTarget(ArmPosition p) {
    target = p;
    targetShoulderPosition = p.shoulderPosition();
    targetWristPosition = p.wristPosition();
    shoulder.setPosition(targetShoulderPosition);
    wrist.setPosition(targetWristPosition);
  }

  public void setTarget(ArmPosition p, double threshold) {
    target = p;
    targetShoulderPosition = p.shoulderPosition();
    targetWristPosition = p.wristPosition();
    shoulder.setPosition(targetShoulderPosition);
    // Does NOT currently support both directions
    if (shoulder.leftMotor.getDegrees() >= threshold &&
        shoulder.rightMotor.getDegrees() >= threshold) {
      wrist.setPosition(targetWristPosition);
    }
  }

  public boolean checkShoulder() {
    boolean checkShoulderLeft = checkPosition(shoulder.leftMotor.getDegrees(), targetShoulderPosition);
    boolean checkShoulderRight = checkPosition(shoulder.rightMotor.getDegrees(), targetShoulderPosition);
    return checkShoulderLeft && checkShoulderRight;
  }

  public boolean checkWrist() {
    boolean checkWrist = checkPosition(wrist.wristMotor.getDegrees(), targetWristPosition);
    return checkWrist;
  }

  public boolean checkBoth() {
    return checkShoulder() && checkWrist();
  }

  public boolean checkPosition(double current, double target) {
    return ExtraMath.within(current, target, 1);
  }

  public void printDashboard(boolean checkAll) {
    SmartDashboard.putString("Arm Target Position", target.toString());
    SmartDashboard.putBoolean("Arm At Target?", checkAll);
    SmartDashboard.putNumber("Left Shoulder Position", shoulder.leftMotor.getDegrees());
    SmartDashboard.putNumber("Right Shoulder Position", shoulder.rightMotor.getDegrees());
    SmartDashboard.putNumber("Wrist Position", wrist.wristMotor.getDegrees());
  }
}