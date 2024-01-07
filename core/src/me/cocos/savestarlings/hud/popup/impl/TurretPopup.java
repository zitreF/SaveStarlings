package me.cocos.savestarlings.hud.popup.impl;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import me.cocos.savestarlings.builder.FontBuilder;
import me.cocos.savestarlings.hud.popup.BuildingPopupType;
import me.cocos.savestarlings.hud.popup.Popup;
import me.cocos.savestarlings.hud.node.progressbar.impl.StatsProgressBar;
import me.cocos.savestarlings.asset.AssetService;

public class TurretPopup extends Popup {

    public TurretPopup(BuildingPopupType type, float max, float min) {
        super(type.getName(), 1000f, 600f, BackgroundType.TURRET);

        Label.LabelStyle labelStyle = new Label.LabelStyle();

        labelStyle.font = FontBuilder.from("ui/font/Gabarito-Medium.ttf")
                .size(20)
                .color(Color.valueOf("#02A5C8"))
                .filter(Texture.TextureFilter.Linear)
                .build();

        Label description = new Label(type.getDescription(), labelStyle);
        description.setWrap(true);
        this.row();
        Table center = new Table();
        Image turretImage = new Image(AssetService.getAsset(type.getTexture(), Texture.class));
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
        this.add(center).expandY().center().padBottom(75f);
        this.row();
        this.add(description).size(300f, 100f).left().bottom().padLeft(90f).padBottom(100f);
    }
}
