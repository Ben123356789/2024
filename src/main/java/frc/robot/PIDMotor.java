package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PIDMotor {
    String name;
    boolean initialized = false;

    ControlType controlType;
    
    double p, i, d, f;
    double target = 0.0;
    double rotationsPerUnit;
    double pidfEpsilon = 1.001;

    RelativeEncoder encoder;
    CANSparkMax motor;
    SparkPIDController controller;

    private PIDMotor(int deviceID, String name, double p, double i, double d, double f, ControlType type, double rotationsPerUnit) {
        this.controlType = type;

        this.p = p;
        this.i = i;
        this.d = d;
        this.f = f;

        this.rotationsPerUnit = rotationsPerUnit;

        motor = new CANSparkMax(deviceID, MotorType.kBrushless);
        controller = motor.getPIDController();
        encoder = motor.getEncoder();

        this.name = name;
    }

    public static PIDMotor makeMotor(int deviceID, String name, double p, double i, double d, double f, ControlType type, double rotationsPerUnit){
        PIDMotor motor = new PIDMotor(deviceID, name, p, i, d, f, type, rotationsPerUnit);
        motor.init();
        return motor;
    }

    private void catchUninit() {
        if (!initialized) {
            new Exception("PIDMotor `" + name + "` has not been initialized! Call `init()` before using the motor!").printStackTrace();
        }
    }

    private void init() {
        if (!initialized) {
            motor.restoreFactoryDefaults();
            resetAll();
            putPIDF();
            updatePIDF();
            initialized = true;
        }
    }

    public boolean pidfRequiresUpdate() {
        catchUninit();
        return (
            ExtraMath.withinFactor(controller.getP(), this.p, pidfEpsilon) ||
            ExtraMath.withinFactor(controller.getI(), this.i, pidfEpsilon) ||
            ExtraMath.withinFactor(controller.getD(), this.d, pidfEpsilon) ||
            ExtraMath.withinFactor(controller.getFF(), this.f, pidfEpsilon)
        );
    }

    public void printPIDF() {
        catchUninit();
        System.out.println("PIDF values for motor `" + name + "`");
        System.out.println("- P:" + controller.getP());
        System.out.println("- I:" + controller.getI());
        System.out.println("- D:" + controller.getD());
        System.out.println("- F:" + controller.getFF());
        System.out.println("- Software P:" + p);
        System.out.println("- Software I:" + i);
        System.out.println("- Software D:" + d);
        System.out.println("- Software F:" + f);
    }

    String pKey() { return "Motor `" + name + "` P"; }
    String iKey() { return "Motor `" + name + "` I"; }
    String dKey() { return "Motor `" + name + "` D"; }
    String fKey() { return "Motor `" + name + "` F"; }

    public void putPIDF() {
        catchUninit();
        SmartDashboard.putNumber(pKey(), p);
        SmartDashboard.putNumber(iKey(), i);
        SmartDashboard.putNumber(dKey(), d);
        SmartDashboard.putNumber(fKey(), f);
    }

    public void setPIDF(double p, double i, double d, double f) {
        catchUninit();
        this.p = p;
        this.i = i;
        this.d = d;
        this.f = f;
    }

    public void fetchPIDFFromDashboard() {
        catchUninit();
        setPIDF(
            SmartDashboard.getNumber(pKey(), p),
            SmartDashboard.getNumber(iKey(), i),
            SmartDashboard.getNumber(dKey(), d),
            SmartDashboard.getNumber(fKey(), f)
        );
    }

    public void updatePIDF() {
        catchUninit();
        controller.setP(p);
        controller.setI(i);
        controller.setD(d);
        controller.setFF(f);
    }

    public void resetEncoder() {
        catchUninit();
        encoder.setPosition(0);
    }
    public void resetIAccum() {
        catchUninit();
        controller.setIAccum(0);
    }

    public void resetAll() {
        catchUninit();
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
        catchUninit();
        this.target = rawTarget;
        controller.setReference(target, controlType);
    }

    public boolean target(double units) {
        return targetRaw(unitsToRotations(units));
    }

    public void set(double speed) {
        catchUninit();
        motor.set(speed);
    }

    public void setControlType(ControlType ctype) {
        catchUninit();
        this.controlType = ctype;
    }

    public void setInverted(boolean state){
        motor.setInverted(state);
    }
}
