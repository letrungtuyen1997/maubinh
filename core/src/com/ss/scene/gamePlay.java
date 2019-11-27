package com.ss.scene;

import com.badlogic.gdx.graphics.Color;
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
import com.effect.SoundEffect;
import com.ss.GMain;
import com.ss.commons.Tweens;
import com.ss.core.action.exAction.GSimpleAction;
import com.ss.core.util.GAssetsManager;
import com.ss.core.util.GLayer;
import com.ss.core.util.GScreen;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;
import com.ss.object.PaticleSuper;
import com.ss.object.boardConfig;
import com.ss.object.boardGame;
import com.ss.object.setting;

import java.text.DecimalFormat;

public class gamePlay extends GScreen {
    TextureAtlas atlas, cardAtlas,adsAtlas;
    BitmapFont font, fontmonney, fontName,font_white,fontResult,fontResult1;
    Group group = new Group();
    Group groupAvt = new Group();
    Group groupBoard = new Group();
    Group groupParticle = new Group();
    Group groupMonney = new Group();
    public Array<Vector2> positionCards;
    Array<Image> AvtBotArr = new Array<>();
    Array<Image> frameNameBotArr = new Array<>();
    Array<Label> LabelNameBotArr = new Array<>();
    Array<Long> arrMonney = new Array<>();
    Array<Label> arrLabel = new Array<>();
    Image btnNewgame;
    int countAds =0;


