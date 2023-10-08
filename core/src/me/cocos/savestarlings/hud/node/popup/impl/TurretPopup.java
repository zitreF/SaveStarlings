package me.cocos.savestarlings.hud.node.popup.impl;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import me.cocos.savestarlings.builder.FontBuilder;
import me.cocos.savestarlings.hud.node.popup.Popup;
import me.cocos.savestarlings.hud.progressbar.GLProgressBar;
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
        Image turretImage = new Image(AssetService.getAsset("ui/popup/turrets/blast_cannon_popup.png", Texture.class));
        this.add(turretImage).size(250f, 230f).left().padLeft((250f / 2f)).padBottom(230f / 3f);
        // TODO: add stats Table
        GLProgressBar glProgressBar = new GLProgressBar(300f, 150f);
        this.add(glProgressBar);

        this.row();
        this.add(description).size(300f, 100f).left().bottom().padLeft(90f).padBottom(100f);
    }
}
