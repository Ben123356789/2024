package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.ExtraMath;

public class LEDSubsystem extends SubsystemBase {
    AddressableLED ledStrip;
    Timer timer;

    final Strip leftStrip;
    final Strip rightStrip;
    public final Strip fullStrip;

    public final Strip quadStrip1;
    public final Strip quadStrip2;
    public final Strip quadStrip3;
    public final Strip quadStrip4;


    public AddressableLEDBuffer showingBuffer;
    final AddressableLEDBuffer onBuffer;
    final AddressableLEDBuffer offBuffer;
    final AddressableLEDBuffer voltageBuffer;

    LimelightSubsystem limelight1;
    PowerDistribution pdp;
    boolean[] conditions;
    int functionIndex = -1;

    public LEDSubsystem(LimelightSubsystem limelight, PowerDistribution pdp) {
        leftStrip = new Strip(6, 0);
        rightStrip = new Strip(7, 13);
        fullStrip = new Strip(0, 13);

        quadStrip1 = new Strip(0, 2);
        quadStrip2 = new Strip(3, 5);
        quadStrip3 = new Strip(6, 8);
        quadStrip4 = new Strip(9, 11);

        int length = fullStrip.length;

        onBuffer = new AddressableLEDBuffer(length);
        offBuffer = new AddressableLEDBuffer(length);
        showingBuffer = new AddressableLEDBuffer(length);
        voltageBuffer = new AddressableLEDBuffer(length);

        ledStrip = new AddressableLED(Constants.LED_STRIP_ID[0]);
        ledStrip.setLength(length);
        ledStrip.start();
        timer = new Timer();

        this.limelight1 = limelight;
        this.pdp = pdp;

        conditions = new boolean[1];
    }

    private class Strip {
        // Both start and end are inclusive
        public final int start;
        public final int end;
        public final int direction;
        public final int length;

        public Strip(int start, int end) {

            this.start = start;
            this.end = end;

            length = Math.abs(start - end) + 1;

            if (start < end) {
                direction = 1;
            } else {
                direction = -1;
            }
        }
    }

    @Override
    public void periodic() {
        checkConditions();
        priorityCheck();
        switch (functionIndex) {
        case 0:
            followNote();
            break;
        default:
            displayVoltage();
            break;
        }
        ledStrip.setData(showingBuffer);
    }

    // Checks conditions for all functions
    public void checkConditions() {
        for (int i = 0; i < conditions.length; i++) {
            conditions[i] = false;
        }
        // if (limelight1.resultLength() > 0) {
        //     conditions[0] = true;
        // }
    }

    // Based on the conditions, decides which module to use
    public void priorityCheck() {
        functionIndex = -1;
        for (int i = 0; i < conditions.length; i++) {
            if (conditions[i]) {
                functionIndex = i;
                break;
            }
        }
    }

    // Sets the strip to one static colour
    public AddressableLEDBuffer setColour(Color color, AddressableLEDBuffer buffer, Strip strip) {
        for (var i = strip.start; i != strip.end + strip.direction; i += strip.direction) {
            safeSetLED(buffer, i, color);
        }
        return buffer;
    }

    // Colours the entire strip orange when a note is visible
    public void seeingNote() {
        setColour(Color.kOrangeRed, showingBuffer, fullStrip);
    }

    // Draws cursors that vary in position and size depending on the location of notes
    public void followNote() {
        setColour(Color.kBlack, showingBuffer, fullStrip);
        // for (int i = 0; i < limelight1.resultLength(); i++) {
        //     int size = (int) ExtraMath.rangeMap(limelight1.getTargets()[i].ta, 0, 1, fullStrip.start, fullStrip.end);
        //     Color color;
        //     if(limelight1.getTargets()[i].className.equals("redbobot")){
        //         color = Color.kRed;
        //     } else if(limelight1.getTargets()[i].className.equals("bluebobot")){
        //         color = Color.kBlue;
        //     } else if (limelight1.resultLargestAreaTarget() == i) {
        //         color = Color.kWhite; 
        //     } else {
        //         color = Color.kOrangeRed;
        //     }
        //     drawCursor(limelight1.getTargets()[i].tx, -29.8, 29.8, fullStrip, showingBuffer, color, size);
        // }
    }

    // Gets voltage from the PDP and displays it as a percentage
    public void displayVoltage() {
        double voltage = pdp.getVoltage();
        final double minVoltage = 9;
        final double maxVoltage = 12;
        double percentageVoltage = (voltage - minVoltage) / (maxVoltage - minVoltage);
        Color color1 = Color.kGreen;
        Color color2 = Color.kBlack;
        twoColourProgressBar(fullStrip, showingBuffer, percentageVoltage, color1, color2);
    }

    // Given two colours, draws the first to a specific percentage of the buffer
    // length, filled in with the 2nd colour
    public void twoColourProgressBar(Strip strip, AddressableLEDBuffer buffer, double percentage, Color color1, Color color2) {
        percentage = ExtraMath.clamp(percentage, 0, 1);
        int numLEDs = (int) (strip.length * percentage);
        setColour(color2, buffer, strip);
        for (var i = strip.start; i != numLEDs * strip.direction + strip.start; i += strip.direction) {
            i = ExtraMath.clamp(i, 0, 14);
            safeSetLED(buffer, i, color1);
        }
    }

    // API for drawing a cursor on the LED strip
    public void drawCursor(double val, double min, double max, Strip strip, AddressableLEDBuffer buffer, Color color, int size) {
        int centerLED = (int) ExtraMath.rangeMap(val, min, max, strip.start, strip.end);
        int halfSize = (int) (size - 1) / 2;
        for (int i = centerLED - halfSize; i <= centerLED + halfSize; i++) {
            safeSetLED(buffer, i, color);
        }
    }

    // Clamps the index of the LED to "safely" set the LED to a buffer
    public void safeSetLED(AddressableLEDBuffer buffer, int index, Color color) {
        int clampedIndex = ExtraMath.clamp(index, 0, buffer.getLength());
        if (index != clampedIndex) {
            System.out.println("!! LED index was out of bounds when trying to setLED !!");
        }
        buffer.setLED(clampedIndex, color);
    }

    public void setRed(){
        setColour(Color.kRed, showingBuffer, quadStrip1);
    }

    public void setBlue(){
        setColour(Color.kBlue, showingBuffer, quadStrip2);
    }

    public void setYellow(){
        setColour(Color.kYellow, showingBuffer, quadStrip3);
    }

    public void setGreen(){
        setColour(Color.kGreen, showingBuffer, quadStrip4);
    }
}
