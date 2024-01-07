package me.cocos.savestarlings.hud.popup.impl;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;
import me.cocos.savestarlings.builder.FontBuilder;
import me.cocos.savestarlings.hud.popup.Popup;
import me.cocos.savestarlings.hud.node.scene2d.TypingLabel;
import me.cocos.savestarlings.asset.AssetService;
import me.cocos.savestarlings.util.SoundUtil;

public class BattlePopup extends Popup {

    public BattlePopup(String title) {
        super(title, 900f, 400f, BackgroundType.DEFAULT);
        this.row();
        Label.LabelStyle labelStyle = new Label.LabelStyle();

        labelStyle.font = FontBuilder.from("ui/font/Gabarito-Medium.ttf")
                .size(20)
                .color(Color.valueOf("#02A5C8"))
                .filter(Texture.TextureFilter.Linear)
                .build();
        Table center = new Table();
        Image starlingImage = new Image(AssetService.getAsset("ui/starlings/starling_pointing.png", Texture.class));

        String messageText = """
                Our spies saw Firebit getting ready for war. We need to prepare\s
                defenses! Place as many turrets as you can to protect your StarBase!\s
                We have estimated our time to 5 minutes, get ready!
                """.replace("\n", "");

        TypingLabel message = new TypingLabel(messageText, labelStyle);
        message.setWrap(true);
        message.setAlignment(Align.topLeft);
        message.setTotalTime(0.75f);
        message.startTypingAnimation();
        Table messageTable = new Table();
        messageTable.add(message).grow().padLeft(500f / 10f + 15f).padTop(5f).padRight(15f).center();
        NinePatch ninePatch = new NinePatch(AssetService.getAsset("ui/popup/dialog/dialog_background.png", Texture.class));

        Color backgroundColor = Color.BLACK;

        backgroundColor.a = 0.5f;

        ninePatch.setColor(backgroundColor);

        NinePatchDrawable ninePatchDrawable = new NinePatchDrawable(ninePatch);
        messageTable.background(ninePatchDrawable);
        center.add(starlingImage).size(300f, 300f).left().padRight(15f);
        center.add(messageTable).size(500f, 200f).right();

        this.add(center).expandY().growX();

        SoundUtil.playSound("starling/Body_1.mp3");
    }
}