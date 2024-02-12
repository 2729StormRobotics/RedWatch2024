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
    public static Color[] fireMatrix = 
    {new Color(  0,  0,  0), new Color(  0,  0,  0), new Color(  0,  0,  0), new Color(  0,  0,  0), new Color( 53, 25, 23), new Color( 78, 33, 26), new Color(  0,  0,  0), new Color(  0,  0,  0), new Color(  0,  0,  0), new Color(  0,  0,  0), 
        new Color(  0,  0,  0), new Color(  0,  0,  0), new Color(  0,  0,  0), new Color(  0,  0,  0), new Color( 53, 25, 21), new Color(235, 81, 47), new Color( 92, 35, 24), new Color(  0,  1,  1), new Color(  0,  0,  0), new Color(  0,  0,  0), 
        new Color(  0,  0,  0), new Color(  6,  4,  4), new Color( 35, 22, 20), new Color( 22, 12, 12), new Color(172, 67, 41), new Color(255,142, 70), new Color(243, 90, 44), new Color( 52, 19, 15), new Color(  0,  0,  0), new Color(  0,  0,  0), 
        new Color(  0,  0,  0), new Color( 40, 22, 21), new Color( 75, 43, 40), new Color(102, 36, 22), new Color(255,127, 63), new Color(255,164, 73), new Color(255,112, 42), new Color( 98, 26, 15), new Color(  0,  0,  0), new Color(  0,  0,  0), 
        new Color(  0,  0,  0), new Color( 37, 20, 18), new Color( 50, 32, 33), new Color(154, 58, 29), new Color(255,158, 65), new Color(255,181, 72), new Color(255,111, 46), new Color(129, 37, 27), new Color( 43, 27, 25), new Color(  0,  0,  0), 
        new Color(  0,  0,  0), new Color(101, 36, 14), new Color(206, 83, 38), new Color(219, 89, 40), new Color(255,187, 69), new Color(255,203, 76), new Color(255,129, 65), new Color(252, 73, 33), new Color(149, 37, 18), new Color(  0,  0,  0), 
        new Color(  0,  0,  0), new Color(109, 26,  6), new Color(255,107, 14), new Color(255,159, 55), new Color(255,219, 88), new Color(255,223, 94), new Color(255,186, 63), new Color(255,100, 21), new Color(161, 24,  6), new Color(  0,  0,  0), 
        new Color(  0,  0,  0), new Color( 49, 16, 16), new Color(233, 97, 10), new Color(255,204, 43), new Color(253,247,149), new Color(253,251,176), new Color(255,228, 75), new Color(247,122, 14), new Color( 82, 23, 19), new Color(  0,  0,  0), 
        new Color(  0,  0,  0), new Color(  3,  5,  8), new Color(159, 70, 23), new Color(255,196, 25), new Color(254,255,192), new Color(255,255,221), new Color(255,224, 56), new Color(163, 90, 19), new Color(  2,  3,  5), new Color(  0,  0,  0), 
        new Color(  0,  0,  0), new Color(  0,  0,  0), new Color( 20, 10,  8), new Color(139, 96, 25), new Color(228,209,116), new Color(211,201,143), new Color(117, 90, 22), new Color( 18, 10,  7), new Color(  0,  0,  0), new Color(  0,  0,  0), 
        };
}
