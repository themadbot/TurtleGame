package com.dreamchasers.cin;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Created by root on 6/12/16.
 */
public class MainScreen extends ScreenAdapter {
    BitmapFont font;
    Skin skin;
    Pixmap pixmap;
    TextButton.TextButtonStyle textButtonStyle;
    Stage stage;
    TextButton start;
    TextButton customize;
    Game game;

    MainScreen(Game game) {
        this.game = game;

        //Create a font
        font = new BitmapFont();
        skin = new Skin();
        skin.add("default", font);

        //Create a texture
        pixmap = new Pixmap(100, 30, Pixmap.Format.RGB888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("background", new Texture(pixmap));

        //Create a button style
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("background", Color.RED);
        textButtonStyle.down = skin.newDrawable("background", Color.BLUE);
        textButtonStyle.checked = skin.newDrawable("background", Color.DARK_GRAY);
        textButtonStyle.over = skin.newDrawable("background", Color.BLUE
        );
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);// Make the stage consume events

        start = new TextButton("Start", skin); // Use the initialized skin
        customize = new TextButton("Customize", skin);

        float startX = -10;
        float startY = World.HEIGHT-start.getHeight()-10;

        start.setPosition(startX, startY);
        customize.setPosition(startX, startY - customize.getHeight()*2);
        stage.addActor(start);
        stage.addActor(customize);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

        if(start.isChecked()) {
            if(World.current_weapon != null) {
                AssetManager.Load();
                game.setScreen(new Classic(game, World.current_weapon));
                dispose();
            } else {
                AssetManager.Load();
                game.setScreen(new Classic(game, new Cannon()));
                dispose();
            }
        }

        if(customize.isChecked()) {
            AssetManager.Load();
            game.setScreen(new Customize(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        stage.getViewport().update(width, height, false);
    }

    @Override
    public void dispose() {
        super.dispose();
        font.dispose();
        pixmap.dispose();
        stage.dispose();
        skin.dispose();
    }
}
