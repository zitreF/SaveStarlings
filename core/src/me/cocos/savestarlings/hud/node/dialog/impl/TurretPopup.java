package me.cocos.savestarlings.hud.node.dialog.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import me.cocos.savestarlings.builder.FontBuilder;
import me.cocos.savestarlings.hud.node.dialog.Popup;

public class TurretPopup extends Popup {

    public TurretPopup(String name, String text) {
        super(name);

        Label.LabelStyle labelStyle = new Label.LabelStyle();

        labelStyle.font = FontBuilder.from("ui/font/glfont.ttf")
                .size(15)
                .color(Color.valueOf("#02A5C8"))
                .filter(Texture.TextureFilter.Linear)
                .shadowOffsetY(3)
                .build();

        Label description = new Label(text, labelStyle);
        description.setWrap(true);
        this.row();
        this.add(description).size(300f, 100f).left().bottom().padLeft(90f).padBottom(100f);
    }
}
