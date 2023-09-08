package me.cocos.savestarlings.hud.node.table;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import me.cocos.savestarlings.hud.node.BuilderHud;

public class MenuTable extends Table {

    public MenuTable(BuilderHud hud) {
        this.setSize(400f, 50f);

        this.defaults().pad(1f);

        Pixmap pixmap = hud.createRoundedRectanglePixmap(400, 50, 20, Color.BLACK);
        Texture texture = new Texture(pixmap);

        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        NinePatch ninePatch = new NinePatch(texture);

        Color backgroundColor = Color.BLACK;

        backgroundColor.a = 0.5f;

        ninePatch.setColor(backgroundColor);

        NinePatchDrawable ninePatchDrawable = new NinePatchDrawable(ninePatch);

        this.addButton("RESOURCES");
        this.addButton("ARMY");
        this.addButton("TURRETS");
        this.addButton("DEFENSES");
        this.addButton("DECORATIONS");

        this.background(ninePatchDrawable);
    }

    private void addButton(String text) {
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();

        TextureRegionDrawable upDrawable = new TextureRegionDrawable(new TextureRegion(new Texture("ui/buttons/hud_background.png")));
        TextureRegionDrawable downDrawable = new TextureRegionDrawable(new TextureRegion(new Texture("ui/buttons/hud_background_2.png")));

        buttonStyle.up = upDrawable;
        buttonStyle.down = downDrawable;

        FreeTypeFontGenerator freeTypeFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("ui/font/glfont.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 9;

        buttonStyle.font = freeTypeFontGenerator.generateFont(parameter);

        TextButton button = new TextButton(text, buttonStyle);

        this.add(button).size(75f, 30f);
    }
}
