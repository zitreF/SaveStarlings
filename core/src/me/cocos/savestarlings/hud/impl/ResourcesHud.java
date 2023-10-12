package me.cocos.savestarlings.hud.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import me.cocos.savestarlings.hud.node.table.ResourcesTable;

public class ResourcesHud extends Table {

    public ResourcesHud() {
        this.setSize(400, 50);
        TextureRegion textureRegion = new TextureRegion(new Texture("ui/background/resources_background.png"));
        this.setBackground(new TextureRegionDrawable(textureRegion));
        this.setPosition(Gdx.graphics.getWidth() / 2f - this.getWidth() / 2f, Gdx.graphics.getHeight() - this.getHeight());
        ResourcesTable coins = new ResourcesTable("ui/icons/coins.png", 10_000, 5_000, 100, 25);
        this.add(coins);
    }
}