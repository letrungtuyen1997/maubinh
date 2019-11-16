package com.ss.scene;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.ss.core.util.GAssetsManager;
import com.ss.core.util.GLayer;
import com.ss.core.util.GScreen;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;

public class gameStart extends GScreen {
    Group group = new Group();
    TextureAtlas uiAtlas;

    @Override
    public void dispose() {

    }

    @Override
    public void init() {
        GStage.addToLayer(GLayer.ui,group);
        initAtlas();
        showBg();
    }

    @Override
    public void run() {

    }
    void showBg(){
        Image bg = GUI.createImage(uiAtlas,"table1");
        group.addActor(bg);


    }
    void initAtlas(){
        uiAtlas = GAssetsManager.getTextureAtlas("ui");
    }
}
