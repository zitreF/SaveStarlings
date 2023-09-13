package me.cocos.savestarlings.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import me.cocos.savestarlings.SaveStarlings;
import me.cocos.savestarlings.hud.menu.AnimatedActor;
import me.cocos.savestarlings.service.AssetService;

public class MenuScreen implements Screen {

    private final Stage stage;
    private final Array<TextureRegionDrawable> progressTextures;
    private final Image progressBar;
    private float progress;
    private final SaveStarlings saveStarlings;

    public MenuScreen(SaveStarlings saveStarlings) {
        this.saveStarlings = saveStarlings;
        this.stage = new Stage(new FitViewport(1600f, 900f));
        Array<Texture> spiralTextures = new Array<>();
        for (int i = 1; i < 31; i++) {
            Texture texture = new Texture("menu/spiral/" + i + ".png");
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            spiralTextures.add(texture);
        }
        AnimatedActor spiralAnimation = new AnimatedActor(spiralTextures.toArray(Texture.class), 0.05f);

        spiralAnimation.setWidth(1400f);
        spiralAnimation.setHeight(1400f);

        spiralAnimation.setX(Gdx.graphics.getWidth() / 2f - 700f);
        spiralAnimation.setY(Gdx.graphics.getHeight() / 2f - 700f);

        stage.addActor(spiralAnimation);

        Array<Texture> unitTextures = new Array<>();
        for (int i = 1; i < 50; i++) {
            Texture texture = new Texture("menu/unit/" + i + ".png");
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            unitTextures.add(texture);
        }
        AnimatedActor unitAnimation = new AnimatedActor(unitTextures.toArray(Texture.class), 0.05f);

        unitAnimation.setSize(600f, 500f);

        unitAnimation.setX(Gdx.graphics.getWidth() / 2f - 300f);
        unitAnimation.setY(Gdx.graphics.getHeight() / 2f - 150f);

        stage.addActor(unitAnimation);

        Array<Texture> logoTextures = new Array<>();
        for (int i = 1; i < 182; i++) {
            Texture texture = new Texture("menu/logo/" + i + ".png");
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            logoTextures.add(texture);
        }
        AnimatedActor logoAnimation = new AnimatedActor(logoTextures.toArray(Texture.class), 0.025f);

        logoAnimation.setWidth(800f);
        logoAnimation.setHeight(logoAnimation.getWidth() / 6f);

        logoAnimation.setX(Gdx.graphics.getWidth() / 2f - (logoAnimation.getWidth() / 2f) + logoAnimation.getWidth() / 7f);
        logoAnimation.setY(Gdx.graphics.getHeight() / 2f - (logoAnimation.getHeight() / 2f) - 125f);

        stage.addActor(logoAnimation);

        this.progressTextures = new Array<>();
        for (int i = 1; i < 51; i++) {
            Texture texture = new Texture("menu/progressbar/" + i + ".png");
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            progressTextures.add(new TextureRegionDrawable(texture));
        }
        this.progressBar = new Image(progressTextures.get(0));

        progressBar.setSize(800f, 150f);

        progressBar.setX(Gdx.graphics.getWidth() / 2f - (progressBar.getWidth() / 2f) + progressBar.getWidth() / 8f);
        progressBar.setY(Gdx.graphics.getHeight() / 2f - 300f);

        stage.addActor(progressBar);

        Label.LabelStyle topicStyle = new Label.LabelStyle();
        FreeTypeFontGenerator freeTypeFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("ui/font/glfont.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 18;

        topicStyle.font = freeTypeFontGenerator.generateFont(parameter);
        topicStyle.fontColor = Color.WHITE;

        Label topic = new Label("Coming soon :)", topicStyle);

        this.stage.addActor(topic);
        AssetService.load();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        if (!AssetService.getAssetManager().isFinished()) {
            AssetService.getAssetManager().update();
            this.progress = AssetService.getAssetManager().getProgress();
            progressBar.setDrawable(this.progressTextures.get(Math.min(49, (int) progress * 50)));
        } else {
            this.saveStarlings.setScreen(new GameScreen());
            return;
        }
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
