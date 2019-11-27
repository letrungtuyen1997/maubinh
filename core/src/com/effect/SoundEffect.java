package com.effect;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.ss.core.util.GAssetsManager;

/* renamed from: com.ss.effect.SoundEffect */
public class SoundEffect {
    public static int MAX_COMMON = 20;
    public static Music bgSound = null;
    public static Music bgSound2 = null;
    public static Sound[] commons = null;
    public static boolean music = false;
    public static boolean music2 = false;
    public static boolean mute = false;
    public  static int click =0;
    public  static int chiabai =1;
    public  static int latbai =2;
    public  static int onTurn =3;
    public  static int bomB =4;
    public  static int Check =5;
    public  static int Pay =6;
    public  static int PannelIn =7;
    public  static int PannelOut =8;
    public  static int lose =9;
    public  static int win =10;
    public  static int WinSpecial =11;



    public static Audio bg = null;


    public static void initSound() {
        commons = new Sound[MAX_COMMON];
        commons[click] = GAssetsManager.getSound("click.mp3");
        commons[chiabai] = GAssetsManager.getSound("chiabai3.mp3");
        commons[latbai] = GAssetsManager.getSound("chiabai2.mp3");
        commons[onTurn] = GAssetsManager.getSound("playerTurn.mp3");
        commons[bomB] = GAssetsManager.getSound("bomb.mp3");
        commons[Check] = GAssetsManager.getSound("tothem.mp3");
        commons[Pay] = GAssetsManager.getSound("pay.mp3");
        commons[lose] = GAssetsManager.getSound("lose.mp3");
        commons[win] = GAssetsManager.getSound("win.mp3");
        commons[PannelOut] = GAssetsManager.getSound("Panel out.mp3");
        commons[PannelIn] = GAssetsManager.getSound("Panel in.mp3");
        commons[WinSpecial] = GAssetsManager.getSound("winSpecial.mp3");
        bgSound = GAssetsManager.getMusic("bg.mp3");
        bgSound2 = GAssetsManager.getMusic("bg2.mp3");

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
        bgSound2.pause();
    }
    public static void Playmusic2() {
        music2 = false;
        bgSound2.play();
        bgSound2.setLooping(true);
        bgSound2.setVolume(0.5f);
        bgSound.pause();

    }

    public static void Stopmusic() {
        music = true;
        bgSound.pause();
    }
    public static void Stopmusic2() {
        music2 = true;
        bgSound2.pause();
    }
}
