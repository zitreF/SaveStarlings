package me.cocos.savestarlings.hud.node.table;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import me.cocos.savestarlings.builder.FontBuilder;
import me.cocos.savestarlings.hud.progressbar.GLProgressBar;
import me.cocos.savestarlings.service.AssetService;
import me.cocos.savestarlings.util.FormatUtils;

public class ResourcesTable extends Table {

    private final GLProgressBar progressBar;


    public ResourcesTable(String texturePath, String color, String fontColor, String borderColor, float max, float value, int width, int height) {
        Texture texture = AssetService.getAsset(texturePath);
        Image image = new Image(texture);
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = FontBuilder.from("ui/font/glfont.ttf")
                .filter(Texture.TextureFilter.Linear)
                .size(12)
                .color(Color.valueOf(fontColor))
                .borderColor(Color.valueOf(borderColor))
                .borderWidth(2f)
                .build();
        this.progressBar = new GLProgressBar(color, max, value, width, height);
        Label valueLabel = new Label(FormatUtils.formatNumber(value), labelStyle);

        Stack stack = new Stack();
        stack.add(progressBar);
        Container<Label> valueContainer = new Container<>(valueLabel);
        stack.add(valueContainer.center());
        this.add(image).size(height, height);
        this.add(stack).size(width, height);
    }
}
