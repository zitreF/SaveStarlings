package me.cocos.savestarlings.hud.node;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import me.cocos.savestarlings.entity.building.BuildingType;
import me.cocos.savestarlings.hud.Hud;

public class BuilderTable extends Table {

    private final Hud hud;

    public BuilderTable(Hud hud) {
        this.hud = hud;
        Texture backgroundTexture = new Texture("ui/hud.png");
        backgroundTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        Image backgroundImage = new Image(backgroundTexture);

        this.defaults().pad(20f);

        this.addBuilding(BuildingType.SNIPER, "ui/buildings/turrets/sniper.png", "ui/buildings/turrets/sniper_pressed.png");
        this.addBuilding(BuildingType.CANNON_BLAST, "ui/buildings/turrets/cannon_blast.png", "ui/buildings/turrets/cannon_blast_pressed.png");

        this.background(backgroundImage.getDrawable());
        this.setSize(800f, 200f);
        this.setPosition(Gdx.graphics.getWidth() / 2f - this.getWidth() / 2f, this.getY()-50f);
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
