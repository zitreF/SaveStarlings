package me.cocos.savestarlings.hud.node.table;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import me.cocos.savestarlings.hud.node.BuilderHud;
import me.cocos.savestarlings.util.SoundUtil;

public class MenuTable extends Table {

    private final BuildingsTable buildingsTable;

    public MenuTable(BuilderHud hud, BuildingsTable buildingsTable) {
        this.buildingsTable = buildingsTable;

        this.setSize(450f, 80f);

        Pixmap pixmap = hud.createRoundedRectanglePixmap(450, 80, 20, Color.BLACK);
        Texture texture = new Texture(pixmap);
        pixmap.dispose();

        NinePatch ninePatch = new NinePatch(texture);

        Color backgroundColor = Color.BLACK;

        backgroundColor.a = 0.5f;

        ninePatch.setColor(backgroundColor);

        NinePatchDrawable ninePatchDrawable = new NinePatchDrawable(ninePatch);

        this.addButton("ui/icons/turrets/resources.png", () -> {
            Category.RESOURCES.loadTable(buildingsTable);
        });
        this.addButton("ui/icons/turrets/army.png", () -> {
            Category.ARMY.loadTable(buildingsTable);
        });
        this.addButton("ui/icons/turrets/turrets.png", () -> {
            Category.TURRETS.loadTable(buildingsTable);
        });
        this.addButton("ui/icons/turrets/defenses.png", () -> {
            Category.DEFENSES.loadTable(buildingsTable);
        });
        this.addButton("ui/icons/turrets/decorations.png", () -> {
            Category.DECORATIONS.loadTable(buildingsTable);
        });

        this.background(ninePatchDrawable);
    }

    private void addButton(String texture, Runnable onClick) {

        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();

        Texture upTexture = new Texture(texture);
        upTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        Texture downTexture = new Texture(texture.replace(".png", "_hover.png"));
        downTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        TextureRegionDrawable upDrawable = new TextureRegionDrawable(new TextureRegion(upTexture));
        TextureRegionDrawable downDrawable = new TextureRegionDrawable(new TextureRegion(downTexture));

        buttonStyle.up = upDrawable;
        buttonStyle.over = downDrawable;

        Button button = new Button(buttonStyle);

        button.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                buildingsTable.clear();
                onClick.run();
                SoundUtil.playSound("other/click.mp3");
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        this.add(button).expandX();
    }
}
