package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ShooterSubsystem extends SubsystemBase {
  CANSparkMax shooterTop, shooterBottom, intakeTop, intakeBottom;

  Timer shooterTimer;
  int state;
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
    state = 0;
    intakeBottomStartPos = 0;
    intakeTopStartPos = 0;

    // room for inverse
  }

  @Override
  public void periodic() {
    if (shootWhenReady && state == 0) {
      state = 1;
    }
    manageShooterRollers(true, motorPower);
    printDashboard();
  }

  public void intakeInit() {
    shooterTimer.restart();
  }

  public void manageShooterRollers(boolean shootFront, double shooterPowerPercent) {
    CANSparkMax inTop, inBottom, outTop, outBottom;

    // Don't worry about the memory leaks
    inBottom = (shootFront ? intakeBottom : shooterBottom);
    inTop = (shootFront ? intakeTop : shooterTop);
    outBottom = (shootFront ? shooterBottom : intakeBottom);
    outTop = (shootFront ? shooterTop : intakeTop);

    switch (state) {
    case 1: // Step 1: Get starting position of intakes
      intakeBottomStartPos = inBottom.getEncoder().getPosition();
      intakeTopStartPos = inTop.getEncoder().getPosition();
      state = 2;
      break;
    case 2: // Step 2: Pull note back so it won't get caught on shooters
      if (intakeBottomStartPos - inBottom.getEncoder().getPosition() >= 1.5 || intakeTopStartPos - inTop.getEncoder().getPosition() >= 1.5) {
        state = 3;
      } else {
        inBottom.set(-0.25);
        inTop.set(-0.25);
      }
      break;
    case 3: // Step 3: Wait for rollers to reach maximum velocity
      if (outTop.getEncoder().getVelocity() >= 10000 / shooterPowerPercent && outBottom.getEncoder().getVelocity() >= 10000 / shooterPowerPercent) {
        shooterTimer.restart();
        state = 4;
      } else {
        outBottom.set(shooterPowerPercent);
        outTop.set(shooterPowerPercent);
      }
    case 4: // Step 4: Shoot note at full power for 1.5 seconds
      if (shooterTimer.get() < 1.5) {
        inBottom.set(1);
        inTop.set(1);
        outBottom.set(shooterPowerPercent);
        outTop.set(shooterPowerPercent);
      } else {
        state = 0;
        shootWhenReady = false;
      }
      break;
    default: // Motors shut off
      inBottom.set(0);
      inTop.set(0);
      outBottom.set(0);
      outTop.set(0);
      break;
    }
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
    // SmartDashboard.putNumber();
  }
}
