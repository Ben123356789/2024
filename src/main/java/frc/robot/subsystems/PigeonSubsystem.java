package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.sensors.WPI_PigeonIMU;

public class PigeonSubsystem extends SubsystemBase {
  WPI_PigeonIMU pigeon;
  public double[] angles = {1, 2, 3};
  public short[] accelAngles = {0, 0, 0};

  public PigeonSubsystem() {
    pigeon = new WPI_PigeonIMU(Constants.PIGEON_ID);
  }

  @Override
  public void periodic() {
    try {
      retrieveValues();
      printDashboard();
    } catch (Exception e) {
      SmartDashboard.putString("CTRE Last Error", e.getMessage());
    }
  }

  public void retrieveValues() throws Exception {
    ErrorCode err = pigeon.getRawGyro(angles);
    if (err != ErrorCode.OK) throw new Exception(err.toString());
    err = pigeon.getBiasedAccelerometer(accelAngles);
  }

  public void printDashboard() {
    SmartDashboard.putNumber("Pigeon X", angles[0]);
    SmartDashboard.putNumber("Pigeon Y", angles[1]);
    SmartDashboard.putNumber("Pigeon Z", angles[2]);
    SmartDashboard.putNumber("Pigeon Acc X", accelAngles[0]);
    SmartDashboard.putNumber("Pigeon Acc Y", accelAngles[1]);
    SmartDashboard.putNumber("Pigeon Acc Z", accelAngles[2]);
    SmartDashboard.putNumber("Pigeon Current Temp [C]", pigeon.getTemp());
  }
}
