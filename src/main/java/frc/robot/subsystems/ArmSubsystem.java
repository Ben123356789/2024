package frc.robot.subsystems;

import com.revrobotics.CANSparkBase.ControlType;
import frc.robot.Constants;
import frc.robot.ExtraMath;
import frc.robot.PIDMotor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ArmSubsystem extends SubsystemBase {
  public PIDMotor leftShoulderMotor;
  public PIDMotor rightShoulderMotor;
  public PIDMotor wristMotor;
  PIDMotor elevatorMotor;

  static final double SHOULDER_DEGREE_MIN = 0.0;
  static final double SHOULDER_DEGREE_MAX = -119.0;
  static final double WRIST_ENCODER_MIN = 0.0;
  static final double WRIST_ENCODER_MAX = 150.0;

  static final double shoulderP = 0.05;
  static final double shoulderI = 0;
  static final double shoulderD = 0;
  static final double shoulderF = 0;
  static final double wristP = 0.05;
  static final double wristI = 0;
  static final double wristD = 0;
  static final double wristF = 0;
  static final double elevatorP = 0.06;
  static final double elevatorI = 0;
  static final double elevatorD = 0;
  static final double elevatorF = 0;

  static final double ELEVATOR_ENCODER_MIN = 0.0;
  static final double ELEVATOR_ENCODER_MAX = -92.0;
  static final double ELEVATOR_HEIGHT_CM_MIN = 0.0;
  static final double ELEVATOR_HEIGHT_CM_MAX = 9.0;

  ArmPosition target = ArmPosition.Stowed;

  public enum ArmPosition {
    Stowed, Intake, Source, SpeakerHigh, SpeakerLow, Amp, Trap, SubWoofer, PodiumHigh, PodiumLow;

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
        case SubWoofer:
          return Constants.SHOULDER_SUBWOOFER_POSITION;
        case PodiumHigh:
          return Constants.SHOULDER_PODIUM_HIGH_POSITION;
        case PodiumLow:
          return Constants.SHOULDER_PODIUM_LOW_POSITION;
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
        case SubWoofer:
          return Constants.WRIST_SUBWOOFER_POSITION;
        case PodiumHigh:
          return Constants.WRIST_PODIUM_HIGH_POSITION;
        case PodiumLow:
          return Constants.WRIST_PODIUM_LOW_POSITION;
        default:
          return Constants.WRIST_STOWED_POSITION;
      }
    }

    public double elevatorPosition() {
      switch (this) {
        case Stowed:
          return Constants.ELEVATOR_STOWED_POSITION;
        case Intake:
          return Constants.ELEVATOR_INTAKE_POSITION;
        case Source:
          return Constants.ELEVATOR_SOURCE_POSITION;
        case SpeakerHigh:
          return Constants.ELEVATOR_SPEAKER_HIGH_POSITION;
        case SpeakerLow:
          return Constants.ELEVATOR_SPEAKER_LOW_POSITION;
        case Amp:
          return Constants.ELEVATOR_AMP_POSITION;
        case Trap:
          return Constants.ELEVATOR_TRAP_POSITION;
        case SubWoofer:
          return Constants.ELEVATOR_SUBWOOFER_POSITION;
          case PodiumHigh:
          return Constants.ELEVATOR_PODIUM_HIGH_POSITION;
          case PodiumLow:
          return Constants.ELEVATOR_PODIUM_LOW_POSITION;
          default:
          return Constants.ELEVATOR_STOWED_POSITION;
        }
      }
      
      @Override
      public String toString() {
        return String.format("Position(Shoulder: %f, Wrist: %f, Elevator: %f)", shoulderPosition(), wristPosition(),
        elevatorPosition());
      }
    }
    
    public ArmSubsystem() {
      leftShoulderMotor = PIDMotor.makeMotor(Constants.SHOULDER_LEFT_MOTOR_ID, "Shoulder Left", shoulderP, shoulderI,
      shoulderD, shoulderF, ControlType.kPosition,
        1, 70, 250);
        rightShoulderMotor = PIDMotor.makeMotor(Constants.SHOULDER_RIGHT_MOTOR_ID, "Shoulder Right", shoulderP, shoulderI,
        shoulderD, shoulderF, ControlType.kPosition,
        1, 70, 250);
        rightShoulderMotor.follow(leftShoulderMotor, true);
        // rightShoulderMotor.setInverted(true);
        wristMotor = PIDMotor.makeMotor(Constants.WRIST_MOTOR_ID, "Wrist", wristP, wristI, wristD, wristF,
        ControlType.kPosition, 1,140, 500);
        elevatorMotor = PIDMotor.makeMotor(Constants.ELEVATOR_MOTOR_ID, "Elevator", elevatorP, elevatorI, elevatorD,
        elevatorF, ControlType.kPosition,1, 100, 300);
        
        elevatorMotor.generateTrapezoidPath(0, 0);
        leftShoulderMotor.generateTrapezoidPath(0, 0);
        // rightShoulderMotor.generateTrapezoidPath(0, 0);
        wristMotor.generateTrapezoidPath(0, 0);
        leftShoulderMotor.putPIDF();
        wristMotor.putPIDF();
        elevatorMotor.putPIDF();
      }
      
      @Override
      public void periodic() {
        elevatorMotor.runTrapezoidPath();
        leftShoulderMotor.runTrapezoidPath();
        // rightShoulderMotor.runTrapezoidPath();
        wristMotor.runTrapezoidPath();
        printDashboard();
        // leftShoulderMotor.fetchPIDFFromDashboard();
        // wristMotor.fetchPIDFFromDashboard();
        // elevatorMotor.fetchPIDFFromDashboard();
      }

  public void unsafeSetPosition(ArmPosition p) {
    target = p;
    elevatorMotor.generateTrapezoidPath(target.elevatorPosition(), 0);
    leftShoulderMotor.generateTrapezoidPath(target.shoulderPosition(), 0);
    // rightShoulderMotor.generateTrapezoidPath(0, 0, target.shoulderPosition(), 0);
    wristMotor.generateTrapezoidPath(target.wristPosition(), 0);
  }

  public double getShoulderPosition() {
    return leftShoulderMotor.getPosition();
  }

  public double getWristPosition() {
    return (wristMotor.getPosition());
  }

  public double getElevatorPosition() {
    return (elevatorMotor.getPosition());
  }

  public boolean checkShoulderPosition() {
    return ExtraMath.within(target.shoulderPosition(), getShoulderPosition(), 10);
  }

  public boolean checkWristPosition() {
    return ExtraMath.within(target.wristPosition(), getWristPosition(), 10);
  }

  public boolean checkElevatorPosition() {
    return ExtraMath.within(target.elevatorPosition(), getElevatorPosition(), 10);
  }

  public void setElevatorHeightCm(double height) {
    double clampedHeight = ExtraMath.clamp(height, ELEVATOR_HEIGHT_CM_MIN, ELEVATOR_HEIGHT_CM_MAX);
    elevatorMotor.setTarget(clampedHeight);
  }

  public void printDashboard() {
    SmartDashboard.putString("Arm Target Position", target.toString());
    // SmartDashboard.putBoolean("Arm At Target?", checkShoulderPosition() &&
    // checkWristPosition() && checkElevatorPosition());
    // SmartDashboard.putNumber("Shoulder Position", getShoulderPosition());
    // SmartDashboard.putNumber("Wrist Position", getWristPosition());
    // SmartDashboard.putNumber("Elevator Position", getElevatorPosition());

    leftShoulderMotor.putPV();
    SmartDashboard.putNumber("Shoulder Left Degrees", leftShoulderMotor.getPosition()/588*360);
    rightShoulderMotor.putPV();
    SmartDashboard.putNumber("Shoulder Right Degrees", rightShoulderMotor.getPosition()/588*360);
    wristMotor.putPV();
    elevatorMotor.putPV();
  }
}