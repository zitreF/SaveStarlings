package me.cocos.savestarlings.hud.node;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import me.cocos.savestarlings.entity.building.BuildingType;
import me.cocos.savestarlings.hud.popup.BuildingPopupType;
import me.cocos.savestarlings.hud.popup.Popup;
import me.cocos.savestarlings.hud.popup.impl.TurretPopup;
import me.cocos.savestarlings.asset.AssetService;
import me.cocos.savestarlings.service.GameService;
import me.cocos.savestarlings.util.SoundUtil;

public enum Category {

    RESOURCES() {
        @Override
        public void loadTable(Table table) {
            Category.addBuilding(BuildingPopupType.BLAST_CANNON, BuildingType.BANK, table,
                    "ui/buildings/turrets/sniper.png",
                    "ui/buildings/turrets/sniper_pressed.png");
        }
    },
    ARMY() {
        @Override
        public void loadTable(Table table) {
            Category.addBuilding(BuildingPopupType.BLAST_CANNON, BuildingType.LABORATORY, table,
                    "ui/buildings/turrets/sniper.png",
                    "ui/buildings/turrets/sniper_pressed.png");
        }
    },
    TURRETS() {
        @Override
        public void loadTable(Table table) {
            Category.addBuilding(BuildingPopupType.SNIPER_TOWER, BuildingType.SNIPER_TOWER, table,
                    "ui/buildings/turrets/sniper.png",
                    "ui/buildings/turrets/sniper_pressed.png");
            Category.addBuilding(BuildingPopupType.BLAST_CANNON, BuildingType.CANNON_BLAST, table,
                    "ui/buildings/turrets/cannon_blast.png",
                    "ui/buildings/turrets/cannon_blast_pressed.png");
        }
    },
    DEFENSES() {
        @Override
        public void loadTable(Table table) {

        }
    },
    DECORATIONS() {
        @Override
        public void loadTable(Table table) {

        }
    };

    public abstract void loadTable(Table table);
    private static void addBuilding(BuildingPopupType buildingPopupType, BuildingType buildingType, Table table, String texturePath, String pressedPath) {

        Texture infoTexture = AssetService.getAsset("ui/buildings/turrets/btn_info.png");
        infoTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        Image infoImage = new Image(infoTexture);

        infoImage.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (Popup.IS_OPENED) {
                    return super.touchDown(event, x, y, pointer, button);
                }
                Popup popup = new TurretPopup(buildingPopupType, 10_000f, 3_000f);
                popup.show(table.getStage());
                SoundUtil.playSound("other/click.mp3");
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        Texture texture = AssetService.getAsset(texturePath);
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        Texture pressedTexture = AssetService.getAsset(pressedPath);
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
                SoundUtil.playSound("other/click.mp3");
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
        table.add(stack).padBottom(10f).size(100f, 100f);
    }
}
