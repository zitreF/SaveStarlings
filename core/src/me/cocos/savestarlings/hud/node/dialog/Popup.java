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
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Popup extends Dialog {

    private static final WindowStyle WINDOW_STYLE;

    static {
        WINDOW_STYLE = new WindowStyle();
        FreeTypeFontGenerator freeTypeFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("ui/font/glfont.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 15;
        WINDOW_STYLE.titleFont = freeTypeFontGenerator.generateFont(parameter);
        WINDOW_STYLE.titleFontColor = Color.WHITE;
    }

    public Popup(String title) {
        super(title, WINDOW_STYLE);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        FreeTypeFontGenerator freeTypeFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("ui/font/glfont.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 15;

        labelStyle.font = freeTypeFontGenerator.generateFont(parameter);
        labelStyle.fontColor = Color.WHITE;

        this.debugAll();

        this.getTitleTable().padTop(25f);
        this.getTitleTable().add(this.createCloseButton()).right().padBottom(5f);

        Pixmap backgroundPixmap = new Pixmap(500, 250, Pixmap.Format.RGBA8888);
        backgroundPixmap.setColor(0f, 0f, 0f, 0.5f);
        backgroundPixmap.fill();
        Texture backgroundTexture = new Texture(backgroundPixmap);
        backgroundPixmap.dispose();

        TextureRegionDrawable backgroundDrawable = new TextureRegionDrawable(new TextureRegion(backgroundTexture));
        this.setBackground(backgroundDrawable);

        this.text("Testing text blablabla :)", labelStyle);

        this.setSize(500f, 250f);

        this.setPosition((Gdx.graphics.getWidth() - this.getWidth()) / 2, (Gdx.graphics.getHeight() - this.getHeight()) / 2);
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
                hide();
            }
        });
        return closeButton;
    }
}
