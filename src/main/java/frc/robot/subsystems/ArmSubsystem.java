package frc.robot.subsystems;

import com.revrobotics.CANSparkBase.ControlType;
import frc.robot.Constants;
import frc.robot.ExtraMath;
import frc.robot.PIDMotor;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ArmSubsystem extends SubsystemBase {
  public PIDMotor leftShoulderMotor;
  public PIDMotor rightShoulderMotor;
  public PIDMotor wristMotor;
  PIDMotor elevatorMotor;
  
  static final double SHOULDER_DEGREE_MIN = 0.0;
  static final double SHOULDER_DEGREE_MAX = 80.0;
  static final double WRIST_DEGREE_MIN = 0.0;
  static final double WRIST_DEGREE_MAX = 210.0;
  
  static final double shoulderP = 0;
  static final double shoulderI = 0;
  static final double shoulderD = 0;
  static final double shoulderF = 0;
  static final double wristP = 0;
  static final double wristI = 0;
  static final double wristD = 0;
  static final double wristF = 0;
  static final double elevatorP = 0;
  static final double elevatorI = 0;
  static final double elevatorD = 0;
  static final double elevatorF = 0;

  static final double ELEVATOR_ENCODER_MIN = 0.0;
  static final double ELEVATOR_ENCODER_MAX = 0.0;
  static final double ELEVATOR_HEIGHT_CM_MIN = 0.0;
  static final double ELEVATOR_HEIGHT_CM_MAX = 9.0;

  TrapezoidProfile elevatorProfile;
  TrapezoidProfile.Constraints elevatorConstraints;
  TrapezoidProfile.State elevatorCurrentState;
  TrapezoidProfile.State elevatorTargetState;
  Timer elevatorTimer;

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

    public double elevatorPosition() {
      // TODO: Write good values for elevator. It is using wrist current... 
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
        default:
          return Constants.ELEVATOR_STOWED_POSITION;
      }
    }

    @Override
    public String toString() {
        return String.format("Position(Shoulder: %f, Wrist: %f, Elevator: %f)", shoulderPosition(), wristPosition(), elevatorPosition());
    }
  }

  public ArmSubsystem() {
    leftShoulderMotor = PIDMotor.makeMotor(Constants.SHOULDER_LEFT_MOTOR_ID, "Shoulder Left", shoulderP, shoulderI,
        shoulderD, shoulderF, ControlType.kPosition,
        Constants.SHOULDER_ENCODER_TICK_PER_DEG);
    rightShoulderMotor = PIDMotor.makeMotor(Constants.SHOULDER_RIGHT_MOTOR_ID, "Shoulder Right", shoulderP, shoulderI,
        shoulderD, shoulderF, ControlType.kPosition,
        Constants.SHOULDER_ENCODER_TICK_PER_DEG);
    rightShoulderMotor.setInverted(true);
    wristMotor = PIDMotor.makeMotor(Constants.WRIST_MOTOR_ID, "Wrist", wristP, wristI, wristD, wristF,
        ControlType.kPosition, Constants.WRIST_ENCODER_TICK_PER_DEG);
    elevatorMotor = PIDMotor.makeMotor(Constants.ELEVATOR_ID, "Elevator", elevatorP, elevatorI, elevatorD, elevatorF, ControlType.kPosition, 0);

    shoulderConstraints = new Constraints(150, 500);
    shoulderCurrentState = new TrapezoidProfile.State(shoulderMotor.getPosition(), 0);
    shoulderTargetState = new TrapezoidProfile.State(shoulderMotor.getPosition(), 0);
    shoulderProfile = new TrapezoidProfile(shoulderConstraints, shoulderTargetState, shoulderCurrentState);
    shoulderTimer = new Timer();
    shoulderTimer.start();
    shoulderTimer.reset();

    elevatorConstraints = new Constraints(150, 500);
    elevatorCurrentState = new TrapezoidProfile.State(elevatorMotor.getPosition(), 0);
    elevatorTargetState = new TrapezoidProfile.State(elevatorMotor.getPosition(), 0);
    elevatorProfile = new TrapezoidProfile(elevatorConstraints, elevatorTargetState, elevatorCurrentState);
    elevatorTimer = new Timer();
    elevatorTimer.start();
    elevatorTimer.reset();

    elevatorConstraints = new Constraints(150, 500);
    elevatorCurrentState = new TrapezoidProfile.State(elevatorMotor.getPosition(), 0);
    elevatorTargetState = new TrapezoidProfile.State(elevatorMotor.getPosition(), 0);
    elevatorProfile = new TrapezoidProfile(elevatorConstraints, elevatorTargetState, elevatorCurrentState);
    elevatorTimer = new Timer();
    elevatorTimer.start();
    elevatorTimer.reset();
  }

 // #define target = ◎;
  @Override
  public void periodic() {
    TrapezoidProfile.State elevatorState = elevatorProfile.calculate(elevatorTimer.get());
    elevatorMotor.setTarget(elevatorState.position);
  }

  public void setElevatorPosition(double cm){
    elevatorCurrentState = new TrapezoidProfile.State(elevatorMotor.getPosition(), 0);
    elevatorTargetState = new TrapezoidProfile.State(cm, 0);
    elevatorProfile = new TrapezoidProfile(elevatorConstraints, elevatorTargetState, elevatorCurrentState);
    elevatorTimer.reset();
    elevatorTimer.start();
  }

  public void unsafeSetPosition(ArmPosition p){
    target = p;
    setShoulderPosition(target.shoulderPosition());
    setWristPosition(target.wristPosition());
    setElevatorPosition(target.elevatorPosition());
  }

  public void setShoulderPosition(double degrees) {
    degrees = ExtraMath.clamp(degrees, SHOULDER_DEGREE_MIN, SHOULDER_DEGREE_MAX);
    leftShoulderMotor.setTarget(degrees);
    rightShoulderMotor.setTarget(degrees);
  }
  
  public void setWristPosition(double degrees) {
    degrees = ExtraMath.clamp(degrees, WRIST_DEGREE_MIN, WRIST_DEGREE_MAX);
    wristMotor.setTarget(degrees);
  }

  public double getShoulderPosition() {
    return (leftShoulderMotor.getDegrees() + rightShoulderMotor.getDegrees()) / 2;
  }

  public double getWristPosition() {
    return (wristMotor.getDegrees());
  }

  public boolean checkShoulderPosition() {
    return ExtraMath.within(target.shoulderPosition(), getShoulderPosition(), 1);
  }

  public boolean checkWristPosition() {
    return ExtraMath.within(target.wristPosition(), getWristPosition(), 1);
  }

  public void setElevatorHeightCm(double height) {
    double clampedHeight = ExtraMath.clamp(height, ELEVATOR_HEIGHT_CM_MIN, ELEVATOR_HEIGHT_CM_MAX);
    elevatorMotor.setTarget(clampedHeight);
  }

  public void printDashboard() {
    SmartDashboard.putString("Arm Target Position", target.toString());
    SmartDashboard.putBoolean("Arm At Target?", checkShoulderPosition() && checkWristPosition());
    SmartDashboard.putNumber("Shoulder Position", getShoulderPosition());
    SmartDashboard.putNumber("Wrist Position", getWristPosition());
  }
}