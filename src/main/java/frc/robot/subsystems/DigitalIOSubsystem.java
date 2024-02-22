package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DigitalIOSubsystem extends SubsystemBase {
    DigitalInput zeroEncodersButton;
    DigitalInput brakeModeButton;
    // Timer buttonCooldown;
    ArmSubsystem arm;
    ShooterSubsystem shooter;
    FloorIntakeSubsystem floorIntake;
    boolean lastIterationBrakeButtonState, lastIterationZeroEncButtonState = false;

    public DigitalIOSubsystem(ArmSubsystem arm, ShooterSubsystem shooter, FloorIntakeSubsystem floorIntake) {
        zeroEncodersButton = new DigitalInput(1);
        brakeModeButton = new DigitalInput(2);
        this.arm = arm;
        this.shooter = shooter;
        this.floorIntake = floorIntake;
        // buttonCooldown.start();
    }

    @Override
    public void periodic() {
        boolean brake, encoder;
        brake = !brakeModeButton.get();
        encoder = !zeroEncodersButton.get();

        if (DriverStation.isDisabled()) {
            SmartDashboard.putString("button values:", brake + " & " + encoder);
            if (brake && !lastIterationBrakeButtonState) {
                arm.disableBrakeMode();
                shooter.disableBrakeMode();
                floorIntake.disableBrakeMode();
            } else if (!brake && lastIterationBrakeButtonState) {
                arm.enableBrakeMode();
                shooter.enableBrakeMode();
                floorIntake.enableBrakeMode();
            }

            if (encoder && !lastIterationZeroEncButtonState) {
                arm.zeroEncoders();
                shooter.zeroEncoders();
                floorIntake.zeroEncoders();
            }
        }/* else {
            arm.enableBrakeMode();
            shooter.enableBrakeMode();
            floorIntake.enableBrakeMode();
        }*/
        lastIterationBrakeButtonState = brake;
        lastIterationZeroEncButtonState = encoder;
    }
}
