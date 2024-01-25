package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class ShooterSubsystem extends SubsystemBase {
  CANSparkMax shooterTop;
  CANSparkMax shooterBottom;
  CANSparkMax intakeTop;
  CANSparkMax intakeBottom;
  Timer shooterTimer;
  int state;
  double intakeBottomStartPos;
  double intakeTopStartPos;
  public boolean shootWhenReady;
  
  public ShooterSubsystem() {
    shooterTop = new CANSparkMax(Constants.ShooterTopID, MotorType.kBrushless);
    shooterBottom = new CANSparkMax(Constants.ShooterBottomID, MotorType.kBrushless);
    intakeTop = new CANSparkMax(Constants.IntakeTopID, MotorType.kBrushless);
    intakeBottom = new CANSparkMax(Constants.IntakeBottomID, MotorType.kBrushless);
    shooterTimer = new Timer();
    state = 0;
    intakeBottomStartPos = 0;
    intakeTopStartPos = 0;

    //room for inverse
  }

  @Override
  public void periodic() {
    if(shootWhenReady && state == 0){
        state = 1;
    }
    manageShooterRollers();
    printDashboard();
  }
  
  public void intakeInit() {
    shooterTimer.restart();
  }



  public void manageShooterRollers(){
    switch(state){
        case 1:
            intakeBottomStartPos = intakeBottom.getEncoder().getPosition();
            intakeTopStartPos = intakeTop.getEncoder().getPosition();
            state = 2;
            break;
        case 2:
            if(intakeBottomStartPos - intakeBottom.getEncoder().getPosition() >= 1.5 || intakeTopStartPos - intakeTop.getEncoder().getPosition() >= 1.5){
                state = 3;
            } else{
                intakeBottom.set(-0.25);
                intakeTop.set(-0.25);
            }
            intakeBottom.getEncoder().getPosition();
            break;
        case 3:
            if(shooterTop.getEncoder().getVelocity() >= 10000 || shooterBottom.getEncoder().getVelocity() >= 10000){
                shooterTimer.restart();
                state = 4;
            } else{
                shooterBottom.set(1);
                shooterTop.set(1);
            }
        case 4:
            if(shooterTimer.get() < 1.5){
                intakeBottom.set(1);
                intakeTop.set(1);
                shooterBottom.set(1);
                shooterTop.set(1);
            } else{
                state = 0;
                shootWhenReady = false;
            }
            break;
        default:
            intakeBottom.set(0);
            intakeTop.set(0);
            shooterBottom.set(0);
            shooterTop.set(0);
            break;
    }
  }

  public void intakeNote(){
    if(shooterTimer.get() < 0.5){
        intakeBottom.set(0.25);
        intakeTop.set(0.25);
    } else{
        intakeBottom.set(0);
        intakeTop.set(0);
    }
  }

  public void printDashboard(){
    //SmartDashboard.putNumber("Pigeon X", angles[0]);
  }
}
