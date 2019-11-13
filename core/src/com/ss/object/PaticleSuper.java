package com.ss.object;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.effect.effectWin;
import com.ss.GMain;
import com.ss.commons.Tweens;
import com.ss.core.exSprite.GShapeSprite;
import com.ss.core.util.GLayer;
import com.ss.core.util.GStage;

public class PaticleSuper {
    Group group = new Group();
    public PaticleSuper(int type){
        GStage.addToLayer(GLayer.top,group);
        darkScreen();
        effectWin effect = new effectWin(type,GStage.getWorldWidth()/2,GStage.getWorldHeight()/2);
        effect.effect.setDuration(5);
        group.addActor(effect);
        effect.start();
        Tweens.setTimeout(group,5f,()->{
            group.clear();
            group.remove();
        });

    }
    private void darkScreen(){
        final GShapeSprite blackOverlay = new GShapeSprite();
        blackOverlay.createRectangle(true, -GStage.getWorldWidth()/2,-GStage.getWorldHeight()/2, GStage.getWorldWidth()*2, GStage.getWorldHeight()*2);
        blackOverlay.setColor(0,0,0,0.7f);
        group.addActor(blackOverlay);
    }
}
