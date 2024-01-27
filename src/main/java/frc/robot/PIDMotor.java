package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class PIDMotor {
    ControlType ctype;
    
    double p, i, d, f;
    double target = 0.0;
    double rotationsPerUnit;

    RelativeEncoder encoder;
    CANSparkMax motor;
    SparkPIDController controller;

    public PIDMotor(int deviceID, double p, double i, double d, double f, ControlType type, double rotationsPerUnit) {
        this.ctype = type;

        this.p = p;
        this.i = i;
        this.d = d;
        this.f = f;

        this.rotationsPerUnit = rotationsPerUnit;

        motor = new CANSparkMax(deviceID, MotorType.kBrushless);
        controller = motor.getPIDController();
        encoder = motor.getEncoder();

        updatePIDF();
        reset();
    }

    public void updatePIDF() {
        controller.setP(p);
        controller.setI(i);
        controller.setD(d);
        controller.setFF(f);
    }

    public void reset() {
        controller.setIAccum(0);
    }

    public double toRotations(double units) {
        return units * rotationsPerUnit;
    }

    public void targetRaw(double rawTarget) {
        this.target = rawTarget;
        controller.setReference(target, ctype);
    }

    public void target(double target) {
        targetRaw(toRotations(target));
    }

    public void set(double speed) {
        motor.set(speed);
    }

    public void setControlType(ControlType ctype) {
        this.ctype = ctype;
    }
}
