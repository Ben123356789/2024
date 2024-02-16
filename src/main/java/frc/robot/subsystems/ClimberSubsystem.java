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
    static final double ENGAGE_BACKING_DISTANCE = 1.0;
    
    double target = 0.0;

    PIDMotor leftMotor = PIDMotor.makeMotor(Constants.CLIMBER_LEFT_ID, "Climber Left", 0, 0, 0, 0, ControlType.kPosition, 1);
    PIDMotor rightMotor = PIDMotor.makeMotor(Constants.CLIMBER_RIGHT_ID, "Climber Right", 0, 0, 0, 0, ControlType.kPosition, 1);
    Servo ratchetServo = new Servo(SERVO_CHANNEL);

    public ClimberSubsystem() {}

    public double position() {
        return ExtraMath.average(leftMotor.getPosition(), rightMotor.getPosition());
    }

    public void setHeight(double newHeight) {
        newHeight = ExtraMath.clamp(newHeight, ENGAGE_BACKING_DISTANCE, HEIGHT);
        if (newHeight > position()) {
            engageBackingTarget = target - ENGAGE_BACKING_DISTANCE;
            engageBacking = true;
        }
        target = newHeight;
        leftMotor.setTarget(newHeight);
        rightMotor.setTarget(newHeight);
    }

    boolean ratchetEngaged = false;
    double engageBackingTarget = 0.0;
    boolean engageBacking = false;

    void engageRatchet() {
        ratchetEngaged = true;
    }

    void disengageRatchet() {
        ratchetEngaged = false;
    }

    @Override
    public void periodic() {
        if (engageBacking) {
            // setReference*()
        } else {
            
        }
        ratchetServo.setPosition(ratchetEngaged ? SERVO_ENGAGE_POS : SERVO_DISENGAGE_POS);
    }
}
