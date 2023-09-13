package me.cocos.savestarlings;

import com.badlogic.gdx.Game;
import me.cocos.savestarlings.screen.GameScreen;
import me.cocos.savestarlings.screen.MenuScreen;
import me.cocos.savestarlings.service.AssetService;

public class SaveStarlings extends Game {

	private GameScreen gameScreen;
	
	@Override
	public void create() {
		//this.gameScreen = new GameScreen();
		MenuScreen menuScreen = new MenuScreen(this);
		this.setScreen(menuScreen);
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
