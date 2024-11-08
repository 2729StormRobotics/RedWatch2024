package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Blinkin extends SubsystemBase{
    
    private static Blinkin blinkin;
    private Spark blinkinController;

    private double initialTime, currentTime;

    private boolean useReturnToRobotStateTimer;
    private double returnToRobotStateTimeLimit;

    private double lastFlashTime, flashColor, flashRate;
    private boolean useFlashColor, flashOn;

    private double currentColor;

    /*SET BASE COLORS TO:
     * COLOR 1: RED
     * COLOR 2: ORANGE
     * PORT IS SET TO 6 on PWM PORTS
     */

    public Blinkin() {
        blinkinController = new Spark(1); //~

        initialTime = 0.0;
        currentTime = 0.0;

        useReturnToRobotStateTimer = false;
        returnToRobotStateTimeLimit = 0.0;

        lastFlashTime = 0.0;
        useFlashColor = false;
        flashColor = 0.0;
        flashRate = 0.0;
        flashOn = false;   //~ 

        currentColor = 0.0;

        neutral();
    }

    public static Blinkin getInstance(){
      //~ - KYAN
        if(blinkin == null){
            blinkin = new Blinkin();
        }
        return blinkin;
    }

    public void set(double value) {
        
        blinkinController.set(value);
        currentColor = value;
        useReturnToRobotStateTimer = false;
    }

    // Color to use when doing nothing ~
    public void neutral(){
        // blinkinController.disable();
        set(0.53);
        // set(0.99);
    }

    // Solid purple color (not necessarily identical to color 2 preset)
    public void purple(){
        set(0.89);
    }

    // Solid gold color (not necessarily identical to color 1 preset)
    public void gold(){
        set(0.63);
    }

    // Solid pink color WHY 
    public void pink(){
        set(0.57);
    }

    // Solid green color
    public void green(){
        set(0.75);
    }

    // Solid red color
    public void red(){
        set(0.61);
    }   

    // Solid aqua color
    public void yellow(){
        set(0.69);
    }

    // Solid orange color
    public void orange(){
        set(0.65);    
    }

    public void rainbowTwinkle(){ 
                set(-0.55);
    }
    
    public void whiteOverride(){
        set(0.93);
    }

    public void redHeartbeat(){
        set(0.05);
    }

    public void redStrobe(){
        set(-0.11);
    }

    public void orangeHeartbeat(){
        set(-0.07);
    }

    // Solid green (for various success modes)
    public void success(){
        green();
        returnToRobotState(1.5); 
    }

     // Solid red (for various failure modes)
     public void failure() {
        red();
        returnToRobotState(1); 
    }   

    // Turns the LEDS to flashing green while auto-aligning
    public void autoAlignClose(){
        green();
        flashColorAtRate(0.1, 0.75);
    }

    // Turns the LEDS to solid green when both auto-align stages are complete
    public void autoAlignSuccess(){
        useFlashColor = false;
        green();
    }

    // If the arm is being rehomed, go solid pink during process (special status)
    // Also used for some other special operator overrides. ~
    public void specialOperatorFunctionality(){
        whiteOverride();
    }

    public void gyroClimbSuccess(){
        green();
    }

    public void gyroClimbOverrun(){
        pink();
    }

    public void detectChargeStation(){
        orange();
    }

    // Immediately reset LED's to current robot state
    public void returnToRobotState(){
        useReturnToRobotStateTimer = false;
        useFlashColor = false;
        neutral();
        // if(claw.getState() == ClawState.INTAKING_CONE){
        //     goldHeartbeat();
        // }
        // else if(claw.getState() == ClawState.INTAKING_CUBE){
        //     purpleHeartbeat();
        // }
        // else if(claw.getState() == ClawState.CONE){
        //     gold();
        // }
        // else if(claw.getState() == ClawState.CUBE){
        //     purple();
        // } 
        // // else if(claw.getState() == ClawState.UNKNOWN){
        // //     red();
        // // }    
        // else{
        //     neutral();
        // }
    }

    // Return to robot state after a certain number of seconds (color/pattern on timer)
    public void returnToRobotState(double seconds){
        initialTime = Timer.getFPGATimestamp();
        useReturnToRobotStateTimer = true;
        returnToRobotStateTimeLimit = seconds;
    }

    public void flashColorAtRate(double seconds, double colorCode){
        lastFlashTime = Timer.getFPGATimestamp();
        useFlashColor = true;
        flashColor = colorCode;
        flashRate = seconds;
        flashOn = false;
    }

    @Override
    public void periodic() {        
        if(true){
            SmartDashboard.putNumber("current LED color set", currentColor);
            SmartDashboard.putNumber("current LED color blinkin", blinkinController.get());

            if(useReturnToRobotStateTimer){
                currentTime = Timer.getFPGATimestamp();
                if(currentTime - initialTime > returnToRobotStateTimeLimit){
                    returnToRobotState();
                }
            }

            if(useFlashColor){
                currentTime = Timer.getFPGATimestamp();
                if(currentTime - lastFlashTime > flashRate){
                    if(flashOn){
                        blinkin.neutral();
                        lastFlashTime = Timer.getFPGATimestamp();
                        flashOn = false;
                    }
                    else{
                        blinkin.set(flashColor);
                        lastFlashTime = Timer.getFPGATimestamp();
                        flashOn = true;
                    }
                }
            }
        }
    }

}