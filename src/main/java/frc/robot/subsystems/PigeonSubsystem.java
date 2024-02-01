package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import com.ctre.phoenix.sensors.WPI_PigeonIMU;

public class PigeonSubsystem extends SubsystemBase {
  WPI_PigeonIMU pigeon;
  public double[] angles = {0, 0, 0};
  public short[] accelAngles = {0, 0, 0};

  public PigeonSubsystem() {
    pigeon = new WPI_PigeonIMU(Constants.PIGEON_ID);
  }

  @Override
  public void periodic() {
    try {
      pigeon.getRawGyro(angles);
      pigeon.getBiasedAccelerometer(accelAngles);
      printDashboard();

    } catch (Exception e) {
      System.out.println(e);
    }
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
