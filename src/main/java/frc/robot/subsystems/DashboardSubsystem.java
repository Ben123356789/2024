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
//   FlumperSubsystem flumper;

//   public DashboardSubsystem(ArmSubsystem arm, ShooterSubsystem shooter, ClimberSubsystem climber, FlumperSubsystem flumper) {
//     this.arm = arm;
//     this.shooter = shooter;
//     this.climber = climber;
//     this.flumper = flumper;
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
