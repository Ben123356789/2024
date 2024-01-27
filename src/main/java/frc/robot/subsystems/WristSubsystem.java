package frc.robot.subsystems;

import com.revrobotics.CANSparkBase.ControlType;
import frc.robot.Constants;
import frc.robot.ExtraMath;
import frc.robot.PIDMotor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class WristSubsystem extends SubsystemBase {
  PIDMotor wristMotor;

  static final double DEGREE_MIN = 0.0;
  static final double DEGREE_MAX = 210.0;

  static final double p = 0;
  static final double i = 0;
  static final double d = 0;
  static final double f = 0;

  public WristSubsystem() {
    wristMotor = new PIDMotor(Constants.WRIST_MOTOR_ID, p,i,d,f,ControlType.kPosition,Constants.WRIST_ENCODER_TICK_PER_DEG);
  }

  @Override
  public void periodic() {
  }

  public void setPosition(double degrees){
    degrees = ExtraMath.clamp(degrees,DEGREE_MIN,DEGREE_MAX);
    wristMotor.target(degrees);
  }

}
