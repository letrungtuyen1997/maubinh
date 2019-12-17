package com.ss.object;

import com.badlogic.gdx.Gdx;
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
import com.effect.effectWin;
import com.ss.GMain;
import com.ss.commons.Tweens;
import com.ss.core.action.exAction.GSimpleAction;
import com.ss.core.util.GLayer;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;
import com.ss.scene.gamePlay;

import java.text.DecimalFormat;

public class boardGame {
  gamePlay gamePlay;
  TextureAtlas cardAtlas, uiAtlas;
  BitmapFont font,font1;
  Group group = new Group();
  Group groupParticle = new Group();
  Group groupResult = new Group();
  Array<Card> allCard = new Array<>();
  Array<Integer> cards;
  Array<Array<Card>> CardPlay = new Array<>();
  Array<Card> CardTest = new Array<>();
  Array<Integer> ArrSpecial = new Array<>();
  Array<Integer> ArrCompare = new Array<>();
  Array<Label> LabelMonney ;
  Array<Long> ArrMonney ;
  Array<Array<Integer>> arrayCheck= new Array<>();
  Array<Integer> arrayWinner= new Array<>();
  Array<Integer> arrSupper = new Array<>();
  Array<Integer> resultBranch = new Array<>();
  Array<Array<Integer>> CheckLoseAll = new Array<>();
  int branch =0;
  public boolean checkBinhLung = false;