    @Override
    public void dispose() {
    }
    @Override
    public void init() {
        SoundEffect.Playmusic2();
        GStage.addToLayer(GLayer.ui,group);
        GStage.addToLayer(GLayer.ui,groupBoard);
        GStage.addToLayer(GLayer.ui,groupParticle);
        GStage.addToLayer(GLayer.ui,groupAvt);
        GStage.addToLayer(GLayer.ui,groupMonney);
        initAtlas();
        initFont();
        initPositionCards();
        showBg();
        loadAvtPlayer();
        loadAvtBot();
        loadLabelMoney();
        showframeSetting();
        showMuccuoc();


    }
    @Override
    public void run() {
    }
    void showBg(){
        Image bg = GUI.createImage(atlas,"table2");
        bg.setWidth(GStage.getWorldWidth());
        bg.setHeight(GStage.getWorldHeight());
        group.addActor(bg);
        ////// new boardgame//////
        Tweens.setTimeout(group,1,()->{
            new boardGame(cardAtlas,atlas,this,groupBoard,groupParticle,fontResult,fontResult1,arrMonney,arrLabel);
        });

    }
    private void initPositionCards(){
        positionCards = new Array<>();
        int numberPlayer = boardConfig.modePlay;
        float delta2 = (GStage.getWorldHeight()*16/9)/5;
        float delta3 = (GStage.getWorldHeight()*16/9)/15;
        float deltaY = GStage.getWorldHeight()/20;
        float delta = (GStage.getWorldWidth() - (float)GStage.getWorldHeight()*16/9)/2;
        switch (numberPlayer){
            case 2: {
                Vector2 position1 = new Vector2(delta + delta2 + delta3, GStage.getWorldHeight()*3/4+100);
                Vector2 position2 = new Vector2((float)GStage.getWorldHeight()*16/9 - (delta + delta2 + delta3 + boardConfig.widthCard*0.4f), GStage.getWorldHeight()/3 - deltaY);
                positionCards.add(position1, position2);
                break;
            }
            case 3: {
                Vector2 position1 = new Vector2(GStage.getWorldWidth()/2, GStage.getWorldHeight()*3/4+100);
                Vector2 position2 = new Vector2(delta + delta2+200,GStage.getWorldHeight()/3);
                Vector2 position3 = new Vector2((float)GStage.getWorldHeight()*16/9 - delta - delta2-150, GStage.getWorldHeight()/3);
                positionCards.add(position1, position2, position3);
                break;
            }
            case 4: {
                Vector2 position1 = new Vector2(GStage.getWorldWidth()/2+100, GStage.getWorldHeight()*3/4+50);
                Vector2 position2 = new Vector2(170,GStage.getWorldHeight()/2 +50);
                Vector2 position3 = new Vector2(GStage.getWorldWidth()/2-70,delta2-50);
                Vector2 position4 = new Vector2(GStage.getWorldWidth()- delta2+80, GStage.getWorldHeight()/2-50);
                positionCards.add(position1, position2, position3, position4);
                break;
            }
            case 5: {
                Vector2 position1 = new Vector2(GStage.getWorldWidth()/2, GStage.getWorldHeight()*3/4+100);
                Vector2 position2 = new Vector2(delta + delta2,GStage.getWorldHeight()*2/3-40);
                Vector2 position3 = new Vector2(delta + delta2+200,GStage.getWorldHeight()/3);
                Vector2 position4 = new Vector2((float)GStage.getWorldHeight()*16/9 - delta - delta2-150, GStage.getWorldHeight()/3);
                Vector2 position5 = new Vector2((float)GStage.getWorldHeight()*16/9+50 - delta - delta2, GStage.getWorldHeight()*2/3-40);
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
                x = positionCards.get(i).x-50;
                y = positionCards.get(i).y-300;
                paddingX=20;
            }else if(i==2){
                x = positionCards.get(i).x+260;
                y = positionCards.get(i).y-120;
                paddingX=15;

            }else if(i==3){
                x = positionCards.get(i).x;
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
    void  loadLabelMoney(){
        arrMonney.add(boardConfig.Mymonney);
        for(int i=0;i<3;i++){
            long monney = boardConfig.monneyStart*(long)(Math.random()*100+24);
            arrMonney.add(monney);
        }
        float x = 0;
        float y = 0;
        for(int i=0;i<boardConfig.modePlay;i++){
            Label monney = new Label("$ "+FortmartPrice(arrMonney.get(i)),new Label.LabelStyle(fontmonney,null));
            if(i==0){x=positionCards.get(i).x; y=positionCards.get(i).y+90;}
            else if(i==1){x=positionCards.get(i).x-60; y=positionCards.get(i).y-220; monney.setFontScale(0.5f);
            }
            else if(i==2){x=positionCards.get(i).x+255; y=positionCards.get(i).y-40; monney.setFontScale(0.5f);
            }
            else if(i==3){x=positionCards.get(i).x-5; y=positionCards.get(i).y+200; monney.setFontScale(0.5f);
            }
            monney.setOrigin(Align.center);
            monney.setPosition(x+10,y,Align.center);
            monney.setAlignment(Align.center);
            groupMonney.addActor(monney);
            arrLabel.add(monney);
        }
    }
    public void showbtnNewGame(){
        btnNewgame = GUI.createImage(atlas,"btnReset");
        btnNewgame.setOrigin(Align.center);
        btnNewgame.setPosition(GStage.getWorldWidth()/2, GStage.getWorldHeight()/2-30,Align.center);
        group.addActor(btnNewgame);
        btnNewgame.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                countAds++;
                if(countAds==3){
                    GMain.platform.ShowFullscreen();
                    countAds=0;
                }
                SoundEffect.Play(SoundEffect.click);
                btnNewgame.setTouchable(Touchable.disabled);
                btnNewgame.addAction(Actions.sequence(
                        Actions.scaleTo(0.8f,0.8f,0.1f),
                        Actions.scaleTo(1f,1f,0.1f)
                ));
                Tweens.setTimeout(group,0.2f,()->{
                    btnNewgame.setTouchable(Touchable.enabled);
                    checkMonney();
                    if(arrMonney.get(0)>(boardConfig.monneyStart*24)){
                        removebtnNewgame();
                        btnNewgame.setTouchable(Touchable.enabled);
                        groupBoard.remove();
                        groupBoard.clear();
                        groupParticle.remove();
                        groupParticle.clear();
                        new boardGame(cardAtlas,atlas,gamePlay.this,groupBoard,groupParticle,fontResult,fontResult1,arrMonney,arrLabel);
                    }

                });
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }
    public void removebtnNewgame(){
        btnNewgame.remove();
        btnNewgame.clear();
    }
    void showframeSetting(){
        Image btnSetting = GUI.createImage(atlas,"btnSetting");
        btnSetting.setPosition(GStage.getWorldWidth()-btnSetting.getWidth()/2,btnSetting.getHeight()/2,Align.center);
        group.addActor(btnSetting);
        btnSetting.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                boardConfig.TimePlay++;
                GMain.platform.TrackCustomEvent("TimePlay: "+boardConfig.TimePlay);
                SoundEffect.Play(SoundEffect.click);
                btnSetting.setTouchable(Touchable.disabled);
                btnSetting.addAction(Actions.sequence(
                        Actions.scaleTo(0.8f,0.8f,0.1f),
                        Actions.scaleTo(1f,1f,0.1f),
                        GSimpleAction.simpleAction((d,a)->{
                            new setting(atlas,btnSetting,gamePlay.this);
                            return true;
                        })
                ));
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }
    void showMuccuoc(){
        Image frame = GUI.createImage(atlas,"frm");
        frame.setWidth(frame.getWidth()*0.7f);
        frame.setHeight(frame.getHeight()*0.7f);
        frame.setOrigin(Align.center);
        frame.setPosition(frame.getWidth()/2,frame.getHeight()/2,Align.center);
        group.addActor(frame);
        Label text = new Label("Mức cược: $ "+FortmartPrice(boardConfig.monneyStart),new Label.LabelStyle(font,null));
        text.setFontScale(0.4f);
        text.setOrigin(Align.center);
        text.setAlignment(Align.center);
        text.setPosition(frame.getX()+140,20,Align.center);
        group.addActor(text);
    }
    void checkMonney(){
        for (int i=0;i<arrMonney.size;i++){
            if(arrMonney.get(i)<=0||arrMonney.get(i)<(boardConfig.monneyStart*24)){
                if(i==0){
                    WatchAds();
                }else {
                    long monney = boardConfig.monneyStart*(long)(Math.random()*100+24);
                    arrMonney.set(i,monney);
                    arrLabel.get(i).setText(FortmartPrice(arrMonney.get(i)));
                }
            }
        }
    }
    void WatchAds(){
        Group groupfrm = new Group();
        GStage.addToLayer(GLayer.top,groupfrm);
        groupfrm.setPosition(GStage.getWorldWidth()/2,GStage.getWorldHeight()/2,Align.center);
        Image frmAds = GUI.createImage(adsAtlas,"frameAds");
        frmAds.setPosition(0,0,Align.center);
        groupfrm.addActor(frmAds);
        groupfrm.setScale(0);
        groupfrm.addAction(Actions.scaleTo(1,1,0.1f));
        /////// btn close////////
        Image btnClose = GUI.createImage(adsAtlas,"btnClose2");
        btnClose.setPosition(-btnClose.getWidth(),btnClose.getHeight()+30,Align.center);
        groupfrm.addActor(btnClose);
        ///////// btn watch ///////
        Image btnWatch = GUI.createImage(adsAtlas,"btnWatch");
        btnWatch.setPosition(btnWatch.getWidth(),btnWatch.getHeight()+30,Align.center);
        groupfrm.addActor(btnWatch);
        /////// event btn//////
        evenbtnclose(btnClose,groupfrm);
        evenbtnwatch(btnWatch,groupfrm);
    }
    void evenbtnclose(Image btn, Group group){
        btn.setOrigin(Align.center);
        btn.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                SoundEffect.Play(SoundEffect.click);
                btn.setTouchable(Touchable.disabled);
                btn.addAction(Actions.sequence(
                        Actions.scaleTo(0.8f,0.8f,0.1f),
                        Actions.scaleTo(1f,1f,0.1f),
                        GSimpleAction.simpleAction((d,a)->{

                            group.clear();
                            group.remove();
                            return true;
                        })
                ));
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }
    void evenbtnwatch(Image btn, Group group){
        btn.setOrigin(Align.center);
        btn.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                SoundEffect.Play(SoundEffect.click);
                btn.setTouchable(Touchable.disabled);
                btn.addAction(Actions.sequence(
                        Actions.scaleTo(0.8f,0.8f,0.1f),
                        Actions.scaleTo(1f,1f,0.1f),
                        GSimpleAction.simpleAction((d,a)->{
                            showAds(btn,group);
                            return true;
                        })
                ));
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }
    void showAds(Image btn,Group group){
        if(GMain.platform.isVideoRewardReady()) {
            GMain.platform.ShowVideoReward((boolean success) -> {
                if (success) {
                    long monney_donate = GMain.platform.GetConfigIntValue("money_donateAds",500000);
                    boardConfig.Mymonney+=monney_donate;
                    GMain.prefs.putLong("mymonney",boardConfig.Mymonney);
                    GMain.prefs.flush();
                    arrMonney.set(0,boardConfig.Mymonney);
                    arrLabel.get(0).setText(FortmartPrice(arrMonney.get(0)));
                    Label text = new Label("+"+FortmartPrice(monney_donate),new Label.LabelStyle(font,null));
                    text.setPosition(0,0,Align.center);
                    group.addActor(text);
                    text.addAction(Actions.moveBy(0,-100,0.6f));
                    Tweens.setTimeout(group,0.6f,()->{
                        group.clear();
                        group.remove();
                    });
                }else {
                    btn.setTouchable(Touchable.enabled);

                }
            });
        }else {
            Label notice = new Label("Kiểm tra kết nối",new Label.LabelStyle(font, Color.RED));
            notice.setPosition(0,0,Align.center);
            group.addActor(notice);
            notice.addAction(Actions.sequence(
                    Actions.moveBy(0,-50,0.5f),
                    GSimpleAction.simpleAction((d, a)->{
                        notice.clear();
                        notice.remove();
                        btn.setTouchable(Touchable.enabled);
                        return true;
                    })
            ));

        }
    }


    void initFont(){
        font = GAssetsManager.getBitmapFont("font_white.fnt");
        fontName = GAssetsManager.getBitmapFont("fontVn.fnt");
        fontmonney = GAssetsManager.getBitmapFont("gold.fnt");
        font_white = GAssetsManager.getBitmapFont("font_white.fnt");
        fontResult = GAssetsManager.getBitmapFont("fontBlue.fnt");
        fontResult1 = GAssetsManager.getBitmapFont("fontGray.fnt");
    }
    void initAtlas(){
        atlas = GAssetsManager.getTextureAtlas("ui.atlas");
        adsAtlas = GAssetsManager.getTextureAtlas("uiStart.atlas");
        cardAtlas = GAssetsManager.getTextureAtlas("card.atlas");
    }
    private String FortmartPrice(Long Price) {

        DecimalFormat mDecimalFormat = new DecimalFormat("###,###,###,###");
        String mPrice = mDecimalFormat.format(Price);

        return mPrice;
    }
}
