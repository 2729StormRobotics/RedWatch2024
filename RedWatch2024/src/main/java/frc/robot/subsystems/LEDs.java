package frc.robot.subsystems;

import com.ctre.phoenix.led.Animation;
import com.ctre.phoenix.led.CANdle;
import com.ctre.phoenix.led.CANdle.LEDStripType;
import com.ctre.phoenix.led.CANdle.VBatOutputMode;
import com.ctre.phoenix.led.CANdleConfiguration;
import com.ctre.phoenix.led.ColorFlowAnimation;
import com.ctre.phoenix.led.ColorFlowAnimation.Direction;
import com.ctre.phoenix.led.LarsonAnimation;
import com.ctre.phoenix.led.LarsonAnimation.BounceMode;
import com.ctre.phoenix.led.RainbowAnimation;
import com.ctre.phoenix.led.SingleFadeAnimation;
import com.ctre.phoenix.led.StrobeAnimation;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.LightsConstants;
/*
 * USAGE:
 * ANYWHERE YOU WANT TO USE LEDS
 * import the LEDSegment
 * then run:
 * LEDSegment.StatusLEDs.(whatever command)
 * if any questions, ask Krithik
 * 
 * remember, the LED count includes the 8 onboard candle LEDS.
 * 
 * To SET A GENERAL COLOR
 * USE LEDs.(whatever color) as a parameter
 * 
 * To use a SPECIFIC color do:
 * new Color(0, 0, 0) as a parameter
 * 
 * Request Note Ground: setColor(orange)
 * Request Note: setBandAnimationf(orange)
 * Aligning to speaker: setColor(red)
 * Aligned to speaker: setColor(green)
 * Default: setFadeAnimation(red)
 * Partymode: setRainbowAnimation(1)
 * 
 * MATRIX USAGE:
 * Create an order for the matrix in the matrixPresets.java file
 * use the command setMatrixToGrid with your preset as a parameter
 * if needs to be offset make sure to offset both the led segment AND 
 * variable "no" in the setMatrixToGrid function
 */

public class LEDs extends SubsystemBase {
    public static final CANdle candle = new CANdle(LightsConstants.CANDLE_PORT);
    private static LEDs leds;
    // Team colors
    public static final Color red = new Color(255, 0, 0);
    public static final Color black = new Color(0, 0, 0);
    public static final Color brown = new Color(139,69,19);
    int r=0;
    int g=0;
    int b=0;
    public Color ElasticColor = new Color(r, g, b);

    // Game piece colors
    public static final Color yellow = new Color(242, 60, 0);
    public static final Color purple = new Color(184, 0, 185);

    // Indicator colors
    public static final Color white = new Color(255, 230, 220);
    public static final Color green = new Color(56, 209, 0);
    public static final Color blue = new Color(8, 32, 255);
    public static final Color orange = new Color(255, 25, 0);
    public static final Color skin = new Color(169, 125, 100);



    public static Color allianceColor = blue;

    
    public LEDs() {
        SmartDashboard.putNumber("LED R", r);
        SmartDashboard.putNumber("LED G", g);
        SmartDashboard.putNumber("LED B", b);
        CANdleConfiguration candleConfiguration = new CANdleConfiguration();
        candleConfiguration.statusLedOffWhenActive = true;
        candleConfiguration.enableOptimizations = true;
        candleConfiguration.disableWhenLOS = false;
        candleConfiguration.stripType = LEDStripType.RGB;
        candleConfiguration.brightnessScalar = 1.0;
        candleConfiguration.vBatOutputMode = VBatOutputMode.Modulated;
        candle.configAllSettings(candleConfiguration, 100);
        setDefaultCommand(defaultCommand());
    }

    public void setBrightness(double percent) {
        candle.configBrightnessScalar(percent, 100);
    }

    public static LEDs getInstance(){
        if(leds ==null){
            leds = new LEDs();
        }
        return leds;
    }

    public Command defaultCommand() {
        // setBrightness(1);
        return runOnce(() -> {
            // LEDSegment.MainStrip.setColor(allianceColor);
            // LEDSegment.Matrix.setRainbowAnimation(1);
            });
    }
    public Command Partymode(){
        return runOnce(() -> {

            LEDSegment.MainStrip.setRainbowAnimation(1);

        });
    } 

    public Command clearSegmentCommand(LEDSegment segment) {
        return runOnce(() -> {
            segment.clearAnimation();
            segment.disableLEDs();
        });
    }
    public Command setSegmentCommand(LEDSegment segment) {
        return runOnce(() -> {
        });
    }

    public void periodic(){
        r = (int)SmartDashboard.getNumber("LED R", 255);
        g = (int)SmartDashboard.getNumber("LED G", 255);
        b = (int)SmartDashboard.getNumber("LED B", 255);
        ElasticColor = new Color(r, g, b);
    }
    public static enum LEDSegment {
        StatusLEDs(0, 7, 0),
        MainStrip(8,50,1);

        public final int startIndex;
        public final int segmentSize;
        public final int animationSlot;

        private LEDSegment(int startIndex, int segmentSize, int animationSlot) {
            this.startIndex = startIndex;
            this.segmentSize = segmentSize;
            this.animationSlot = animationSlot;
        }

        public void setColor(Color color) {
            clearAnimation();
            candle.setLEDs(color.red, color.green, color.blue, 0, startIndex, segmentSize);
        }

        private void setAnimation(Animation animation) {
            candle.animate(animation, animationSlot);
        }
        public void fullClear() {
            clearAnimation();
            disableLEDs();
        }

        public void clearAnimation() {
            candle.clearAnimation(animationSlot);
        }

        public void disableLEDs() {
            setColor(black);
        }

        public void setFlowAnimation(Color color, double speed) {
            setAnimation(new ColorFlowAnimation(
                    color.red, color.green, color.blue, 0, speed, segmentSize, Direction.Forward, startIndex));
        }

        public void setFadeAnimation(Color color, double speed) {
            setAnimation(
                    new SingleFadeAnimation(color.red, color.green, color.blue, 0, speed, segmentSize, startIndex));
        }

        public void setBandAnimation(Color color, double speed) {
            setAnimation(new LarsonAnimation(
                    color.red, color.green, color.blue, 0, speed, segmentSize, BounceMode.Center, 15, startIndex));
        }

        public void setStrobeAnimation(Color color, double speed) {
            setAnimation(new StrobeAnimation(color.red, color.green, color.blue, 0, speed, segmentSize, startIndex));
        }

        public void setRainbowAnimation(double speed) {
            setAnimation(new RainbowAnimation(1, speed, segmentSize, false, startIndex));
        }
    }

    public static class Color {
        public int red;
        public int green;
        public int blue;

        public Color(int red, int green, int blue) {
            this.red = red;
            this.green = green;
            this.blue = blue;
        }

    }
    public void setMatrixToGrid(Color[] matrix)
    {
        int no = 7;
        for (int i = 0; i < matrix.length; i++) {
    
            Color color = matrix[i];
            no++;
            // candle.animate(new RainbowAnimation(1, 0.7, 1, false, no));
            candle.setLEDs(color.red, color.green, color.blue, 0, no, 1);
        }
    }
}