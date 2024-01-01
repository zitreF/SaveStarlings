package me.cocos.savestarlings.hud.impl;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import me.cocos.savestarlings.builder.FontBuilder;
import me.cocos.savestarlings.service.AssetService;

public class BattleHud extends Table {

    public BattleHud() {
        TextureRegion textureRegion = new TextureRegion(AssetService.getAsset("ui/background/resources_background.png", Texture.class));
        this.setBackground(new TextureRegionDrawable(textureRegion));
        Label.LabelStyle titleStyle = new Label.LabelStyle();
        titleStyle.font = FontBuilder.from("ui/font/glfont.ttf")
                .filter(Texture.TextureFilter.Linear)
                .size(40)
                .color(Color.valueOf("#ffffff"))
                .borderColor(Color.BLACK)
                .borderWidth(2f)
                .build();
        Label title = new Label("WARNING!", titleStyle);
        this.add(title).expandX().expandY().top().padTop(10f).row();
    }
}
