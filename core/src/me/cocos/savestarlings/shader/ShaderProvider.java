package me.cocos.savestarlings.shader;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import me.cocos.savestarlings.shader.impl.CloudShader;

public class ShaderProvider {

    private final SpriteBatch spriteBatch;
    private final Array<Shader> shaders;

    public ShaderProvider() {
        this.spriteBatch = new SpriteBatch();
        this.shaders = Array.with(
                new CloudShader(spriteBatch)
        );
    }

    public void render() {
        for (Shader shader : this.shaders) {
            if (!shader.isEnabled()) {
                continue;
            }
        }
    }
}
