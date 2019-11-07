package com.ss.object;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.ss.GMain;
import com.ss.core.exSprite.GShapeSprite;
import com.ss.core.util.GLayer;
import com.ss.core.util.GStage;

public class BinhPlayer {
  private TextureAtlas cardAtlas;
  private Group groupOverLay;
  private Array<Card> cardsPlayerSrc;
  private Array<Card> cardsPlayerDes;
  private float maxRange;

  public BinhPlayer(TextureAtlas cardAtlas, Array<Card> cardsPlayer){
    this.cardAtlas = cardAtlas;
    groupOverLay = new Group();
    GStage.addToLayer(GLayer.top, groupOverLay);
    this.cardsPlayerSrc = cardsPlayer;

    float rX = (float)Math.pow(cardsPlayer.get(0).card.getWidth(),2);
    float rY = (float)Math.pow(cardsPlayer.get(0).card.getHeight(),2);
    maxRange = (float)Math.sqrt(rX + rY);

    darkScreen();
    cloneCards();
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
    float paddingX = GMain.screenWidth/2 - (2f*cardsPlayerDes.get(0).card.getWidth() + 20);
    float paddingY = GMain.screenHeight/2 + (1.5f*cardsPlayerDes.get(0).card.getHeight() + 10);
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
    blackOverlay.createRectangle(true, -GMain.screenWidth/2,-GMain.screenHeight/2, GMain.screenWidth*2, GMain.screenHeight*2);
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

    int indexEx1 = index1 < index2 ? 1 : 0;
    Card cardTemp1 = cards.removeIndex(index1);
    Card cardTemp2 = cards.removeIndex(index2 - indexEx1);

    int indexEx2 = index1 > cards.size ? 1 : 0;
    cards.insert(index1 - indexEx2, cardTemp2);
    cards.insert(index2, cardTemp1);

    int zIndexTemp = cardTemp1.card.getZIndex();
    cardTemp1.card.setZIndex(cardTemp2.card.getZIndex());
    cardTemp2.card.setZIndex(zIndexTemp);

    cardTemp1.card.addAction(Actions.sequence(
      Actions.moveTo(cardTemp2.card.getX(),cardTemp2.card.getY(), 0.2f, Interpolation.fastSlow)
    ));

    cardTemp2.card.addAction(Actions.sequence(
      Actions.moveTo(p1.x, p1.y, 0.1f, Interpolation.fastSlow)
    ));
  }

  public void setTouchCards(Touchable touchable){
    for(Card card : cardsPlayerDes){
      card.card.setTouchable(touchable);
    }
  }

}
