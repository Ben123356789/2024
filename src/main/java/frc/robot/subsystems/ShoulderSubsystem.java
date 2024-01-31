package frc.robot.subsystems;

import com.revrobotics.CANSparkBase.ControlType;
import frc.robot.Constants;
import frc.robot.ExtraMath;
import frc.robot.PIDMotor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShoulderSubsystem extends SubsystemBase {
  PIDMotor leftMotor, rightMotor;

  static final double DEGREE_MIN = 0.0;
  static final double DEGREE_MAX = 80.0;

  static final double p = 0;
  static final double i = 0;
  static final double d = 0;
  static final double f = 0;

  public ShoulderSubsystem() {
    leftMotor = PIDMotor.makeMotor(Constants.SHOULDER_LEFT_MOTOR_ID, "Shoulder Left", p, i, d, f, ControlType.kPosition,
        Constants.SHOULDER_ENCODER_TICK_PER_DEG);
    rightMotor = PIDMotor.makeMotor(Constants.SHOULDER_RIGHT_MOTOR_ID, "Shoulder Right", p, i, d, f, ControlType.kPosition,
        Constants.SHOULDER_ENCODER_TICK_PER_DEG);
    rightMotor.setInverted(true);
  }

  @Override
  public void periodic() {}

  public void setPosition(double degrees) {
    degrees = ExtraMath.clamp(degrees, DEGREE_MIN, DEGREE_MAX);
    leftMotor.target(degrees);
    rightMotor.target(degrees);
  }

}
