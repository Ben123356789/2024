package frc.robot.tests;

import frc.robot.ExtraMath;

public class ExtraMathTests extends TestSuite {
    @Override public void addTests() {
        TestFramework.add(this::averageTest, "ExtraMath::average test");
        TestFramework.add(this::clampDoubleTest, "ExtraMath::clamp(double) test");
        TestFramework.add(this::degreeDistanceOptimal, "ExtraMath::degreeDistance optimal results");        
        TestFramework.add(this::remEuclidWellDefined, "ExtraMath::remEuclid well defined");
    }
    @TestCase public void averageTest() {
        assertClose(ExtraMath.average(1.0, 2.0, 3.0, 4.0, 5.0), 3.0, "ExtraMath::average fail");
    }
    @TestCase public void clampIntTest() {
        assertEq(ExtraMath.clamp(-2, 2, -5), -2, "ExtraMath::clamp(int) low fail");
        assertEq(ExtraMath.clamp(-2, 2, 5), 2, "ExtraMath::clamp(int) high fail");
        assertEq(ExtraMath.clamp(-2, 2, 1), 1, "ExtraMath::clamp(int) in range fail");
        assertEq(ExtraMath.clamp(2, -2, 1), 1, "ExtraMath::clamp(int) swap start end fail");
    }
    @TestCase public void clampDoubleTest() {
        assertClose(ExtraMath.clamp(-2.0, 2.0, -5.0), -2.0, "ExtraMath::clamp(double) low fail");
        assertClose(ExtraMath.clamp(-2.0, 2.0, 5.0), 2.0, "ExtraMath::clamp(double) high fail");
        assertClose(ExtraMath.clamp(-2.0, 2.0, 1.0), 1.0, "ExtraMath::clamp(double) in range fail");
        assertClose(ExtraMath.clamp(2.0, -2.0, 1.0), 1.0, "ExtraMath::clamp(double) bad start & end correction fail");
    }
    @TestCase public void degreeDistanceOptimal() {
        assertClose(ExtraMath.degreeDistance(45, 90), 45.0, "simple positive distance fail");
        assertClose(ExtraMath.degreeDistance(90, 45), -45.0, "simple negative distance fail");
        assertClose(ExtraMath.degreeDistance(90, 90), 0.0, "zero distance fail");
        assertClose(ExtraMath.degreeDistance(45, 315), -90.0, "wrap under zero fail");
        assertClose(ExtraMath.degreeDistance(315, 45), 90.0, "wrap over 360 fail");

        assertClose(ExtraMath.degreeDistance(-45, -90), -45.0, "simple positive distance (negative) fail");
        assertClose(ExtraMath.degreeDistance(-90, -45), 45.0, "simple negative distance (negative) fail");
        assertClose(ExtraMath.degreeDistance(-90, -90), 0.0, "zero distance (negative) fail");
        assertClose(ExtraMath.degreeDistance(-45, -315), 90.0, "wrap under zero (negative) fail");
        assertClose(ExtraMath.degreeDistance(-315, -45), -90.0, "wrap over 360 (negative) fail");
    }
    @TestCase public void remEuclidWellDefined() {
        assertClose(ExtraMath.remEuclid(40, 7), 5.0, "p % p fail");
        assertClose(ExtraMath.remEuclid(40, -7), 5.0, "p % n fail");
        assertClose(ExtraMath.remEuclid(-40, 7), 2.0, "n % p fail");
        assertClose(ExtraMath.remEuclid(-40, -7), 2.0, "n % n fail");

        assertClose(ExtraMath.remEuclid(40, 8), 0.0, "p % p perfect division fail");
        assertClose(ExtraMath.remEuclid(40, -8), -0.0, "p % n perfect division fail");
        assertClose(ExtraMath.remEuclid(-40, 8), 0.0, "n % p perfect division fail");
        assertClose(ExtraMath.remEuclid(-40, -8), -0.0, "n % n perfect division fail");
    }
}
