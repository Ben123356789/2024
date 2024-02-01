package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ArmPositionSubsystem extends SubsystemBase {
  ShoulderSubsystem shoulder;
  WristSubsystem wrist;

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

  public ArmPositionSubsystem(ArmPosition position) {
    shoulder.setPosition(position.shoulderPosition());
    wrist.setPosition(position.wristPosition());
  }

  @Override
  public void periodic() {
  }
}
