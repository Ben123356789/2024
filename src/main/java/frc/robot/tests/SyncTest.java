package frc.robot.tests;

public class SyncTest {
    public Runnable target;
    public String name;
    public SyncTest(Runnable target, String name) {
        this.target = target;
        this.name = name;
    }
}
