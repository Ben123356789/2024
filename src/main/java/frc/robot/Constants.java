package frc.robot;

public final class Constants {
    private Constants() {}

    // Climber motor IDs
    public static final int CLIMBER_LEFT_ID = 30;
    public static final int CLIMBER_RIGHT_ID = 31;

    // Floor intake motor ID
    public static final int FLUMPER_ID = 32;

    // Pigeon ID
    public static final int PIGEON_ID = 18;

    // Shooter motor IDs
    public static final int SHOOTER_TOP_ID = 46;
    public static final int SHOOTER_BOTTOM_ID = 47;
    public static final int INTAKE_TOP_ID = 44;
    public static final int INTAKE_BOTTOM_ID = 45;

    // Shoulder motor IDs & set positions. Positions are in encoder revolutions.
    public static final int SHOULDER_LEFT_MOTOR_ID = 40;
    public static final int SHOULDER_RIGHT_MOTOR_ID = 41;
    public static final double SHOULDER_ENCODER_MAX = -119;
    public static final double SHOULDER_STOWED_POSITION = 0;
    public static final double SHOULDER_INTAKE_POSITION = 0;
    public static final double SHOULDER_SOURCE_POSITION = 0;
    public static final double SHOULDER_SPEAKER_HIGH_POSITION = 0;
    public static final double SHOULDER_SPEAKER_LOW_POSITION = 0;
    public static final double SHOULDER_AMP_POSITION = -76.1;
    public static final double SHOULDER_TRAP_POSITION = -110.0;
    public static final double SHOULDER_SUBWOOFER_POSITION = 0;
    public static final double SHOULDER_PODIUM_HIGH_POSITION = 0;
    public static final double SHOULDER_PODIUM_LOW_POSITION = 0;

    // Wrist motor ID & set positions. Positions are in encoder revolutions.
    public static final int WRIST_MOTOR_ID = 43;
    public static final double WRIST_ENCODER_MAX = 150;
    public static final double WRIST_STOWED_POSITION = 0;
    public static final double WRIST_INTAKE_POSITION = 64.0;
    public static final double WRIST_SOURCE_POSITION = 0;
    public static final double WRIST_SPEAKER_HIGH_POSITION = 0;
    public static final double WRIST_SPEAKER_LOW_POSITION = 0;
    public static final double WRIST_AMP_POSITION = 150;
    public static final double WRIST_TRAP_POSITION = 50.0;
    public static final double WRIST_SUBWOOFER_POSITION = 0;
    public static final double WRIST_PODIUM_HIGH_POSITION = 0;
    public static final double WRIST_PODIUM_LOW_POSITION = 0;

    // Elevator motor ID & set positions. Positions are in encoder revolutions.
    public static final int ELEVATOR_MOTOR_ID = 42;
    public static final double ELEVATOR_ENCODER_MAX = -92;
    public static final double ELEVATOR_STOWED_POSITION = 0;
    public static final double ELEVATOR_INTAKE_POSITION = 0;
    public static final double ELEVATOR_SOURCE_POSITION = 0;
    public static final double ELEVATOR_SPEAKER_HIGH_POSITION = 0;
    public static final double ELEVATOR_SPEAKER_LOW_POSITION = 0;
    public static final double ELEVATOR_AMP_POSITION = 0;
    public static final double ELEVATOR_TRAP_POSITION = -80.0;
    public static final double ELEVATOR_SUBWOOFER_POSITION = 0;
    public static final double ELEVATOR_PODIUM_HIGH_POSITION = 0;
    public static final double ELEVATOR_PODIUM_LOW_POSITION = 0;

    // Power Distribution Panel ID
    public static final int PDP_ID = 1;

    // Xbox Controller Ports
    public static final int DRIVER_CONTROLLER_PORT = 0;
    public static final int CODRIVER_CONTROLLER_PORT = 1;

    // LED Strip IDs
    public static final int[] LED_STRIP_ID = {21};
}
