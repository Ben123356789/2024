package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.LimelightHelpers;

public class LimelightSubsystem extends SubsystemBase {
  public LimelightHelpers.LimelightResults llresults;

  public LimelightSubsystem() {
  }

  @Override
  public void periodic() {
    llresults = LimelightHelpers.getLatestResults("");
    printDashboard();
  }

  public int resultLength() {
    return llresults.targetingResults.targets_Detector.length;
  }

  public double resultClosestXAxisTarget(){
    double closest = 100;
    for(int i = 0; i < resultLength(); i++){
      if(llresults.targetingResults.targets_Detector[i].tx < closest){
        closest = llresults.targetingResults.targets_Detector[i].tx;
      }
    }
    return closest;
  }

  public void printDashboard() {
    SmartDashboard.putNumber("LL # of Results", resultLength());
    for (int i = 0; i < resultLength(); i++) {
      SmartDashboard.putString("LL #"+(i+1)+" Object Type", llresults.targetingResults.targets_Detector[i].className);
      SmartDashboard.putNumber("LL #"+(i+1)+" Confidence", llresults.targetingResults.targets_Detector[i].confidence);
      SmartDashboard.putNumber("LL #"+(i+1)+" X-Offset", llresults.targetingResults.targets_Detector[i].tx);
      SmartDashboard.putNumber("LL #"+(i+1)+" Y-Offset", llresults.targetingResults.targets_Detector[i].ty);
      SmartDashboard.putNumber("LL #"+(i+1)+" Area %", llresults.targetingResults.targets_Detector[i].ta);
    }
  }
}
