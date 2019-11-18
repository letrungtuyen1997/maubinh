package com.effect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

public class effectWin extends Actor {
  private static FileHandle mauthau = Gdx.files.internal("particle/mau-thau");
  private static FileHandle doi = Gdx.files.internal("particle/doi");
  private static FileHandle thu = Gdx.files.internal("particle/thu");
  private static FileHandle xam = Gdx.files.internal("particle/sam");
  private static FileHandle sanh = Gdx.files.internal("particle/sanh");
  private static FileHandle thung = Gdx.files.internal("particle/thung");
  private static FileHandle culu = Gdx.files.internal("particle/cu-lu");
  private static FileHandle tuquy = Gdx.files.internal("particle/tu-quy");
  private static FileHandle thungphasanh = Gdx.files.internal("particle/thung-pha-sanh");
  private static FileHandle win = Gdx.files.internal("particle/win");
  private static FileHandle lightFrame = Gdx.files.internal("particle/lightFrame");
  private static FileHandle bathung = Gdx.files.internal("particle/3Thung");
  private static FileHandle basanh = Gdx.files.internal("particle/3Sanh");
  private static FileHandle lucphebon = Gdx.files.internal("particle/lucphebon");
  private static FileHandle namdoi1sam = Gdx.files.internal("particle/5doi1sam");
  private static FileHandle sanhrong = Gdx.files.internal("particle/SanhRong");
  private static FileHandle rongcuon = Gdx.files.internal("particle/RongCuon");
  private static FileHandle bathung1 = Gdx.files.internal("particle/3thung1");
  private static FileHandle basanh1 = Gdx.files.internal("particle/3sanh1");
  private static FileHandle lucphebon1 = Gdx.files.internal("particle/lucphebon1");
  private static FileHandle namdoi1sam1 = Gdx.files.internal("particle/5doi1sam1");
  private static FileHandle sanhrong1 = Gdx.files.internal("particle/SanhRong1");
  private static FileHandle rongcuon1 = Gdx.files.internal("particle/RongCuon1");
  private static FileHandle winner = Gdx.files.internal("particleOver/paricleWin");
  private static FileHandle loser = Gdx.files.internal("particleOver/particleLose");
  private static FileHandle winner2 = Gdx.files.internal("particleOver/win");


  public ParticleEffect effect;
  private Actor parent = this.parent;
  private Group stage;

