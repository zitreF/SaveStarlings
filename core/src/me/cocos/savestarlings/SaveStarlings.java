package me.cocos.savestarlings;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import me.cocos.savestarlings.screen.GameScreen;
import me.cocos.savestarlings.service.AssetService;
import me.cocos.savestarlings.service.EntityService;

public class SaveStarlings extends Game {

	private GameScreen gameScreen;
	
	@Override
	public void create() {
		AssetService.load();
		while (!AssetService.getAssetManager().isFinished()) {
			AssetService.getAssetManager().update();
		}
		this.gameScreen = new GameScreen();
		this.setScreen(gameScreen);
	}

	@Override
	public void render() {
		super.render();
	}
	
	@Override
	public void dispose() {
		gameScreen.dispose();
	}
}
