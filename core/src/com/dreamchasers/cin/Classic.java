package com.dreamchasers.cin;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by root on 5/30/16
 */
public class Classic extends ScreenAdapter {
    private Viewport viewport;
    private float gameWidth = 480;
    private float gameHeight = 320;

    private SpriteBatch batch;
    private Hero hero;

    public static OrthographicCamera camera;

    private int numberOfEnemies;
    private Enemy enemies[];

    private BitmapFont bitmapFont;

    private MyInputProcessor myInputProcessor;

    private Game game;
    private Weapon weapon;
    public static boolean running;

    Classic(Game game, Weapon weapon) {
        running = false;
        this.game = game;
        this.weapon = weapon;

        batch = new SpriteBatch();
        bitmapFont = new BitmapFont();

        camera = new OrthographicCamera(World.WIDTH, World.HEIGHT);
        camera.setToOrtho(false, World.WIDTH, World.HEIGHT);
        viewport = new ScreenViewport(camera);
        camera.position.set(camera.viewportWidth/2, camera.viewportHeight/3, 0);
        camera.update();
        viewport.apply();

        batch = new SpriteBatch();
        hero = new Hero();

        numberOfEnemies = 20;
        enemies = new Enemy[numberOfEnemies];

        for(int i = 0; i < numberOfEnemies; i++) {
            enemies[i] = new Enemy();
        }

        GlobalWeapon.globalWeapon = weapon;
        GlobalWeapon.globalAmmo = weapon.getBullets();
        GlobalWeapon.globalCount = weapon.getBullets().length;
        myInputProcessor = new MyInputProcessor(camera);
        Gdx.input.setInputProcessor(myInputProcessor);
        running = true;
    }


    @Override
    public void render(float delta) {
        super.render(delta);

        if(!running) {
            batch.begin();
            bitmapFont.draw(batch, "Paused", Gdx.graphics.getWidth()/2-bitmapFont.getSpaceWidth(), Gdx.graphics.getHeight()/2);
            batch.end();
        }

        if(running) {
            if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE) || Gdx.input.isKeyPressed(Input.Keys.BACK)) {
                game.setScreen(new MainScreen(game));
                dispose();
            }

            update(delta);

            camera.update();
            batch.setProjectionMatrix(camera.combined);
            Gdx.gl.glClearColor(1, .5f, .5f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            batch.begin();

            String ammoInfo = Integer.toString(GlobalWeapon.globalAmmo.length);
            bitmapFont.draw(batch, "infinite", 2, 20);
            String worldInfo = "Width: " + Float.toString(World.WIDTH) + "Height: " + Float.toString(World.WIDTH);
            //bitmapFont.draw(batch, worldInfo, World.WIDTH-300, 20);
            //Gdx.app.log("GdxTag", worldInfo);

            for (Enemy enemy : enemies) {
                if (enemy.isVisible()) {
                    if (enemy.isHit()) {
                        batch.setColor(Color.RED);
                    }
                    batch.draw(enemy.getTexture(), enemy.getX(), enemy.getY());
                    batch.setColor(1, 1, 1, 1);
                }
            }

            hero.getSprite().draw(batch);

            weapon.getSprite().draw(batch);
            for (Ammunition bullet : GlobalWeapon.globalAmmo) {
                if (bullet.isVisible()) {
                    batch.draw(bullet.getTexture(), bullet.getX(), bullet.getY());
                }
            }
            batch.end();
            BoxWorld.world.step(1/60f, 6, 2);
        }
    }

    private void update(float delta) {
        hero.update(delta);
        weapon.update(delta);
        for(Ammunition bullet : GlobalWeapon.globalAmmo) {
            bullet.update(delta);
        }

        for(Enemy enemy : enemies) {
            enemy.update(delta);

            for (Ammunition bullet : GlobalWeapon.globalAmmo) {
                if(bullet.isVisible()) {
                    bullet.collisionDetection(enemies);
                }
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        World.WIDTH = width;
        World.HEIGHT = height;
        viewport.update(width, height);
        hero.getSprite().setPosition(0, 0);
    }

    @Override
    public void dispose() {
        super.dispose();
        AssetManager.dispose();
    }
}
