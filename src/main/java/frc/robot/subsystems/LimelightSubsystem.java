package frc.robot.subsystems;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.util.datalog.DoubleLogEntry;
import edu.wpi.first.wpilibj.DataLogManager;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LimelightSubsystem extends SubsystemBase{
    NetworkTable limelightVals;
    public enum LimeState {
        GREEN,
        APRIL,
        OFF,
    }
    LimeState lState;
    public double tx; //Target X-Axis Offset, Range -29.8 to 29.8
    public double ty; //Target Y-Axis Offset, Range -24.85 - 24.85
    public double tv; //Bool of seeing any targets, Return 0 or 1
    public double ta; //Area as percentage of screen, Range 0% - 100%
    public double tid; //Gets current ID of target
    public double getpipe; //Current pipeline, Range: 0 - 9

    DoubleLogEntry txLogEntry;
    DoubleLogEntry tyLogEntry;
    DoubleLogEntry tvLogEntry;
    DoubleLogEntry taLogEntry;
    DoubleLogEntry tidLogEntry;
    public LimelightSubsystem(){
         limelightVals = NetworkTableInstance.getDefault().getTable("limelight");

        try {
            txLogEntry = new DoubleLogEntry(DataLogManager.getLog(), "tx");
            tyLogEntry = new DoubleLogEntry(DataLogManager.getLog(), "ty");
            taLogEntry = new DoubleLogEntry(DataLogManager.getLog(), "tv");
            tvLogEntry = new DoubleLogEntry(DataLogManager.getLog(), "ta");
            tidLogEntry = new DoubleLogEntry(DataLogManager.getLog(), "tid");

        } catch (Exception e) {}
    }

    @Override public void periodic() {
        // Updates limelight values, runs constantly
        tx = printLimes("X-Axis Offset", "tx");
        ty = printLimes("Y-Axis Offset", "ty");
        ta = printLimes("% of Screen", "ta");
        if(SmartDashboard.getNumber("% of Screen PR",0)<limelightVals.getEntry("ta").getDouble(0)){
            SmartDashboard.putNumber("% of Screen PR",limelightVals.getEntry("ta").getDouble(0));
        }
        tv = printLimes("Target?", "tv");
        tid = printLimes("Target ID","tid");
        getpipe = printLimes("Pipeline","getpipe");
    }

    public double printLimes(String title, String key) {
        // Prints to smartdashboard and returns values
        double limelightvalue = limelightVals.getEntry(key).getDouble(0);
        SmartDashboard.putNumber(title, limelightvalue);
        return limelightvalue;
    }

    public void setPipeline(LimeState ligState) {
        lState = ligState;
        if (ligState == LimeState.OFF) {
            limelightVals.getEntry("pipeline").setNumber(0);
        } else if (ligState == LimeState.GREEN) {
            limelightVals.getEntry("pipeline").setNumber(1);
        } else {
            limelightVals.getEntry("pipeline").setNumber(2);
        }
    }
}
