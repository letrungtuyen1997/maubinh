package com.ss.object;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
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
import com.ss.core.action.exAction.GSimpleAction;
import com.ss.core.exSprite.GShapeSprite;
import com.ss.core.util.GAssetsManager;
import com.ss.core.util.GLayer;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;

public class BinhPlayer {
  private TextureAtlas cardAtlas, uiAtlas;
  private Group groupOverLay, groupLabel;
  private Array<Card> cardsPlayerSrc;
  private Array<Card> cardsPlayerDes;
  private float maxRange;
  private BitmapFont font;
  private Array<Array<Integer>> arrayBinh = new Array<>();
  private Image swapBtn;

  public BinhPlayer(TextureAtlas cardAtlas,TextureAtlas uiAtlas, Array<Card> cardsPlayer,Runnable runnable){
    this.uiAtlas = uiAtlas;
    this.cardAtlas = cardAtlas;
    groupOverLay = new Group();
    groupLabel = new Group();
    GStage.addToLayer(GLayer.top, groupOverLay);
    GStage.addToLayer(GLayer.top, groupLabel);
    this.cardsPlayerSrc = cardsPlayer;

    float rX = (float)Math.pow(cardsPlayer.get(0).card.getWidth(),2);
    float rY = (float)Math.pow(cardsPlayer.get(0).card.getHeight(),2);
    maxRange = (float)Math.sqrt(rX + rY);
    initFont();
    darkScreen();
    cloneCards();
    checkBinh();
    initSwapBtn();
    showBtnDone(runnable);
  }

