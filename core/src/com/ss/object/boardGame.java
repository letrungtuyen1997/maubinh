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
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
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

import java.util.Comparator;

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
            Tweens.setTimeout(group,1f,()->{
                for (int i=0;i<4;i++){
                    int j=i+1;
                    if(i==3){
                        j=i;
                    }
                    BinhBot(j);
                    setPositonCard(i);
                }

                Tweens.setTimeout(group,2f,()->{
                    setflipAllCard();
                    lifeGame(0);
                });
            });

            return;
        }
        final int indexTemp = index+1;
        Tweens.setTimeout(group,0.03f,()->{
            allCard.get(index).moveCard(gamePlay.positionCards.get(index%boardConfig.modePlay).x - allCard.get(index).card.getWidth()/2,gamePlay.positionCards.get(index%boardConfig.modePlay).y - allCard.get(index).card.getHeight()/2,(int)(Math.random()*100));
            CardPlay.get(index%boardConfig.modePlay).add(allCard.get(index));
            allCard.get(index).scaleCard();
            allCard.get(index).tileDown.setVisible(true);
        });


        Tweens.setTimeout(group, 0.01f, ()->{
            distributeCardInside(indexTemp);
        });
    }
    void setPositonCard(int index){

        float paddingCardX = allCard.get(0).card.getWidth()/2;
        float paddingCardY = allCard.get(0).card.getHeight()/2-20;
        for (int i = 0; i<5;i++){
            CardPlay.get(index).get(i).moveCardEnd(gamePlay.positionCards.get(index).x-(paddingCardX*4)/2+paddingCardX*i,gamePlay.positionCards.get(index).y,0,0.5f);
            CardPlay.get(index).get(i).setZindexCard(i+10);


        }
        for (int i = 5; i<10;i++){
            CardPlay.get(index).get(i).moveCardEnd(gamePlay.positionCards.get(index).x-(paddingCardX*4)/2+paddingCardX*(i-5),gamePlay.positionCards.get(index).y-paddingCardY,0,0.5f);
            CardPlay.get(index).get(i).setZindexCard(i);
        }
        for (int i=10;i<13;i++){
            CardPlay.get(index).get(i).moveCardEnd(gamePlay.positionCards.get(index).x-(paddingCardX*4)/2+paddingCardX*(i-10),gamePlay.positionCards.get(index).y-(paddingCardY*2),0,0.5f);
            CardPlay.get(index).get(i).setZindexCard(i-10);
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
                    new BinhPlayer(cardAtlas,CardPlay.get(0));


                });
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }
    void BinhBot(int index){
        Array<Integer> Cardbot = new Array<>();
        for(int i=0;i<CardPlay.get(index).size;i++){
            Cardbot.add(CardPlay.get(index).get(i).Key);
        }


        Array<Array<Integer>> BotBinhFinish =  CheckCard.move(Cardbot);
        for (Integer i : BotBinhFinish.get(2))
            System.out.print(CheckCard.nameMap.get(i) + " ");
        System.out.println();
        SwapCardBot(BotBinhFinish,index);
    }
    void SwapCardBot(Array<Array<Integer>> BotBinhFinish,int index){
        /////// binh top//////
        for (int j=0;j<5;j++){
            for (int i=0;i<CardPlay.get(index).size;i++){
                if(CardPlay.get(index).get(i).Key==BotBinhFinish.get(2).get(j)){
                    SwapArrCard(CardPlay.get(index), i, j);
                }
            }
        }
        /////// binh mid//////
        for (int j=0;j<5;j++){
            for (int i=0;i<CardPlay.get(index).size;i++){
                if(CardPlay.get(index).get(i).Key==BotBinhFinish.get(1).get(j)){
                    SwapArrCard(CardPlay.get(index), i,5+ j);
                }
            }
        }
        /////// binh low//////
        for (int j=0;j<3;j++){
            for (int i=0;i<CardPlay.get(index).size;i++){
                if(CardPlay.get(index).get(i).Key==BotBinhFinish.get(0).get(j)){
                    SwapArrCard(CardPlay.get(index), i,10+ j);
                }

            }
        }



    }

    void SwapArrCard(Array<Card> A,int indexA , int indexB){
        A.swap(indexA,indexB);
    }
    void lifeGame(int index){
        if(index>=3){
            return;
        }
        if(index==0) {
            for (int i = 0; i < 5; i++) {
                int finalI = i;
                Tweens.setTimeout(group, 0.1f * i, () -> {
                    CardPlay.get(0).get(finalI).setVisibleTiledown();
                    CardPlay.get(0).get(finalI).flipAllCard(boardConfig.scaleBot, 0.3f);
                    CardPlay.get(1).get(finalI).setVisibleTiledown();
                    CardPlay.get(1).get(finalI).flipAllCard(boardConfig.scaleBot, 0.3f);
                    CardPlay.get(2).get(finalI).setVisibleTiledown();
                    CardPlay.get(2).get(finalI).flipAllCard(boardConfig.scaleBot, 0.3f);
                    CardPlay.get(3).get(finalI).setVisibleTiledown();
                    CardPlay.get(3).get(finalI).flipAllCard(boardConfig.scaleBot, 0.3f);

                });
            }
        }else if(index==1){
            for (int i = 5; i < 10; i++) {
                int finalI = i;
                Tweens.setTimeout(group, 0.1f * i, () -> {
                    CardPlay.get(0).get(finalI).setVisibleTiledown();
                    CardPlay.get(0).get(finalI).flipAllCard(boardConfig.scaleBot, 0.3f);
                    CardPlay.get(1).get(finalI).setVisibleTiledown();
                    CardPlay.get(1).get(finalI).flipAllCard(boardConfig.scaleBot, 0.3f);
                    CardPlay.get(2).get(finalI).setVisibleTiledown();
                    CardPlay.get(2).get(finalI).flipAllCard(boardConfig.scaleBot, 0.3f);
                    CardPlay.get(3).get(finalI).setVisibleTiledown();
                    CardPlay.get(3).get(finalI).flipAllCard(boardConfig.scaleBot, 0.3f);

                });
            }
        }else {
            for (int i = 10; i < 13; i++) {
                int finalI = i;
                Tweens.setTimeout(group, 0.1f * i, () -> {
                    CardPlay.get(0).get(finalI).setVisibleTiledown();
                    CardPlay.get(0).get(finalI).flipAllCard(boardConfig.scaleBot, 0.3f);
                    CardPlay.get(1).get(finalI).setVisibleTiledown();
                    CardPlay.get(1).get(finalI).flipAllCard(boardConfig.scaleBot, 0.3f);
                    CardPlay.get(2).get(finalI).setVisibleTiledown();
                    CardPlay.get(2).get(finalI).flipAllCard(boardConfig.scaleBot, 0.3f);
                    CardPlay.get(3).get(finalI).setVisibleTiledown();
                    CardPlay.get(3).get(finalI).flipAllCard(boardConfig.scaleBot, 0.3f);

                });
            }
        }
        int finalIndex1 = index;
        Tweens.setTimeout(group,2f,()->{
            setZindexCard(finalIndex1);
        });
        index++;
        int finalIndex = index;
        Tweens.setTimeout(group,3f,()->{
            lifeGame(finalIndex);
        });

    }
    void setZindexCard(int index){
        for (int j=0;j<boardConfig.modePlay;j++){
            if(index==0){
                for (int i = 0; i<5;i++){
                    CardPlay.get(j).get(i).setZindexCard2(i+8);
                    CardPlay.get(j).get(i).card.setColor(Color.DARK_GRAY);
                }
            }else if(index==1){
                for (int i = 5; i<10;i++){
                    CardPlay.get(j).get(i).setZindexCard2(i);
                    CardPlay.get(j).get(i).card.setColor(Color.DARK_GRAY);
                }
            }else {
                for (int i=10;i<13;i++){
                    CardPlay.get(j).get(i).setZindexCard2(i-10);
                    CardPlay.get(j).get(i).card.setColor(Color.DARK_GRAY);
                }
            }
        }

    }
    void setflipAllCard(){
        for(int i=0;i<CardPlay.size;i++){
            for (int j=0;j<CardPlay.get(i).size;j++){
                CardPlay.get(i).get(j).flipAllCard(-boardConfig.scaleBot,0f);
            }
        }
    }

}
