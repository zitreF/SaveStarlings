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

public class Popup extends Table {

    public Popup(String title, String text) {
        this.setSize(800f, 400f);

        this.debugAll();

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        FreeTypeFontGenerator freeTypeFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("ui/font/glfont.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 15;

        labelStyle.font = freeTypeFontGenerator.generateFont(parameter);
        labelStyle.fontColor = new Color(1f, 1f, 1f, 1f);

        Pixmap backgroundPixmap = new Pixmap(800, 400, Pixmap.Format.RGB888);
        backgroundPixmap.setColor(0f, 0f, 0f, 1f);
        backgroundPixmap.fill();
        Texture backgroundTexture = new Texture(backgroundPixmap);
        backgroundPixmap.dispose();

        TextureRegionDrawable backgroundDrawable = new TextureRegionDrawable(new TextureRegion(backgroundTexture));

        this.setBackground(backgroundDrawable);
        this.setPosition((Gdx.graphics.getWidth() - this.getWidth()) / 2, (Gdx.graphics.getHeight() - this.getHeight()) / 2);

        Table topBar = new Table();
        topBar.setSize(this.getWidth(), 50f);

        Label titleLabel = new Label(title, labelStyle);

        topBar.add(titleLabel).expand().center();
        topBar.add(this.createCloseButton()).size(32f, 32f).right();

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
