package frc.robot.subsystems;

import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkBase.IdleMode;

import frc.robot.Constants;
import frc.robot.PIDMotor;
import frc.robot.PIDMotor.ExtraIdleMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ArmSubsystem extends SubsystemBase {
  public PIDMotor leftShoulderMotor;
  public PIDMotor rightShoulderMotor;
  public PIDMotor wristMotor;
  public PIDMotor elevatorMotor;
  
  public ArmPosition target = ArmPosition.Stowed;
  
  public enum ArmPosition {
    Stowed, Intake, Source, SpeakerHigh, SpeakerLow, Amp, Trap, SubWoofer, PodiumHigh, PodiumLow;
    
    public double shoulderPosition() {
      switch (this) {
        case Stowed: return Constants.SHOULDER_STOWED_POSITION;
        case Intake: return Constants.SHOULDER_INTAKE_POSITION;
        case Source: return Constants.SHOULDER_SOURCE_POSITION;
        case SpeakerHigh: return Constants.SHOULDER_SPEAKER_HIGH_POSITION;
        case SpeakerLow: return Constants.SHOULDER_SPEAKER_LOW_POSITION;
        case Amp: return Constants.SHOULDER_AMP_POSITION;
        case Trap: return Constants.SHOULDER_TRAP_POSITION;
        case SubWoofer: return Constants.SHOULDER_SUBWOOFER_POSITION;
        case PodiumHigh: return Constants.SHOULDER_PODIUM_HIGH_POSITION;
        case PodiumLow: return Constants.SHOULDER_PODIUM_LOW_POSITION;
        default: return Constants.SHOULDER_STOWED_POSITION;
      }
    }
    
    public double wristPosition() {
      switch (this) {
        case Stowed: return Constants.WRIST_STOWED_POSITION;
        case Intake: return Constants.WRIST_INTAKE_POSITION;
        case Source: return Constants.WRIST_SOURCE_POSITION;
        case SpeakerHigh: return Constants.WRIST_SPEAKER_HIGH_POSITION;
        case SpeakerLow: return Constants.WRIST_SPEAKER_LOW_POSITION;
        case Amp: return Constants.WRIST_AMP_POSITION;
        case Trap: return Constants.WRIST_TRAP_POSITION;
        case SubWoofer: return Constants.WRIST_SUBWOOFER_POSITION;
        case PodiumHigh: return Constants.WRIST_PODIUM_HIGH_POSITION;
        case PodiumLow: return Constants.WRIST_PODIUM_LOW_POSITION;
        default: return Constants.WRIST_STOWED_POSITION;
      }
    }

    public double elevatorPosition() {
      switch (this) {
        case Stowed: return Constants.ELEVATOR_STOWED_POSITION;
        case Intake: return Constants.ELEVATOR_INTAKE_POSITION;
        case Source: return Constants.ELEVATOR_SOURCE_POSITION;
        case SpeakerHigh: return Constants.ELEVATOR_SPEAKER_HIGH_POSITION;
        case SpeakerLow: return Constants.ELEVATOR_SPEAKER_LOW_POSITION;
        case Amp: return Constants.ELEVATOR_AMP_POSITION;
        case Trap: return Constants.ELEVATOR_TRAP_POSITION;
        case SubWoofer: return Constants.ELEVATOR_SUBWOOFER_POSITION;
        case PodiumHigh: return Constants.ELEVATOR_PODIUM_HIGH_POSITION;
        case PodiumLow: return Constants.ELEVATOR_PODIUM_LOW_POSITION;
        default: return Constants.ELEVATOR_STOWED_POSITION;
      }
    }
  
  @Override
  public String toString() {
      return String.format("Position(Shoulder: %f, Wrist: %f, Elevator: %f)", shoulderPosition(), wristPosition(), elevatorPosition());
    }
  }
  
  public ArmSubsystem() {
    leftShoulderMotor = PIDMotor.makeMotor(Constants.SHOULDER_LEFT_MOTOR_ID, "Shoulder Left", 0.05, 0, 0, 0,
        ControlType.kPosition, 70, 250);
    rightShoulderMotor = PIDMotor.makeMotor(Constants.SHOULDER_RIGHT_MOTOR_ID, "Shoulder Right", 0.05, 0, 0, 0,
        ControlType.kPosition, 70, 250);
    rightShoulderMotor.follow(leftShoulderMotor, true);
    wristMotor = PIDMotor.makeMotor(Constants.WRIST_MOTOR_ID, "Wrist", 0.05, 0, 0, 0, ControlType.kPosition, 140, 500);
    elevatorMotor = PIDMotor.makeMotor(Constants.ELEVATOR_MOTOR_ID, "Elevator", 0.06, 0, 0, 0, ControlType.kPosition, 100, 300);

    leftShoulderMotor.generateTrapezoidPath(0, 0);
    wristMotor.generateTrapezoidPath(0, 0);
    elevatorMotor.generateTrapezoidPath(0, 0);
    leftShoulderMotor.putPIDF();
    wristMotor.putPIDF();
    elevatorMotor.putPIDF();
  }

  @Override
  public void periodic() {
    leftShoulderMotor.runTrapezoidPath();
    wristMotor.runTrapezoidPath();
    elevatorMotor.runTrapezoidPath();
    printDashboard();
    // leftShoulderMotor.fetchPIDFFromDashboard();
    // wristMotor.fetchPIDFFromDashboard();
    // elevatorMotor.fetchPIDFFromDashboard();
  }

  /**
   * Generates a trapezoidal path towards the intended arm position.
   * @param target Target arm position.
   */
  public void unsafeSetPosition(ArmPosition target) {
    this.target = target;
    leftShoulderMotor.generateTrapezoidPath(target.shoulderPosition(), 0);
    wristMotor.generateTrapezoidPath(target.wristPosition(), 0);
    elevatorMotor.generateTrapezoidPath(target.elevatorPosition(), 0);
  }

 /**
  * Prints the positions and velocities of all the arm motors to the SmartDashboard.
  */
  public void printDashboard() {
    SmartDashboard.putString("Arm Target Position", target.toString());
    leftShoulderMotor.putPV();
    rightShoulderMotor.putPV();
    wristMotor.putPV();
    elevatorMotor.putPV();
  }

  public void disableBrakeMode(){
    leftShoulderMotor.setIdleCoastMode();
    rightShoulderMotor.setIdleCoastMode();
    wristMotor.setIdleCoastMode();
    elevatorMotor.setIdleCoastMode();
  }

  public void enableBrakeMode(){
    leftShoulderMotor.setIdleBrakeMode();
    rightShoulderMotor.setIdleBrakeMode();
    wristMotor.setIdleBrakeMode();
    elevatorMotor.setIdleBrakeMode();
  }

  public void zeroEncoders(){
    leftShoulderMotor.resetEncoder();
    rightShoulderMotor.resetEncoder();
    wristMotor.resetEncoder();
    elevatorMotor.resetEncoder();
  }

  // feels super inefficient, come back to
  public ExtraIdleMode getIdleMode(){
    IdleMode iB = leftShoulderMotor.getIdleMode();
    IdleMode iT = rightShoulderMotor.getIdleMode();
    IdleMode sB = wristMotor.getIdleMode();
    IdleMode sT = elevatorMotor.getIdleMode();

    // if they are all the same
    if(iB == IdleMode.kBrake &&
       iT == IdleMode.kBrake &&
       sB == IdleMode.kBrake &&
       sT == IdleMode.kBrake ||
       iB == IdleMode.kCoast &&
       iT == IdleMode.kCoast &&
       sB == IdleMode.kCoast &&
       sT == IdleMode.kCoast){
      return (iB == IdleMode.kBrake) ? ExtraIdleMode.kBrake : ExtraIdleMode.kCoast;
    } else{
      // always need to be switched
      return ExtraIdleMode.kOther;
    }
  }
}