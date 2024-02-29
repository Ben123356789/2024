package frc.robot.subsystems;

import com.revrobotics.CANSparkBase.ControlType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.PIDMotor;

public class FloorIntakeSubsystem extends SubsystemBase {
  public enum FloorIntakeState {
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

  PIDMotor floorIntakeMotor;
  double speed;
  FloorIntakeState state = FloorIntakeState.Stop;
  
  public FloorIntakeSubsystem() {
    floorIntakeMotor = PIDMotor.makeMotor(Constants.FLOOR_INTAKE_ID,"FloorIntake",0,0,0,0,ControlType.kPosition);
  }

  public void set(FloorIntakeState state) {
    this.state = state;
    set(state.speed());
  }

  public void set(double speed) {
    this.speed = speed;
  }

  @Override
  public void periodic() {
    floorIntakeMotor.setPercentOutput(speed);
    // printDashboard();
  }

  public void printDashboard() {
    // SmartDashboard.putString("Shooter State:", state.toString());
    floorIntakeMotor.putPV();
  }

  public void disableBrakeMode(){
    floorIntakeMotor.setIdleCoastMode();
  }

  public void enableBrakeMode(){
    floorIntakeMotor.setIdleBrakeMode();
  }

  public void zeroEncoders(){
    floorIntakeMotor.resetEncoder();
  }
}
