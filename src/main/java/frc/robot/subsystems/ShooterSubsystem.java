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
  public ShootState state;
  double intakeBottomStartPos;
  double intakeTopStartPos;
  public boolean shootWhenReady;
  public double motorPower;

  // TODO: Calculate velocity
  public double shooterV;

  public ShooterSubsystem() {
    shooterTop = PIDMotor.makeMotor(Constants.SHOOTER_TOP_ID, "Shooter Top", 0, 0, 0, 0, ControlType.kVelocity);
    shooterBottom = PIDMotor.makeMotor(Constants.SHOOTER_BOTTOM_ID, "Shooter Bottom", 0, 0, 0, 0, ControlType.kVelocity);
    intakeTop = PIDMotor.makeMotor(Constants.INTAKE_TOP_ID, "Intake Top", 0, 0, 0, 0, ControlType.kPosition);
    intakeBottom = PIDMotor.makeMotor(Constants.INTAKE_BOTTOM_ID, "Intake Bottom", 0, 0, 0, 0, ControlType.kPosition);

    shooterTimer = new Timer();
    state = ShootState.Idle;
    intakeBottomStartPos = 0;
    intakeTopStartPos = 0;
  }

  @Override
  public void periodic() {
    if (shootWhenReady && state == ShootState.Idle) {
      state = ShootState.Start;
    }
    manageShooterRollers();
    printDashboard();
  }

  public void intakeInit() {
    shooterTimer.restart();
    state = ShootState.Start;
  }

  public enum ShootState {
    Idle,
    Start,
    Pull,
    WaitForMax,
    Shoot,
    Intake,
    ReverseIntake,
    Preload
  }

  public void manageShooterRollers() {
    switch (state) {
      // Stops all the motors.
      case Idle: {
        intakeTop.setPercentOutput(0);
        intakeBottom.setPercentOutput(0);
        shooterTop.setPercentOutput(0);
        shooterBottom.setPercentOutput(0);
      }
        break;
      // Resets encoder counts.
      case Start: {
        intakeTop.resetEncoder();
        intakeBottom.resetEncoder();
        state = ShootState.Pull;
      }
        break;
      // Revs up the front shooter motors until they are at their desired velocity.
      case WaitForMax: {
        shooterTop.setTarget(shooterV);
        shooterBottom.setTarget(shooterV);
        if (shooterTop.atVelocity(200) && shooterBottom.atVelocity(200) || shooterTimer.get() >= 2.0) {
          shooterTimer.restart();
          state = ShootState.Shoot;
        }
      }
        break;
      case Shoot: {
        intakeBottom.setPercentOutput(1);
        intakeTop.setPercentOutput(1);
        shooterBottom.setTarget(shooterV);
        shooterTop.setTarget(shooterV);
        if (shooterTimer.get() >= 1.5) {
          state = ShootState.Idle;
        }
      }
        break;
      case Intake: {
        intakeBottom.setPercentOutput(-0.25);
        intakeTop.setPercentOutput(0.25);
        if (shooterTimer.get() >= 1.5) {
          state = ShootState.Idle;
        }
        shooterTimer.restart();
        intakeTop.resetEncoder();
        intakeBottom.resetEncoder();
      }
        break;
      case Preload: {
        if (shooterTimer.get() < 0.2) {
          intakeBottom.setPercentOutput(0);
          intakeTop.setPercentOutput(0);
          intakeTop.resetEncoder();
          intakeBottom.resetEncoder();
        } else if (shooterTimer.get() > 0.5) {
          intakeBottom.setPercentOutput(0.15);
          intakeTop.setPercentOutput(-0.15);
          if (Math.abs(intakeBottom.getPosition()) >= 1.375 || Math.abs(intakeTop.getPosition()) >= 1.375) {
            intakeBottom.setPercentOutput(0);
            intakeTop.setPercentOutput(0);
            state = ShootState.Idle;
          }
        }
      }
        break;
      case ReverseIntake: {
        intakeBottom.setPercentOutput(-0.5);
        intakeTop.setPercentOutput(-0.5);
        shooterBottom.setPercentOutput(-0.5);
        shooterTop.setPercentOutput(-0.5);
      }
        break;
    }
  }

  public void startShooting() {
    state = ShootState.Start;
  }

  public void intakeNote() {
    if (shooterTimer.get() < 0.5) {
      intakeBottom.setPercentOutput(0.25);
      intakeTop.setPercentOutput(0.25);
    } else {
      intakeBottom.setPercentOutput(0);
      intakeTop.setPercentOutput(0);
    }
  }

  public void printDashboard() {
    SmartDashboard.putString("Shooter State:", state.toString());
  }

  public void disableBrakeMode(){
    intakeBottom.setIdleCoastMode();
    intakeTop.setIdleCoastMode();
    shooterBottom.setIdleCoastMode();
    shooterTop.setIdleCoastMode();
  }

  public void enableBrakeMode(){
    intakeBottom.setIdleBrakeMode();
    intakeTop.setIdleBrakeMode();
    shooterBottom.setIdleBrakeMode();
    shooterTop.setIdleBrakeMode();
  }

  public void zeroEncoders(){
    intakeBottom.resetEncoder();
    intakeTop.resetEncoder();
    shooterBottom.resetEncoder();
    shooterTop.resetEncoder();
  }
}
