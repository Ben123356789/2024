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
    static final double LEFT_SERVO_RELEASE_POS = 0.42;
    static final double RIGHT_SERVO_ENGAGE_POS = 0.38;
    static final double RIGHT_SERVO_RELEASE_POS = 0.5;
    static final double ENGAGE_BACKING_DISTANCE = 3.0;

    public double target = 0.0;
    boolean ratchetEngaged = false;

    PIDMotor leftMotor = PIDMotor.makeMotor(Constants.CLIMBER_LEFT_ID, "Climber Left", 0.02, 0, 0, 0,
            ControlType.kPosition);
    PIDMotor rightMotor = PIDMotor.makeMotor(Constants.CLIMBER_RIGHT_ID, "Climber Right", 0.02, 0, 0, 0,
            ControlType.kPosition);
    Servo leftRatchetServo = new Servo(LEFT_SERVO_CHANNEL);
    Servo rightRatchetServo = new Servo(RIGHT_SERVO_CHANNEL);

    public enum ClimbState {
        Max, Min, Mid;

        public double height(){
        switch(this){
            case Max: return Constants.CLIMBER_HIGH_HEIGHT;
            case Mid: return Constants.CLIMBER_MID_HEIGHT;
            case Min: return Constants.CLIMBER_LOW_HEIGHT;
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

    /**
     * Sets a new desired height for the climbers.
     * 
     * @param newHeight
     */
    public void setHeight(double newHeight) {
        target = newHeight;
    }

    /**
     * Engages the ratchet, which locks the climber mechanism.
     */
    public void engageRatchet() {
        ratchetEngaged = true;
    }

    /**
     * Releases the ratchet, which unlocks the climber mechanism.
     */
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
        printDashboard();
    }

    /**
     * Toggles the ratchet between a locked and unlocked state.
     */
    public void toggleRatchet(){
        if(ratchetEngaged){
            ratchetEngaged = false;
        } else{
            ratchetEngaged = true;
        }
    }

    /**
     * Prints the climber motor positions in revolutions.
     */
    public void printDashboard() {
        SmartDashboard.putNumber("Left Climber Position", leftMotor.getPosition());
        SmartDashboard.putNumber("Right Climber Position", rightMotor.getPosition());
    }

    public void disableBrakeMode(){
      leftMotor.setIdleCoastMode();
      rightMotor.setIdleCoastMode();
    }
  
    public void enableBrakeMode(){
      leftMotor.setIdleBrakeMode();
      rightMotor.setIdleBrakeMode();
    }
  
    public void zeroEncoders(){
      leftMotor.resetEncoder();
      rightMotor.resetEncoder();
    }
}
