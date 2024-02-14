package frc.robot;

import com.revrobotics.CANSparkBase.ControlType;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {
  private Command m_autonomousCommand;
  private RobotContainer m_robotContainer;
  // PID intake velocity values:
  // P: 0.0002 | I: 0.000001 | D: 0.0 | F: 0.0

  @Override
  public void robotInit() {
    m_robotContainer = new RobotContainer();
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
    
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void disabledExit() {}

  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void autonomousExit() {}

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {}

  @Override
  public void teleopExit() {}

  PIDMotor[] motorArray;

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
    
    motorArray = new PIDMotor[]{
      PIDMotor.makeMotor(10, "climber left", 0 , 0, 0, 0, ControlType.kPosition, 1),
      PIDMotor.makeMotor(11, "climber right", 0 , 0, 0, 0, ControlType.kPosition, 1),
      PIDMotor.makeMotor(12, "elevator", 0 , 0, 0, 0, ControlType.kPosition, 1),
      PIDMotor.makeMotor(13, "flumper", 0 , 0, 0, 0, ControlType.kPosition, 1),
      PIDMotor.makeMotor(14, "shoulder left", 0 , 0, 0, 0, ControlType.kPosition, 1),
      PIDMotor.makeMotor(15, "shoulder right", 0 , 0, 0, 0, ControlType.kPosition, 1),
      PIDMotor.makeMotor(16, "wrist", 0 , 0, 0, 0, ControlType.kPosition, 1),
      PIDMotor.makeMotor(30, "intake bottom", 0 , 0, 0, 0, ControlType.kPosition, 1),
      PIDMotor.makeMotor(31, "intake top", 0 , 0, 0, 0, ControlType.kPosition, 1),
      PIDMotor.makeMotor(32, "shooter bottom", 0 , 0, 0, 0, ControlType.kPosition, 1),
      PIDMotor.makeMotor(33, "shooter top", 0 , 0, 0, 0, ControlType.kPosition, 1)
    };
  }

  int currIndex = 0;
  XboxController testController = new XboxController(Constants.DRIVER_CONTROLLER_PORT);
  

  @Override
  public void testPeriodic() {
    if(testController.getRightBumperPressed() && currIndex < motorArray.length-1){
      motorArray[currIndex].set(0);
      ++currIndex;
    } else if (testController.getLeftBumperPressed() && currIndex > 0){
      motorArray[currIndex].set(0);
      --currIndex;
    }
    motorArray[currIndex].set(testController.getLeftY());
    SmartDashboard.putNumber("Motor Index", currIndex);
    SmartDashboard.putString("Motor Name", motorArray[currIndex].name);
  }

  @Override
  public void testExit() {}
}
