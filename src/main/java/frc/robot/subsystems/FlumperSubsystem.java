package frc.robot.subsystems;

import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkBase.IdleMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.PIDMotor;
import frc.robot.PIDMotor.ExtraIdleMode;

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

  public void disableBrakeMode(){
    flumperMotor.setIdleCoastMode();
  }

  public void enableBrakeMode(){
    flumperMotor.setIdleBrakeMode();
  }

  public void zeroEncoders(){
    flumperMotor.resetEncoder();
  }

  // feels super inefficient, come back to
  public ExtraIdleMode getIdleMode(){
    IdleMode fM = flumperMotor.getIdleMode();
    return (fM == IdleMode.kBrake) ? ExtraIdleMode.kBrake : ExtraIdleMode.kCoast;
  }
}
