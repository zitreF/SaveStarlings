package me.cocos.savestarlings;

import com.badlogic.gdx.Game;
import me.cocos.savestarlings.screen.MenuScreen;

public class SaveStarlings extends Game {

	private MenuScreen menuScreen;

	@Override
	public void create() {
		//this.gameScreen = new GameScreen();
		this.menuScreen = new MenuScreen(this);
		this.setScreen(menuScreen);
	}

	@Override
	public void render() {
		super.render();
	}
	
	@Override
	public void dispose() {
		menuScreen.dispose();
	}
}
