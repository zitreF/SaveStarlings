package me.cocos.savestarlings.hud.node.scene2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class TypingLabel extends Label {

    private float totalTime;
    private final String typingText;

    public TypingLabel(String typingText, LabelStyle style) {
        super("", style);
        this.typingText = typingText;
        this.totalTime = 1f;
    }

    public void startTypingAnimation() {
        Timer.schedule(new Task() {
            private float timeElapsed = 0f;

            @Override
            public void run() {
                this.timeElapsed += Gdx.graphics.getDeltaTime();
                float progress = Math.min(1f, timeElapsed / totalTime);
                int endIndex = (int) (typingText.length() * progress);
                setText(typingText.subSequence(0, endIndex));

                if (progress >= 1f) {
                    setText(typingText);
                    this.cancel();
                }
            }
        }, 0f, 1f / 30f);
    }

    public float getTotalTime() {
        return this.totalTime;
    }

    public void setTotalTime(float totalTime) {
        this.totalTime = totalTime;
    }

    public String getTypingText() {
        return this.typingText;
    }
}
