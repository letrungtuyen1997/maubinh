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
import com.ss.core.util.GAssetsManager;
import com.ss.core.util.GLayer;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;
import com.ss.scene.gamePlay;

import java.util.Comparator;

public class boardGame {
  gamePlay gamePlay;
  TextureAtlas cardAtlas, uiAtlas;
  BitmapFont font;
  Group group = new Group();
  Group groupParticle = new Group();
  Group groupTest = new Group();
  Array<Card> allCard = new Array<>();
  Array<Integer> cards;
  Array<Array<Card>> CardPlay = new Array<>();
  Array<Card> CardTest = new Array<>();

  public boardGame(TextureAtlas cardAtlas,TextureAtlas uiAtlas,gamePlay gamePlay,Group group, BitmapFont font){
    this.font = font;
    this.group = group;
    this.uiAtlas = uiAtlas;
    this.gamePlay = gamePlay;
    GStage.addToLayer(GLayer.ui,group);
    GStage.addToLayer(GLayer.top,groupParticle);
    GStage.addToLayer(GLayer.top,groupTest);
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
//        System.out.println("checkSanhRong: "+checkSanhRong(CardPlay.get(0)));
        checkSanhRong(CardPlay.get(0));
       // testSanhRong();

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
      return;
    }
    if(index==0) {
      for (int i = 0; i < 5; i++) {
        int finalI = i;
        Tweens.setTimeout(group, 0.1f * i, () -> {
          for(int j=0;j<4;j++){
            CardPlay.get(j).get(finalI).setVisibleTiledown();
            CardPlay.get(j).get(finalI).flipAllCard(boardConfig.scaleBot, 0.3f);
          }
        });
      }
    }else if(index==1){
      for (int i = 5; i < 10; i++) {
        int finalI = i;
        Tweens.setTimeout(group, 0.1f * i, () -> {
          for(int j=0;j<4;j++){
            CardPlay.get(j).get(finalI).setVisibleTiledown();
            CardPlay.get(j).get(finalI).flipAllCard(boardConfig.scaleBot, 0.3f);
          }

        });
      }
    }else {
      for (int i = 10; i < 13; i++) {
        int finalI = i;
        Tweens.setTimeout(group, 0.1f * i, () -> {
          for(int j=0;j<4;j++){
            CardPlay.get(j).get(finalI).setVisibleTiledown();
            CardPlay.get(j).get(finalI).flipAllCard(boardConfig.scaleBot, 0.3f);
          }

        });
      }
    }
    int finalIndex1 = index;
    Tweens.setTimeout(group,2f,()->{
      setLableResult(finalIndex1);
      setZindexCard(finalIndex1);
    });
    index++;
    int finalIndex = index;
    Tweens.setTimeout(group,3f,()->{
      lifeGame(finalIndex);
    });

  }
  void setZindexCard(int index){
    int zIndex = 10;

    for (int j=0;j<boardConfig.modePlay;j++){
      if(index==0){
        for (int i = 0; i<5;i++){
          CardPlay.get(j).get(i).card.setZIndex(zIndex);
          zIndex++;
          CardPlay.get(j).get(i).tileDown.setZIndex(zIndex);
          zIndex++;

          CardPlay.get(j).get(i).card.setColor(Color.DARK_GRAY);
        }
      }else if(index==1){
        for (int i = 5; i<10;i++){
          CardPlay.get(j).get(i).card.setZIndex(zIndex);
          zIndex++;
          CardPlay.get(j).get(i).tileDown.setZIndex(zIndex);
          zIndex++;
          CardPlay.get(j).get(i).card.setColor(Color.DARK_GRAY);
        }
      }else {
        for (int i=10;i<13;i++){
          CardPlay.get(j).get(i).card.setZIndex(zIndex);
          zIndex++;
          CardPlay.get(j).get(i).tileDown.setZIndex(zIndex);
          zIndex++;
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
  void setLableResult(int index){
    if(index==0){
      for(int i=0;i<boardConfig.modePlay;i++){
        int type = CheckCard.check(BinhTop(i))>>13;
        Image frmResult = GUI.createImage(uiAtlas,"frameResult");
        frmResult.setWidth(frmResult.getWidth()*0.5f);
        frmResult.setHeight(frmResult.getHeight()*0.8f);
        frmResult.setOrigin(Align.center);
        frmResult.setPosition(gamePlay.positionCards.get(i).x,gamePlay.positionCards.get(i).y,Align.center);
        groupParticle.addActor(frmResult);
        effectWin win = new effectWin(type,gamePlay.positionCards.get(i).x,gamePlay.positionCards.get(i).y);
        groupParticle.addActor(win);
      }
    }else if(index==1){
      for(int i=0;i<boardConfig.modePlay;i++){
        int type = CheckCard.check(BinhMid(i))>>13;
        Image frmResult = GUI.createImage(uiAtlas,"frameResult");
        frmResult.setWidth(frmResult.getWidth()*0.5f);
        frmResult.setHeight(frmResult.getHeight()*0.8f);
        frmResult.setOrigin(Align.center);
        frmResult.setPosition(gamePlay.positionCards.get(i).x,gamePlay.positionCards.get(i).y-80,Align.center);
        groupParticle.addActor(frmResult);
        effectWin win = new effectWin(type,gamePlay.positionCards.get(i).x,gamePlay.positionCards.get(i).y-80);
        groupParticle.addActor(win);
      }
    }else if(index==2){
      for(int i=0;i<boardConfig.modePlay;i++){
        int type = CheckCard.check(BinhLow(i))>>13;
        Image frmResult = GUI.createImage(uiAtlas,"frameResult");
        frmResult.setWidth(frmResult.getWidth()*0.5f);
        frmResult.setHeight(frmResult.getHeight()*0.8f);
        frmResult.setOrigin(Align.center);
        frmResult.setPosition(gamePlay.positionCards.get(i).x,gamePlay.positionCards.get(i).y-160,Align.center);
        groupParticle.addActor(frmResult);
        effectWin win = new effectWin(type,gamePlay.positionCards.get(i).x,gamePlay.positionCards.get(i).y-160);
        groupParticle.addActor(win);
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

  int checkRongCuon(Array<Card> ArrCard ){
    int temp=ArrCard.get(0).Key;
    int temp2=0;
    String resultSanh = "1111111111111";
    int resultClr = 0;
    for (int i=1;i<ArrCard.size;i++){
      temp= temp | ArrCard.get(i).Key;
    }
    temp2 = temp & 8191;
    temp=temp>>13;
    resultClr=Integer.parseInt(Integer.toBinaryString(temp),2);
    if(resultSanh.equals(Integer.toBinaryString(temp2))==true&&resultClr==8||resultClr==1||resultClr==2||resultClr==4){
      return 1;
    }
    return 0;
  }
  int checkSanhRong(Array<Card> ArrCard ){
    int temp=ArrCard.get(0).Key;
    int temp2=0;
    String resultSanh = "1111111111111";
    int resultClr = 0;
    for (int i=1;i<ArrCard.size;i++){
      temp= temp | ArrCard.get(i).Key;
    }
    temp2 = temp & 8191;
    temp=temp>>13;
    resultClr=Integer.parseInt(Integer.toBinaryString(temp),2);
    if(resultSanh.equals(Integer.toBinaryString(temp2))==true&&resultClr!=8&&resultClr!=1&&resultClr!=2&&resultClr!=4){
      return 2;
    }
    return 0;
  }

  int checkLucPheBon(Array<Card> ArrCard){
    int temp=ArrCard.get(0).Key;
    int temp2=0;
    for (int i=1;i<ArrCard.size;i++){
      temp= temp | ArrCard.get(i).Key;
    }
    System.out.println("temp1: "+Integer.toBinaryString(temp));
    temp2 = temp & 8191;
    System.out.println("temp2: "+Integer.bitCount(temp2));
    if(Integer.bitCount(temp2)==6){
      return 3;
    }
    return 0;

  }
  ///////////
  //////////////
  //Todo: card test//////////////////
  void testSanhRong(){
    int type[] = {1,2,3,4,5,6,7,8,9,17,18,21,22};
    for (int i=0;i<type.length;i++)
    for (int j=0;j<allCard.size;j++){
      if(allCard.get(j).getValue()==type[i]){
        Card card = new Card(cardAtlas,groupTest,type[i]);
          card.setKey(allCard.get(j).Key);
          CardTest.add(card);
      }

    }
//    for (int i=0; i<13; i++){
//      int type= ((i*4)+(int)(Math.random()*4)+1);
//      System.out.println("okok"+type);
//      for (int j=0;j<allCard.size;j++){
//        if(allCard.get(j).getValue()==type){
//          Card card = new Card(cardAtlas,groupTest,type);
//          card.setKey(allCard.get(j).Key);
//          CardTest.add(card);
//        }
//      }
//    }
    BinhBotTest(CardTest);
    setPositonCardTest(CardTest);

        System.out.println("======binh top======");
    for(int i = 0; i < 5; i++) {
      System.out.print(" " + CheckCard.nameMap.get(CardTest.get(i).Key));
    }
    System.out.println();
    System.out.println("======binh Mid========");
    for(int i = 5; i < 10; i++) {
      System.out.print(" " + CheckCard.nameMap.get(CardTest.get(i).Key));
    }
    System.out.println();
    System.out.println("======binh Mid=========");
    for(int i = 10; i < 13; i++) {
      System.out.print(" " + CheckCard.nameMap.get(CardTest.get(i).Key));
    }
    System.out.println();
//    System.out.println("check sanh rong: "+ checkSanhRong(CardTest));
//    System.out.println("check sanh rong cuon: "+ checkRongCuon(CardPlay.get(1)));
    System.out.println("check LucPheBon: "+ checkLucPheBon(CardTest));
  }
  void setPositonCardTest(Array<Card> CardTest){

    float paddingCardX = allCard.get(0).card.getWidth()/2;
    float paddingCardY = allCard.get(0).card.getHeight()/2;
    int zIndex = 100;

    for (int i=10;i<13;i++){
      CardTest.get(i).moveCardEnd(GStage.getWorldWidth()/2-(paddingCardX*4)/2+paddingCardX*(i-10),GStage.getWorldHeight()/2-(paddingCardY*2),0,0.5f);
      //CardPlay.get(index).get(i).setZindexCard(i-10);
      CardTest.get(i).card.setZIndex(zIndex);
      zIndex++;
      CardTest.get(i).tileDown.setZIndex(zIndex);
      zIndex++;
      CardTest.get(i).setVisibleTiledown();
    }

    for (int i = 5; i<10;i++){
      CardTest.get(i).moveCardEnd(GStage.getWorldWidth()/2-(paddingCardX*4)/2+paddingCardX*(i-5),GStage.getWorldHeight()/2-paddingCardY,0,0.5f);
      //CardPlay.get(index).get(i).setZindexCard(i);
      CardTest.get(i).card.setZIndex(zIndex);
      zIndex++;
      CardTest.get(i).tileDown.setZIndex(zIndex);
      zIndex++;
      CardTest.get(i).setVisibleTiledown();



    }
    for (int i = 0; i<5;i++){
      CardTest.get(i).moveCardEnd(GStage.getWorldWidth()/2-(paddingCardX*4)/2+paddingCardX*i,GStage.getWorldHeight()/2,0,0.5f);
      //CardPlay.get(index).get(i).setZindexCard(i+10);
      CardTest.get(i).card.setZIndex(zIndex);
      zIndex++;
      CardTest.get(i).tileDown.setZIndex(zIndex);
      zIndex++;
      CardTest.get(i).setVisibleTiledown();
    }


  }
  void BinhBotTest(Array<Card> CardTest){
    Array<Integer> Cardbot = new Array<>();
    for(int i=0;i<CardTest.size;i++){
      Cardbot.add(CardTest.get(i).Key);
    }

    Array<Array<Integer>> BotBinhFinish =  CheckCard.move(Cardbot);
//    for (Integer i : BotBinhFinish.get(2))
//      System.out.print(CheckCard.nameMap.get(i) + " ");
//    System.out.println();
    SwapCardBotTest(BotBinhFinish,CardTest);
  }
  void SwapCardBotTest(Array<Array<Integer>> BotBinhFinish, Array<Card> CardTest){
    /////// binh top//////
    for (int j=0;j<5;j++){
      for (int i=0;i<CardTest.size;i++){
        if(CardTest.get(i).Key==BotBinhFinish.get(2).get(j)){
          CardTest.swap(i,j);
        }
      }
    }
    /////// binh mid//////
    for (int j=0;j<5;j++){
      for (int i=0;i<CardTest.size;i++){
        if(CardTest.get(i).Key==BotBinhFinish.get(1).get(j)){
          CardTest.swap(i,5+j);
        }
      }
    }
    /////// binh low//////
    for (int j=0;j<3;j++){
      for (int i=0;i<CardTest.size;i++){
        if(CardTest.get(i).Key==BotBinhFinish.get(0).get(j)){
          CardTest.swap(i,10+j);

        }

      }
    }
  }


}
