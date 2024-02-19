package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.PIDMotor;

public class ShooterSubsystem extends SubsystemBase {
  PIDMotor shooterTop, shooterBottom, intakeTop, intakeBottom;

  Timer shooterTimer;
  public IntakeState intakeState;
  public ShooterState shooterState;
  public boolean okToShoot = true;

  // TODO: Calculate velocity
  public double shooterV;

  public ShooterSubsystem() {
    shooterTop = PIDMotor.makeMotor(Constants.SHOOTER_TOP_ID, "Shooter Top", 0.00005, 0, 0.001, 0.00009, ControlType.kVelocity);
    shooterBottom = PIDMotor.makeMotor(Constants.SHOOTER_BOTTOM_ID, "Shooter Bottom", 0.00005, 0, 0.001, 0.00009, ControlType.kVelocity);
    intakeTop = PIDMotor.makeMotor(Constants.INTAKE_TOP_ID, "Intake Top", 0, 0, 0, 0, ControlType.kPosition);
    intakeBottom = PIDMotor.makeMotor(Constants.INTAKE_BOTTOM_ID, "Intake Bottom", 0, 0, 0, 0, ControlType.kPosition);

    shooterBottom.setIdleCoastMode();
    shooterTop.setIdleCoastMode();
    shooterBottom.setInverted(true);

    shooterTimer = new Timer();
    intakeState = IntakeState.Idle;
    shooterState = ShooterState.Idle;
    shooterV = 0;
  }

  @Override
  public void periodic() {
    // TODO: calculate shooterV when in limelight shooter state
    manageShooterRollers();
    printDashboard();
  }

  public enum IntakeState {
    Idle,
    ShootNow,
    ShootAuto,
    Intake,
    ReverseIntake,
    Preload,
    ResetTimer
  }

  public enum ShooterState {
    Idle,
    SpinFixed,
    SpinLimelight,
  }

  public void manageShooterRollers() {

    switch (shooterState) {
      case Idle:
        shooterBottom.setPercentOutput(0);
        shooterTop.setPercentOutput(0);
        break;
      case SpinFixed:
      case SpinLimelight:
        shooterBottom.setTarget(shooterV);
        shooterTop.setTarget(shooterV);
      break;
      default:
        break;
    }

    switch (intakeState) {
      // Stops all the motors.
      case Idle: {
        intakeTop.setPercentOutput(0);
        intakeBottom.setPercentOutput(0);
      }
        break;
      case ShootAuto:
      // TODO
        break;
      case ShootNow:
        intakeBottom.setPercentOutput(-1);
        intakeTop.setPercentOutput(1);
        break;
      case Intake: {
        intakeBottom.setPercentOutput(-0.4);
        intakeTop.setPercentOutput(0.4);
        shooterTimer.restart();
        intakeTop.resetEncoder();
        intakeBottom.resetEncoder();
      }
        break;
      case Preload: {
        if (shooterTimer.get() < 0.15) {
          intakeBottom.setPercentOutput(0);
          intakeTop.setPercentOutput(0);
          intakeTop.resetEncoder();
          intakeBottom.resetEncoder();
        } else if (shooterTimer.get() > 0.35) {
          intakeBottom.setPercentOutput(0.15);
          intakeTop.setPercentOutput(-0.15);
          if (Math.abs(intakeBottom.getPosition()) >= 1.375 || Math.abs(intakeTop.getPosition()) >= 1.375) {
            intakeBottom.setPercentOutput(0);
            intakeTop.setPercentOutput(0);
            intakeState = IntakeState.Idle;
          }
        }
      }
        break;
      case ReverseIntake: {
        intakeBottom.setPercentOutput(-0.5);
        intakeTop.setPercentOutput(-0.5);
      }
        break;
      case ResetTimer:
        shooterTimer.restart();
        intakeTop.resetEncoder();
        intakeBottom.resetEncoder();
        break;
    }
  }

  public void printDashboard() {
    SmartDashboard.putString("Intake State:", intakeState.toString());
    SmartDashboard.putString("Shooter State:", shooterState.toString());
    shooterBottom.putPV();
    shooterTop.putPV();
  }

  public void disableBrakeMode() {
    intakeBottom.setIdleCoastMode();
    intakeTop.setIdleCoastMode();
  }

  public void enableBrakeMode() {
    intakeBottom.setIdleBrakeMode();
    intakeTop.setIdleBrakeMode();
  }

  public void zeroEncoders() {
    intakeBottom.resetEncoder();
    intakeTop.resetEncoder();
    shooterBottom.resetEncoder();
    shooterTop.resetEncoder();
  }
}