  public boardGame(TextureAtlas cardAtlas,TextureAtlas uiAtlas,gamePlay gamePlay,Group group,Group groupParticle, BitmapFont font,BitmapFont font1,Array<Long> monney,Array<Label> labelmonney){
    this.LabelMonney = labelmonney;
    this.ArrMonney = monney;
    this.font = font;
    this.font1 = font1;
    this.group = group;
    this.groupParticle = groupParticle;
    this.uiAtlas = uiAtlas;
    this.gamePlay = gamePlay;
    GStage.addToLayer(GLayer.ui,group);
    GStage.addToLayer(GLayer.top,groupParticle);
    GStage.addToLayer(GLayer.top,groupResult);
    this.cardAtlas = cardAtlas;
    innitPlayer();
    renderCard();
    distributeCardsOutSide();
    //new GameOver(resultBranch);
    setArrCheckLoseAll();

  }
  void innitPlayer(){
    for(int i=0;i<boardConfig.modePlay;i++){
      Array<Card> card = new Array<>();
      CardPlay.add(card);
      resultBranch.add(0);
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
      for(int i=0;i<13;i++){
          Tweens.setTimeout(group,0.1f*i,()->{
              SoundEffect.Play(SoundEffect.chiabai);
          });
      }
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
        Tweens.setTimeout(group,1f,()->{
          checkSupper();
          if(ArrSpecial.size!=0){
            for (int i=0; i<arrSupper.size;i++){
              if(arrSupper.get(i)!=0){
                resultSpecial(arrSupper.get(i));
              }
            }
            if(ArrSpecial.get(0)==0 ){
                Tweens.setTimeout(group,1f,()->{
                    lifeGame(0,"game");
                });
            }else {
              showbtnXepBai();
            }
          }else {
            showbtnXepBai();
//            gamePlay.showbtnNewGame();
          }
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
    int zIndex = 100;

    for (int i=10;i<13;i++){
      CardPlay.get(index).get(i).moveCardEnd(gamePlay.positionCards.get(index).x-(paddingCardX*4)/2+paddingCardX*(i-10),gamePlay.positionCards.get(index).y-(paddingCardY*2),0,0.5f);
      //CardPlay.get(index).get(i).setZindexCard(i-10);
      CardPlay.get(index).get(i).card.setZIndex(zIndex);
      zIndex++;
      CardPlay.get(index).get(i).tileDown.setZIndex(zIndex);
      zIndex++;
    }

    for (int i = 5; i<10;i++){
      CardPlay.get(index).get(i).moveCardEnd(gamePlay.positionCards.get(index).x-(paddingCardX*4)/2+paddingCardX*(i-5),gamePlay.positionCards.get(index).y-paddingCardY,0,0.5f);
      //CardPlay.get(index).get(i).setZindexCard(i);
      CardPlay.get(index).get(i).card.setZIndex(zIndex);
      zIndex++;
      CardPlay.get(index).get(i).tileDown.setZIndex(zIndex);
      zIndex++;


    }
    for (int i = 0; i<5;i++){
      CardPlay.get(index).get(i).moveCardEnd(gamePlay.positionCards.get(index).x-(paddingCardX*4)/2+paddingCardX*i,gamePlay.positionCards.get(index).y,0,0.5f);
      //CardPlay.get(index).get(i).setZindexCard(i+10);
      CardPlay.get(index).get(i).card.setZIndex(zIndex);
      zIndex++;
      CardPlay.get(index).get(i).tileDown.setZIndex(zIndex);
      zIndex++;

    }


  }
  void showbtnXepBai(){
    Image btnXepbai = GUI.createImage(uiAtlas,"btnXepbai");
    btnXepbai.setOrigin(Align.center);
    btnXepbai.setPosition(GStage.getWorldWidth()-100, GStage.getWorldHeight()-100,Align.center);
    group.addActor(btnXepbai);
    btnXepbai.addListener(new ClickListener(){
      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
      SoundEffect.Play(SoundEffect.click);
      btnXepbai.setTouchable(Touchable.disabled);
      btnXepbai.addAction(Actions.sequence(
        Actions.scaleTo(0.8f,0.8f,0.1f),
        Actions.scaleTo(1f,1f,0.1f)
      ));
      Tweens.setTimeout(group,0.2f,()->{
        btnXepbai.remove();
        btnXepbai.clear();
        btnXepbai.setTouchable(Touchable.enabled);
        new BinhPlayer(cardAtlas,uiAtlas,CardPlay.get(0),boardGame.this,()->{
          setflipAllCard();
          if(checkBinhLung==true){
            SoundEffect.Play(SoundEffect.bomB);
            setBinhLung();
          }else {
            lifeGame(0,"game");
          }
        });
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
//    for (Integer i : BotBinhFinish.get(2))
//      System.out.print(CheckCard.nameMap.get(i) + " ");
//    System.out.println();
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
  void lifeGame(int index,String mode){
    if(index>=3){
      groupParticle.clear();
      for (int i=0;i<allCard.size;i++){
        allCard.get(i).card.setColor(Color.WHITE);
      }
      for(int i=0;i<resultBranch.size;i++){
        System.out.println("resultBranch bot "+i+": "+resultBranch.get(i));
      }
      actionChi(index);
      Tweens.setTimeout(group,0.5f,()->{
        resultfinish(mode);
      });
      Tweens.setTimeout(group,2.5f,()->{
        new GameOver(resultBranch,gamePlay);
        replay();
      });
      return;
    }
    SoundEffect.Play(SoundEffect.onTurn);
    branch= index;
    groupParticle.clear();
    actionChi(index);
    if(index==0) {
      for (int i = 0; i < 5; i++) {
        int finalI = i;
        Tweens.setTimeout(group, 0.1f * i, () -> {
          SoundEffect.Play(SoundEffect.latbai);
          for(int j=0;j<ArrCompare.size;j++){
              CardPlay.get(ArrCompare.get(j)).get(finalI).setVisibleTiledown();
              CardPlay.get(ArrCompare.get(j)).get(finalI).card.setColor(Color.WHITE);
              CardPlay.get(ArrCompare.get(j)).get(finalI).flipAllCard(boardConfig.scaleBot, 0.3f);

          }
        });
      }
    }else if(index==1){
      for (int i = 5; i < 10; i++) {
        int finalI = i;
        Tweens.setTimeout(group, 0.1f * i, () -> {
          SoundEffect.Play(SoundEffect.latbai);
          for(int j=0;j<ArrCompare.size;j++){
            CardPlay.get(ArrCompare.get(j)).get(finalI).setVisibleTiledown();
            CardPlay.get(ArrCompare.get(j)).get(finalI).card.setColor(Color.WHITE);
            CardPlay.get(ArrCompare.get(j)).get(finalI).flipAllCard(boardConfig.scaleBot, 0.3f);
          }

        });
      }
    }else {
      for (int i = 10; i < 13; i++) {
        int finalI = i;
        Tweens.setTimeout(group, 0.1f * i, () -> {
          SoundEffect.Play(SoundEffect.latbai);
          for(int j=0;j<ArrCompare.size;j++){
            CardPlay.get(ArrCompare.get(j)).get(finalI).setVisibleTiledown();
            CardPlay.get(ArrCompare.get(j)).get(finalI).card.setColor(Color.WHITE);
            CardPlay.get(ArrCompare.get(j)).get(finalI).flipAllCard(boardConfig.scaleBot, 0.3f);
          }
        });
      }
    }
    int finalIndex1 = index;
    Tweens.setTimeout(group,2f,()->{
      SoundEffect.Play(SoundEffect.Check);
      setLableResult(finalIndex1);
      Tweens.setTimeout(group,2f,()->{
        SoundEffect.Play(SoundEffect.Pay);
        setZindexCard(finalIndex1);
        CheckWin(mode);

      });
    });
    index++;
    int finalIndex = index;
    Tweens.setTimeout(group,6f,()->{
      lifeGame(finalIndex,mode);
    });

  }
  void setZindexCard(int index){
    int zIndex = 10;

    for (int j=0;j<ArrCompare.size;j++){
      if(index==0){
        for (int i = 0; i<5;i++){
          CardPlay.get(ArrCompare.get(j)).get(i).card.setZIndex(zIndex);
          zIndex++;
          CardPlay.get(ArrCompare.get(j)).get(i).tileDown.setZIndex(zIndex);
          zIndex++;

          CardPlay.get(ArrCompare.get(j)).get(i).card.setColor(Color.DARK_GRAY);
        }
      }else if(index==1){
        for (int i = 5; i<10;i++){
          CardPlay.get(ArrCompare.get(j)).get(i).card.setZIndex(zIndex);
          zIndex++;
          CardPlay.get(ArrCompare.get(j)).get(i).tileDown.setZIndex(zIndex);
          zIndex++;
          CardPlay.get(ArrCompare.get(j)).get(i).card.setColor(Color.DARK_GRAY);
        }
      }else {
        for (int i=10;i<13;i++){
          CardPlay.get(ArrCompare.get(j)).get(i).card.setZIndex(zIndex);
          zIndex++;
          CardPlay.get(ArrCompare.get(j)).get(i).tileDown.setZIndex(zIndex);
          zIndex++;
          CardPlay.get(ArrCompare.get(j)).get(i).card.setColor(Color.DARK_GRAY);
        }
      }
    }
  }
  void setflipAllCard(){
    for(int i=0;i<ArrCompare.size;i++){
      for (int j=0;j<CardPlay.get(i).size;j++){
        CardPlay.get(ArrCompare.get(i)).get(j).flipAllCard(-boardConfig.scaleBot,0f);
      }
    }
  }
  void setLableResult(int index){
    arrayCheck.clear();
    if(index==0){
      for(int i=0;i<ArrCompare.size;i++){
        int type = CheckCard.check(BinhTop(ArrCompare.get(i)))>>13;
        Image frmResult = GUI.createImage(uiAtlas,"frameResult");
        frmResult.setWidth(frmResult.getWidth()*0.5f);
        frmResult.setHeight(frmResult.getHeight()*0.8f);
        frmResult.setOrigin(Align.center);
        frmResult.setPosition(gamePlay.positionCards.get(ArrCompare.get(i)).x,gamePlay.positionCards.get(ArrCompare.get(i)).y,Align.center);
        groupParticle.addActor(frmResult);
        effectWin win = new effectWin(type,gamePlay.positionCards.get(ArrCompare.get(i)).x,gamePlay.positionCards.get(ArrCompare.get(i)).y);
        groupParticle.addActor(win);
        arrayCheck.add(BinhTop(ArrCompare.get(i)));

      }
    }else if(index==1){
      for(int i=0;i<ArrCompare.size;i++){
        int type = CheckCard.check(BinhMid(ArrCompare.get(i)))>>13;
        Image frmResult = GUI.createImage(uiAtlas,"frameResult");
        frmResult.setWidth(frmResult.getWidth()*0.5f);
        frmResult.setHeight(frmResult.getHeight()*0.8f);
        frmResult.setOrigin(Align.center);
        frmResult.setPosition(gamePlay.positionCards.get(ArrCompare.get(i)).x,gamePlay.positionCards.get(ArrCompare.get(i)).y-80,Align.center);
        groupParticle.addActor(frmResult);
        effectWin win = new effectWin(type,gamePlay.positionCards.get(ArrCompare.get(i)).x,gamePlay.positionCards.get(ArrCompare.get(i)).y-80);
        groupParticle.addActor(win);
        arrayCheck.add(BinhMid(ArrCompare.get(i)));

      }
    }else if(index==2){
      for(int i=0;i<ArrCompare.size;i++){
        int type = CheckCard.check(BinhLow(ArrCompare.get(i)))>>13;
        Image frmResult = GUI.createImage(uiAtlas,"frameResult");
        frmResult.setWidth(frmResult.getWidth()*0.5f);
        frmResult.setHeight(frmResult.getHeight()*0.8f);
        frmResult.setOrigin(Align.center);
        frmResult.setPosition(gamePlay.positionCards.get(ArrCompare.get(i)).x,gamePlay.positionCards.get(ArrCompare.get(i)).y-160,Align.center);
        groupParticle.addActor(frmResult);
        effectWin win = new effectWin(type,gamePlay.positionCards.get(ArrCompare.get(i)).x,gamePlay.positionCards.get(ArrCompare.get(i)).y-160);
        groupParticle.addActor(win);
        arrayCheck.add(BinhLow(ArrCompare.get(i)));

      }
    }
  }

  Array<Integer> BinhTop(int index){
    Array<Integer> CardBinh= new Array<>();
    for (int i=0;i<5;i++){
      CardBinh.add(CardPlay.get(index).get(i).Key);
    }
    return CardBinh;
  }
  Array<Integer> BinhMid(int index){
    Array<Integer> CardBinh= new Array<>();
    for (int i=5;i<10;i++){
      CardBinh.add(CardPlay.get(index).get(i).Key);
    }
    return CardBinh;
  }
  Array<Integer> BinhLow(int index){
    Array<Integer> CardBinh= new Array<>();
    for (int i=10;i<13;i++){
      CardBinh.add(CardPlay.get(index).get(i).Key);
    }
    return CardBinh;
  }
  /////// check Special case////////
    void checkSupper(){
      Array<Array<Integer>> arrayBinh = new Array<>();
      for(int i=0;i<4;i++){
          Array<Integer> array = new Array<>();
          for(int j=0;j<13;j++){
              array.add(CardPlay.get(i).get(j).Key);
          }
          arrayBinh.add(array);
      }
//      Array<Integer> arrSupper = new Array<>();
      for(int i=0;i<arrayBinh.size;i++){
        arrSupper.add(CheckCard.checkSuper(arrayBinh.get(i)));
//        System.out.println("checkBinhSupper: "+arrSupper.get(i));
      }
      for(int i=0;i<arrSupper.size;i++){
        if(arrSupper.get(i)==15||arrSupper.get(i)==14||arrSupper.get(i)==13||arrSupper.get(i)==12||arrSupper.get(i)==11||arrSupper.get(i)==10){
          flipAllCard(i);
          SoundEffect.Play(SoundEffect.WinSpecial);
          new PaticleSuper(arrSupper.get(i));
          effectWin effect = new effectWin(arrSupper.get(i)+8,gamePlay.positionCards.get(i).x,gamePlay.positionCards.get(i).y-90);
          group.addActor(effect);
          ArrSpecial.add(i);
        }
      }
        for(int i=0;i<4;i++){
          ArrCompare.add(i);
      }
      if(ArrSpecial.size!=0){
        ArrCompare.clear();
        for(int i=0;i<4;i++){
          if(ArrSpecial.size==1){
            if(i!=ArrSpecial.get(0)){
              ArrCompare.add(i);
            }
          }else if(ArrSpecial.size==2){
            if(i!=ArrSpecial.get(0) && i!=ArrSpecial.get(1) ){
              ArrCompare.add(i);
            }
          }else if(ArrSpecial.size==3){
            if(i!=ArrSpecial.get(0) && i!=ArrSpecial.get(1)&& i!=ArrSpecial.get(2) ){
              ArrCompare.add(i);
            }
          }else if(ArrSpecial.size==4){
            if(i!=ArrSpecial.get(0) && i!=ArrSpecial.get(1)&& i!=ArrSpecial.get(2)&& i!=ArrSpecial.get(3) ){
              ArrCompare.add(i);
            }
          }
        }
        for (int i=0;i<ArrCompare.size;i++){
          System.out.println("==========chech: "+ArrCompare.get(i));
        }
      }

    }
    void setBinhLung(){
    ArrCompare.clear();
    flipAllCard(0);
    effectWin binhlung = new effectWin(27,gamePlay.positionCards.get(0).x,gamePlay.positionCards.get(0).y);
    group.addActor(binhlung);
      Label rlt;
      Label rltMonney;
      for (int i =1;i<4;i++){
        if(ArrSpecial.size!=0){
          if(ArrSpecial.size==1){
            if(ArrSpecial.get(0)!=i&& i!=0){
              ArrCompare.add(i);
            }
          }else if(ArrSpecial.size==2){
            if(ArrSpecial.get(0)!=i&&ArrSpecial.get(1)!=i&& i!=0){
              ArrCompare.add(i);
            }
          }
        }else {
          ArrCompare.add(i);
        }
      }
      rlt = new Label("thua "+9+" chi",new Label.LabelStyle(font1,null));
      rltMonney = new Label(""+FortmartPrice(-9*boardConfig.monneyStart)+" $",new Label.LabelStyle(font1,null));
      rlt.setPosition( gamePlay.positionCards.get(0).x,gamePlay.positionCards.get(0).y,Align.center);
      rltMonney.setPosition( gamePlay.positionCards.get(0).x,gamePlay.positionCards.get(0).y-60,Align.center);
      groupResult.addActor(rlt);
      groupResult.addActor(rltMonney);
      Label finalRlt = rlt;
      rlt.addAction(Actions.sequence(
              Actions.moveBy(0,-100,2f),
              GSimpleAction.simpleAction((d,a)->{
                finalRlt.remove();
                return true;
              })
      ));
      Label finalRltMonney = rltMonney;
      rltMonney.addAction(Actions.sequence(
              Actions.moveBy(0,-100,2f),
              GSimpleAction.simpleAction((d,a)->{
                finalRltMonney.remove();
                lifeGame(0,"game");
                return true;
              })
      ));
      ArrMonney.set(0,(long)(ArrMonney.get(0)+(-9)*(boardConfig.monneyStart)));
      LabelMonney.get(0).setText(FortmartPrice(ArrMonney.get(0)));
      resultBranch.set(0,(-9));

      for (int i=0;i<ArrCompare.size;i++){
          rlt = new Label("ăn "+3+" chi",new Label.LabelStyle(font,null));
          rltMonney = new Label(""+FortmartPrice(3*boardConfig.monneyStart)+" $",new Label.LabelStyle(font,null));
          rlt.setPosition( gamePlay.positionCards.get(ArrCompare.get(i)).x,gamePlay.positionCards.get(ArrCompare.get(i)).y,Align.center);
          rltMonney.setPosition( gamePlay.positionCards.get(ArrCompare.get(i)).x,gamePlay.positionCards.get(ArrCompare.get(i)).y-60,Align.center);
          groupResult.addActor(rlt);
          groupResult.addActor(rltMonney);
          Label finalRlt1 = rlt;
          rlt.addAction(Actions.sequence(
                  Actions.moveBy(0,-100,2f),
                  GSimpleAction.simpleAction((d,a)->{
                    finalRlt1.remove();
                    return true;
                  })
          ));
          Label finalRltMonney1 = rltMonney;
          rltMonney.addAction(Actions.sequence(
                  Actions.moveBy(0,-100,2f),
                  GSimpleAction.simpleAction((d,a)->{
                    finalRltMonney1.remove();
                    return true;
                  })
          ));
          ArrMonney.set(ArrCompare.get(i),(long)(ArrMonney.get(ArrCompare.get(i))+3*(boardConfig.monneyStart)));
          LabelMonney.get(ArrCompare.get(i)).setText(FortmartPrice(ArrMonney.get(ArrCompare.get(i))));
          resultBranch.set(ArrCompare.get(i),3);

        }
      }

    void flipAllCard(int index){
      for(int i=0;i<13;i++){
          CardPlay.get(index).get(i).setVisibleTiledown();
          CardPlay.get(index).get(i).flipAllCard(boardConfig.scaleBot, 0.3f);
          CardPlay.get(index).get(i).setColorCard();
      }
    }

    void CheckWin(String mode){

      System.out.println("size array check: "+arrayCheck.size);
      System.out.println("size array special: "+ArrSpecial.size);
      System.out.println("size array Compare: "+ArrCompare.size);
      int tg=0;
      Array<Integer> ArryTg;
        for (int i=0; i<arrayCheck.size;i++){
          for (int j=i+1;j<arrayCheck.size;j++){
            if(CheckCard.compare(arrayCheck.get(i),arrayCheck.get(j))==-1){
              tg = ArrCompare.get(i);
              ArrCompare.set(i,ArrCompare.get(j));
              ArrCompare.set(j,tg);
              ArryTg = arrayCheck.get(i);
              arrayCheck.set(i,arrayCheck.get(j));
              arrayCheck.set(j,ArryTg);
            }
          }
        }
          for (int i=0;i<ArrCompare.size;i++){
            System.out.println("checkWinner: "+ArrCompare.get(i));
          }
        resultWin(mode);
        System.out.println("===================");

    }

    void resultWin(String mode){
        Array<Integer> rerultTemp = new Array<>();
        for (int i=0;i<4;i++){
          rerultTemp.add(0);
        }
        int result =0;
        int rslSpcl =0;
        Label rlt;
        Label rltMonney;
          for (int i=0;i<ArrCompare.size;i++){
            for(int j=0;j<ArrCompare.size;j++){
              if(CheckCard.check(arrayCheck.get(i))>>13==7){
                if(branch==0){
                  rslSpcl =4;
                }else if(branch==1) {
                  rslSpcl =8;
                }
              }
              if(CheckCard.check(arrayCheck.get(i))>>13==9){
                if(branch==0){
                  rslSpcl =5;
                }else if(branch==1) {
                  rslSpcl =10;
                }
              }
              if(CheckCard.check(arrayCheck.get(i))>>13==6&&branch==1){
                  rslSpcl =2;
              }
            }
            if(i==0){result=(ArrCompare.size-1)+rslSpcl;}
            if(i==1){result=(ArrCompare.size-3)-rslSpcl;}
            if(i==2){result=(ArrCompare.size-(ArrCompare.size+1))-rslSpcl;}
            if(i==3){result=(1-ArrCompare.size)-rslSpcl;}
            if(result>=0){
              rlt = new Label("ăn "+result+" chi",new Label.LabelStyle(font,null));
                rltMonney = new Label("+" + FortmartPrice(result * boardConfig.monneyStart) + " $", new Label.LabelStyle(font, null));
//              if(mode=="game") {
//                ArrMonney.set(ArrCompare.get(i), (long) (ArrMonney.get(ArrCompare.get(i)) + result * (boardConfig.monneyStart)));
//                LabelMonney.get(ArrCompare.get(i)).setText(FortmartPrice(ArrMonney.get(ArrCompare.get(i))));
//              }
            }else {
              rlt = new Label("thua "+result*(-1)+" chi",new Label.LabelStyle(font1,null));
              rltMonney = new Label(""+FortmartPrice(result*boardConfig.monneyStart)+" $",new Label.LabelStyle(font1,null));
//              if(mode=="game") {
//                ArrMonney.set(ArrCompare.get(i), (long) (ArrMonney.get(ArrCompare.get(i)) + result * (boardConfig.monneyStart)));
//                LabelMonney.get(ArrCompare.get(i)).setText(FortmartPrice(ArrMonney.get(ArrCompare.get(i))));
//              }
            }
            rlt.setFontScale(0.7f);
            rltMonney.setFontScale(0.7f);
            rlt.setOrigin(Align.center);
            rltMonney.setOrigin(Align.center);
            rlt.setAlignment(Align.center);
            rltMonney.setAlignment(Align.center);
            rlt.setPosition( gamePlay.positionCards.get(ArrCompare.get(i)).x,gamePlay.positionCards.get(ArrCompare.get(i)).y,Align.center);
            rltMonney.setPosition( gamePlay.positionCards.get(ArrCompare.get(i)).x,gamePlay.positionCards.get(ArrCompare.get(i)).y-60,Align.center);
            groupResult.addActor(rlt);
            groupResult.addActor(rltMonney);
            Label finalRlt = rlt;
            rlt.addAction(Actions.sequence(
                    Actions.moveBy(0,-50,2f),
                    GSimpleAction.simpleAction((d,a)->{
                      finalRlt.remove();
                      return true;
                    })
            ));
            Label finalRltMonney = rltMonney;
            rltMonney.addAction(Actions.sequence(
                    Actions.moveBy(0,-50,2f),
                    GSimpleAction.simpleAction((d,a)->{
                      finalRltMonney.remove();
                      return true;
                    })
            ));
            /////// result branch/////
            if(mode=="game") {
              resultBranch.set(ArrCompare.get(i), resultBranch.get(ArrCompare.get(i)) + result);
            }
            rerultTemp.set(ArrCompare.get(i),result);
          }
          for (int i=0;i<rerultTemp.size;i++){
            System.out.println("check Result: "+rerultTemp.get(i));
          }
      setCheckLoseAll(rerultTemp);
    }
    void resultSpecial(int type){
      int result =0;
      Label rlt;
      Label rltMonney;
          if(type==15){result =-24;}
          if(type==14){result =-12;}
          if(type==13){result =-6;}
          if(type==12){result =-3;}
          if(type==11){result =-3;}
          if(type==10){result =-3;}

          for (int i=0;i<ArrCompare.size;i++){
            for(int j=0;j<ArrSpecial.size;j++){
              if(ArrCompare.get(i)!=ArrSpecial.get(j)){
                rlt = new Label("thua "+result*(-1)+" chi",new Label.LabelStyle(font1,null));
                rltMonney = new Label(""+FortmartPrice(result*boardConfig.monneyStart)+" $",new Label.LabelStyle(font1,null));
                rlt.setPosition( gamePlay.positionCards.get(ArrCompare.get(i)).x,gamePlay.positionCards.get(ArrCompare.get(i)).y,Align.center);
                rltMonney.setPosition( gamePlay.positionCards.get(ArrCompare.get(i)).x,gamePlay.positionCards.get(ArrCompare.get(i)).y-60,Align.center);
                groupResult.addActor(rlt);
                groupResult.addActor(rltMonney);
                Label finalRlt = rlt;
                rlt.addAction(Actions.sequence(
                        Actions.moveBy(0,-50,2f),
                        GSimpleAction.simpleAction((d,a)->{
                          finalRlt.remove();
                          return true;
                        })
                ));
                Label finalRltMonney = rltMonney;
                rltMonney.addAction(Actions.sequence(
                        Actions.moveBy(0,-50,2f),
                        GSimpleAction.simpleAction((d,a)->{
                          finalRltMonney.remove();
                          return true;
                        })
                ));
                ArrMonney.set(ArrCompare.get(i),(long)(ArrMonney.get(ArrCompare.get(i))+result*(boardConfig.monneyStart)));
                LabelMonney.get(ArrCompare.get(i)).setText(FortmartPrice(ArrMonney.get(ArrCompare.get(i))));
                resultBranch.set(ArrCompare.get(i),result);
              }
            }
          }
          for(int i=0;i<ArrSpecial.size;i++){
            result=result*(-1);
            rlt = new Label("ăn "+result*ArrCompare.size+" chi",new Label.LabelStyle(font,null));
            rltMonney = new Label(""+FortmartPrice(result*boardConfig.monneyStart)+" $",new Label.LabelStyle(font,null));
            rlt.setPosition( gamePlay.positionCards.get(ArrSpecial.get(i)).x,gamePlay.positionCards.get(ArrSpecial.get(i)).y,Align.center);
            rltMonney.setPosition( gamePlay.positionCards.get(ArrSpecial.get(i)).x,gamePlay.positionCards.get(ArrSpecial.get(i)).y-60,Align.center);
            groupResult.addActor(rlt);
            groupResult.addActor(rltMonney);
            Label finalRlt = rlt;
            rlt.addAction(Actions.sequence(
                    Actions.moveBy(0,-50,2f),
                    GSimpleAction.simpleAction((d,a)->{
                      finalRlt.remove();
                      return true;
                    })
            ));
            Label finalRltMonney1 = rltMonney;
            rltMonney.addAction(Actions.sequence(
                    Actions.moveBy(0,-50,2f),
                    GSimpleAction.simpleAction((d,a)->{
                      finalRltMonney1.remove();
                      return true;
                    })
            ));
            ArrMonney.set(ArrSpecial.get(i),(long)(ArrMonney.get(ArrSpecial.get(i))+result*ArrCompare.size*(boardConfig.monneyStart)));
            LabelMonney.get(ArrSpecial.get(i)).setText(FortmartPrice(ArrMonney.get(ArrSpecial.get(i))));
            resultBranch.set(ArrSpecial.get(i),result*ArrCompare.size);

          }
    }
    void setArrCheckLoseAll(){
      for (int i=0;i<4;i++){
        Array<Integer> array = new Array<>();
        CheckLoseAll.add(array);
      }
    }
    void setCheckLoseAll(Array<Integer> check ){
    for (int i=0;i<check.size;i++){
      CheckLoseAll.get(i).add(check.get(i));
    }
    for (int i=0;i<CheckLoseAll.size;i++){
      for (int j=0;j<CheckLoseAll.get(i).size;j++){
        System.out.println("lose all bot "+i+": "+CheckLoseAll.get(i).get(j));

      }
    }
    }
    void resultfinish(String mode){
    if(checkBinhLung==true){
      setColorAllcard(0);
    }
    Tweens.setTimeout(group,0.2f,()->{
      SoundEffect.Play(SoundEffect.Pay);
    });
    checkLossAllBranch(mode);
      Label rlt;
      Label rltMonney;
      for (int i=0;i<resultBranch.size;i++){
        if(resultBranch.get(i)>=0){
          rlt = new Label("ăn "+resultBranch.get(i)+" chi",new Label.LabelStyle(font,null));
          rltMonney = new Label("+"+FortmartPrice(resultBranch.get(i)*boardConfig.monneyStart)+" $",new Label.LabelStyle(font,null));
          if (mode == "game"){
            ArrMonney.set(i, (long) (ArrMonney.get(i) + resultBranch.get(i) * (boardConfig.monneyStart)));
            LabelMonney.get(i).setText(FortmartPrice(ArrMonney.get(i)));

          }
        }else {
            rlt = new Label("thua "+resultBranch.get(i)*(-1)+" chi",new Label.LabelStyle(font1,null));
            rltMonney = new Label(""+FortmartPrice(resultBranch.get(i)*boardConfig.monneyStart)+" $",new Label.LabelStyle(font1,null));
          if((resultBranch.get(i)*boardConfig.monneyStart)+ArrMonney.get(i)<0 && mode == "game"){
              ArrMonney.set(i,(long) 0);
              LabelMonney.get(i).setText(FortmartPrice(ArrMonney.get(i)));
          }else if((resultBranch.get(i)*boardConfig.monneyStart)+ArrMonney.get(i)>=0 && mode == "game"){
              ArrMonney.set(i, (long) (ArrMonney.get(i) + resultBranch.get(i) * (boardConfig.monneyStart)));
              LabelMonney.get(i).setText(FortmartPrice(ArrMonney.get(i)));
          }

        }
        rlt.setFontScale(0.7f);
        rltMonney.setFontScale(0.7f);
        rlt.setOrigin(Align.center);
        rltMonney.setOrigin(Align.center);
        rlt.setAlignment(Align.center);
        rltMonney.setAlignment(Align.center);
        rlt.setPosition( gamePlay.positionCards.get(i).x,gamePlay.positionCards.get(i).y,Align.center);
        rltMonney.setPosition( gamePlay.positionCards.get(i).x,gamePlay.positionCards.get(i).y-60,Align.center);
        groupResult.addActor(rlt);
        groupResult.addActor(rltMonney);
        Label finalRlt = rlt;
        rlt.addAction(Actions.sequence(
                Actions.moveBy(0,-50,2f),
                GSimpleAction.simpleAction((d,a)->{
                  finalRlt.remove();
                  return true;
                })
        ));
        Label finalRltMonney = rltMonney;
        rltMonney.addAction(Actions.sequence(
                Actions.moveBy(0,-50,2f),
                GSimpleAction.simpleAction((d,a)->{
                  finalRltMonney.remove();
                  return true;
                })
        ));
      }
      //////// setmonney//////
      boardConfig.Mymonney = ArrMonney.get(0);
      GMain.prefs.putLong("mymonney",ArrMonney.get(0));
      GMain.prefs.flush();

    }
    int coutWin(int index){
        int countlose=0,countWin=0,countSpecial=0;
        for(int j=0;j<CheckLoseAll.get(index).size;j++){
          if(CheckLoseAll.get(index).get(j)<0){
            countlose++;
          }else {
            countWin++;
            if(CheckLoseAll.get(index).get(j)==0){
              countSpecial++;
            }
          }
        }
        if(countlose==3)
          return -1;
        if(countWin==3&&countSpecial!=3)
          return 1;
      return 0;
    }

    void checkLossAllBranch(String mode){
    int count=0;
      Image label;
      effectWin effect;
      for(int i=0;i<CheckLoseAll.size;i++){
          if(coutWin(i)==-1){
            SoundEffect.Play(SoundEffect.bomB);
            count++;
            setColorAllcard(i);
            effect = new effectWin(28,gamePlay.positionCards.get(i).x,gamePlay.positionCards.get(i).y);
            groupParticle.addActor(effect);
            if(mode=="game"){
              resultBranch.set(i,resultBranch.get(i)*3);
            }
          }
          if(coutWin(i)==1&&count==3) {
            if(mode=="game"){
              resultBranch.set(i,resultBranch.get(i)*9);
            }
            setColorAllcard(i);
            effect = new effectWin(30,gamePlay.positionCards.get(i).x,gamePlay.positionCards.get(i).y);
            groupParticle.addActor(effect);
          }
          if(coutWin(i)==1 &&count!=0){
              if(mode=="game"&&count==2){
                resultBranch.set(i,resultBranch.get(i)*6);
              }
              if(mode=="game"&&count==1){
                resultBranch.set(i,resultBranch.get(i)*3);
              }
            setColorAllcard(i);
            effect = new effectWin(29,gamePlay.positionCards.get(i).x,gamePlay.positionCards.get(i).y);
            groupParticle.addActor(effect);
          }
      }
    }
    void replay(){
        CheckLoseAll.clear();
        setArrCheckLoseAll();
        Image replay = GUI.createImage(uiAtlas,"btnReplay");
        replay.setWidth(replay.getWidth()*0.7f);
        replay.setHeight(replay.getHeight()*0.7f);
        replay.setOrigin(Align.center);
        replay.setPosition(GStage.getWorldWidth()-(replay.getWidth()+100),replay.getHeight()-20,Align.center);
        replay.setOrigin(Align.center);
        group.addActor(replay);
        replay.addListener(new ClickListener(){
          @Override
          public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            showAds();
            replay.addAction(Actions.sequence(
                    Actions.scaleTo(0.8f,0.8f,0.1f),
                    Actions.scaleTo(1f,1f,0.1f),
                    GSimpleAction.simpleAction((d,a)->{
                      for (int i=0;i<4;i++){
                        setColorAllcard(i);
                      }
                        gamePlay.removebtnNewgame();
                      replay.remove();
                      replay.clear();
                      flipdownall();
                      lifeGame(0,"replay");
                      return true;
                    })
            ));
            return super.touchDown(event, x, y, pointer, button);
          }
        });
    }
    void setColorAllcard(int index){
      for(int i=0;i<13;i++){
        CardPlay.get(index).get(i).setColorCard();
      }
    }
    void flipdownall(){
      for(int i=0;i<ArrCompare.size;i++){
        for (int j=0;j<13;j++){
          CardPlay.get(ArrCompare.get(i)).get(j).flipAllCard(-boardConfig.scaleBot,0);
          CardPlay.get(ArrCompare.get(i)).get(j).tileDown.setVisible(true);
        }
      }
    }
    void actionChi(int index){
      Image chi = GUI.createImage(uiAtlas,"chi"+(index+1));
      chi.setPosition(chi.getWidth()/2+20,GStage.getWorldHeight()-chi.getHeight(),Align.center);
      chi.setOrigin(Align.center);
      chi.setScale(0.4f);
      chi.addAction(Actions.scaleTo(1,1,0.2f));
      groupParticle.addActor(chi);
    }

  private String FortmartPrice(Long Price) {

    DecimalFormat mDecimalFormat = new DecimalFormat("###,###,###,###");
    String mPrice = mDecimalFormat.format(Price);
    return mPrice;
  }
  void showAds(){
      GMain.platform.ShowFullscreen();
  }

}
