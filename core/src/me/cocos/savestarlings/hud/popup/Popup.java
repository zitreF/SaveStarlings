package me.cocos.savestarlings.hud.popup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import me.cocos.savestarlings.builder.FontBuilder;
import me.cocos.savestarlings.service.AssetService;
import me.cocos.savestarlings.util.SoundUtil;

public class Popup extends Table {

    public static boolean IS_OPENED;

    public Popup(String title, float width, float height, BackgroundType backgroundType) {
        this.setSize(width, height);

        Label.LabelStyle labelStyle = new Label.LabelStyle();

        labelStyle.font = FontBuilder.from("ui/font/glfont.ttf")
                .size(25)
                .shadowOffsetY(3)
                .build();

        TextureRegionDrawable backgroundDrawable = new TextureRegionDrawable(new TextureRegion(AssetService.getAsset(backgroundType.getAssetPath(), Texture.class)));
        this.setBackground(backgroundDrawable);
        this.setPosition((1600 - this.getWidth()) / 2f, (900 - this.getHeight()) / 2f + 50f);

        Table topBar = new Table();
        topBar.setSize(this.getWidth(), 50f);

        Label titleLabel = new Label(title, labelStyle);
        titleLabel.setColor(1f, 1f, 1f, 1f);
        topBar.add(titleLabel).expand().padTop(5f).padLeft(titleLabel.getWidth() / 4f).center();
        topBar.add(this.createCloseButton()).size(32f, 32f).right().padRight(10f);

        this.add(topBar).growX().top();
    }

    private ImageButton createCloseButton() {
        Texture closeButtonTexture = AssetService.getAsset("ui/buildings/turrets/btn_close.png");
        ImageButton.ImageButtonStyle closeButtonStyle = new ImageButton.ImageButtonStyle();
        closeButtonStyle.imageUp = new TextureRegionDrawable(new TextureRegion(closeButtonTexture));
        ImageButton closeButton = new ImageButton(closeButtonStyle);
        closeButton.setSize(32f, 32f);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SoundUtil.playSound("other/click.mp3");
                remove();
                IS_OPENED = false;
            }
        });
        return closeButton;
    }

    public void show(Stage stage) {
        stage.addActor(this);
        IS_OPENED = true;
    }

    public enum BackgroundType {
        DEFAULT("ui/popup/default_popup.png"),
        TURRET("ui/popup/turret_popup.png");

        private final String assetPath;

        BackgroundType(String assetPath) {
            this.assetPath = assetPath;
        }

        public String getAssetPath() {
            return this.assetPath;
        }
    }
}
