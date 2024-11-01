package me.cocos.savestarlings.builder;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class FontBuilder {

    private final String path;
    private Color color;
    private boolean colorMarkup;
    private int size;
    private float borderWidth;
    private Color borderColor;
    private int shadowOffsetX;
    private int shadowOffsetY;
    private Texture.TextureFilter filter;

    public FontBuilder(String path) {
        this.path = path;
        this.size = 15;
        this.shadowOffsetX = 0;
        this.shadowOffsetY = 0;
        this.filter = Texture.TextureFilter.Linear;
        this.color = Color.WHITE;
        this.borderWidth = 0;
        this.borderColor = Color.BLACK;
    }

    public FontBuilder color(Color color) {
        this.color = color;
        return this;
    }

    public FontBuilder colorMarkup(boolean colorMarkup) {
        this.colorMarkup = colorMarkup;
        return this;
    }

    public FontBuilder size(int size) {
        this.size = size;
        return this;
    }

    public FontBuilder shadowOffsetY(int shadowOffsetY) {
        this.shadowOffsetY = shadowOffsetY;
        return this;
    }

    public FontBuilder shadowOffsetX(int shadowOffsetX) {
        this.shadowOffsetX = shadowOffsetX;
        return this;
    }

    public FontBuilder filter(Texture.TextureFilter filter) {
        this.filter = filter;
        return this;
    }

    public FontBuilder borderWidth(float width) {
        this.borderWidth = width;
        return this;
    }

    public FontBuilder borderColor(Color color) {
        this.borderColor = color;
        return this;
    }

    public BitmapFont build() {
        FreeTypeFontGenerator freeTypeFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal(this.path));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = this.size;
        parameter.minFilter = this.filter;
        parameter.magFilter = this.filter;
        parameter.shadowColor = Color.BLACK;
        parameter.shadowOffsetY = this.shadowOffsetY;
        parameter.shadowOffsetX = this.shadowOffsetX;
        parameter.color = this.color;
        parameter.borderWidth = this.borderWidth;
        parameter.borderColor = this.borderColor;
        BitmapFont bitmapFont = freeTypeFontGenerator.generateFont(parameter);
        if (colorMarkup) {
            bitmapFont.getData().markupEnabled = colorMarkup;
        }
        freeTypeFontGenerator.dispose();
        return bitmapFont;
    }

    public static FontBuilder from(String path) {
        return new FontBuilder(path);
    }
}
