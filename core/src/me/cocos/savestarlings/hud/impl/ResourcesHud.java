package me.cocos.savestarlings.hud.impl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import me.cocos.savestarlings.hud.node.table.ResourcesTable;
import me.cocos.savestarlings.asset.AssetService;

public class ResourcesHud extends Table {

    public ResourcesHud() {
        this.setTouchable(Touchable.enabled);
        TextureRegion textureRegion = new TextureRegion(AssetService.getAsset("ui/background/resources_background.png", Texture.class));
        this.setBackground(new TextureRegionDrawable(textureRegion));
        ResourcesTable coins = new ResourcesTable("ui/icons/coins.png", "yellow", "#FAD46E", "#B7850B", 10_000, 5_000, 100, 25);
        ResourcesTable minerals = new ResourcesTable("ui/icons/minerals.png", "green", "#65EF72", "#049B00", 10_000, 5_000, 100, 25);
        this.add(coins);
        this.add(minerals).padLeft(15f);
    }
}