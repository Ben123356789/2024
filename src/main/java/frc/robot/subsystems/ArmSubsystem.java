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
  }

  @Override
  public void periodic() {
  }

  public void unsafeSetPosition(ArmPosition p){
    target = p;
    setShoulderPosition(target.shoulderPosition());
    setWristPosition(target.wristPosition());
  }

  public void setShoulderPosition(double degrees) {
    degrees = ExtraMath.clamp(degrees, SHOULDER_DEGREE_MIN, SHOULDER_DEGREE_MAX);
    leftShoulderMotor.target(degrees);
    rightShoulderMotor.target(degrees);
  }
  
  public void setWristPosition(double degrees) {
    degrees = ExtraMath.clamp(degrees, WRIST_DEGREE_MIN, WRIST_DEGREE_MAX);
    wristMotor.target(degrees);
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

  public boolean isShoulderSafe() {
    return ExtraMath.within(Constants.SHOULDER_SAFE_ANGLE, getShoulderPosition(), 1);
  }

  public boolean isWristSafe() {
    return ExtraMath.within(Constants.WRIST_SAFE_ANGLE, getWristPosition(), 1);
  }

  public void printDashboard() {
    SmartDashboard.putString("Arm Target Position", target.toString());
    SmartDashboard.putBoolean("Arm At Target?", checkShoulderPosition() && checkWristPosition());
    SmartDashboard.putNumber("Shoulder Position", getShoulderPosition());
    SmartDashboard.putNumber("Wrist Position", getWristPosition());
  }
}