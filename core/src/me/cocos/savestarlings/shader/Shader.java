package me.cocos.savestarlings.shader;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public abstract class Shader {

    protected final ShaderProgram shaderProgram;
    private boolean enabled;

    public Shader(ShaderProgram shaderProgram) {
        this.shaderProgram = shaderProgram;
        this.enabled = false;
    }

    protected abstract void bind();

    public abstract void render();

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public ShaderProgram getShaderProgram() {
        return this.shaderProgram;
    }
}
