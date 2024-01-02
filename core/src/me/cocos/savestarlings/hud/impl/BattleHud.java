package me.cocos.savestarlings.hud.impl;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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
                .color(Color.valueOf("#D30000"))
                .shadowOffsetX(1)
                .shadowOffsetY(1)
                .build();
        Label.LabelStyle timerStyle = new Label.LabelStyle();
        timerStyle.font = FontBuilder.from("ui/font/glfont.ttf")
                .filter(Texture.TextureFilter.Linear)
                .size(20)
                .color(Color.valueOf("#ffffff"))
                .borderColor(Color.BLACK)
                .borderWidth(1f)
                .build();

        Label.LabelStyle messageStyle = new Label.LabelStyle();
        messageStyle.font = FontBuilder.from("ui/font/glfont.ttf")
                .filter(Texture.TextureFilter.Linear)
                .size(15)
                .color(Color.valueOf("#ffffff"))
                .borderColor(Color.BLACK)
                .borderWidth(1f)
                .build();
        Label title = new Label("WARNING!", titleStyle);

        Image enemyImage = new Image(new Texture("ui/characters/Firebit.png"));

        Label timer = new Label("5:00", timerStyle);

        Label message = new Label("I'm coming for you. Get ready :)", messageStyle);
        message.setWrap(true);

        Table topSide = new Table();
        topSide.add(title).expandX().top().padTop(10f).row();
        topSide.add(enemyImage).expandX().top().row();
        topSide.add(timer).expandX().top().row();
        topSide.add(message).growX().top().padTop(20f).padRight(20f).padLeft(20f);
        this.add(topSide).expandX().expandY().top();
    }
}
