package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.sensors.WPI_PigeonIMU;

public class PigeonSubsystem extends SubsystemBase {
  WPI_PigeonIMU pigeon;
  public double[] angles = {0,0,0};
  public short[] accelAngles = {0,0,0};
  
  public PigeonSubsystem() {
    pigeon = new WPI_PigeonIMU(1);
  }

  @Override
  public void periodic() {
    pigeon.getRawGyro(angles);
    pigeon.getBiasedAccelerometer(accelAngles);
    printDashboard();
  }

  public void printDashboard(){
    SmartDashboard.putNumber("Pigeon X", angles[0]);
    SmartDashboard.putNumber("Pigeon Y", angles[1]);
    SmartDashboard.putNumber("Pigeon Z", angles[2]);
    SmartDashboard.putNumber("Pigeon Acc X", accelAngles[0]);
    SmartDashboard.putNumber("Pigeon Acc Y", accelAngles[1]);
    SmartDashboard.putNumber("Pigeon Acc Z", accelAngles[2]);
    SmartDashboard.putNumber("Pigeon Current Temp [C]", pigeon.getTemp());
  }
}
