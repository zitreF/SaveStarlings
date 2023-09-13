package me.cocos.savestarlings.hud.menu;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.graphics.Texture;

public class AnimatedActor extends Actor {
    private Texture[] frames;
    private float stateTime;
    private final float speed;
    private int currentFrame;

    public AnimatedActor(Texture[] frames, float speed) {
        this.frames = frames;
        this.speed = speed;
        this.stateTime = 0;
        this.currentFrame = 0;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        this.stateTime += delta;

        this.currentFrame = (int) (stateTime / speed) % frames.length;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.draw(frames[currentFrame], this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }
}
