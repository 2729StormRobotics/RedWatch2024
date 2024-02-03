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
import frc.robot.presets.matrixPresets;
import frc.robot.presets.matrixPresets.*;
/*
 * USAGE:
 * ANYWHERE YOU WANT TO USE LEDS
 * import the LEDSegment
 * then run:
 * LEDSegment.MainStrip.(whatever command)
 * if any questions, ask Krithik
 * 
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
 * 
 */

public class LEDs extends SubsystemBase {
    public static final CANdle candle = new CANdle(LightsConstants.CANDLE_PORT);

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

    public Command defaultCommand() {
        return runOnce(() -> {
            // LEDSegment.BatteryIndicator.fullClear();
            // LEDSegment.PressureIndicator.fullClear();
            // LEDSegment.MastEncoderIndicator.fullClear();
            // LEDSegment.BoomEncoderIndicator.fullClear();
            // LEDSegment.WristEncoderIndicator.fullClear();
            // Uncomment these lines once we start testing LEDs
            LEDSegment.MainStrip.setRainbowAnimation(1);
            // LEDSegment.Matrix.setRainbowAnimation(1);
            setMatrixToGrid(matrixPresets.ggMatrix);
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
    public static enum LEDSegment {
        // BatteryIndicator(0, 2, 0),
        // PressureIndicator(2, 2, 1),
        // MastEncoderIndicator(4, 1, -1),
        // BoomEncoderIndicator(5, 1, -1),
        // WristEncoderIndicator(6, 1, -1),
        // DriverStationIndicator(7, 1, -1),
        // ALL THIS ABOVE CODE IS TO BE TESTED ONCE WE HAVE OUR LED STRIPS
        MainStrip(0, 300, 0),
        Matrix(8,100,1);
        // Strip2(8,4,1),
        // Strip3(13,2,2),
        // Strip4(18,7,3),
        // Strip5(20,1,4),
        // Strip6(21,2,5),
        // Strip7(24,1,6),
        // Strip8(26,5,7),
        // Strip9(27,1,8),
        // Strip10(28,4,9),
        // Strip11(30,1,0),
        // Strip12(34,3,0),
        // Strip13(39,2,0),
        // Strip14(41,4,0),
        // Strip15(46,2,0),
        // Strip16(50,2,0),
        // Strip17(52,2,0),
        // Strip18(53,1,0),
        // Strip19(54,2,0),
        // Strip20(56,1,0),
        // Strip21(59,2,0),
        // Strip22(60, 2, 0),
        // Strip23(13,2,2),
        // Strip24(18,1,3),
        // Strip25(20,2,4),
        // Strip26(21,1,5),
        // Strip27(24,2,6),
        // Strip28(26,2,7),
        // Strip29(27,2,8),
        // Strip30(28,4,9),
        // Strip31(30,2,0),
        // Strip32(8,3,1),
        // Strip33(13,1,2),
        // Strip34(18,4,3),
        // Strip35(20,1,4),
        // Strip36(21,5,5),
        // Strip37(24,1,6),
        // Strip38(26,2,7),
        // Strip39(27,1,8),
        // Strip40(28,7,9),
        // Strip41(30,2,0),
        // Strip42(8,4,1);
        // MAIN STRIP SHOULD BE STARTING AT INDEX 8, leave at 0 when testing

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
    public void setMatrixToGrid(Color[][] matrix)
    {
        int no = 8;
        for (int i = 0; i < matrix.length; i++) {
    
          // Iterate through the columns of the current row
          for (int j = 0; j < matrix[i].length; j++) {
              Color color = matrix[i][j];
              no++;
              candle.setLEDs(color.red, color.green, color.blue, 0, no, 1);
          }
        }
    }
}