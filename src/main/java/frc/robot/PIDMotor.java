package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class PIDMotor {
    ControlType controlType;
    
    double p, i, d, f;
    double target = 0.0;
    double rotationsPerUnit;
    double pidfEpsilon = 1.001;

    RelativeEncoder encoder;
    CANSparkMax motor;
    SparkPIDController controller;

    public PIDMotor(int deviceID, double p, double i, double d, double f, ControlType type, double rotationsPerUnit) {
        this.controlType = type;

        this.p = p;
        this.i = i;
        this.d = d;
        this.f = f;

        this.rotationsPerUnit = rotationsPerUnit;

        motor = new CANSparkMax(deviceID, MotorType.kBrushless);
        controller = motor.getPIDController();
        encoder = motor.getEncoder();

        updatePIDF();
    }

    public PIDMotor init() {
        motor.restoreFactoryDefaults();
        resetAll();
        return this;
    }

    public boolean pidfRequiresUpdate() {
        return (
            JMath.changedByAtLeastFactor(controller.getP(), this.p, pidfEpsilon) ||
            JMath.changedByAtLeastFactor(controller.getI(), this.i, pidfEpsilon) ||
            JMath.changedByAtLeastFactor(controller.getD(), this.d, pidfEpsilon) ||
            JMath.changedByAtLeastFactor(controller.getFF(), this.f, pidfEpsilon)
        );
    }

    public void printPIDF() {
        System.out.println("PIDF values for ID " + motor.getDeviceId());
        System.out.println("- P:" + controller.getP());
        System.out.println("- I:" + controller.getI());
        System.out.println("- D:" + controller.getD());
        System.out.println("- F:" + controller.getFF());
        System.out.println("- Software P:" + p);
        System.out.println("- Software I:" + i);
        System.out.println("- Software D:" + d);
        System.out.println("- Software F:" + f);
    }

    public void updatePIDF() {
        controller.setP(p);
        controller.setI(i);
        controller.setD(d);
        controller.setFF(f);
    }

    public void resetEncoder() {
        encoder.setPosition(0);
    }
    public void resetIAccum() {
        controller.setIAccum(0);
    }

    public void resetAll() {
        resetEncoder();
        resetIAccum();
    }

    public double unitsToRotations(double units) {
        return units * rotationsPerUnit;
    }

    /**
     * Sets the target rotations/RPM to reach.
     * @param rawTarget The target. Rotations/RPM instead of defined units.
     * @return Whether or not the target is different from the previous one.
     */
    public boolean targetRaw(double rawTarget) {
        boolean changed = this.target != rawTarget;
        if (changed) {
            forceTargetRaw(rawTarget);
        }
        return changed;
    }

    public void forceTargetRaw(double rawTarget) {
        this.target = rawTarget;
        controller.setReference(target, controlType);
    }

    public boolean target(double units) {
        return targetRaw(unitsToRotations(units));
    }

    public void set(double speed) {
        motor.set(speed);
    }

    public void setControlType(ControlType ctype) {
        this.controlType = ctype;
    }
}
