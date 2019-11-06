package com.ss.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
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
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;
import com.ss.scene.gamePlay;

public class boardGame {
    gamePlay gamePlay;
    TextureAtlas cardAtlas, uiAtlas;
    Group group = new Group();
    Array<Card> allCard = new Array<>();
    Array<Integer> cards;
    Array<Array<Card>> CardPlay = new Array<>();



    public boardGame(TextureAtlas cardAtlas,TextureAtlas uiAtlas,gamePlay gamePlay,Group group){
        this.group = group;
        this.uiAtlas = uiAtlas;
        this.gamePlay = gamePlay;
        GStage.addToLayer(GLayer.ui,group);
        this.cardAtlas = cardAtlas;
        innitPlayer();
        renderCard();
        distributeCardsOutSide();
        showbtnXepBai();

    }
    void innitPlayer(){
        for(int i=0;i<boardConfig.modePlay;i++){
            Array<Card> card = new Array<>();
            CardPlay.add(card);

        }
    }
    void renderCard(){
        cards = CheckCard.makeCards();
        for(int i=1;i<53;i++){
            Card card = new Card(cardAtlas,group,i);
            card.scaleCard();
            allCard.add(card);
        }
        for (int i=0 ; i < allCard.size; i++){
            allCard.get(i).setKey(cards.get(i));
        }
    }
    void distributeCardsOutSide(){
        allCard.shuffle();
        distributeCardInside(0);

    }

    void distributeCardInside(int index){
        if(index >= gamePlay.positionCards.size*13){
            Gdx.app.log("okok","card size: "+CardPlay.get(2).size);
            Tweens.setTimeout(group,0.8f,()->{
                for (int i=0;i<4;i++){
                    setPositonCard(i);
                }
            });

            return;
        }
        final int indexTemp = index+1;
        Tweens.setTimeout(group,0.01f,()->{
            allCard.get(index).moveCard(gamePlay.positionCards.get(index%boardConfig.modePlay).x - allCard.get(index).card.getWidth()/2,gamePlay.positionCards.get(index%boardConfig.modePlay).y - allCard.get(index).card.getHeight()/2,(int)(Math.random()*100));
            CardPlay.get(index%boardConfig.modePlay).add(allCard.get(index));
            allCard.get(index).scaleCard();
            allCard.get(index).setVisibleTiledown();
        });


        Tweens.setTimeout(group, 0.02f, ()->{
            distributeCardInside(indexTemp);
        });
    }
    void setPositonCard(int index){

        float paddingCardX = allCard.get(0).card.getWidth()/2;
        float paddingCardY = allCard.get(0).card.getHeight()/3;
        for (int i = 0; i<5;i++){
            CardPlay.get(index).get(i).moveCardEnd(gamePlay.positionCards.get(index).x-(paddingCardX*4)/2+paddingCardX*i,gamePlay.positionCards.get(index).y,0,0.5f);
            CardPlay.get(index).get(i).setVisibleTiledown();
            CardPlay.get(index).get(i).card.setZIndex(i+8);
        }
        for (int i = 5; i<10;i++){
            CardPlay.get(index).get(i).moveCardEnd(gamePlay.positionCards.get(index).x-(paddingCardX*4)/2+paddingCardX*(i-5),gamePlay.positionCards.get(index).y-paddingCardY,0,0.5f);
            CardPlay.get(index).get(i).setVisibleTiledown();
            CardPlay.get(index).get(i).card.setZIndex(i-2);

        }
        for (int i=10;i<13;i++){
            CardPlay.get(index).get(i).moveCardEnd(gamePlay.positionCards.get(index).x-(paddingCardX*4)/2+paddingCardX*(i-10),gamePlay.positionCards.get(index).y-(paddingCardY*2),0,0.5f);
            CardPlay.get(index).get(i).setVisibleTiledown();
            CardPlay.get(index).get(i).card.setZIndex(i-10);

        }
    }
    void showbtnXepBai(){
        Image btnXepbai = GUI.createImage(uiAtlas,"btnXepbai");
        btnXepbai.setOrigin(Align.center);
        btnXepbai.setPosition(GMain.screenWidth-100, GMain.screenHeight-100,Align.center);
        group.addActor(btnXepbai);
        btnXepbai.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                btnXepbai.setTouchable(Touchable.disabled);
                btnXepbai.addAction(Actions.sequence(
                        Actions.scaleTo(0.8f,0.8f,0.1f),
                        Actions.scaleTo(1f,1f,0.1f)
                ));
                Tweens.setTimeout(group,0.2f,()->{
                    btnXepbai.setTouchable(Touchable.enabled);


                });
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }








}
