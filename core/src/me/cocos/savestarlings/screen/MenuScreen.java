package me.cocos.savestarlings.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import me.cocos.savestarlings.hud.menu.AnimatedActor;

public class MenuScreen implements Screen {

    private final Array<AnimatedActor> animatedActors;
    private final Stage stage;

    public MenuScreen() {
        this.stage = new Stage(new FitViewport(1600f, 900f));
        this.animatedActors = new Array<>();
        Array<Texture> textures = new Array<>();
        for (int i = 1; i < 31; i++) {
            textures.add(new Texture("menu/spiral/" + i + ".png"));
        }
        AnimatedActor animationActor = new AnimatedActor(textures.toArray(Texture.class));

        animationActor.setX(Gdx.graphics.getWidth() / 2f - 700f);
        animationActor.setY(Gdx.graphics.getHeight() / 2f - 700f);

        animationActor.setWidth(1400f);
        animationActor.setHeight(1400f);

        stage.addActor(animationActor);

        animatedActors.add(animationActor);


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        this.stage.act(delta);
        this.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        this.stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
