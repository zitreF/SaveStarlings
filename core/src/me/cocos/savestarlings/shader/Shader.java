package me.cocos.savestarlings.shader;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public abstract class Shader {

    protected final ShaderProgram shader;
    private boolean enabled;

    public Shader(ShaderProgram shaderProgram) {
        this.shader = shaderProgram;
        if (!shaderProgram.isCompiled()) {
            String errorLog = shaderProgram.getLog();
            System.out.println("Shader compilation error: " + errorLog);
        }
        this.enabled = false;
    }

    public abstract void bind(float delta);

    public abstract void render();


    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public ShaderProgram getShaderProgram() {
        return this.shader;
    }
}
