package com.ss.scene;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.ss.GMain;
import com.ss.commons.Tweens;
import com.ss.core.util.GAssetsManager;
import com.ss.core.util.GLayer;
import com.ss.core.util.GScreen;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;
import com.ss.object.boardConfig;
import com.ss.object.boardGame;

public class gamePlay extends GScreen {
    TextureAtlas atlas, cardAtlas;
    BitmapFont font, fontmonney, fontName,font_white;
    Group group = new Group();
    Group groupAvt = new Group();
    Group groupBoard = new Group();
    public Array<Vector2> positionCards;
    Array<Image> AvtBotArr = new Array<>();
    Array<Image> frameNameBotArr = new Array<>();
    Array<Label> LabelNameBotArr = new Array<>();


    @Override
    public void dispose() {
    }
    @Override
    public void init() {
        GStage.addToLayer(GLayer.ui,group);
        GStage.addToLayer(GLayer.ui,groupBoard);
        GStage.addToLayer(GLayer.ui,groupAvt);
        initAtlas();
        initFont();
        initPositionCards();
        showBg();
        loadAvtPlayer();
        loadAvtBot();

    }
    @Override
    public void run() {
    }
    void showBg(){
        Image bg = GUI.createImage(atlas,"table2");
        group.addActor(bg);
        ////// new boardgame//////
        new boardGame(cardAtlas,atlas,this,groupBoard);

    }
    private void initPositionCards(){
        positionCards = new Array<>();
        int numberPlayer = boardConfig.modePlay;
        float delta2 = (GMain.screenHeight*16/9)/5;
        float delta3 = (GMain.screenHeight*16/9)/15;
        float deltaY = GMain.screenHeight/20;
        float delta = (GMain.screenWidth - (float)GMain.screenHeight*16/9)/2;
        switch (numberPlayer){
            case 2: {
                Vector2 position1 = new Vector2(delta + delta2 + delta3, GMain.screenHeight*3/4+100);
                Vector2 position2 = new Vector2((float)GMain.screenHeight*16/9 - (delta + delta2 + delta3 + boardConfig.widthCard*0.4f), GMain.screenHeight/3 - deltaY);
                positionCards.add(position1, position2);
                break;
            }
            case 3: {
                Vector2 position1 = new Vector2(GMain.screenWidth/2, GMain.screenHeight*3/4+100);
                Vector2 position2 = new Vector2(delta + delta2+200,GMain.screenHeight/3);
                Vector2 position3 = new Vector2((float)GMain.screenHeight*16/9 - delta - delta2-150, GMain.screenHeight/3);
                positionCards.add(position1, position2, position3);
                break;
            }
            case 4: {
                Vector2 position1 = new Vector2(GMain.screenWidth/2+100, GMain.screenHeight*3/4+100);
                Vector2 position2 = new Vector2(170,GMain.screenHeight/2 +50);
                Vector2 position3 = new Vector2(GMain.screenWidth/2-70,delta2-80);
                Vector2 position4 = new Vector2(GMain.screenWidth- delta2+80, GMain.screenHeight/2-50);
                positionCards.add(position1, position2, position3, position4);
                break;
            }
            case 5: {
                Vector2 position1 = new Vector2(GMain.screenWidth/2, GMain.screenHeight*3/4+100);
                Vector2 position2 = new Vector2(delta + delta2,GMain.screenHeight*2/3-40);
                Vector2 position3 = new Vector2(delta + delta2+200,GMain.screenHeight/3);
                Vector2 position4 = new Vector2((float)GMain.screenHeight*16/9 - delta - delta2-150, GMain.screenHeight/3);
                Vector2 position5 = new Vector2((float)GMain.screenHeight*16/9+50 - delta - delta2, GMain.screenHeight*2/3-40);
                positionCards.add(position1, position2, position3, position4);
                positionCards.add(position5);
                break;
            }

            default: break;
        }
    }
    void loadAvtPlayer(){
        Image MyAvt = GUI.createImage(atlas,"avtMe");
        MyAvt.setPosition(positionCards.get(0).x-MyAvt.getWidth()*2+50,positionCards.get(0).y, Align.center);
        groupAvt.addActor(MyAvt);
        Image frameName = GUI.createImage(atlas,"namePlayer");
        frameName.setPosition(MyAvt.getX()+MyAvt.getWidth()/2,MyAvt.getY()+MyAvt.getWidth()-18,Align.center);
        groupAvt.addActor(frameName);
        Label name = new Label("Thánh Bài",new Label.LabelStyle(fontName,null));
        name.setFontScale(0.7f);
        name.setOrigin(Align.center);
        name.setPosition(MyAvt.getX()+MyAvt.getWidth()/2+20,MyAvt.getY()+MyAvt.getWidth()-28,Align.center);
        groupAvt.addActor(name);

    }
    void loadAvtBot(){
        float x=0,y=0,paddingX=0;
        for(int i=1;i<boardConfig.modePlay;i++){
            if(i==1){
                x = positionCards.get(i).x-70;
                y = positionCards.get(i).y-220;
                paddingX=20;
            }else if(i==2){
                x = positionCards.get(i).x+220;
                y = positionCards.get(i).y-70;
                paddingX=15;

            }else if(i==3){
                x = positionCards.get(i).x+70;
                y = positionCards.get(i).y+120;
                paddingX=25;
            }
            Image avtBot = GUI.createImage(atlas,"avtBot"+i);
            Image frameName = GUI.createImage(atlas,"nameBot");
            Label name = new Label(""+boardConfig.arrName[i],new Label.LabelStyle(fontName,null));
            avtBot.setPosition(x,y,Align.center);
            frameName.setPosition(x,y+40,Align.center);
            name.setFontScale(0.4f);
            name.setOrigin(Align.center);
            name.setPosition(frameName.getX()+paddingX,frameName.getY()-5);

            groupAvt.addActor(avtBot);
            groupAvt.addActor(frameName);
            groupAvt.addActor(name);
            AvtBotArr.add(avtBot);
            frameNameBotArr.add(frameName);
            LabelNameBotArr.add(name);

        }
    }

    void initFont(){
        font = GAssetsManager.getBitmapFont("silver.fnt");
        fontName = GAssetsManager.getBitmapFont("fontVn.fnt");
        fontmonney = GAssetsManager.getBitmapFont("gold.fnt");
        font_white = GAssetsManager.getBitmapFont("font_white.fnt");
    }


    void initAtlas(){
        atlas = GAssetsManager.getTextureAtlas("ui.atlas");
        cardAtlas = GAssetsManager.getTextureAtlas("card.atlas");
    }
}
