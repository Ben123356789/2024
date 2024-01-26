package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LEDSubsystem extends SubsystemBase {
    AddressableLED ledStrip;
    Timer timer;

    final Strip leftStrip;
    final Strip rightStrip;
    final Strip fullStrip;

    AddressableLEDBuffer showingBuffer;
    final AddressableLEDBuffer onBuffer;
    final AddressableLEDBuffer offBuffer;
    final AddressableLEDBuffer voltageBuffer;

    boolean isSeeingNote;
    LimelightSubsystem limelight1;
    PowerDistribution pdp;

    public LEDSubsystem(LimelightSubsystem limelight, PowerDistribution pdp) {
        leftStrip = new Strip(6, 0);
        rightStrip = new Strip(7, 13);
        fullStrip = new Strip(0, 13);
        int length = fullStrip.length;

        onBuffer = new AddressableLEDBuffer(length);
        offBuffer = new AddressableLEDBuffer(length);
        showingBuffer = new AddressableLEDBuffer(length);
        voltageBuffer = new AddressableLEDBuffer(length);

        ledStrip = new AddressableLED(0);
        ledStrip.setLength(length);
        ledStrip.start();
        timer = new Timer();

        this.limelight1 = limelight;
        this.pdp = pdp;
    }

    private class Strip {
        // Both start and end are inclusive (we love inclusivity)
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
        ledStrip.setData(showingBuffer);
    }

    public void checkConditions() {
        if (limelight1.resultLength() > 0) {
            isSeeingNote = true;
        } else{
            isSeeingNote = false;
        }
    }

    public void priorityCheck() {
        if (isSeeingNote) {
            seeingNote();
        } else {
            displayVoltage();
        }
    }

    public AddressableLEDBuffer setColour(Color color, AddressableLEDBuffer buffer, Strip strip) {
        for (var i = strip.start; i != strip.end + strip.direction; i += strip.direction) {
            buffer.setLED(i, color);
        }
        return buffer;
    }

    public void seeingNote() {
        setColour(Color.kOrangeRed, showingBuffer, fullStrip);
    }

    public void displayVoltage() {
        double voltage = pdp.getVoltage();
        final double minVoltage = 9;
        final double maxVoltage = 12;
        double percentageVoltage = (voltage - minVoltage) / (maxVoltage - minVoltage);
        Color color1 = Color.kGreen;
        Color color2 = Color.kBlack;
        twoColourProgressBar(leftStrip, showingBuffer, percentageVoltage, color1, color2);
    }

    // Given two colours, draws the first to a specific percentage of the buffer
    // length, filled in with the 2nd colour
    public void twoColourProgressBar(Strip strip, AddressableLEDBuffer buffer,
            double percentage, Color color1, Color color2) {
        if (percentage > 1) {
            percentage = 1;
        }
        if (percentage < 0) {
            percentage = 0;
        }
        int numLEDs = (int) (strip.length * percentage);
        setColour(color2, buffer, strip);
        for (var i = strip.start; i != numLEDs * strip.direction + strip.start; i += strip.direction) {
            buffer.setLED(i, color1);
        }
    }
}
