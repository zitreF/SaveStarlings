package me.cocos.savestarlings.hud.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.profiling.GLProfiler;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import me.cocos.savestarlings.builder.FontBuilder;
import me.cocos.savestarlings.util.BinaryUtil;

public class DebugHud extends Table {

    private final Label debug;
    private static GLProfiler glProfiler;

    public DebugHud() {
//        glProfiler = new GLProfiler(Gdx.graphics);
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = FontBuilder.from("ui/font/Gabarito-Medium.ttf")
                .color(Color.WHITE)
                .size(20)
                .borderWidth(1f)
                .borderColor(Color.BLACK)
                .colorMarkup(true)
                .filter(Texture.TextureFilter.Linear)
                .build();
        this.debug = new Label("DEBUG", labelStyle);
        this.add(debug).top().left();
    }

    public void update() {
        StringBuilder statsBuilder = new StringBuilder();
        statsBuilder.append("[WHITE]FPS: [GREEN]").append(Gdx.graphics.getFramesPerSecond()).append("\n");
        statsBuilder.append("[WHITE]RAM Usage: [#d62b47]")
                .append(BinaryUtil.bytesToGigabytes(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()))
                .append("GB [WHITE]/ [GREEN]")
                .append(BinaryUtil.bytesToGigabytes(Runtime.getRuntime().maxMemory()))
                .append("GB\n");
        statsBuilder.append("[WHITE]Available CPU Cores: [GREEN]").append(Runtime.getRuntime().availableProcessors()).append("\n");
        if (glProfiler != null) {
            statsBuilder.append("[WHITE]Draw Calls: [GREEN]").append(glProfiler.getDrawCalls()).append("\n");
            statsBuilder.append("[WHITE]Total Vertex Count: [GREEN]").append(glProfiler.getVertexCount().total);
        }
        this.debug.setText(statsBuilder.toString());
    }

    public static GLProfiler getGlProfiler() {
        return glProfiler;
    }
}
