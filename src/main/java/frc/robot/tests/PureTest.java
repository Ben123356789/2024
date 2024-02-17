package frc.robot.tests;

/**
 * Represents a test case that is pure; it does not modify a global state or do any IO operations (filesystem, printing, networking).
 * 
 * The rogrammer must make this distinction themselves. If unsure, look at `SyncTest`.
 */
public class PureTest {
    public Thread thread;
    public String name;
    public PureTest(Thread thread, String name) {
        this.thread = thread;
        this.name = name;
    }
}
