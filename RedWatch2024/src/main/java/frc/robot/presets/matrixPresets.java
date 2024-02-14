package frc.robot.presets;

import frc.robot.subsystems.LEDs.Color;

public class matrixPresets {
    // colors:
    // Team colors
    public static final Color red = new Color(255, 0, 0);
    // public static final Color black  = new Color(255, 230, 220);
    public static final Color black   = new Color(0, 0, 0);
    public static final Color brown = new Color(139,69,19);

    // Game piece colors
    public static final Color yellow = new Color(242, 60, 0);
    public static final Color purple = new Color(184, 0, 185);

    // Indicator colors
    public static final Color white  = new Color(255, 230, 220);
    public static final Color green = new Color(56, 209, 0);
    public static final Color blue = new Color(8, 32, 255);
    public static final Color orange = new Color(255, 25, 0);
    public static final Color skin = new Color(169, 125, 100);


    public static Color[] ggMatrix = {      
        black  ,black  ,black  ,black  ,black  ,black  ,black  ,black  ,black  ,black  , 
        black  ,black  ,black  ,red    ,red    ,red    ,red    ,black  ,black  ,black  , 
        black  ,black  ,red    ,black  ,black  ,black  ,black  ,red    ,black  ,black, 
        black  ,black  ,red    ,black  ,red    ,black  ,black  ,red    ,black  ,black  , 
        black  ,black  ,black  ,red    ,black  ,red    ,red    ,black  ,black  ,black, 
        black  ,black  ,black  ,black  ,black  ,black  ,black  ,black  ,black  ,black, 
        black  ,black  ,black  ,red    ,red    ,red    ,red    ,black  ,black  ,black  , 
        black  ,black  ,red    ,black  ,black  ,black  ,black  ,red    ,black  ,black, 
        black  ,black  ,red    ,black  ,black  ,red    ,black  ,red    ,black  ,black  , 
        black  ,black  ,black  ,red    ,red    ,black  ,red    ,black  ,black  ,black,
    };

    public static Color[][] allwhiteMatrix = {   
        {white ,white ,white ,white ,white ,white ,white ,white ,white ,white }, 
        {white ,white ,white ,white ,white ,white ,white ,white ,white ,white }, 
        {white ,white ,white ,white ,white ,white ,white ,white ,white ,white }, 
        {white ,white ,white ,white ,white ,white ,white ,white ,white ,white }, 
        {white ,white ,white ,white ,white ,white ,white ,white ,white ,white }, 
        {white ,white ,white ,white ,white ,white ,white ,white ,white ,white }, 
        {white ,white ,white ,white ,white ,white ,white ,white ,white ,white }, 
        {white ,white ,white ,white ,white ,white ,white ,white ,white ,white },
        {white ,white ,white ,white ,white ,white ,white ,white ,white ,white }, 
        {white ,white ,white ,white ,white ,white ,white ,white ,white ,white }
    };

    public static Color[][] emptyMatrix = {   
        {black ,black ,black ,black ,black ,black ,black ,black ,black ,black }, 
        {black ,black ,black ,black ,black ,black ,black ,black ,black ,black }, 
        {black ,black ,black ,black ,black ,black ,black ,black ,black ,black }, 
        {black ,black ,black ,black ,black ,black ,black ,black ,black ,black }, 
        {black ,black ,black ,black ,black ,black ,black ,black ,black ,black }, 
        {black ,black ,black ,black ,black ,black ,black ,black ,black ,black }, 
        {black ,black ,black ,black ,black ,black ,black ,black ,black ,black }, 
        {black ,black ,black ,black ,black ,black ,black ,black ,black ,black },
        {black ,black ,black ,black ,black ,black ,black ,black ,black ,black }, 
        {black ,black ,black ,black ,black ,black ,black ,black ,black ,black }
    };

    public static Color[] smileyMatrix = {   
        white ,white ,white ,white ,white ,white ,white ,white ,white ,white , 
        white ,white ,yellow,yellow,white ,white ,white ,white ,white ,white , 
        white ,yellow,yellow,yellow,white ,white ,yellow,yellow,white ,white , 
        white ,yellow,yellow,white ,white ,white ,yellow,yellow,white ,white , 
        white ,yellow,yellow,white ,white ,white ,white ,white ,white ,white , 
        white ,yellow,yellow,white ,white ,white ,white ,white ,white ,white , 
        white ,yellow,yellow,white ,white ,white ,yellow,yellow,white ,white , 
        white ,yellow,yellow,yellow,white ,white ,yellow,yellow,white ,white ,
        white ,white ,yellow,yellow,white ,white ,white ,white ,white ,white , 
        white ,white ,white ,white ,white ,white ,white ,white ,white ,white 
    };
    public static Color[][] Red2720Animation = {
        Animation2720_Red.frame1_2720,
        Animation2720_Red.frame2_2720,
        Animation2720_Red.frame3_2720,
        Animation2720_Red.frame4_2720,
        Animation2720_Red.frame5_2720,
        Animation2720_Red.frame6_2720,
        Animation2720_Red.frame7_2720,
        Animation2720_Red.frame8_2720,
        Animation2720_Red.frame9_2720,
        Animation2720_Red.frame10_2720,
        Animation2720_Red.frame11_2720,
        Animation2720_Red.frame12_2720,
        Animation2720_Red.frame13_2720,
        Animation2720_Red.frame14_2720,
        Animation2720_Red.frame15_2720,
        Animation2720_Red.frame16_2720,
        Animation2720_Red.frame17_2720,
        Animation2720_Red.frame18_2720,
        Animation2720_Red.frame19_2720,
        Animation2720_Red.frame20_2720,
        Animation2720_Red.frame21_2720,
        Animation2720_Red.frame22_2720,
        Animation2720_Red.frame23_2720
      };
      public static Color[][] rampUpAnimation = {
        PercentMotorSpeed.frame1_10per,
        PercentMotorSpeed.frame2_20per,
        PercentMotorSpeed.frame3_30per,
        PercentMotorSpeed.frame4_40per,
        PercentMotorSpeed.frame5_50per,
        PercentMotorSpeed.frame6_60per,
        PercentMotorSpeed.frame7_70per,
        PercentMotorSpeed.frame8_80per,
        PercentMotorSpeed.frame9_90per,
        PercentMotorSpeed.frame10_100per
      };
}