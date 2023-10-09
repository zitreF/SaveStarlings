package me.cocos.savestarlings.hud.node.popup.impl;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import me.cocos.savestarlings.builder.FontBuilder;
import me.cocos.savestarlings.hud.node.popup.Popup;
import me.cocos.savestarlings.hud.progressbar.GLProgressBar;
import me.cocos.savestarlings.hud.progressbar.impl.StatsProgressBar;
import me.cocos.savestarlings.service.AssetService;

public class TurretPopup extends Popup {

    public TurretPopup(String name, String text) {
        super(name);

        Label.LabelStyle labelStyle = new Label.LabelStyle();

        labelStyle.font = FontBuilder.from("ui/font/roboto.ttf")
                .size(15)
                .color(Color.valueOf("#02A5C8"))
                .filter(Texture.TextureFilter.Linear)
                .build();

        Label description = new Label(text, labelStyle);
        description.setWrap(true);
        this.row();
        Table center = new Table();
        Image turretImage = new Image(AssetService.getAsset("ui/popup/turrets/blast_cannon_popup.png", Texture.class));

        StatsProgressBar damage = new StatsProgressBar("ui/popup/icons/damage_icon.png", 400f, 200f, 200, 35);
        center.add(turretImage).size(250f, 230f).padRight(75f);
        center.add(damage).padLeft(100f);
        this.add(center).center().padBottom(75f);
        this.row();
        this.add(description).size(300f, 100f).left().bottom().padLeft(90f).padBottom(100f);
    }
}
