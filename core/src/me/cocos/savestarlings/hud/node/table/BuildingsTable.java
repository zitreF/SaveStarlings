package me.cocos.savestarlings.hud.node.table;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import me.cocos.savestarlings.entity.building.BuildingType;
import me.cocos.savestarlings.hud.Hud;
import me.cocos.savestarlings.hud.node.BuilderHud;

public class BuildingsTable extends Table {

    private final Hud hud;

    public BuildingsTable(BuilderHud hud) {
        this.hud = hud.getHud();

        this.defaults().pad(20f);
        this.setSize(800f, 200f);

        Pixmap pixmap = hud.createRoundedRectanglePixmap(800, 200, 20, Color.BLACK);
        Texture texture = new Texture(pixmap);

        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        NinePatch ninePatch = new NinePatch(texture);

        Color backgroundColor = Color.BLACK;

        backgroundColor.a = 0.5f;

        ninePatch.setColor(backgroundColor);

        NinePatchDrawable ninePatchDrawable = new NinePatchDrawable(ninePatch);

        this.background(ninePatchDrawable);

        this.left();

        this.addBuilding(BuildingType.SNIPER, "ui/buildings/turrets/sniper.png", "ui/buildings/turrets/sniper_pressed.png");
        this.addBuilding(BuildingType.CANNON_BLAST, "ui/buildings/turrets/cannon_blast.png", "ui/buildings/turrets/cannon_blast_pressed.png");
    }

    private void addBuilding(BuildingType buildingType, String texturePath, String pressedPath) {
        Texture texture = new Texture(texturePath);
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        Texture pressedTexture = new Texture(pressedPath);
        pressedTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        Image image = new Image(texture);

        TextureRegionDrawable textureDrawable = new TextureRegionDrawable(texture);

        TextureRegionDrawable pressedTextureDrawable = new TextureRegionDrawable(pressedTexture);

        image.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.postRunnable(() -> {
                    hud.getBuildingService().setCurrentBuilding(buildingType);
                });
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                image.setDrawable(pressedTextureDrawable);
                super.enter(event, x, y, pointer, fromActor);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                image.setDrawable(textureDrawable);
                super.exit(event, x, y, pointer, toActor);
            }
        });
        this.add(image).padBottom(70f).size(100f, 100f);
    }
}
