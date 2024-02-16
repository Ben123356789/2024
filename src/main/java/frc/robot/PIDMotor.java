package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PIDMotor {
    String name;
    boolean initialized = false;

    ControlType controlType;

    double p, i, d, f;
    double target = 0.0;
    double rotationsPerUnit;
    double pidfEpsilonFactor = 1.001;

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

    /**
     * Constructs and initializes a new PIDMotor.
     * @param deviceID The CAN ID for this motor.
     * @param name This motor's name.
     * @param p The initial proportional coefficient for this motor.
     * @param i The initial integral coefficient for this motor.
     * @param d The initial derivative coefficient for this motor.
     * @param f The initial feed-forward coefficient for this motor.
     * @param type The control type of this motor.
     * @param rotationsPerUnit The number of encoder rotations per user-defined unit. For example, one rotation could correspond to moving half an inch. If the units are inches, then `rotationsPerUnit` would be 2.
     * @return The constructed PIDMotor.
     */
    public static PIDMotor makeMotor(int deviceID, String name, double p, double i, double d, double f, ControlType type, double rotationsPerUnit) {
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

    /**
     * Gets whether or not any of the PIDF values require updating.
     * @return Whether or not any of the PIDF values differ from the values in the motor controller.
     */
    public boolean pidfRequiresUpdate() {
        catchUninit();
        return (ExtraMath.withinFactor(controller.getP(), this.p, pidfEpsilonFactor) ||
                ExtraMath.withinFactor(controller.getI(), this.i, pidfEpsilonFactor) ||
                ExtraMath.withinFactor(controller.getD(), this.d, pidfEpsilonFactor) ||
                ExtraMath.withinFactor(controller.getFF(), this.f, pidfEpsilonFactor));
    }

    /**
     * Prints the software PIDF values to the RioLog.
     */
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

    String pKey() {
        return "Motor `" + name + "` P";
    }

    String iKey() {
        return "Motor `" + name + "` I";
    }

    String dKey() {
        return "Motor `" + name + "` D";
    }
    String fKey() {
        return "Motor `" + name + "` F";
    }

    /**
     * Puts this motor's PIDF values to the SmartDashboard.
     */
    public void putPIDF() {
        SmartDashboard.putNumber(pKey(), p);
        SmartDashboard.putNumber(iKey(), i);
        SmartDashboard.putNumber(dKey(), d);
        SmartDashboard.putNumber(fKey(), f);
    }

    /**
     * Sets the PIDF values for this motor. Call `updatePIDF` to send the values to the motor controller.
     * @param p The new proportional coefficient.
     * @param i The new integral coefficient.
     * @param d The new derivative coefficient.
     * @param f The new feed-forward coefficient.
     */
    public void setPIDF(double p, double i, double d, double f) {
        catchUninit();
        this.p = p;
        this.i = i;
        this.d = d;
        this.f = f;
    }

    /**
     * Gets the position of the encoder in degrees.
     * @return The position of the encoder in degrees.
     */
    public double getDegrees() {
        return encoder.getPosition() * 360;
    }

    /**
     * Fetches the PIDF values from the SmartDashboard. Call `updatePIDF` to send the values to the motor controller.
     */
    public void fetchPIDFFromDashboard() {
        catchUninit();
        setPIDF(SmartDashboard.getNumber(pKey(), p),
                SmartDashboard.getNumber(iKey(), i),
                SmartDashboard.getNumber(dKey(), d),
                SmartDashboard.getNumber(fKey(), f));
    }

    /**
     * Sends the PIDF values to the motor controller. Call when PIDF values are changed.
     */
    public void updatePIDF() {
        controller.setP(p);
        controller.setI(i);
        controller.setD(d);
        controller.setFF(f);
    }

    /**
     * Resets the encoder, making its current position 0.
     */
    public void resetEncoder() {
        encoder.setPosition(0);
    }

    /**
     * Resets the controller's integral accumulation. Call this every time this motor is enabled.
     */
    public void resetIAccum() {
        controller.setIAccum(0);
    }

    /**
     * Resets the state of the motor. Call this every time this motor is enabled.
     */
    public void resetAll() {
        resetEncoder();
        resetIAccum();
    }

    /**
     * Converts the units for this motor into encoder rotations.
     * @param units The units specified by this motor to convert.
     * @return The number of rotations that correspond to the given units.
     */
    public double unitsToRotations(double units) {
        return units * rotationsPerUnit;
    }

    /**
     * Converts encoder rotations into units for this motor.
     * @param units The number of rotations to convert.
     * @return The units specified by this motor that correspond to the given rotations.
     */
    public double rotationsToUnits(double rotations) {
        return rotations / rotationsPerUnit;
    }

    /**
     * Sets the motor's target to a given unit value.
     * @param units The units specified by this motor to target to.
     */
    public void setTarget(double units) {
        double targetTicks = unitsToRotations(units);
        controller.setReference(targetTicks, controlType);
    }

    /**
     * Sets the motor to a given speed as a fraction of the maximum output, overriding the PID controller.
     * @param speed A fraction from -1 to 1 specifying the power to set this motor to.
     */
    public void set(double speed) {
        catchUninit();
        motor.set(speed);
    }

    /**
     * Sets the control type for this motor.
     * @param ctype The control type to target.
     */
    public void setControlType(ControlType ctype) {
        catchUninit();
        this.controlType = ctype;
    }

    /**
     * Sets this motor to have inverse rotation.
     * @param state Whether or not to make this motor inverted.
     */
    public void setInverted(boolean state) {
        motor.setInverted(state);
    }

    /**
     * Gets the encoder's current position.
     * @return The position of the encoder.
     */
    public double getPosition(){
        return rotationsToUnits(encoder.getPosition());
    }

    TrapezoidProfile motorProfile;
    TrapezoidProfile.Constraints motorConstraints;
    TrapezoidProfile.State motorCurrentState;
    TrapezoidProfile.State motorTargetState;
    Timer motorTimer;

    /**
     * Generates a trapezoidal path, like so:
     * 
     * V
     * |      -------
     * |     /       \
     * |    /         \
     * |----           -----
     *           T
     */
    public void generateTrapezoidPath(double maxV, double maxA, double targetP, double targetV){
        motorConstraints = new Constraints(maxV, maxA);
        motorCurrentState = new TrapezoidProfile.State(getPosition(), 0);
        motorTargetState = new TrapezoidProfile.State(targetP, targetV);
        motorProfile = new TrapezoidProfile(motorConstraints, motorTargetState, motorCurrentState);
        motorTimer.reset();
        motorTimer.start();
    }

    /** After generating a trapezoidal path, run this function periodically to update the target of the PIDMotor */
    public void runTrapezoidPath(){
        TrapezoidProfile.State state = motorProfile.calculate(motorTimer.get());
        setTarget(state.position);
    }
    
    public double getVelocity(){
        return encoder.getVelocity();
    }
}