  public effectWin(int id, float f, float f2) {
    this.effect = new ParticleEffect();
    setX(f);
    setY(f2);
    if(id==0) {
      this.effect.load(mauthau, Gdx.files.internal("particle"));
      this.effect.scaleEffect(1.0f);
      for (int i = 0; i < this.effect.getEmitters().size; i++) {
        ((ParticleEmitter) this.effect.getEmitters().get(i)).flipY();
      }
    }else if(id==1){
      this.effect.load(doi, Gdx.files.internal("particle"));
      this.effect.scaleEffect(0.7f);
      for (int i = 0; i < this.effect.getEmitters().size; i++) {
        ((ParticleEmitter) this.effect.getEmitters().get(i)).flipY();
      }
    }
    else if(id==2){
      this.effect.load(thu, Gdx.files.internal("particle"));
      this.effect.scaleEffect(0.7f);
      for (int i = 0; i < this.effect.getEmitters().size; i++) {
        ((ParticleEmitter) this.effect.getEmitters().get(i)).flipY();
      }
    }
    else if(id==3){
      this.effect.load(xam, Gdx.files.internal("particle"));
      this.effect.scaleEffect(1.0f);
      for (int i = 0; i < this.effect.getEmitters().size; i++) {
        ((ParticleEmitter) this.effect.getEmitters().get(i)).flipY();
      }
    }
    else if(id==4){
      this.effect.load(sanh, Gdx.files.internal("particle"));
      this.effect.scaleEffect(1.0f);
      for (int i = 0; i < this.effect.getEmitters().size; i++) {
        ((ParticleEmitter) this.effect.getEmitters().get(i)).flipY();
      }
    }
    else if(id==5){
      this.effect.load(thung, Gdx.files.internal("particle"));
      this.effect.scaleEffect(1.2f);
      for (int i = 0; i < this.effect.getEmitters().size; i++) {
        ((ParticleEmitter) this.effect.getEmitters().get(i)).flipY();
      }
    }
    else if(id==6){
      this.effect.load(culu, Gdx.files.internal("particle"));
      this.effect.scaleEffect(1f);
      for (int i = 0; i < this.effect.getEmitters().size; i++) {
        ((ParticleEmitter) this.effect.getEmitters().get(i)).flipY();
      }
    }
    else if(id==7){
      this.effect.load(tuquy, Gdx.files.internal("particle"));
      this.effect.scaleEffect(1.0f);
      for (int i = 0; i < this.effect.getEmitters().size; i++) {
        ((ParticleEmitter) this.effect.getEmitters().get(i)).flipY();
      }
    }
    else if(id==9){
      this.effect.load(thungphasanh, Gdx.files.internal("particle"));
      this.effect.scaleEffect(1.5f);
      for (int i = 0; i < this.effect.getEmitters().size; i++) {
        ((ParticleEmitter) this.effect.getEmitters().get(i)).flipY();
      }
    }else if(id==10){
      this.effect.load(win, Gdx.files.internal("particle"));
      this.effect.scaleEffect(1.5f);
      for (int i = 0; i < this.effect.getEmitters().size; i++) {
        ((ParticleEmitter) this.effect.getEmitters().get(i)).flipY();
      }
    } else if(id==11){
      this.effect.load(lightFrame, Gdx.files.internal("particle"));
      this.effect.scaleEffect(1.0f);
      for (int i = 0; i < this.effect.getEmitters().size; i++) {
        ((ParticleEmitter) this.effect.getEmitters().get(i)).flipY();
      }
    }else if(id==12){
      this.effect.load(basanh, Gdx.files.internal("particle"));
      this.effect.scaleEffect(1.0f);
      for (int i = 0; i < this.effect.getEmitters().size; i++) {
        ((ParticleEmitter) this.effect.getEmitters().get(i)).flipY();
      }
    }else if(id==13){
      this.effect.load(bathung, Gdx.files.internal("particle"));
      this.effect.scaleEffect(1.0f);
      for (int i = 0; i < this.effect.getEmitters().size; i++) {
        ((ParticleEmitter) this.effect.getEmitters().get(i)).flipY();
      }
    }else if(id==14){
      this.effect.load(lucphebon, Gdx.files.internal("particle"));
      this.effect.scaleEffect(1.0f);
      for (int i = 0; i < this.effect.getEmitters().size; i++) {
        ((ParticleEmitter) this.effect.getEmitters().get(i)).flipY();
      }
    }else if(id==15){
      this.effect.load(namdoi1sam, Gdx.files.internal("particle"));
      this.effect.scaleEffect(1.0f);
      for (int i = 0; i < this.effect.getEmitters().size; i++) {
        ((ParticleEmitter) this.effect.getEmitters().get(i)).flipY();
      }
    } else if(id==16){
      this.effect.load(sanhrong, Gdx.files.internal("particle"));
      this.effect.scaleEffect(2.0f);
      for (int i = 0; i < this.effect.getEmitters().size; i++) {
        ((ParticleEmitter) this.effect.getEmitters().get(i)).flipY();
      }
    } else if(id==17){
      this.effect.load(rongcuon, Gdx.files.internal("particle"));
      this.effect.scaleEffect(2.0f);
      for (int i = 0; i < this.effect.getEmitters().size; i++) {
        ((ParticleEmitter) this.effect.getEmitters().get(i)).flipY();
      }
    }else if(id==18){
        this.effect.load(basanh1, Gdx.files.internal("particle"));
        this.effect.scaleEffect(0.7f);
        for (int i = 0; i < this.effect.getEmitters().size; i++) {
            ((ParticleEmitter) this.effect.getEmitters().get(i)).flipY();
        }
    }else if(id==19){
        this.effect.load(bathung1, Gdx.files.internal("particle"));
        this.effect.scaleEffect(0.7f);
        for (int i = 0; i < this.effect.getEmitters().size; i++) {
            ((ParticleEmitter) this.effect.getEmitters().get(i)).flipY();
        }
    }else if(id==20){
        this.effect.load(lucphebon1, Gdx.files.internal("particle"));
        this.effect.scaleEffect(0.7f);
        for (int i = 0; i < this.effect.getEmitters().size; i++) {
            ((ParticleEmitter) this.effect.getEmitters().get(i)).flipY();
        }
    }else if(id==21){
        this.effect.load(namdoi1sam1, Gdx.files.internal("particle"));
        this.effect.scaleEffect(0.7f);
        for (int i = 0; i < this.effect.getEmitters().size; i++) {
            ((ParticleEmitter) this.effect.getEmitters().get(i)).flipY();
        }
    } else if(id==22){
        this.effect.load(sanhrong1, Gdx.files.internal("particle"));
        this.effect.scaleEffect(0.7f);
        for (int i = 0; i < this.effect.getEmitters().size; i++) {
            ((ParticleEmitter) this.effect.getEmitters().get(i)).flipY();
        }
    } else if(id==23){
        this.effect.load(rongcuon1, Gdx.files.internal("particle"));
        this.effect.scaleEffect(0.7f);
        for (int i = 0; i < this.effect.getEmitters().size; i++) {
            ((ParticleEmitter) this.effect.getEmitters().get(i)).flipY();
        }
    }else if(id==24){
      this.effect.load(winner, Gdx.files.internal("particleOver"));
      this.effect.scaleEffect(2f);
      for (int i = 0; i < this.effect.getEmitters().size; i++) {
        ((ParticleEmitter) this.effect.getEmitters().get(i)).flipY();
      }
    }else if(id==25){
      this.effect.load(loser, Gdx.files.internal("particleOver"));
      this.effect.scaleEffect(2f);
      for (int i = 0; i < this.effect.getEmitters().size; i++) {
        ((ParticleEmitter) this.effect.getEmitters().get(i)).flipY();
      }
    }else if(id==26){
      this.effect.load(winner2, Gdx.files.internal("particleOver"));
      this.effect.scaleEffect(2f);
      for (int i = 0; i < this.effect.getEmitters().size; i++) {
        ((ParticleEmitter) this.effect.getEmitters().get(i)).flipY();
      }
    }
    this.effect.setPosition(f, f2);


  }

  public void act(float f) {
    super.act(f);
    this.effect.setPosition(getX(), getY());
    this.effect.update(f);
  }

  public void draw(Batch batch, float f) {
    super.draw(batch, f);
    if (!this.effect.isComplete()) {
      this.effect.draw(batch);
      return;
    }
    this.effect.dispose();
  }

  public void start() {
    this.effect.start();
  }
}