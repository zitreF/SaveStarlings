package me.cocos.savestarlings.hud.node.table;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import me.cocos.savestarlings.hud.node.BuilderHud;

public class BuildingsTable extends Table {

    public BuildingsTable(BuilderHud hud) {
        this.defaults().pad(20f);
        this.setSize(800f, 150f);

        Pixmap pixmap = hud.createRoundedRectanglePixmap(800, 150, 20, Color.BLACK);
        Texture texture = new Texture(pixmap);
        pixmap.dispose();

        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        NinePatch ninePatch = new NinePatch(texture);

        Color backgroundColor = Color.BLACK;

        backgroundColor.a = 0.6f;

        ninePatch.setColor(backgroundColor);

        NinePatchDrawable ninePatchDrawable = new NinePatchDrawable(ninePatch);

        this.background(ninePatchDrawable);

        this.left();

        Category.RESOURCES.loadTable(this);
    }
}
