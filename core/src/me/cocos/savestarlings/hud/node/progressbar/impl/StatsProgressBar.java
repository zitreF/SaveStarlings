package me.cocos.savestarlings.hud.node.progressbar.impl;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import me.cocos.savestarlings.builder.FontBuilder;
import me.cocos.savestarlings.hud.node.progressbar.GLProgressBar;
import me.cocos.savestarlings.asset.AssetService;
import me.cocos.savestarlings.util.FormatUtils;

public class StatsProgressBar extends Table {

    private final GLProgressBar progressBar;


    public StatsProgressBar(String texturePath, String info, float max, float value, int width, int height) {
        Texture texture = AssetService.getAsset(texturePath);
        Image image = new Image(texture);
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = FontBuilder.from("ui/font/glfont.ttf")
                .filter(Texture.TextureFilter.Linear)
                .size(12)
                .color(Color.valueOf("#039EC0"))
                .shadowOffsetY(1)
                .build();
        Label infoLabel = new Label(info, labelStyle);
        this.progressBar = new GLProgressBar("blue", max, value, width, height);
        Label valueLabel = new Label(FormatUtils.formatNumber(value), labelStyle);
        this.add(image).size(height, height);
        this.add(infoLabel).width(75f).padLeft(5f);
        this.add(progressBar).padLeft(20f).size(width, height);
        this.add(valueLabel).padLeft(20f);
    }
}