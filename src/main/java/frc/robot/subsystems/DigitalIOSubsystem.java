package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DigitalIOSubsystem extends SubsystemBase{
    DigitalInput resetEncodersButton;
    DigitalInput brakeModeButton;
    Timer buttonCooldown;
    ArmSubsystem arm;
    ShooterSubsystem shooter;
    FlumperSubsystem flumper;
    
    public DigitalIOSubsystem(ArmSubsystem arm, ShooterSubsystem shooter, FlumperSubsystem flumper){
        resetEncodersButton = new DigitalInput(1);
        brakeModeButton = new DigitalInput(2);
        this.arm = arm;
        this.shooter = shooter;
        this.flumper = flumper;
        buttonCooldown.start();
    }

    @Override
    public void periodic() {
        if(DriverStation.isDisabled()){
            if(brakeModeButton.get()/*&& buttonCooldown.get() > 1.00*/){
                arm.disableBrakeMode();
                shooter.disableBrakeMode();
                flumper.disableBrakeMode();
                // buttonCooldown.restart();
            } else{
                arm.enableBrakeMode();
                shooter.enableBrakeMode();
                flumper.enableBrakeMode();
            }
        } else {
            arm.enableBrakeMode();
            shooter.enableBrakeMode();
            flumper.enableBrakeMode();
        }
    }
}
