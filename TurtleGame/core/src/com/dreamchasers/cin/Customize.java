package com.dreamchasers.cin;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Created by root on 6/12/16.
 */
public class Customize extends ScreenAdapter {
    private Game game;
    private Texture cannonTexture;
    private float cannonX;
    private float cannonY;
    private Texture boomerangTexture;
    private float boomerangX;
    private float boomerangY;
    private Stage stage;

    private ImageButton.ImageButtonStyle cannonButtonStyle;
    private ImageButton cannonImageButton;
    Skin cannonSkin;
    BitmapFont cannonFont;

    private ImageButton.ImageButtonStyle boomerangButtonStyle;
    private ImageButton boomerangImageButton;
    private Skin boomerangSkin;
    private BitmapFont boomerangFont;

    private ShapeRenderer shapeRenderer;

    Customize(Game game){
        this.game = game;

        shapeRenderer = new ShapeRenderer();
        cannonTexture = AssetManager.cannon_right;
        cannonX = 0;
        cannonY = World.HEIGHT - cannonTexture.getHeight();

        boomerangTexture = AssetManager.boomerang;
        boomerangX = boomerangTexture.getWidth() * 2;
        boomerangY = World.HEIGHT - boomerangTexture.getHeight();

        stage = new Stage(new ScreenViewport());

        cannonFont = new BitmapFont();
        cannonSkin = new Skin();
        cannonSkin.add("default", cannonFont);
        cannonSkin.add("background", cannonTexture);

        cannonButtonStyle = new ImageButton.ImageButtonStyle();
        cannonButtonStyle.up = cannonSkin.newDrawable("background", Color.RED);
        cannonButtonStyle.down = cannonSkin.newDrawable("background", Color.BROWN);
        cannonButtonStyle.checked = cannonSkin.newDrawable("background", Color.DARK_GRAY);
        cannonButtonStyle.over = cannonSkin.newDrawable("background", Color.BROWN);
        cannonSkin.add("default", cannonButtonStyle);
        cannonImageButton = new ImageButton(cannonButtonStyle);

        boomerangFont = new BitmapFont();
        boomerangSkin = new Skin();
        boomerangSkin.add("default", boomerangFont);
        boomerangSkin.add("background", boomerangTexture);

        boomerangButtonStyle = new ImageButton.ImageButtonStyle();
        boomerangButtonStyle.up = boomerangSkin.newDrawable("background", Color.RED);
        boomerangButtonStyle.down = boomerangSkin.newDrawable("background", Color.BLUE);
        boomerangButtonStyle.checked = boomerangSkin.newDrawable("background", Color.DARK_GRAY);
        boomerangButtonStyle.over = boomerangSkin.newDrawable("background", Color.BLUE);
        boomerangSkin.add("default", boomerangButtonStyle);
        boomerangImageButton = new ImageButton(boomerangButtonStyle);
        boomerangImageButton = new ImageButton(boomerangButtonStyle);

        cannonImageButton.setPosition(cannonX, cannonY);
        boomerangImageButton.setPosition(boomerangX, boomerangY);
        stage.addActor(cannonImageButton);
        stage.addActor(boomerangImageButton);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE) || Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            game.setScreen(new MainScreen(game));
        }

        Gdx.gl.glClearColor(0, 0, .3f, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        int rectSize = 250;
        /*shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.LIME);
        shapeRenderer.rect(0, World.HEIGHT-rectSize, rectSize, rectSize);
        shapeRenderer.end();*/

        stage.act();
        stage.draw();
        update(delta);
    }

    private void update(float delta) {
        if(cannonImageButton.isChecked()) {
            World.current_weapon = new Cannon();
        }else if(boomerangImageButton.isChecked()) {
            World.current_weapon = new Boomerang();
        }
    }
}
