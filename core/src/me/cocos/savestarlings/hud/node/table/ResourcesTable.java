package me.cocos.savestarlings.hud.node.table;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import me.cocos.savestarlings.builder.FontBuilder;
import me.cocos.savestarlings.hud.progressbar.GLProgressBar;
import me.cocos.savestarlings.hud.progressbar.impl.ResourcesProgressBar;
import me.cocos.savestarlings.util.FormatUtils;

public class ResourcesTable extends Table {

    private final ResourcesProgressBar progressBar;


    public ResourcesTable(String texturePath, float max, float value, int width, int height) {
        Texture texture = new Texture(texturePath);
        Image image = new Image(texture);
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = FontBuilder.from("ui/font/glfont.ttf")
                .filter(Texture.TextureFilter.Linear)
                .size(12)
                .color(Color.valueOf("#FAD46E"))
                .borderColor(Color.valueOf("#B7850B"))
                .borderWidth(2f)
                .build();
        this.progressBar = new ResourcesProgressBar(max, value, width, height);
        Label valueLabel = new Label(FormatUtils.formatNumber(value), labelStyle);

        Stack stack = new Stack();
        stack.add(progressBar);
        Container<Label> valueContainer = new Container<>(valueLabel);
        stack.add(valueContainer.center());

        this.add(image).size(height, height);
        this.add(stack).padLeft(5f).size(width, height);
    }
}
