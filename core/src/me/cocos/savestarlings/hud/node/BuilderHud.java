package me.cocos.savestarlings.hud.node;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import me.cocos.savestarlings.hud.Hud;
import me.cocos.savestarlings.hud.node.table.BuildingsTable;
import me.cocos.savestarlings.hud.node.table.MenuTable;

public class BuilderHud extends Table {

    private final Hud hud;

    public BuilderHud(Hud hud) {
        this.hud = hud;

        BuildingsTable buildingsTable = new BuildingsTable(this);

        MenuTable menuTable = new MenuTable(this, buildingsTable);

        this.add(menuTable).left().padLeft(30f).row();

        this.add(buildingsTable);

        this.setSize(800f, 250f);

        this.setPosition(Gdx.graphics.getWidth() / 2f - this.getWidth() / 2f, this.getY() - 50f);
    }

    public Pixmap createRoundedRectanglePixmap(int width, int height, int cornerRadius, Color color) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);

        pixmap.setColor(0, 0, 0, 0);
        pixmap.fill();

        pixmap.setColor(color.r, color.g, color.b, 1f);

        int diameter = cornerRadius * 2;

        pixmap.fillCircle(cornerRadius, cornerRadius, cornerRadius);
        pixmap.fillCircle(width - cornerRadius - 1, cornerRadius, cornerRadius);

        pixmap.fillRectangle(cornerRadius, 0, width - diameter, height);
        pixmap.fillRectangle(0, cornerRadius, width, height - cornerRadius);

        return pixmap;
    }

    public Hud getHud() {
        return this.hud;
    }
}