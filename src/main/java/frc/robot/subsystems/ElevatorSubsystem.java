package frc.robot.subsystems;

import com.revrobotics.CANSparkBase.ControlType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.JMath;
import frc.robot.PIDMotor;

public class ElevatorSubsystem extends SubsystemBase {
    static final int ID = 0;
    static final double ENCODER_LOWEST = 0.0;
    static final double ENCODER_HIGHEST = 0.0;
    static final double HEIGHT = 9.0;

    PIDMotor elevatorMotor = new PIDMotor(ID, 0, 0, 0, 0, ControlType.kPosition, 0);

    public ElevatorSubsystem() {}

    public void setHeight(double height) {
        setUnchecked(JMath.clamp(height, 0.0, HEIGHT));
    }

    void setUnchecked(double height) {
        double pos = JMath.map(height, 0.0, HEIGHT, ENCODER_LOWEST, ENCODER_HIGHEST);
        elevatorMotor.targetRaw(pos);
    }

    @Override public void periodic() { }
}
