package frc.robot.subsystems;

import com.revrobotics.CANSparkBase.ControlType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.ExtraMath;
import frc.robot.PIDMotor;

public class ElevatorSubsystem extends SubsystemBase {
    static final double ENCODER_LOWEST = 0.0;
    static final double ENCODER_HIGHEST = 0.0;
    static final double HEIGHT = 9.0;

    PIDMotor elevatorMotor = PIDMotor.makeMotor(Constants.ELEVATOR_ID, "Elevator", 0, 0, 0, 0, ControlType.kPosition, 0);

    public ElevatorSubsystem() {}

    public void setHeight(double height) {
        setUnchecked(ExtraMath.clamp(height, 0.0, HEIGHT));
    }

    void setUnchecked(double height) {
        double pos = ExtraMath.rangeMap(height, 0.0, HEIGHT, ENCODER_LOWEST, ENCODER_HIGHEST);
        elevatorMotor.targetRaw(pos);
    }

    @Override
    public void periodic() {}
}
