package me.cocos.savestarlings.shader.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import me.cocos.savestarlings.shader.Shader;

public class CloudShader extends Shader {

    private final SpriteBatch batch;
    private final Texture texture;
    private float time;

    public CloudShader(SpriteBatch batch) {
        super(new ShaderProgram(batch.getShader().getVertexShaderSource(), Gdx.files.internal("shaders/screen/cloud.frag").readString()));
        this.batch = batch;
        this.texture = new Texture("shaders/textures/cloud.png");
        this.time = 0f;
    }

    @Override
    public void bind(float delta) {
        this.shader.bind();
        this.time += delta;
        shader.setUniformf("u_amount", 10);
        shader.setUniformf("u_speed", 0.5f);
        shader.setUniformf("u_time", time);
    }

    @Override
    public void render() {
        batch.setShader(this.shader);
        batch.begin();
        batch.draw(texture, 0, 250, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
        batch.setShader(null);
    }
}
