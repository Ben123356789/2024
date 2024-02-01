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

  // Returns the number of targets
  public int resultLength() {
    return llresults.targetingResults.targets_Detector.length;
  }

  // Returns the target that is closest to the centre on the X-axis
  public double resultClosestXAxisTarget() {
    double closest = 100;
    for (int i = 0; i < resultLength(); i++) {
      if (llresults.targetingResults.targets_Detector[i].tx < closest) {
        closest = llresults.targetingResults.targets_Detector[i].tx;
      }
    }
    return closest;
  }

  // Returns the index of the target with the largest area
  public int resultLargestAreaTarget() {
    int largest = 0;
    double size = 0;
    for (int i = 0; i < resultLength(); i++) {
      if (llresults.targetingResults.targets_Detector[i].ta > size) {
        size = llresults.targetingResults.targets_Detector[i].ta;
        largest = i;
      }
    }
    return largest;
  }

  // Returns an array of all target values
  public LimelightHelpers.LimelightTarget_Detector[] getTargets() {
    return llresults.targetingResults.targets_Detector;
  }

  // Prints various values of every target
  public void printDashboard() {
    SmartDashboard.putNumber("LL # of Results", resultLength());
    for (int i = 0; i < resultLength(); i++) {
      SmartDashboard.putString("LL #" + (i + 1) + " Object Type",
          llresults.targetingResults.targets_Detector[i].className);
      SmartDashboard.putNumber("LL #" + (i + 1) + " Confidence",
          llresults.targetingResults.targets_Detector[i].confidence);
      SmartDashboard.putNumber("LL #" + (i + 1) + " X-Offset", llresults.targetingResults.targets_Detector[i].tx);
      SmartDashboard.putNumber("LL #" + (i + 1) + " Y-Offset", llresults.targetingResults.targets_Detector[i].ty);
      SmartDashboard.putNumber("LL #" + (i + 1) + " Area %", llresults.targetingResults.targets_Detector[i].ta);
    }
  }
}
