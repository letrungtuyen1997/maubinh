package com.ss.object;

import com.badlogic.gdx.graphics.Color;
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
import com.badlogic.gdx.utils.Array;
import com.effect.SoundEffect;
import com.ss.GMain;
import com.ss.commons.Tweens;
import com.ss.core.action.exAction.GSimpleAction;
import com.ss.core.exSprite.GShapeSprite;
import com.ss.core.transitions.GTransition;
import com.ss.core.transitions.GTransitionFade;
import com.ss.core.util.GLayer;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;
import com.ss.scene.gamePlay;
import com.ss.scene.gameStart;

import java.text.DecimalFormat;

public class SelectTable {

    TextureAtlas atlas;
    Group group = new Group();
    Array<Image> btnArr = new Array<>();
    long monney;
    gameStart gameStart;
    Label Labelmonney;
    BitmapFont font;
    public SelectTable(TextureAtlas atlas,long monney,gameStart gameStart,Label LabelMonney,BitmapFont font){
        this.font = font;
        this.Labelmonney = LabelMonney;
        this.gameStart = gameStart;
        this.monney = monney;
        GStage.addToLayer(GLayer.top,group);
        this.atlas = atlas;
        darkScreen();
        showFrame();
        eventbtn();
        btnClose();

    }
    private void darkScreen(){
        final GShapeSprite blackOverlay = new GShapeSprite();
        blackOverlay.createRectangle(true, -GStage.getWorldWidth()/2,-GStage.getWorldHeight()/2, GStage.getWorldWidth()*2, GStage.getWorldHeight()*2);
        blackOverlay.setColor(0,0,0,0.5f);
        group.addActor(blackOverlay);
    }
    void showFrame(){
        group.setPosition(GStage.getWorldWidth()/2,GStage.getWorldHeight()/2,Align.center);
        Image btn;
        for (int i=0;i<4;i++){
            btn = GUI.createImage(atlas,"btn"+(i+1));
            btnArr.add(btn);
            if(i==0||i==1){
                btn.setPosition(-btn.getWidth()/2+(btn.getWidth()*i),-btn.getHeight()/2, Align.center);
            }else {
                btn.setPosition(-btn.getWidth()/2+(btn.getWidth()*(i-2)),btn.getHeight()/2, Align.center);
            }
            group.addActor(btn);
        }
    }
    void btnClose(){
        Image btnClose = GUI.createImage(atlas,"btnReturn");
        btnClose.setPosition(-GStage.getWorldWidth()/2+btnClose.getWidth()/2,-GStage.getWorldHeight()/2+btnClose.getHeight()/2,Align.center);
        group.addActor(btnClose);
        btnClose.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                SoundEffect.Play(SoundEffect.click);
                group.remove();
                group.clear();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }
    void eventbtn(){
        for (int i=0;i<btnArr.size;i++){
            int finalI = i;
            btnArr.get(i).setOrigin(Align.center);
            btnArr.get(i).addListener(new ClickListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    setTouch(true);
                    SoundEffect.Play(SoundEffect.click);
                    btnArr.get(finalI).addAction(Actions.sequence(
                            Actions.scaleTo(0.8f,0.8f,0.1f),
                            Actions.scaleTo(1f,1f,0.1f)
                    ));
                    Tweens.setTimeout(group,0.2f,()->{
                        if(finalI ==0){
                            boardConfig.monneyStart = 10000;
                        }else if(finalI ==1){
                            boardConfig.monneyStart = 100000;

                        }else if(finalI ==2){
                            boardConfig.monneyStart = 500000;

                        }else if(finalI ==3){
                            boardConfig.monneyStart = 1000000;
                        }
                        checkenoughMonney();
                    });
                    return super.touchDown(event, x, y, pointer, button);
                }
            });
        }
    }
    void setTouch(boolean set){
        for (int i=0;i<btnArr.size;i++){
            if(set ==true){
                btnArr.get(i).setTouchable(Touchable.disabled);
            }else if(set==false){
                btnArr.get(i).setTouchable(Touchable.enabled);
            }
        }

    }
    void checkenoughMonney(){
        if(monney<(boardConfig.monneyStart*24)){
            WatchAds();
        }else {
            gameStart.setScreen(new gamePlay(), GTransitionFade.init(0.3f));
        }
    }
    void WatchAds(){
        Group groupfrm = new Group();
        GStage.addToLayer(GLayer.top,groupfrm);
        groupfrm.setPosition(GStage.getWorldWidth()/2,GStage.getWorldHeight()/2,Align.center);
        Image frmAds = GUI.createImage(atlas,"frameAds");
        frmAds.setPosition(0,0,Align.center);
        groupfrm.addActor(frmAds);
        groupfrm.setScale(0);
        groupfrm.addAction(Actions.scaleTo(1,1,0.1f));
        /////// btn close////////
        Image btnClose = GUI.createImage(atlas,"btnClose2");
        btnClose.setPosition(-btnClose.getWidth(),btnClose.getHeight()+30,Align.center);
        groupfrm.addActor(btnClose);
        ///////// btn watch ///////
        Image btnWatch = GUI.createImage(atlas,"btnWatch");
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
                            setTouch(false);
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
                    monney =boardConfig.Mymonney;
                    Labelmonney.setText(FortmartPrice(monney));
                    Label text = new Label("+"+FortmartPrice(monney_donate),new Label.LabelStyle(font,null));
                    text.setPosition(0,0,Align.center);
                    group.addActor(text);
                    text.addAction(Actions.moveBy(0,-100,0.6f));
                    Tweens.setTimeout(group,0.6f,()->{
                        setTouch(false);
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
    private String FortmartPrice(Long Price) {

        DecimalFormat mDecimalFormat = new DecimalFormat("###,###,###,###");
        String mPrice = mDecimalFormat.format(Price);
        return mPrice;
    }
}
