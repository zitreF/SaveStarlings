package me.cocos.savestarlings.hud.node.table;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import me.cocos.savestarlings.entity.building.BuildingType;
import me.cocos.savestarlings.hud.node.dialog.Popup;
import me.cocos.savestarlings.service.GameService;

public enum Category {

    RESOURCES() {
        @Override
        void loadTable(Table table) {

        }
    },
    ARMY() {
        @Override
        void loadTable(Table table) {
            Category.addBuilding(BuildingType.LABORATORY, table, "ui/buildings/turrets/sniper.png", "ui/buildings/turrets/sniper_pressed.png");
        }
    },
    TURRETS() {
        @Override
        void loadTable(Table table) {
            Category.addBuilding(BuildingType.SNIPER, table, "ui/buildings/turrets/sniper.png", "ui/buildings/turrets/sniper_pressed.png");
            Category.addBuilding(BuildingType.CANNON_BLAST, table, "ui/buildings/turrets/cannon_blast.png", "ui/buildings/turrets/cannon_blast_pressed.png");
        }
    },
    DEFENSES() {
        @Override
        void loadTable(Table table) {

        }
    },
    DECORATIONS() {
        @Override
        void loadTable(Table table) {

        }
    };

    abstract void loadTable(Table table);
    private static void addBuilding(BuildingType buildingType, Table table, String texturePath, String pressedPath) {

        Texture infoTexture = new Texture("ui/buildings/turrets/btn_info.png");
        infoTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        Image infoImage = new Image(infoTexture);

        infoImage.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Popup popup = new Popup("Hi there!");
                popup.show(table.getStage());
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        Texture texture = new Texture(texturePath);
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        Texture pressedTexture = new Texture(pressedPath);
        pressedTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        Image image = new Image(texture);

        TextureRegionDrawable textureDrawable = new TextureRegionDrawable(texture);

        TextureRegionDrawable pressedTextureDrawable = new TextureRegionDrawable(pressedTexture);

        image.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.postRunnable(() -> {
                    GameService.getInstance().getHud().getBuildingService().setCurrentBuilding(buildingType);
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
        Stack stack = new Stack();
        stack.add(image);
        Container<Image> infoContainer = new Container<>(infoImage);
        infoContainer.top().left();
        stack.add(infoContainer);
        table.add(stack).padBottom(70f).size(100f, 100f);
    }
}