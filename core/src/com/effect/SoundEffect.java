package com.effect;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.ss.core.util.GAssetsManager;

/* renamed from: com.ss.effect.SoundEffect */
public class SoundEffect {
    public static int MAX_COMMON = 20;
    public static Music bgSound = null;
    public static Sound[] commons = null;
    public static boolean music = false;
    public static boolean mute = false;
    public  static int click =0;
    public  static int chiabai =1;
    public  static int latbai =2;
    public  static int chipFlyFirst =3;
    public  static int chipFly1 =4;
    public  static int chipFly2 =5;
    public  static int chipFly3 =6;
    public  static int chipFly4 =7;
    public  static int chipFly5 =8;
    public  static int PannelIn =9;
    public  static int PannelOut =10;
    public  static int skip =11;
    public  static int theo =12;
    public  static int tothem =13;
    public  static int xalang =14;
    public  static int gameOver =15;
    public  static int win =16;
    public  static int youLose =17;
    public  static int youWin =18;
    public  static int dingdong =19;



    public static Audio bg = null;


    public static void initSound() {
        commons = new Sound[MAX_COMMON];
        commons[click] = GAssetsManager.getSound("button-click.mp3");
        commons[chiabai] = GAssetsManager.getSound("bai_chia.mp3");
        commons[latbai] = GAssetsManager.getSound("bai_lat.mp3");
        commons[chipFlyFirst] = GAssetsManager.getSound("fly_first_chips.mp3");
        commons[chipFly1] = GAssetsManager.getSound("fly_1.mp3");
        commons[chipFly2] = GAssetsManager.getSound("fly_2.mp3");
        commons[chipFly3] = GAssetsManager.getSound("fly_3.mp3");
        commons[chipFly4] = GAssetsManager.getSound("fly_4.mp3");
        commons[chipFly5] = GAssetsManager.getSound("fly_5.mp3");
        commons[PannelIn] = GAssetsManager.getSound("Panel in.mp3");
        commons[PannelOut] = GAssetsManager.getSound("Panel out.mp3");
        commons[skip] = GAssetsManager.getSound("skip.mp3");
        commons[theo] = GAssetsManager.getSound("theo.mp3");
        commons[tothem] = GAssetsManager.getSound("tothem.mp3");
        commons[xalang] = GAssetsManager.getSound("ohYeah.mp3");
        commons[gameOver] = GAssetsManager.getSound("gameOver.mp3");
        commons[win] = GAssetsManager.getSound("Win.mp3");
        commons[youLose] = GAssetsManager.getSound("youLose.mp3");
        commons[youWin] = GAssetsManager.getSound("youWin.mp3");
        commons[dingdong] = GAssetsManager.getSound("dingdong.mp3");
        bgSound = GAssetsManager.getMusic("bgmusic.mp3");

    }

    public static void Play(int i) {
        if (!mute) {
            commons[i].play();
        }
    }

    public static void Playmusic() {
        music = false;
        bgSound.play();
        bgSound.setLooping(true);
        bgSound.setVolume(0.5f);
    }

    public static void Stopmusic() {
        music = true;
        bgSound.pause();
    }
}