  private void initSwapBtn(){
    swapBtn = GUI.createImage(uiAtlas, "swap");
    swapBtn.setAlign(Align.center);
    swapBtn.setOrigin(Align.center);
    swapBtn.setPosition(1280*2/3 + 120, 720*2/3 - 70);
    groupOverLay.addActor(swapBtn);

    swapBtn.addListener(new ClickListener(){
      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        swapBtn.setTouchable(Touchable.disabled);
        swapcds();
        Tweens.setTimeout(groupOverLay, 0.3f, ()->{
          checkBinh();
          swapBtn.setTouchable(Touchable.enabled);
        });
        return super.touchDown(event, x, y, pointer, button);
      }
    });
  }

  private void swapcds(){
    for(int i = 0; i < 5; i++) {
      swapCard(cardsPlayerDes, cardsPlayerDes.get(i), cardsPlayerDes.get(i+5), new Vector2(cardsPlayerDes.get(i).card.getX(), cardsPlayerDes.get(i).card.getY()));
      swapCard(cardsPlayerSrc, cardsPlayerSrc.get(i), cardsPlayerSrc.get(i+5), new Vector2(cardsPlayerSrc.get(i).card.getX(), cardsPlayerSrc.get(i).card.getY()));
    }
  }

  private void cloneCards(){
    cardsPlayerDes = new Array<>();
    for(Card card : cardsPlayerSrc){
      Card cardTemp = new Card(cardAtlas, groupOverLay, card.getValue());
      groupOverLay.addActor(cardTemp.card);
      cardsPlayerDes.add(cardTemp);
      cardTemp.addDrag(this);

    }
    setPositionCards();
  }

  private void setPositionCards(){
    float paddingCardX = cardsPlayerDes.get(0).card.getWidth();
    float paddingCardY = cardsPlayerDes.get(0).card.getHeight() + 10;
    float paddingX = GStage.getWorldWidth()/2 - (2f*cardsPlayerDes.get(0).card.getWidth() + 20);
    float paddingY = GStage.getWorldHeight()/2 + (1.5f*cardsPlayerDes.get(0).card.getHeight() + 10);
    for (int i = 0; i<5;i++){
      cardsPlayerDes.get(i).setPosition(paddingX + (10 + paddingCardX)*i, paddingY - 0.5f*paddingCardY);
      cardsPlayerDes.get(i).setVisibleTiledown();
    }
    for (int i = 5; i<10;i++){
      cardsPlayerDes.get(i).setPosition(paddingX + (10 + paddingCardX)*(i-5), paddingY - 1.5f*paddingCardY);
      cardsPlayerDes.get(i).setVisibleTiledown();

    }
    for (int i=10;i<13;i++){
      cardsPlayerDes.get(i).setPosition(paddingX + (10 + paddingCardX)*(i-10), paddingY - 2.5f*paddingCardY);
      cardsPlayerDes.get(i).setVisibleTiledown();

    }
  }

  private void darkScreen(){
    final GShapeSprite blackOverlay = new GShapeSprite();
    blackOverlay.createRectangle(true, -GStage.getWorldWidth()/2,-GStage.getWorldHeight()/2, GStage.getWorldWidth()*2, GStage.getWorldHeight()*2);
    blackOverlay.setColor(0,0,0,1f);
    groupOverLay.addActor(blackOverlay);
  }

  public int checkPosition(Card cardC, Vector2 p1){
    Array<Integer> indexs = new Array<>();
    int i0;
    int indexResult = -1;
    for(i0 = 0; i0 < 13; i0++){
      if(cardC != cardsPlayerDes.get(i0)){
        if(checkRect(cardC, cardsPlayerDes.get(i0))){
          indexs.add(i0);
        }
      }
    }
    if(indexs.size == 0) {
      return indexResult;
    }
    else {
      float min = getSize2Cards(cardC, cardsPlayerDes.get(indexs.get(0)));
      int indexMin = indexs.get(0);
      for(int i = 1; i < indexs.size; i++){
        float temp = getSize2Cards(cardC, cardsPlayerDes.get(indexs.get(i)));
        if(temp < min){
          min = temp;
          indexMin = indexs.get(i);
        }
      }
      indexResult = indexMin;

      //swap card
      int index1 = cardsPlayerDes.indexOf(cardC, true);
      Vector2 p = new Vector2(cardsPlayerSrc.get(index1).card.getX(),cardsPlayerSrc.get(index1).card.getY());
      swapCard(cardsPlayerSrc, cardsPlayerSrc.get(index1), cardsPlayerSrc.get(indexResult), p);
      swapCard(cardsPlayerDes, cardC, cardsPlayerDes.get(indexResult), p1);
      checkBinh();

      return indexResult;
    }
  }

  private float getSize2Cards(Card card1, Card card2){
    float range;
    Vector2 vt1 = new Vector2(card1.card.getX(), card1.card.getY());
    Vector2 vt2 = new Vector2(card2.card.getX(), card2.card.getY());
    range = getSize2Point(vt1, vt2);
    return range;
  }

  private float getSize2Point(Vector2 vt1, Vector2 vt2){
    float range;
    float dx = (float) Math.pow(vt1.x - vt2.x, 2);
    float dy = (float) Math.pow(vt1.y - vt2.y, 2);
    range = (float) Math.sqrt(dx + dy);
    return range;
  }

  private boolean checkRect(Card card1, Card card2){
    float w = card1.card.getWidth();
    float h = card1.card.getHeight();
    float dx1 = Math.abs(card1.card.getX() - w/2 - card2.card.getX());
    float dy1 = Math.abs(card1.card.getY() - h/2 - card2.card.getY());
    float dx2 = Math.abs(card1.card.getX() + w/2 - card2.card.getX());
    float dy2 = Math.abs(card1.card.getY() - h/2 - card2.card.getY());
    float dx3 = Math.abs(card1.card.getX() - w/2 - card2.card.getX());
    float dy3 = Math.abs(card1.card.getY() + h/2 - card2.card.getY());
    float dx4 = Math.abs(card1.card.getX() + w/2 - card2.card.getX());
    float dy4 = Math.abs(card1.card.getY() + h/2 - card2.card.getY());

    return (dx1 < w/2 && dy1 < h/2) || (dx2 < w/2 && dy2 < h/2)
            || (dx3 < w/2 && dy3 < h/2) || (dx4 < w/2 && dy4 < h/2);
  }

  private void swapCard(Array<Card> cards, Card card1, Card card2, Vector2 p1){

    int index1 = cards.indexOf(card1, true);
    int index2 = cards.indexOf(card2, true);

    int zIndexTemp = card1.card.getZIndex();
    int zIndexTemp1 = card1.tileDown.getZIndex();

    cards.swap(index1, index2);
    setTouchCards(Touchable.disabled);

    card1.tileDown.setPosition(card2.tileDown.getX(), card2.tileDown.getY());
    card2.tileDown.setPosition(p1.x, p1.y);
    card1.tileDown.setZIndex(card2.tileDown.getZIndex());
    card2.tileDown.setZIndex(zIndexTemp1);

    card1.card.addAction(Actions.sequence(
      Actions.moveTo(card2.card.getX(), card2.card.getY(), 0.25f, Interpolation.fastSlow)
    ));

    card2.card.addAction(Actions.sequence(
      Actions.moveTo(p1.x, p1.y, 0.25f, Interpolation.fastSlow),
      GSimpleAction.simpleAction((d, a)->{
        setTouchCards(Touchable.enabled);
        return true;
      })
    ));

    card1.card.setZIndex(card2.card.getZIndex());
    card2.card.setZIndex(zIndexTemp);
  }

  public void setTouchCards(Touchable touchable){
    for(Card card : cardsPlayerDes){
      card.card.setTouchable(touchable);
    }
  }
  void checkBinh(){
    groupLabel.clear();
    arrayBinh.clear();
//    System.out.println("======binh top======");
//    for(int i = 0; i < 5; i++) {
//      System.out.print(" " + CheckCard.nameMap.get(BinhTop().get(i)));
//    }
//    System.out.println();
//    System.out.println("======binh Mid========");
//    for(int i = 0; i < 5; i++) {
//      System.out.print(" " + CheckCard.nameMap.get(BinhMid().get(i)));
//    }
//    System.out.println();
//    System.out.println("======binh Mid=========");
//    for(int i = 0; i < 3; i++) {
//      System.out.print(" " + CheckCard.nameMap.get(BinhLow().get(i)));
//    }
//    System.out.println();



    ///////////// check binh lung///////
    arrayBinh.add(BinhLow());
    arrayBinh.add(BinhMid());
    arrayBinh.add(BinhTop());
    if(CheckCard.validate(arrayBinh)) {
//    System.out.println("array binh: "+CheckCard.validate(arrayBinh));
      //// binh top
      int typetop =CheckCard.check(BinhTop())>>13;
      int typeMid =CheckCard.check(BinhMid())>>13;
      int typeLow =CheckCard.check(BinhLow())>>13;
      System.out.println("binh top: "+typetop);
      System.out.println("binh Mid: "+typeMid);
      System.out.println("binh Low: "+typeLow);

      Label binhTop = new Label(CheckType(typetop),new Label.LabelStyle(font, Color.GREEN));
      binhTop.setFontScale(0.8f);
      binhTop.setPosition(50,500);
      binhTop.setAlignment(Align.center);
      groupLabel.addActor(binhTop);
      Label binhMid= new Label(CheckType(typeMid),new Label.LabelStyle(font,Color.GREEN));
      binhMid.setFontScale(0.8f);
      binhMid.setPosition(50,320);
      binhMid.setAlignment(Align.center);
      groupLabel.addActor(binhMid);
      Label binhLow = new Label(CheckType(typeLow),new Label.LabelStyle(font,Color.GREEN));
      binhLow.setFontScale(0.8f);
      binhLow.setPosition(50,150);
      binhLow.setAlignment(Align.center);
      groupLabel.addActor(binhLow);
    }else {
      Label binhMid= new Label("Binh Lủng",new Label.LabelStyle(font,Color.RED));
      binhMid.setFontScale(0.8f);
      binhMid.setPosition(50,320);
      binhMid.setAlignment(Align.center);
      groupLabel.addActor(binhMid);
    }




  }
  String CheckType(int type){
    String name= "";
    if(type==0) {
      name = "mậu thầu";
    }else if(type==1){
      name = "đôi";
    }else if(type==2){
      name = "thú";

    }else if(type==3){
      name = "xám";

    }else if(type==4){
      name = "sảnh";

    }else if(type==5){
      name = "thùng";

    }else if(type==6){
      name = "cù lũ";

    }else if(type==7){
      name = "tứ quý";

    }else if(type==8){
      name = "lỗi";

    }else if(type==9){
      name = "thùng phá sảnh";

    }
    return name;
  }

  Array<Integer> BinhTop(){
    Array<Integer> CardBinh= new Array<>();
    for (int i=0;i<5;i++){
      CardBinh.add(cardsPlayerSrc.get(i).Key);
    }
    return CardBinh;
  }
  Array<Integer> BinhMid(){
    Array<Integer> CardBinh= new Array<>();
    for (int i=5;i<10;i++){
      CardBinh.add(cardsPlayerSrc.get(i).Key);
    }
    return CardBinh;
  }
  Array<Integer> BinhLow(){
    Array<Integer> CardBinh= new Array<>();
    for (int i=10;i<13;i++){
      CardBinh.add(cardsPlayerSrc.get(i).Key);
    }
    return CardBinh;
  }


  void showBtnDone(Runnable runnable){
    Image btnXong = GUI.createImage(uiAtlas,"btnXong");
    btnXong.setOrigin(Align.center);
    btnXong.setPosition(GStage.getWorldWidth()-100,GStage.getWorldHeight()-100,Align.center);
    groupOverLay.addActor(btnXong);
    btnXong.addListener(new ClickListener(){
      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
      btnXong.setTouchable(Touchable.disabled);
        btnXong.addAction(Actions.sequence(
          Actions.scaleTo(0.8f,0.8f,0.1f),
          Actions.scaleTo(1f,1f,0.1f),
          GSimpleAction.simpleAction((d,a)->{
            groupLabel.clear();
            groupOverLay.clear();
            groupLabel.addAction(Actions.run(runnable));
            return true;
          })
        ));
      Tweens.setTimeout(groupLabel,0.2f,()->{

      });
      return super.touchDown(event, x, y, pointer, button);
      }
    });
  }
  void initFont(){
    font = GAssetsManager.getBitmapFont("font_white.fnt");
  }

}
