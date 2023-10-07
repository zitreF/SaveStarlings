package me.cocos.savestarlings.hud.node.dialog;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import me.cocos.savestarlings.builder.FontBuilder;

public class Popup extends Table {

    public Popup(String title) {
        this.setSize(1000f, 600f);

        this.debugAll();

        Label.LabelStyle labelStyle = new Label.LabelStyle();

        labelStyle.font = FontBuilder.from("ui/font/glfont.ttf")
                .size(25)
                .shadowOffsetY(3)
                .build();

        TextureRegionDrawable backgroundDrawable = new TextureRegionDrawable(new TextureRegion(new Texture("ui/popup/turret_popup.png")));
        this.setBackground(backgroundDrawable);
        this.setPosition((Gdx.graphics.getWidth() - this.getWidth()) / 2f, (Gdx.graphics.getHeight() - this.getHeight()) / 2f + 50f);

        Table topBar = new Table();
        topBar.setSize(this.getWidth(), 50f);

        Label titleLabel = new Label(title, labelStyle);
        titleLabel.setColor(1f, 1f, 1f, 1f);
        topBar.add(titleLabel).expand().padTop(5f).padLeft(titleLabel.getWidth() / 4f).center();
        topBar.add(this.createCloseButton()).size(32f, 32f).padRight(10f).right();

        this.add(topBar).growX().expandY().top();
    }

    private ImageButton createCloseButton() {
        Texture closeButtonTexture = new Texture("ui/buildings/turrets/btn_close.png");
        ImageButton.ImageButtonStyle closeButtonStyle = new ImageButton.ImageButtonStyle();
        closeButtonStyle.imageUp = new TextureRegionDrawable(new TextureRegion(closeButtonTexture));
        ImageButton closeButton = new ImageButton(closeButtonStyle);
        closeButton.setSize(32f, 32f);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                remove();
            }
        });
        return closeButton;
    }

    public void show(Stage stage) {
        stage.addActor(this);
    }
}
