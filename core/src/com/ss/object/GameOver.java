package com.ss.object;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.ss.core.exSprite.GShapeSprite;
import com.ss.core.util.GAssetsManager;
import com.ss.core.util.GLayer;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;

public class GameOver {
    TextureAtlas atlas;
    Array<Integer> resultBranch;
    Array<Long> result = new Array<>();
    BitmapFont font;
    Group group = new Group();
    GameOver(Array<Integer> resultBranch){
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
    }
    private void darkScreen(){
        final GShapeSprite blackOverlay = new GShapeSprite();
        blackOverlay.createRectangle(true, -GStage.getWorldWidth()/2,-GStage.getWorldHeight()/2, GStage.getWorldWidth()*2, GStage.getWorldHeight()*2);
        blackOverlay.setColor(0,0,0,0.7f);
        group.addActor(blackOverlay);
    }

    void showFrame(){

        Image frame = GUI.createImage(atlas,"frameWin");
        frame.setPosition(0,0,Align.center);
        group.addActor(frame);
        Label label;
        for(int i =0;i<4; i++){
            System.out.println("result: "+result.get(i));
            if((resultBranch.get(0)*boardConfig.monneyStart)==result.get(i)){
                label = new Label(""+result.get(i)+" (bạn)",new Label.LabelStyle(font, Color.GOLD));
            }else {
                label = new Label(""+result.get(i),new Label.LabelStyle(font,Color.GOLD));
            }
            label.setFontScale(0.8f);
//            label.setOrigin(Align.center);
//            label.setAlignment(Align.center);
            label.setPosition(70,-100+((i-3)*(-1))*75,Align.topLeft);
            group.addActor(label);
        }


    }
    void sort(){
        for(int i=0;i<resultBranch.size;i++){
            result.add(resultBranch.get(i)*boardConfig.monneyStart);
        }
        result.sort();
    }
    void initFont(){
        font = GAssetsManager.getBitmapFont("font_white.fnt");
    }
    void initAtlas(){
        atlas = GAssetsManager.getTextureAtlas("ui.atlas");
    }

}
