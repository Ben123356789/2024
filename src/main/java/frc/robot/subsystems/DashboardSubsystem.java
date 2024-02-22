// package frc.robot.subsystems;

// import edu.wpi.first.wpilibj2.command.SubsystemBase;

// public class DashboardSubsystem extends SubsystemBase {
//   public enum DashState{
//     Off, Debug, Competition, PID;
//   }

//   DashState state = Constants.DASH_STATE;
//   ArmSubsystem arm;
//   ShooterSubsystem shooter;
//   ClimberSubsystem climber;
//   FloorIntakeSubsystem floorIntake;

//   public DashboardSubsystem(ArmSubsystem arm, ShooterSubsystem shooter, ClimberSubsystem climber, FloorIntakeSubsystem floorIntake) {
//     this.arm = arm;
//     this.shooter = shooter;
//     this.climber = climber;
//     this.floorIntake = floorIntake;
//   }
//   @Override
//   public void periodic() {
//     switch(state){
//       case DashState.Debug:
//         putPV();
//         break;
//       case DashState.Competition:
//       case DashState.PID:
//       case DashState.Off:
//       default:
//     }
//     )
//   }

//   public putPV(){

//   }
// }
