package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ShooterSubsystem extends SubsystemBase {
  CANSparkMax shooterTop, shooterBottom, intakeTop, intakeBottom;

  Timer shooterTimer;
  public ShootState state;
  double intakeBottomStartPos;
  double intakeTopStartPos;
  public boolean shootWhenReady;
  public double motorPower;

  public ShooterSubsystem() {
    shooterTop = new CANSparkMax(Constants.SHOOTER_TOP_ID, MotorType.kBrushless);
    shooterBottom = new CANSparkMax(Constants.SHOOTER_BOTTOM_ID, MotorType.kBrushless);
    intakeTop = new CANSparkMax(Constants.INTAKE_TOP_ID, MotorType.kBrushless);
    intakeBottom = new CANSparkMax(Constants.INTAKE_BOTTOM_ID, MotorType.kBrushless);
    shooterTimer = new Timer();
    state = ShootState.Idle;
    intakeBottomStartPos = 0;
    intakeTopStartPos = 0;

    shooterTop.setIdleMode(IdleMode.kBrake);
    shooterBottom.setIdleMode(IdleMode.kBrake);
    intakeTop.setIdleMode(IdleMode.kBrake);
    intakeBottom.setIdleMode(IdleMode.kBrake);
  }

  @Override
  public void periodic() {
    if (shootWhenReady && state == ShootState.Idle) {
      state = ShootState.Start;
    }
    manageShooterRollers(true, motorPower);
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

  public void manageShooterRollers(boolean shootFront, double shootPower) {
    CANSparkMax inTop, inBottom, shootTop, shootBottom;

    // Don't worry about the memory leaks.
    inBottom = (shootFront ? intakeBottom : shooterBottom);
    inTop = (shootFront ? intakeTop : shooterTop);
    shootBottom = (shootFront ? shooterBottom : intakeBottom);
    shootTop = (shootFront ? shooterTop : intakeTop);

    switch (state) {
      case Idle: {
        inTop.set(0);
        inBottom.set(0);
        shootTop.set(0);
        shootBottom.set(0);
      }
        break;
      case Start: {
        inTop.getEncoder().setPosition(0);
        inBottom.getEncoder().setPosition(0);
        state = ShootState.Pull;
      }
        break;
      case Pull: {
        inBottom.set(-0.25);
        inTop.set(-0.25);
        if (inBottom.getEncoder().getPosition() >= 1.5 || inTop.getEncoder().getPosition() >= 1.5) {
          inBottom.set(0);
          inTop.set(0);
          shooterTimer.restart();
          state = ShootState.WaitForMax;
        }
      }
        break;
      case WaitForMax: {
        shootTop.set(shootPower);
        shootBottom.set(shootPower);
        if ((shootTop.getEncoder().getVelocity() >= 10000 * shootPower &&
            shootBottom.getEncoder().getVelocity() >= 10000 * shootPower) ||
            shooterTimer.get() >= 2.0) {
          shooterTimer.restart();
          state = ShootState.Shoot;
        }
      }
        break;
      case Shoot: {
        inBottom.set(1);
        inTop.set(1);
        shootBottom.set(shootPower);
        shootTop.set(shootPower);
        if (shooterTimer.get() >= 1.5) {
          state = ShootState.Idle;
        }
      }
        break;
      case Intake: {
        inBottom.set(-0.25);
        inTop.set(0.25);
        if (shooterTimer.get() >= 1.5) {
          state = ShootState.Idle;
        }
        shooterTimer.restart();
        inTop.getEncoder().setPosition(0);
        inBottom.getEncoder().setPosition(0);
      }
        break;
      case Preload: {
        if (shooterTimer.get() < 0.2) {
          inBottom.set(0);
          inTop.set(0);
          inTop.getEncoder().setPosition(0);
          inBottom.getEncoder().setPosition(0);
        } else if (shooterTimer.get() > 0.5) {
          inBottom.set(0.15);
          inTop.set(-0.15);
          if (Math.abs(inBottom.getEncoder().getPosition()) >= 1.375
              || Math.abs(inTop.getEncoder().getPosition()) >= 1.375) {
            inBottom.set(0);
            inTop.set(0);
            state = ShootState.Idle;
          }
        }
      }
        break;
      case ReverseIntake: {
        inBottom.set(-0.5);
        inTop.set(-0.5);
        shootBottom.set(-0.5);
        shootTop.set(-0.5);
      }
        break;
    }
  }

  public void startShooting() {
    state = ShootState.Start;
  }

  public void intakeNote() {
    if (shooterTimer.get() < 0.5) {
      intakeBottom.set(0.25);
      intakeTop.set(0.25);
    } else {
      intakeBottom.set(0);
      intakeTop.set(0);
    }
  }

  public void printDashboard() {
    SmartDashboard.putString("Shooter State:", state.toString());
  }
}
