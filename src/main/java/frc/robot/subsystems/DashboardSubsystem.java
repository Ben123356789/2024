package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DashboardSubsystem extends SubsystemBase {
  public enum DashState{
    Off, PV, Competition, PID, Temp;
  }

  DashState state = Constants.DASH_STATE;
  ArmSubsystem arm;
  ShooterSubsystem shooter;
  ClimberSubsystem climber;
  FloorIntakeSubsystem floorIntake;

  public DashboardSubsystem(ArmSubsystem arm, ShooterSubsystem shooter, ClimberSubsystem climber, FloorIntakeSubsystem floorIntake) {
    this.arm = arm;
    this.shooter = shooter;
    this.climber = climber;
    this.floorIntake = floorIntake;
  }

  @Override
  public void periodic() {
    switch(state){
      case PV:
        putPV();
        break;
      case Competition:
        break;
      case PID:
        putPIDF();
        break;
      case Temp:
        temp();
        break;
      case Off:
      default:
        break;
    }
  }

  public void putPV(){
    arm.leftShoulderMotor.putPV();
    arm.wristMotor.putPV();
    arm.elevatorMotor.putPV();
    shooter.shooterTop.putPV();
    shooter.shooterBottom.putPV();
    shooter.intakeTop.putPV();
    shooter.intakeBottom.putPV();
    climber.leftMotor.putPV();
    climber.rightMotor.putPV();
  }

  public void putPIDF(){
    arm.leftShoulderMotor.putPIDF();
    arm.wristMotor.putPIDF();
    arm.elevatorMotor.putPIDF();
    shooter.shooterTop.putPIDF();
    shooter.shooterBottom.putPIDF();
    shooter.intakeTop.putPIDF();
    shooter.intakeBottom.putPIDF();
    climber.leftMotor.putPIDF();
    climber.rightMotor.putPIDF();
  }

  public void temp(){
    SmartDashboard.putNumber("Shooter Top Current", shooter.shooterTop.getCurrent());
    SmartDashboard.putNumber("Shooter Bottom Current", shooter.shooterBottom.getCurrent());
  }
}
