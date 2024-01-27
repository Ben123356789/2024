package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.LimelightHelpers;

public class LimelightSubsystem extends SubsystemBase {
  public LimelightHelpers.LimelightResults llresults;

  public LimelightSubsystem() {
  }

  @Override
  public void periodic() {
    llresults = LimelightHelpers.getLatestResults("");
  }

  public int resultLength(){
    return llresults.targetingResults.targets_Detector.length;
  }
}
