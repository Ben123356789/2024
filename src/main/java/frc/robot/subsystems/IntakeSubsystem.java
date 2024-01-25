package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeSubsystem extends SubsystemBase {
  enum IntakeState {
    Eat,
    Stop,
    Spit;

    public double speed() {
      switch (this) {
        case Eat: return 1.0;
        case Stop: return 0.0;
        case Spit: return -1.0;
        default: return 0.0;
      }
    }
  }

  CANSparkMax intakeMotor;
  // TODO: Revise once design gives us the rundown.
  boolean reverse = false;
  double speed;

  public IntakeSubsystem() {
    intakeMotor = new CANSparkMax(Constants.INTAKE_ID, MotorType.kBrushless);
    intakeMotor.setInverted(reverse);
  }

  public void set(IntakeState state) {
    set(state.speed());
  }
  public void set(double speed) {
    this.speed = speed;
  }

  public void eat() { set(IntakeState.Eat); }
  public void stop() { set(IntakeState.Stop); }
  public void spit() { set(IntakeState.Spit); }

  @Override
  public void periodic() {
    
  }
}
