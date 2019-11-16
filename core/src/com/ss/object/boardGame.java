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
import com.effect.effectWin;
import com.ss.GMain;
import com.ss.commons.Tweens;
import com.ss.core.action.exAction.GSimpleAction;
import com.ss.core.util.GAssetsManager;
import com.ss.core.util.GLayer;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;
import com.ss.scene.gamePlay;

import java.text.DecimalFormat;
import java.util.Comparator;

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
    showbtnXepBai();
//    new GameOver(resultBranch);

  }
  void innitPlayer(){
    for(int i=0;i<boardConfig.modePlay;i++){
      Array<Card> card = new Array<>();
      CardPlay.add(card);
      arrayWinner.add(i);
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
      btnXepbai.setTouchable(Touchable.disabled);
      btnXepbai.addAction(Actions.sequence(
        Actions.scaleTo(0.8f,0.8f,0.1f),
        Actions.scaleTo(1f,1f,0.1f)
      ));
      Tweens.setTimeout(group,0.2f,()->{
        btnXepbai.setTouchable(Touchable.enabled);
        new BinhPlayer(cardAtlas,uiAtlas,CardPlay.get(0),()->{
          setflipAllCard();
          lifeGame(0);
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
  void lifeGame(int index){
    if(index>=3){
//      groupParticle.clear();
      for (int i=0;i<allCard.size;i++){
        allCard.get(i).card.setColor(Color.WHITE);
      }
      for(int i=0;i<resultBranch.size;i++){
        System.out.println("resultBranch bot "+i+": "+resultBranch.get(i));
      }
      new GameOver(resultBranch);
      return;
    }
//    groupParticle.clear();
    if(index==0) {
      for (int i = 0; i < 5; i++) {
        int finalI = i;
        Tweens.setTimeout(group, 0.1f * i, () -> {
          for(int j=0;j<ArrCompare.size;j++){
              CardPlay.get(ArrCompare.get(j)).get(finalI).setVisibleTiledown();
              CardPlay.get(ArrCompare.get(j)).get(finalI).flipAllCard(boardConfig.scaleBot, 0.3f);

          }
        });
      }
    }else if(index==1){
      for (int i = 5; i < 10; i++) {
        int finalI = i;
        Tweens.setTimeout(group, 0.1f * i, () -> {
          for(int j=0;j<ArrCompare.size;j++){
              CardPlay.get(ArrCompare.get(j)).get(finalI).setVisibleTiledown();
              CardPlay.get(ArrCompare.get(j)).get(finalI).flipAllCard(boardConfig.scaleBot, 0.3f);
          }

        });
      }
    }else {
      for (int i = 10; i < 13; i++) {
        int finalI = i;
        Tweens.setTimeout(group, 0.1f * i, () -> {
          for(int j=0;j<ArrCompare.size;j++){
              CardPlay.get(ArrCompare.get(j)).get(finalI).setVisibleTiledown();
              CardPlay.get(ArrCompare.get(j)).get(finalI).flipAllCard(boardConfig.scaleBot, 0.3f);
          }

        });
      }
    }
    int finalIndex1 = index;
    Tweens.setTimeout(group,2f,()->{
      setLableResult(finalIndex1);
      setZindexCard(finalIndex1);
//      for (int i =0; i<arrayCheck.size;i++){
//        System.out.println("ArrayCheck: "+arrayCheck.get(i));
//      }
      Tweens.setTimeout(group,2f,()->{
        CheckWin();

      });
    });
    index++;
    int finalIndex = index;
    Tweens.setTimeout(group,6f,()->{
      lifeGame(finalIndex);
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
          new PaticleSuper(arrSupper.get(i));
          effectWin effect = new effectWin(arrSupper.get(i)+8,gamePlay.positionCards.get(i).x,gamePlay.positionCards.get(i).y-90);
          groupParticle.addActor(effect);
          ArrSpecial.add(i);
        }
      }
      for(int i=0;i<4;i++){
        if(ArrSpecial.size!=0){
          for(int j =0;j<ArrSpecial.size;j++){
            if(ArrSpecial.get(j)!=i){
              ArrCompare.add(i);
            }
          }
        }else {
          ArrCompare.add(i);
        }

      }

    }
    void flipAllCard(int index){
      for(int i=0;i<13;i++){
          CardPlay.get(index).get(i).setVisibleTiledown();
          CardPlay.get(index).get(i).flipAllCard(boardConfig.scaleBot, 0.3f);
          CardPlay.get(index).get(i).setColorCard();
      }
    }

    void setArrWinner(){
      arrayWinner.clear();
        for (int i=0;i<4;i++){
          arrayWinner.add(i);
        }
    }

    void CheckWin(){

      System.out.println("size array check: "+arrayCheck.size);
      System.out.println("size array special: "+ArrSpecial.size);
      System.out.println("size array Compare: "+ArrCompare.size);
      int tg=0;
      Array<Integer> ArryTg;
        for (int i=0; i<arrayCheck.size;i++){
          for (int j=i+1;j<arrayCheck.size;j++){
            if(CheckCard.compare(arrayCheck.get(i),arrayCheck.get(j))==-1){
              tg =arrayWinner.get(i);
              arrayWinner.set(i,arrayWinner.get(j));
              arrayWinner.set(j,tg);
              ArryTg = arrayCheck.get(i);
              arrayCheck.set(i,arrayCheck.get(j));
              arrayCheck.set(j,ArryTg);
            }
          }
        }
        for(int i=0;i<arrayWinner.size;i++) {
          if(ArrSpecial.size!=0) {

            for (int j = 0; j < ArrSpecial.size; j++) {
              if (arrayWinner.get(i) == ArrSpecial.get(j)) {
                  arrayWinner.removeIndex(i);
              }else {
                System.out.println("checkWinner: " + arrayWinner.get(i));

              }
            }
          }else {
            System.out.println("checkWinner: "+arrayWinner.get(i));
          }

        }
        resultWin();
        System.out.println("===================");
        setArrWinner();

    }
    void resultWin(){
        int result =0;
        Label rlt;
        Label rltMonney;
          for (int i=0;i<ArrCompare.size;i++){
            if(i==0){result=ArrCompare.size-1;}
            if(i==1){result=ArrCompare.size-3;}
            if(i==2){result=ArrCompare.size-(ArrCompare.size+1);}
            if(i==3){result=1-ArrCompare.size;}
            if(result>=0){
              rlt = new Label("ăn "+result+" chi",new Label.LabelStyle(font,null));
              rltMonney = new Label("+"+FortmartPrice(result*boardConfig.monneyStart)+" $",new Label.LabelStyle(font,null));
              ArrMonney.set(arrayWinner.get(i),(long)(ArrMonney.get(arrayWinner.get(i))+result*(boardConfig.monneyStart)));
              LabelMonney.get(arrayWinner.get(i)).setText(FortmartPrice(ArrMonney.get(arrayWinner.get(i))));
            }else {
              rlt = new Label("thua "+result*(-1)+" chi",new Label.LabelStyle(font1,null));
              rltMonney = new Label(""+FortmartPrice(result*boardConfig.monneyStart)+" $",new Label.LabelStyle(font1,null));
              ArrMonney.set(arrayWinner.get(i),(long)(ArrMonney.get(arrayWinner.get(i))+result*(boardConfig.monneyStart)));
              LabelMonney.get(arrayWinner.get(i)).setText(FortmartPrice(ArrMonney.get(arrayWinner.get(i))));
            }
            rlt.setFontScale(0.7f);
            rltMonney.setFontScale(0.7f);
            rlt.setOrigin(Align.center);
            rltMonney.setOrigin(Align.center);
            rlt.setAlignment(Align.center);
            rltMonney.setAlignment(Align.center);
            rlt.setPosition( gamePlay.positionCards.get(arrayWinner.get(i)).x,gamePlay.positionCards.get(arrayWinner.get(i)).y,Align.center);
            rltMonney.setPosition( gamePlay.positionCards.get(arrayWinner.get(i)).x,gamePlay.positionCards.get(arrayWinner.get(i)).y-60,Align.center);
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
                      return true;
                    })
            ));
            /////// result branch/////
            resultBranch.set(arrayWinner.get(i),resultBranch.get(arrayWinner.get(i))+result);
          }
    }
    void resultSpecial(int type){
//      for (int i=0;i<ArrMonney.size;i++){
//        System.out.println("monneybot before inc  "+i+": "+ArrMonney.get(i));
//      }
      int result =0;
      Label rlt;
      Label rltMonney;
          if(type==15){result =-24;}
          if(type==14){result =-12;}
          if(type==13){result =-6;}
          if(type==12){result =-3;}
          if(type==11){result =-3;}
          if(type==10){result =-3;}

          for (int i=0;i<arrayWinner.size;i++){
            for(int j=0;j<ArrSpecial.size;j++){
              if(arrayWinner.get(i)!=ArrSpecial.get(j)){
                rlt = new Label("thua "+result*(-1)+" chi",new Label.LabelStyle(font1,null));
                rltMonney = new Label(""+FortmartPrice(result*boardConfig.monneyStart)+" $",new Label.LabelStyle(font1,null));
                rlt.setPosition( gamePlay.positionCards.get(arrayWinner.get(i)).x,gamePlay.positionCards.get(arrayWinner.get(i)).y,Align.center);
                rltMonney.setPosition( gamePlay.positionCards.get(arrayWinner.get(i)).x,gamePlay.positionCards.get(arrayWinner.get(i)).y-60,Align.center);
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
                          return true;
                        })
                ));
                ArrMonney.set(arrayWinner.get(i),(long)(ArrMonney.get(arrayWinner.get(i))+result*(boardConfig.monneyStart)));
                LabelMonney.get(arrayWinner.get(i)).setText(FortmartPrice(ArrMonney.get(arrayWinner.get(i))));
                resultBranch.set(arrayWinner.get(i),result);
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
                    Actions.moveBy(0,-100,2f),
                    GSimpleAction.simpleAction((d,a)->{
                      finalRlt.remove();
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
            ArrMonney.set(ArrSpecial.get(i),(long)(ArrMonney.get(ArrSpecial.get(i))+result*ArrCompare.size*(boardConfig.monneyStart)));
            LabelMonney.get(ArrSpecial.get(i)).setText(FortmartPrice(ArrMonney.get(ArrSpecial.get(i))));
            resultBranch.set(ArrSpecial.get(i),result*ArrCompare.size);

          }
//      for (int i=0;i<ArrMonney.size;i++){
//        System.out.println("monneybot after inc"+i+": "+ArrMonney.get(i));
//      }

    }

  private String FortmartPrice(Long Price) {

    DecimalFormat mDecimalFormat = new DecimalFormat("###,###,###,###");
    String mPrice = mDecimalFormat.format(Price);

    return mPrice;
  }



}
