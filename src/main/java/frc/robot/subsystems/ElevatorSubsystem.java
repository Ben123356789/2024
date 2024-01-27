package frc.robot.subsystems;

import com.revrobotics.CANSparkBase.ControlType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.JMath;
import frc.robot.PIDMotor;

public class ElevatorSubsystem extends SubsystemBase {
    PIDMotor elevatorMotor = new PIDMotor(Constants.ELEVATOR_ID, 0, 0, 0, 0, ControlType.kPosition, 0);

    public ElevatorSubsystem() {}

    double target = Constants.ELEVATOR_HEIGHT / 2;

    public void set(double height) {
        setUnchecked(JMath.clamp(height, 0.0, Constants.ELEVATOR_HEIGHT));
    }

    void setUnchecked(double height) {
        double pos = JMath.map(height, 0.0, Constants.ELEVATOR_HEIGHT, Constants.ELEVATOR_LOWEST, Constants.ELEVATOR_HIGHEST);
        elevatorMotor.target(pos);
    }

    @Override public void periodic() { }
}
