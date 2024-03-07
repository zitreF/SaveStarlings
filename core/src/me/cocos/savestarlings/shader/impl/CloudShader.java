package me.cocos.savestarlings.shader.impl;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import me.cocos.savestarlings.shader.Shader;

public class CloudShader extends Shader {

    private final SpriteBatch batch;

    public CloudShader(SpriteBatch batch) {
        super(new ShaderProgram(batch.getShader().getVertexShaderSource(), ""));
        this.batch = batch;
    }

    @Override
    protected void bind() {
        this.shaderProgram.bind();
    }

    @Override
    public void render() {
        batch.setShader(this.shaderProgram);
        batch.setShader(null);
    }
}
