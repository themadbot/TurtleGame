package com.dreamchasers.cin;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class Main extends Game {

	@Override
	public void create () {
        Gdx.input.setCatchBackKey(true);
        setScreen(new MainScreen(this));
	}

    @Override
    public void dispose() {
        super.dispose();
        AssetManager.dispose();
    }
}
