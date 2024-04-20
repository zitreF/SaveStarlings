package me.cocos.savestarlings;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL32;
import me.cocos.savestarlings.screen.MenuScreen;

public class SaveStarlings extends Game {

	private MenuScreen menuScreen;

	@Override
	public void create() {
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
		System.exit(0);
	}
}
