package frc.robot.presets;

import frc.robot.subsystems.LEDs.Color;

public class matrixPresets {
    // colors:
    // Team colors
    public static final Color red = new Color(255, 0, 0);
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
        black  ,black  ,red    ,black    ,black  ,black  ,black  ,red    ,black  ,black, 
        black  ,black    ,red  ,black  ,red    ,black  ,black    ,red  ,black  ,black  , 
        black  ,black    ,black  ,red    ,black    ,red  ,red    ,black  ,black  ,black, 
        black  ,black    ,black  ,black  ,black    ,black  ,black    ,black,black,black, 
        black  ,black  ,black  ,red    ,red    ,red    ,red    ,black  ,black  ,black  , 
        black  ,black  ,red    ,black    ,black  ,black  ,black  ,red    ,black  ,black, 
        black  ,black    ,red  ,black  ,red    ,black  ,black    ,red  ,black  ,black  , 
        black  ,black    ,black  ,red    ,black    ,red  ,red    ,black  ,black  ,black,
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
}
