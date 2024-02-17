package frc.robot.tests;

import frc.robot.ExtraMath;

public abstract class TestSuite {
    double epsilon = 0.01;
    public abstract void addTests();
    /**
     * Asserts that a condition is true. Throws if `cond` is false.
     * @param cond The condition to check.
     */
    public void _assert(boolean cond) throws AssertionError {
        if (!cond) throw new AssertionError();
    }
    /**
     * Asserts that a condition is true. Throws if `cond` is false with a given message.
     * @param cond The condition to check.
     * @param msg The message to give if the condition is false.
     */
    public void _assert(boolean cond, String msg) throws AssertionError {
        if (!cond) throw new AssertionError(msg);
    }
    /**
     * Asserts that two objects are equal. Throws if `l != r`.
     * @param l The left side of the equality check.
     * @param r The right side of the euqality check.
     */
    public<T> void assertEq(T l, T r) throws AssertionError {
        if (l != r) throw new AssertionError("Expected l == r, got `" + l.toString() + "` != `" + r.toString() + "`!");
    }
    /**
     * Asserts that two objects are equal. Throws if `l != r` with a given message.
     * @param l The left side of the equality check.
     * @param r The right side of the euqality check.
     * @param msg The message to give if the condition is false.
     */
    public void assertEq(Object l, Object r, String msg) throws AssertionError {
        if (l != r) throw new AssertionError(msg);
    }
    /**
     * Asserts that two values are within `epsilon` of each other.
     * @param l The first value to check.
     * @param r The second vaule to check.
     * @param epsilon The maximum distance allowed between `l` and `r`.
     */
    public void assertWithin(double l, double r, double epsilon) throws AssertionError {
        if (!ExtraMath.within(l, r, epsilon)) throw new AssertionError(l + " was not within " + epsilon + " of " + r + "!");
    }
    /**
     * Sets the default epsilon value.
     * @param newEpsilon The new epsilon value.
     */
    public void setEpsilon(double newEpsilon) {
        epsilon = newEpsilon;
    }
    /**
     * Asserts that two values are within this test's epsilon of each other.
     * @param l The first value to check.
     * @param r The second vaule to check.
     */
    public void assertClose(double l, double r) throws AssertionError {
        if (!ExtraMath.within(l, r, epsilon)) throw new AssertionError(l + " was not within " + epsilon + " of " + r + "!");
    }
    /**
     * Asserts that two values are within this test's epsilon of each other.
     * @param l The first value to check.
     * @param r The second vaule to check.
     * @param msg The custom message to throw with.
     */
    public void assertClose(double l, double r, String msg) throws AssertionError {
        if (!ExtraMath.within(l, r, epsilon)) throw new AssertionError(msg);
    }
}
