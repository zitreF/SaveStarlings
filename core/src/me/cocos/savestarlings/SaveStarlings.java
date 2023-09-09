package me.cocos.savestarlings;

import com.badlogic.gdx.Game;
import me.cocos.savestarlings.screen.GameScreen;
import me.cocos.savestarlings.service.AssetService;

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
