package com.ss.scene;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.effect.SoundEffect;
import com.effect.effectWin;
import com.ss.GMain;
import com.ss.commons.Tweens;
import com.ss.core.util.GAssetsManager;
import com.ss.core.util.GLayer;
import com.ss.core.util.GScreen;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;
import com.ss.object.SelectTable;
import com.ss.object.Spine;
import com.ss.object.boardConfig;

import java.text.DecimalFormat;

public class gameStart extends GScreen {
    Group group = new Group();
    TextureAtlas uiAtlas;
    BitmapFont font;
    long monney = 0;
    Label labelmonney;



    @Override
    public void dispose() {

    }

    @Override
    public void init() {
        GMain.platform.TrackCustomEvent("StartGame");
        SoundEffect.Playmusic();
        GStage.addToLayer(GLayer.ui,group);
        initAtlas();
        showBg();
        loadInfo();
    }

    @Override
    public void run() {

    }
    void showBg(){
        if(GMain.prefs.getInteger("checkFirst")==0){
            long monneyInit=GMain.platform.GetConfigIntValue("money_init",3000000);
            GMain.prefs.putLong("mymonney",monneyInit);
            GMain.prefs.flush();
            GMain.prefs.putInteger("checkFirst",1);
            GMain.prefs.flush();
            boardConfig.Mymonney = GMain.prefs.getLong("mymonney");
            monney= boardConfig.Mymonney;
        }else {
            monney= boardConfig.Mymonney;
        }
        Image bg = GUI.createImage(uiAtlas,"bg");
        bg.setWidth(GStage.getWorldWidth());
        bg.setHeight(GStage.getWorldHeight());
        group.addActor(bg);
        Image logo = GUI.createImage(uiAtlas,"logo");
        logo.setPosition(GStage.getWorldWidth()*2/3,160, Align.center);
        group.addActor(logo);
        Image btnStart = GUI.createImage(uiAtlas,"btnStart");
        btnStart.setPosition(GStage.getWorldWidth()*2/3,350,Align.center);
        group.addActor(btnStart);
        Image btnTop = GUI.createImage(uiAtlas,"btnTop");
        btnTop.setPosition(GStage.getWorldWidth()*2/3,btnStart.getY()+btnTop.getHeight()+100,Align.center);
        group.addActor(btnTop);
        /////// event btn//////
        eventBtnStart(btnStart);
        eventBtnTop(btnTop);
        ///////// load spine///////
        Spine s = new Spine();
        group.addActor(s);
        /////// effect btnStart/////
        effectWin effectbtnStart = new effectWin(31,btnStart.getX()+btnStart.getWidth()/2-10,btnStart.getY()+btnStart.getHeight()/2);
        group.addActor(effectbtnStart);
        effectWin effectbtnTop = new effectWin(31,btnTop.getX()+btnStart.getWidth()/2-10,btnTop.getY()+btnStart.getHeight()/2-10);
        group.addActor(effectbtnTop);

    }
    void loadInfo(){
        Image frmAvt = GUI.createImage(uiAtlas,"frameAvt");
        frmAvt.setPosition(frmAvt.getWidth()/2,frmAvt.getHeight()/2,Align.center);
        group.addActor(frmAvt);
        Label name = new Label("you",new Label.LabelStyle(font,null));
        name.setFontScale(0.7f);
        name.setOrigin(Align.center);
        name.setAlignment(Align.center);
        name.setPosition(frmAvt.getX()+200,frmAvt.getY()+50,Align.center);
        group.addActor(name);
        /////// frame monney///////
        Image frmMonney = GUI.createImage(uiAtlas,"frameMonney");
        frmMonney.setPosition(GStage.getWorldWidth()/2-70,frmMonney.getHeight()/2+30,Align.center);
        group.addActor(frmMonney);
        labelmonney = new Label(FortmartPrice(monney),new Label.LabelStyle(font,null));
        labelmonney.setFontScale(0.6f);
        labelmonney.setOrigin(Align.center);
        labelmonney.setAlignment(Align.center);
        labelmonney.setPosition(frmMonney.getX()+170,frmMonney.getY()+25,Align.center);
        group.addActor(labelmonney);




    }
    void eventBtnStart(Image btn) {
        btn.setOrigin(Align.center);
        btn.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                SoundEffect.Play(SoundEffect.click);
                btn.setTouchable(Touchable.disabled);
                btn.addAction(Actions.sequence(
                        Actions.scaleTo(0.7f,0.7f,0.1f),
                        Actions.scaleTo(1f,1f,0.1f)
                ));
                Tweens.setTimeout(group,0.2f,()->{
                    btn.setTouchable(Touchable.enabled);
//                    setScreen(new gamePlay(), GTransitionFade.init(0.2f));
                    new SelectTable(uiAtlas,monney,gameStart.this,labelmonney,font);
                });
                return super.touchDown(event, x, y, pointer, button);

            }
        });
    }
    void eventBtnTop(Image btn) {
        btn.setOrigin(Align.center);
        btn.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                SoundEffect.Play(SoundEffect.click);
                btn.setTouchable(Touchable.disabled);
                btn.addAction(Actions.sequence(
                        Actions.scaleTo(0.7f,0.7f,0.1f),
                        Actions.scaleTo(1f,1f,0.1f)
                ));
                Tweens.setTimeout(group,0.2f,()->{
                    btn.setTouchable(Touchable.enabled);
                    GMain.platform.ShowLeaderboard();
                });
                return super.touchDown(event, x, y, pointer, button);

            }
        });
    }
    void initAtlas(){
        uiAtlas = GAssetsManager.getTextureAtlas("uiStart.atlas");
        font = GAssetsManager.getBitmapFont("font_white.fnt");
    }
    private String FortmartPrice(Long Price) {
        DecimalFormat mDecimalFormat = new DecimalFormat("###,###,###,###");
        String mPrice = mDecimalFormat.format(Price);
        return mPrice;
    }
}
