package frc.robot.subsystems;

import com.revrobotics.CANSparkBase.ControlType;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.ExtraMath;
import frc.robot.PIDMotor;

public class ClimberSubsystem extends SubsystemBase {
    static final double HEIGHT = 0.0;

    static final int LEFT_SERVO_CHANNEL = 1;
    static final int RIGHT_SERVO_CHANNEL = 0;
    static final double LEFT_SERVO_ENGAGE_POS = 0.5;
    static final double LEFT_SERVO_RELEASE_POS = 0.4;
    static final double RIGHT_SERVO_ENGAGE_POS = 0.38;
    static final double RIGHT_SERVO_RELEASE_POS = 0.5;
    static final double ENGAGE_BACKING_DISTANCE = 3.0;

    double target = 0.0;
    boolean ratchetEngaged = false;

    PIDMotor leftMotor = PIDMotor.makeMotor(Constants.CLIMBER_LEFT_ID, "Climber Left", 0.01, 0, 0, 0,
            ControlType.kPosition);
    PIDMotor rightMotor = PIDMotor.makeMotor(Constants.CLIMBER_RIGHT_ID, "Climber Right", 0.01, 0, 0, 0,
            ControlType.kPosition);
    Servo leftRatchetServo = new Servo(LEFT_SERVO_CHANNEL);
    Servo rightRatchetServo = new Servo(RIGHT_SERVO_CHANNEL);

    public enum ClimbState {
        Max, Min, Mid;

        public double height(){
        switch(this){
            case Max: return 950;
            case Min: return 0;
            case Mid: return 900;
            default: return 0;
        }
    }
    }

    public ClimberSubsystem() {
    }

    /**
     * Position of the climbers.
     * 
     * @return The average encoder position of the two climber motors.
     */
    public double position() {
        return ExtraMath.average(leftMotor.getPosition(), rightMotor.getPosition());
    }

    public void setHeight(double newHeight) {
        target = newHeight;
    }

    public void engageRatchet() {
        ratchetEngaged = true;
    }

    public void disengageRatchet() {
        ratchetEngaged = false;
    }

    @Override
    public void periodic() {
        printDashboard();
        if(!ratchetEngaged){
            leftMotor.setTarget(target);
            rightMotor.setTarget(-target);
        } else{
            leftMotor.setPercentOutput(0);
            rightMotor.setPercentOutput(0);
        }
        leftRatchetServo.setPosition(ratchetEngaged ? LEFT_SERVO_ENGAGE_POS : LEFT_SERVO_RELEASE_POS);
        rightRatchetServo.setPosition(ratchetEngaged ? RIGHT_SERVO_ENGAGE_POS : RIGHT_SERVO_RELEASE_POS);
    }

    public void toggleRatchet(){
        if(ratchetEngaged){
            ratchetEngaged = false;
        } else{
            ratchetEngaged = true;
        }
    }

    public void printDashboard() {
        SmartDashboard.putNumber("Left Climber Position", leftMotor.getPosition());
        SmartDashboard.putNumber("Right Climber Position", rightMotor.getPosition());
    }
}
