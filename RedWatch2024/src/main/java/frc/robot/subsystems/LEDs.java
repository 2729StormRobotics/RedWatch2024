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

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.LightsConstants;
import frc.robot.presets.matrixPresets;

public class LEDs extends SubsystemBase {
    public static final CANdle main_candle = new CANdle(LightsConstants.MAIN_PORT);

    // Team colors
    public static final Color red = new Color(255, 0, 0);
    public static final Color black = new Color(0, 0, 0);
    public static final Color brown = new Color(139,69,19);
     

    // Game piece colors
    public static final Color yellow = new Color(242, 60, 0);
    public static final Color purple = new Color(184, 0, 185);

    // Indicator colors
    public static final Color white = new Color(255, 230, 220);
    public static final Color green = new Color(56, 209, 0);
    public static final Color blue = new Color(8, 32, 255);
    public static final Color orange = new Color(255, 25, 0);
    public static final Color skin = new Color(169, 125, 100);
  
    
    public LEDs() {
        CANdleConfiguration candleConfiguration = new CANdleConfiguration();
        candleConfiguration.statusLedOffWhenActive = true;
        candleConfiguration.enableOptimizations = true;
        candleConfiguration.disableWhenLOS = false;
        candleConfiguration.stripType = LEDStripType.RGB;
        candleConfiguration.brightnessScalar = 1.0;
        candleConfiguration.vBatOutputMode = VBatOutputMode.Modulated;
        main_candle.configAllSettings(candleConfiguration, 100);
        setDefaultCommand(defaultCommand());
    }

    public void setBrightness(double percent, CANdle candle) {
        candle.configBrightnessScalar(percent, 100);
    }

    public Command defaultCommand() {
        // setBrightness(1);
        // return new runMatrixAnimation(this);
        return new InstantCommand(() -> {LEDSegment.Underglow.setRainbowAnimation(1);});
        
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
    public static enum LEDSegment {
        // ALL THIS ABOVE CODE IS TO BE TESTED ONCE WE HAVE OUR LED STRIPS
        MainStatusLEDs(0,7,0, main_candle),
        Underglow(7,100,1, main_candle),
        MainStrip(107, 100, 2, main_candle);
        // MAIN STRIP SHOULD BE STARTING AT INDEX 8, leave at 0 when testing

        public final int startIndex;
        public final int segmentSize;
        public final int animationSlot;
        public final CANdle candle;

        private LEDSegment(int startIndex, int segmentSize, int animationSlot, CANdle m_candle) {
            this.startIndex = startIndex;
            this.segmentSize = segmentSize;
            this.animationSlot = animationSlot;
            this.candle = m_candle;
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
                    color.red, color.green, color.blue, 0, speed, segmentSize, BounceMode.Center, 20, startIndex));
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
    public void setMatrixToGrid(Color[] matrix, CANdle candle)
    {
        int no = 7;
        for (int i = 0; i < matrix.length; i++) {
    
            Color color = matrix[i];
            no++;
            // candle.animate(new RainbowAnimation(1, 0.7, 1, false, no));
            candle.setLEDs(color.red, color.green, color.blue, 0, no, 1);
        }
    }
    public static void reverse(Color[] array) {
        int start = 0;
        int end = array.length - 1;

        while (start < end) {
            Color temp = array[start];
            array[start] = array[end];
            array[end] = temp;

            start++;
            end--;
        }
    }
}