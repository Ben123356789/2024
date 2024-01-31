package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class FlumperSubsystem extends SubsystemBase {
  public enum FlumperState {
    Eat, Stop, Spit;

    public double speed() {
      switch (this) {
      case Eat:
        return 1.0;
      case Stop:
        return 0.0;
      case Spit:
        return -1.0;
      default:
        return 0.0;
      }
    }
  }

  CANSparkMax intakeMotor;
  // TODO: Revise once design gives us the rundown.
  boolean reverse = false;
  double speed;

  public FlumperSubsystem() {
    intakeMotor = new CANSparkMax(Constants.FLUMPER_ID, MotorType.kBrushless);
    intakeMotor.setInverted(reverse);
  }

  public void set(FlumperState state) {
    set(state.speed());
  }

  public void set(double speed) {
    this.speed = speed;
  }

  public void eat() {
    set(FlumperState.Eat);
  }

  public void stop() {
    set(FlumperState.Stop);
  }

  public void spit() {
    set(FlumperState.Spit);
  }

  @Override
  public void periodic() {
    intakeMotor.set(speed);
  }
}
