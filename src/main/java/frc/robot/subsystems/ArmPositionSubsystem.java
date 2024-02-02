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
    boolean checkAll = checkComponents();
    printDashboard(checkAll);
  }

  public void setTarget(ArmPosition p){
    target = p;
    targetShoulderPosition = p.shoulderPosition();
    targetWristPosition = p.wristPosition();
    shoulder.setPosition(targetShoulderPosition);
    wrist.setPosition(targetWristPosition);
  }

  public boolean checkComponents(){
    boolean checkShoulderLeft = checkPosition(shoulder.leftMotor.getDegrees(), targetShoulderPosition);
    boolean checkShoulderRight = checkPosition(shoulder.rightMotor.getDegrees(), targetShoulderPosition);
    boolean checkWrist = checkPosition(wrist.wristMotor.getDegrees(), targetWristPosition);
    return checkShoulderLeft && checkShoulderRight && checkWrist;
  }

  public boolean checkPosition(double current, double target) {
    return ExtraMath.within(current, target, 1);
  }

  public void printDashboard(boolean checkAll) {
    SmartDashboard.putString("Arm Target Position", target.toString());
    SmartDashboard.putBoolean("Arm At Target?", checkAll);
  }
}
