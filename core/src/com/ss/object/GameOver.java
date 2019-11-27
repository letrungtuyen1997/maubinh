package com.ss.object;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.effect.SoundEffect;
import com.effect.effectWin;
import com.ss.core.action.exAction.GSimpleAction;
import com.ss.core.exSprite.GShapeSprite;
import com.ss.core.util.GAssetsManager;
import com.ss.core.util.GLayer;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;
import com.ss.scene.gamePlay;

import java.text.DecimalFormat;

public class GameOver {
    TextureAtlas atlas;
    Array<Integer> resultBranch;
    Array<Long> result = new Array<>();
    BitmapFont font;
    Group group = new Group();
    String NameBtn = "";
    Image frame;
    gamePlay gamePlay;
    int id=0;
    GameOver(Array<Integer> resultBranch, gamePlay gamePlay){
        SoundEffect.Stopmusic2();
        this.gamePlay = gamePlay;
        GStage.addToLayer(GLayer.top,group);
        group.setScale(0);
        group.setOrigin(Align.center);
        group.setPosition(GStage.getWorldWidth()/2,GStage.getWorldHeight()/2, Align.center);
        initFont();
        initAtlas();
        this.resultBranch = resultBranch;
        darkScreen();
        sort();
        showFrame();
        group.addAction(Actions.scaleTo(1,1,0.5f, Interpolation.swingOut));
        showbtnDone();
        particleOver();


    }
    private void darkScreen(){
        final GShapeSprite blackOverlay = new GShapeSprite();
        blackOverlay.createRectangle(true, -GStage.getWorldWidth()/2,-GStage.getWorldHeight()/2, GStage.getWorldWidth()*2, GStage.getWorldHeight()*2);
        blackOverlay.setColor(0,0,0,0.7f);
        group.addActor(blackOverlay);
    }

    void showFrame(){
        String nameFrame="";
        String nameRank="";
        float y=0,yy=0,yyy;
        if((resultBranch.get(0)*boardConfig.monneyStart)>0){
            SoundEffect.Play(SoundEffect.win);
            nameFrame = "frameWin";
            NameBtn = "btnCloseR";
            nameRank = "nameRankR";
            y=-40;
            yy=-30;
            yyy = 70;
            id=24;
            effectWin effect1 = new effectWin(26,0,-200);
            group.addActor(effect1);
            effect1.start();

        }else {
            SoundEffect.Play(SoundEffect.lose);
            nameFrame = "frameLose";
            NameBtn = "btnCloseG";
            nameRank = "nameRankG";
            y = -90;
            yy = -80;
            yyy = 30;
            id=25;

        }
        frame = GUI.createImage(atlas,nameFrame);
        frame.setPosition(0,0,Align.center);
        group.addActor(frame);
        Label label ,label2;
        for(int i =0;i<4; i++){
            System.out.println("result: "+result.get(i));
            if((resultBranch.get(0)*boardConfig.monneyStart)+1==result.get(i)){
                if(result.get(i)>=0){
                    label = new Label("+"+FortmartPrice(result.get(i)-1),new Label.LabelStyle(font, null));
                    label2 = new Label("(ăn "+((result.get(i)-1)/boardConfig.monneyStart)+" chi)",new Label.LabelStyle(font,null));
                }else {
                    label = new Label(""+FortmartPrice(result.get(i)-1),new Label.LabelStyle(font, null));
                    label2 = new Label("(thua "+((result.get(i)-1)/boardConfig.monneyStart)*-1+" chi)",new Label.LabelStyle(font,null));

                }
                Image frm = GUI.createImage(atlas,"resultMe");
                frm.setHeight(frm.getHeight()*1.5f);
                frm.setPosition(0,yy+((i-3)*(-1))*frm.getHeight()-20,Align.center);
                group.addActor(frm);
            }else {
                if(result.get(i)>=0){
                    label = new Label("+"+FortmartPrice(result.get(i)),new Label.LabelStyle(font, null));
                    label2 = new Label("(ăn "+(result.get(i)/boardConfig.monneyStart)+" chi)",new Label.LabelStyle(font,null));

                }else {
                    label = new Label(""+FortmartPrice(result.get(i)),new Label.LabelStyle(font, null));
                    label2 = new Label("(thua "+(result.get(i)/boardConfig.monneyStart)*-1+" chi)",new Label.LabelStyle(font,null));

                }
            }
            label.setFontScale(0.6f);
            label2.setFontScale(0.4f);
            label.setPosition(-30,y+((i-3)*(-1))*80,Align.topLeft);
            label2.setPosition(0,y+30+((i-3)*(-1))*80,Align.topLeft);
            group.addActor(label);
            group.addActor(label2);
        }
        Image nameRankimg = GUI.createImage(atlas,nameRank);
        nameRankimg.setPosition(-200,yyy,Align.center);
        group.addActor(nameRankimg);


    }
    void sort(){
        for(int i=0;i<resultBranch.size;i++){
            if(i==0) {
                result.add(resultBranch.get(i) * boardConfig.monneyStart+1);
            }else {
                result.add(resultBranch.get(i) * boardConfig.monneyStart);

            }

        }
        result.sort();
    }
    void particleOver(){
        effectWin effect = new effectWin(id,0,-200);
        group.addActor(effect);
        effect.start();


    }
    void showbtnDone(){
        Image btn = GUI.createImage(atlas,NameBtn);
        btn.setPosition(0,frame.getHeight()/2-btn.getHeight(),Align.center);
        btn.setOrigin(Align.center);
        group.addActor(btn);
        btn.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                SoundEffect.Play(SoundEffect.click);
                btn.addAction(Actions.sequence(
                        Actions.scaleTo(0.8f,0.8f,0.1f),
                        Actions.scaleTo(1f,1f,0.1f),
                        GSimpleAction.simpleAction((d,a)->{
                            group.clear();
                            group.remove();
                            gamePlay.showbtnNewGame();
                            SoundEffect.Playmusic2();
                            return true;
                        })
                ));
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }
    void initFont(){
        font = GAssetsManager.getBitmapFont("fontYellow.fnt");
    }
    void initAtlas(){
        atlas = GAssetsManager.getTextureAtlas("ui.atlas");
    }
    private String FortmartPrice(Long Price) {

        DecimalFormat mDecimalFormat = new DecimalFormat("###,###,###,###");
        String mPrice = mDecimalFormat.format(Price);
        return mPrice;
    }


}
