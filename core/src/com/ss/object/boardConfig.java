package com.ss.object;


import com.badlogic.gdx.scenes.scene2d.Group;
import com.ss.GMain;
import com.ss.core.util.GStage;

public class boardConfig {
    public float ratioX = GStage.getWorldWidth()/1280;
    public float ratioY = GStage.getWorldHeight()/720;
    public static int modePlay = 4;
    public static float durationDistrbute = 0.8f;
    public static float scaleBot = 0.7f;
    public static float widthCard= 169;
    public static long monneyStart= 0;
    public static long Mymonney= GMain.prefs.getLong("mymonney");
    public static String[] arrName =  {"Châu Tinh Trì","Tả Tụng Tinh","Ling Ling Chình","Tiểu Cường","Doãn Thiên Sầu","Châu Tinh Tinh"};
    public static Group Group = new Group();
    public static boolean isDrag = false;
    public static int TimePlay=0;
}

