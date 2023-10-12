package me.cocos.savestarlings.hud.node.popup.impl;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import me.cocos.savestarlings.builder.FontBuilder;
import me.cocos.savestarlings.hud.node.popup.Popup;
import me.cocos.savestarlings.hud.progressbar.impl.StatsProgressBar;
import me.cocos.savestarlings.service.AssetService;

public class TurretPopup extends Popup {

    public TurretPopup(String name, String text, float max, float min) {
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
        center.add(turretImage).size(250f, 230f).padRight(50f);

        Table stats = new Table();

        StatsProgressBar health = new StatsProgressBar("ui/popup/icons/health_icon.png", "Health", max, min, 200, 30);
        StatsProgressBar damage = new StatsProgressBar("ui/popup/icons/damage_icon.png", "Damage", max, min, 200, 30);
        StatsProgressBar range = new StatsProgressBar("ui/popup/icons/range_icon.png", "Range", max, min, 200, 30);
        StatsProgressBar firerate = new StatsProgressBar("ui/popup/icons/accelerate_icon.png", "Fire rate", max, min, 200, 30);

        stats.add(health);
        stats.row().padTop(15f);
        stats.add(damage);
        stats.row().padTop(15f);
        stats.add(range);
        stats.row().padTop(15f);
        stats.add(firerate);

        center.add(stats).padLeft(50f);
        this.add(center).center().padBottom(75f);
        this.row();
        this.add(description).size(300f, 100f).left().bottom().padLeft(90f).padBottom(100f);
    }
}
