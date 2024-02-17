package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.PIDMotor;

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

  PIDMotor intakeMotor;
  boolean reverse = false;
  double speed;
  FlumperState state = FlumperState.Stop;
  
  public FlumperSubsystem() {
    intakeMotor=PIDMotor.makeMotor(Constants.FLUMPER_ID,"Flumper",0,0,0,0,ControlType.kPosition,1, 0, 0);
    intakeMotor.setInverted(reverse);
  }

  public void set(FlumperState state) {
    this.state = state;
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
    printDashboard();
  }

  public void printDashboard() {
    SmartDashboard.putString("Shooter State:", state.toString());
    intakeMotor.putPV();
  }
}
