package me.cocos.savestarlings.hud.menu;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.graphics.Texture;

public class AnimatedActor extends Actor {
    private Texture[] frames;
    private float stateTime;
    private int currentFrame;

    public AnimatedActor(Texture[] frames) {
        this.frames = frames;
        this.stateTime = 0;
        this.currentFrame = 0;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        stateTime += delta;

        currentFrame = (int) (stateTime / 0.05f) % frames.length;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.draw(frames[currentFrame], this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }
}
