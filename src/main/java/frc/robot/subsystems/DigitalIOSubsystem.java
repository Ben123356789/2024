package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.PIDMotor.ExtraIdleMode;

public class DigitalIOSubsystem extends SubsystemBase{
    DigitalInput zeroEncodersButton;
    DigitalInput brakeModeButton;
    // Timer buttonCooldown;
    ArmSubsystem arm;
    ShooterSubsystem shooter;
    FlumperSubsystem flumper;
    
    public DigitalIOSubsystem(ArmSubsystem arm, ShooterSubsystem shooter, FlumperSubsystem flumper){
        zeroEncodersButton = new DigitalInput(1);
        brakeModeButton = new DigitalInput(2);
        this.arm = arm;
        this.shooter = shooter;
        this.flumper = flumper;
        // buttonCooldown.start();
    }

    @Override
    public void periodic() {
        if(DriverStation.isDisabled()){
            // boolean brake, encoder;
            // brake = brakeModeButton.get();
            // encoder = zeroEncodersButton.get();
            // SmartDashboard.putString("button values:", brake + " & " + encoder);
            
            // if the button is pressed
            if(!brakeModeButton.get() && 
               arm.getIdleMode() != ExtraIdleMode.kOther || arm.getIdleMode() == ExtraIdleMode.kBrake &&
               shooter.getIdleMode() != ExtraIdleMode.kOther || shooter.getIdleMode() == ExtraIdleMode.kBrake &&
               flumper.getIdleMode() != ExtraIdleMode.kOther || flumper.getIdleMode() == ExtraIdleMode.kBrake

               /*&& buttonCooldown.get() > 1.00*/){
                // System.out.println("button!!!");
                arm.disableBrakeMode();
                shooter.disableBrakeMode();
                flumper.disableBrakeMode();
                // buttonCooldown.restart();
            } else if (brakeModeButton.get() && 
               arm.getIdleMode() != ExtraIdleMode.kOther || arm.getIdleMode() == ExtraIdleMode.kCoast &&
               shooter.getIdleMode() != ExtraIdleMode.kOther || shooter.getIdleMode() == ExtraIdleMode.kCoast &&
               flumper.getIdleMode() != ExtraIdleMode.kOther || flumper.getIdleMode() == ExtraIdleMode.kCoast) {
                arm.enableBrakeMode();
                shooter.enableBrakeMode();
                flumper.enableBrakeMode();
            }

            if(!zeroEncodersButton.get()/*&& buttonCooldown.get() > 1.00*/){
                arm.zeroEncoders();
                shooter.zeroEncoders();
                flumper.zeroEncoders();
                // buttonCooldown.restart();
            }
        } else {
            if(arm.getIdleMode() != ExtraIdleMode.kOther || arm.getIdleMode() == ExtraIdleMode.kCoast &&
               shooter.getIdleMode() != ExtraIdleMode.kOther || shooter.getIdleMode() == ExtraIdleMode.kCoast &&
               flumper.getIdleMode() != ExtraIdleMode.kOther || flumper.getIdleMode() == ExtraIdleMode.kCoast) {
                    arm.enableBrakeMode();
                    shooter.enableBrakeMode();
                    flumper.enableBrakeMode();
               }
        }
    }
}
