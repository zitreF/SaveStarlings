package me.cocos.savestarlings.hud.node.table;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import me.cocos.savestarlings.hud.node.BuilderHud;

public class MenuTable extends Table {

    private final BuildingsTable buildingsTable;

    public MenuTable(BuilderHud hud, BuildingsTable buildingsTable) {
        this.buildingsTable = buildingsTable;

        this.setSize(400f, 50f);
        this.defaults().pad(1f);

        Pixmap pixmap = hud.createRoundedRectanglePixmap(400, 50, 20, Color.BLACK);
        Texture texture = new Texture(pixmap);
        pixmap.dispose();

        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        NinePatch ninePatch = new NinePatch(texture);

        Color backgroundColor = Color.BLACK;

        backgroundColor.a = 0.5f;

        ninePatch.setColor(backgroundColor);

        NinePatchDrawable ninePatchDrawable = new NinePatchDrawable(ninePatch);

        this.addButton("RESOURCES", () -> {
            Category.RESOURCES.loadTable(buildingsTable);
        });
        this.addButton("ARMY", () -> {
            Category.ARMY.loadTable(buildingsTable);
        });
        this.addButton("TURRETS", () -> {
            Category.TURRETS.loadTable(buildingsTable);
        });
        this.addButton("DEFENSES", () -> {
            Category.DEFENSES.loadTable(buildingsTable);
        });
        this.addButton("DECORATIONS", () -> {
            Category.DECORATIONS.loadTable(buildingsTable);
        });

        this.background(ninePatchDrawable);
    }

    private void addButton(String text, Runnable onClick) {
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();

        TextureRegionDrawable upDrawable = new TextureRegionDrawable(new TextureRegion(new Texture("ui/buttons/hud_background.png")));
        TextureRegionDrawable downDrawable = new TextureRegionDrawable(new TextureRegion(new Texture("ui/buttons/hud_background_clicked.png")));

        buttonStyle.up = upDrawable;
        buttonStyle.down = downDrawable;

        FreeTypeFontGenerator freeTypeFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("ui/font/glfont.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 10;

        buttonStyle.font = freeTypeFontGenerator.generateFont(parameter);

        TextButton button = new TextButton(text, buttonStyle);

        button.setSize(75f, 30f);

        button.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                buildingsTable.clear();
                onClick.run();
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        this.add(button).size(75f, 30f);
    }
}
