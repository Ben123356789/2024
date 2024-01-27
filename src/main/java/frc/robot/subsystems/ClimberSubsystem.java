package frc.robot.subsystems;

import com.revrobotics.CANSparkBase.ControlType;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.JMath;
import frc.robot.PIDMotor;

public class ClimberSubsystem extends SubsystemBase {
    PIDMotor climberMotor = new PIDMotor(Constants.ELEVATOR_ID, 0, 0, 0, 0, ControlType.kPosition, 0);
    Servo ratchetServo = new Servo(Constants.CLIMBER_SERVO_CHANNEL);

    public ClimberSubsystem() {}

    public void setHeight(double height) {
        setUnchecked(JMath.clamp(height, 0.0, Constants.CLIMBER_HEIGHT));
    }

    void setUnchecked(double height) {
        double pos = JMath.map(height, 0.0, Constants.CLIMBER_HEIGHT, Constants.CLIMBER_LOWEST, Constants.CLIMBER_HIGHEST);
        climberMotor.targetRaw(pos);
    }

    public void engageRatchet() {
        ratchetServo.setPosition(Constants.CLIMBER_SERVO_ENGAGE_POS);
    }

    public void disengageRatchet() {
        ratchetServo.setPosition(Constants.CLIMBER_SERVO_DISENGAGE_POS);
    }

    @Override public void periodic() { }
}
