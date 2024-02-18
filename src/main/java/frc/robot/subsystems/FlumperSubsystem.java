package frc.robot.subsystems;

import com.revrobotics.CANSparkBase.ControlType;
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

  PIDMotor flumperMotor;
  double speed;
  FlumperState state = FlumperState.Stop;
  
  public FlumperSubsystem() {
    flumperMotor = PIDMotor.makeMotor(Constants.FLUMPER_ID,"Flumper",0,0,0,0,ControlType.kPosition);
  }

  public void set(FlumperState state) {
    this.state = state;
    set(state.speed());
  }

  public void set(double speed) {
    this.speed = speed;
  }

  @Override
  public void periodic() {
    flumperMotor.setPercentOutput(speed);
    printDashboard();
  }

  public void printDashboard() {
    SmartDashboard.putString("Shooter State:", state.toString());
    flumperMotor.putPV();
  }
}
