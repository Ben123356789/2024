package frc.robot.subsystems;

import com.revrobotics.CANSparkBase.ControlType;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.ExtraMath;
import frc.robot.PIDMotor;

public class ClimberSubsystem extends SubsystemBase {
    static final double ENCODER_LOWEST = 0.0;
    static final double ENCODER_HIGHEST = 0.0;
    static final double HEIGHT = 0.0;
    static final int SERVO_CHANNEL = 0;
    static final double SERVO_DISENGAGE_POS = 0.0;
    static final double SERVO_ENGAGE_POS = 0.0;
    PIDMotor leftMotor = PIDMotor.makeMotor(Constants.CLIMBER_LEFT_ID, "Climber Left", 0, 0, 0, 0, ControlType.kPosition, 0);
    PIDMotor rightMotor = PIDMotor.makeMotor(Constants.CLIMBER_RIGHT_ID, "Climber Right", 0, 0, 0, 0, ControlType.kPosition, 0);
    Servo ratchetServo = new Servo(SERVO_CHANNEL);

    public ClimberSubsystem() {}

    public void setHeight(double height) {
        setUnchecked(ExtraMath.clamp(height, 0.0, HEIGHT));
    }

    void setUnchecked(double height) {
        double pos = ExtraMath.rangeMap(height, 0.0, HEIGHT, ENCODER_LOWEST, ENCODER_HIGHEST);
        leftMotor.targetRaw(pos);
        rightMotor.targetRaw(pos);
    }

    public void engageRatchet() {
        ratchetServo.setPosition(SERVO_ENGAGE_POS);
    }

    public void disengageRatchet() {
        ratchetServo.setPosition(SERVO_DISENGAGE_POS);
    }

    @Override
    public void periodic() {}
}
