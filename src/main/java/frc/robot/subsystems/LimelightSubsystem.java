package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.LimelightHelpers;
import frc.robot.LimelightHelpers.LimelightResults;
import frc.robot.LimelightHelpers.LimelightTarget_Fiducial;

// a: (0.83, -0.95)
// b: (-0.02, 0.4)
// SLOP; 1.35 / -0.85

// y = mx + b
// 0.4 = (1.35 / -0.85)(-0.02) + b
// 0.4 - b = (1.35 / -0.85)(-0.02)
// -b = (1.35 / -0.85)(-0.02) - 0.4
// b = 0.4 - (1.35 / -0.85)(-0.02)

// y = mx + b
// b = y - mx
// b = -0.95 - (1.35 / -0.85) * (0.83)


// m = 1.35 / -0.85
// b = 0.3682352941176472

public class LimelightSubsystem extends SubsystemBase implements Runnable {
  public LimelightHelpers.LimelightResults llresults;
  public boolean limelightRotation = false;
  public double limelightRotationMagnitude = 0;
  String name;

  public LimelightSubsystem(String name) {
    this.name = name;
    llresults = new LimelightResults();
    new Thread(this, "`" + name + "` Thread").start();
  }

  @Override
  public void periodic() {
  }

  // Returns the number of targets
  public int resultLength() {
    synchronized (this) {
      return llresults.targetingResults.targets_Detector.length;
    }
  }

  // Returns the target that is closest to the centre on the X-axis
  public double resultClosestXAxisTarget() {
    synchronized (this) {

      double closest = 100;
      for (int i = 0; i < resultLength(); i++) {
        if (llresults.targetingResults.targets_Detector[i].tx < closest) {
          closest = llresults.targetingResults.targets_Detector[i].tx;
        }
      }
      return closest;
    }
  }

  // Returns the index of the target with the largest area
  public int resultLargestAreaTarget() {
    synchronized (this) {

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
  }

  // Returns an array of all target values
  public LimelightHelpers.LimelightTarget_Detector[] getTargets() {
    synchronized (this) {

      return llresults.targetingResults.targets_Detector;
    }
  }

  // check for null later
  public LimelightTarget_Fiducial getDataForId(int idToFind) {
    synchronized (this) {

      for (int i = 0; i < llresults.targetingResults.targets_Fiducials.length; i++) {
        LimelightTarget_Fiducial r = llresults.targetingResults.targets_Fiducials[i];
        if (r.fiducialID == idToFind) {
          return r;
        }
      }
      return null;
    }
  }

  LimelightTarget_Fiducial tag;

  String dashboardKey() {
    return "`" + name + "`";
  }

  // Prints various values of every target
  public void printDashboard() {
    synchronized (this) {

      // SmartDashboard.putNumber(dashboardKey() + " # of Results", resultLength());
      // for (int i = 0; i < resultLength(); i++) {
      //   SmartDashboard.putString(dashboardKey() + " #" + (i + 1) + " Object Type",
      //       llresults.targetingResults.targets_Detector[i].className);
      //   SmartDashboard.putNumber(dashboardKey() + " #" + (i + 1) + " Confidence",
      //       llresults.targetingResults.targets_Detector[i].confidence);
      //   SmartDashboard.putNumber(dashboardKey() + " #" + (i + 1) + " X-Offset",
      //       llresults.targetingResults.targets_Detector[i].tx);
      //   SmartDashboard.putNumber(dashboardKey() + " #" + (i + 1) + " Y-Offset",
      //       llresults.targetingResults.targets_Detector[i].ty);
      //   SmartDashboard.putNumber(dashboardKey() + " #" + (i + 1) + " Area %",
      //       llresults.targetingResults.targets_Detector[i].ta);

      // }

      tag = getDataForId(7);
      if (tag == null) {
        tag = getDataForId(4);
      }

      if (tag != null) {
        SmartDashboard.putNumber("speaker tag x position", tag.tx);
        SmartDashboard.putNumber("speaker tag y position", tag.ty);
      } else {
        SmartDashboard.putNumber("speaker tag x position", 999);
        SmartDashboard.putNumber("speaker tag y position", 999);
      }
    }
  }

  public void setPipeline(int pipeline) {
    LimelightHelpers.setPipelineIndex(name, pipeline);
  }

  @Override
  public void run() {
    while (true) {
      synchronized (this) {
        llresults = LimelightHelpers.getLatestResults(name);
      }
      // printDashboard();
      try {
        Thread.sleep(10);
      } catch (InterruptedException iex) {
      }
    }
  }
}
