package frc.robot;

public final class Constants {
    private Constants() {}

    // Climber motor IDs
    public static final int CLIMBER_LEFT_ID = 0;
    public static final int CLIMBER_RIGHT_ID = 0;

    // Elevator motor ID & range
    public static final int ELEVATOR_ID = 0;
    public static final double ELEVATOR_LOWEST = 0.0;
    public static final double ELEVATOR_HIGHEST = 0.0;
    public static final double ELEVATOR_HEIGHT = 9.0;

    // Floor intake motor ID
    public static final int FLUMPER_ID = 35;

    // Pigeon ID
    public static final int PIGEON_ID = 18;

    // Shooter motor IDs
    public static final int SHOOTER_TOP_ID = 32;
    public static final int SHOOTER_BOTTOM_ID = 33;
    public static final int INTAKE_TOP_ID = 31;
    public static final int INTAKE_BOTTOM_ID = 30;

    // Shoulder motor IDs & set positions. Positions are in degrees
    public static final int SHOULDER_LEFT_MOTOR_ID = 0;
    public static final int SHOULDER_RIGHT_MOTOR_ID = 0;
    public static final double SHOULDER_STOWED_POSITION = 0;
    public static final double SHOULDER_INTAKE_POSITION = 0;
    public static final double SHOULDER_SOURCE_POSITION = 0;
    public static final double SHOULDER_SPEAKER_HIGH_POSITION = 0;
    public static final double SHOULDER_SPEAKER_LOW_POSITION = 0;
    public static final double SHOULDER_AMP_POSITION = 0;
    public static final double SHOULDER_TRAP_POSITION = 0;
    public static final double SHOULDER_SAFE_ANGLE = 0;

    // Wrist motor ID & set positions. Positions are in degrees
    public static final int WRIST_MOTOR_ID = 34;
    public static final double WRIST_STOWED_POSITION = 0;
    public static final double WRIST_INTAKE_POSITION = 0;
    public static final double WRIST_SOURCE_POSITION = 0;
    public static final double WRIST_SPEAKER_HIGH_POSITION = 0;
    public static final double WRIST_SPEAKER_LOW_POSITION = 0;
    public static final double WRIST_AMP_POSITION = 0;
    public static final double WRIST_TRAP_POSITION = 0;
    public static final double WRIST_SAFE_ANGLE = 0;

    // Elevator motor ID & set positions. Positions are in CM
    public static final int ELEVATOR_MOTOR_ID = 0;
    public static final double ELEVATOR_STOWED_POSITION = 0;
    public static final double ELEVATOR_INTAKE_POSITION = 0;
    public static final double ELEVATOR_SOURCE_POSITION = 0;
    public static final double ELEVATOR_SPEAKER_HIGH_POSITION = 0;
    public static final double ELEVATOR_SPEAKER_LOW_POSITION = 0;
    public static final double ELEVATOR_AMP_POSITION = 0;
    public static final double ELEVATOR_TRAP_POSITION = 0;
    public static final double ELEVATOR_SAFE_ANGLE = 0;

    // Power Distribution Panel ID
    public static final int PDP_ID = 1;

    // Xbox Controller Ports
    public static final int DRIVER_CONTROLLER_PORT = 0;
    public static final int CODRIVER_CONTROLLER_PORT = 0;

    // Conversion rates between encoder ticks and degrees
    public static final int SHOULDER_ENCODER_TICK_PER_DEG = 0;
    public static final int WRIST_ENCODER_TICK_PER_DEG = 0;

    // LED Strip IDs
    public static final int[] LED_STRIP_ID = {0};

    public double ticksToDegrees(double ticks, double tpd) {
        return ticks / tpd;
    }

    public double degreesToTicks(double degrees, double tpd) {
        return degrees * tpd;
    }
}
