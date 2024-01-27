package frc.robot;

public final class Constants {
    private Constants() {}

    public static final int FLUMPER_ID = 0;

    public static final int ELEVATOR_ID = 0;
    public static final double ELEVATOR_LOWEST = 0.0;
    public static final double ELEVATOR_HIGHEST = 0.0;
    public static final double ELEVATOR_HEIGHT = 9.0;

    public static final int SHOOTER_TOP_ID = 14;
    public static final int SHOOTER_BOTTOM_ID = 15;
    public static final int INTAKE_TOP_ID = 16;
    public static final int INTAKE_BOTTOM_ID = 17;

    public static final int CLIMBER_LEFT_ID = 0;
    public static final int CLIMBER_RIGHT_ID = 0;

    public static final int SHOULDER_LEFT_MOTOR_ID = 0;
    public static final int SHOULDER_RIGHT_MOTOR_ID = 0;
    //Following values are in degrees
    public static final int SHOULDER_STOWED_POSITION = 0;
    public static final int SHOULDER_INTAKE_POSITION = 0;
    public static final int SHOULDER_SOURCE_POSITION = 0;
    public static final int SHOULDER_SPEAKER_HIGH_POSITION = 0;
    public static final int SHOULDER_SPEAKER_LOW_POSITION = 0;
    public static final int SHOULDER_AMP_POSITION = 0;
    public static final int SHOULDER_TRAP_POSITION = 0;

    public static final int WRIST_MOTOR_ID = 0;
    //Following values are in degrees
    public static final int WRIST_STOWED_POSITION = 0;
    public static final int WRIST_INTAKE_POSITION = 0;
    public static final int WRIST_SOURCE_POSITION = 0;
    public static final int WRIST_SPEAKER_HIGH_POSITION = 0;
    public static final int WRIST_SPEAKER_LOW_POSITION = 0;
    public static final int WRIST_AMP_POSITION = 0;
    public static final int WRIST_TRAP_POSITION = 0;

    public static final int PDP_ID = 1;
    public static final int PIGEON_ID = 18;

    public static final int SHOULDER_ENCODER_TICK_PER_DEG = 0;
    public static final int WRIST_ENCODER_TICK_PER_DEG = 0;


    public double ticksToDegrees(double ticks, double tpd){
        return ticks/tpd;
    }

    public double degreesToTicks(double degrees, double tpd){
        return degrees*tpd;
    }
}
