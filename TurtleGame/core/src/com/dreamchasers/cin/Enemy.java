package com.dreamchasers.cin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by root on 6/4/16.
 */
public class Enemy {
    private Texture enemy;
    private float enemy_width;
    private float enemy_height;
    private float enemy_dead;

    private float SPEED = (float) (Math.random() * 44 + 1);

    private float coordinateX;
    private float coordinateY;

    private boolean visible;
    private boolean alive;
    private boolean hit;
    private int health = 2;
    private boolean wasSpeedIncreased = false;

    private static final int FRAME_COLS = 6;
    private static final int FRAME_ROWS = 0;

    Animation animation;
    TextureRegion[] textureFrames;
    TextureRegion currentFrame;

    float stateTime;

    Enemy() {
        alive = true;
        visible = false;
        hit = false;

        coordinateX = (float) (Math.random() * World.WIDTH);
        if (coordinateX < 0) coordinateX += enemy_width;
        else if (coordinateX > World.WIDTH) coordinateX -= enemy_width;

        coordinateY = (float) (Math.random() * World.HEIGHT + World.HEIGHT);

        enemy = AssetManager.enemy;
        enemy_width = enemy.getWidth()/6;
        enemy_height = enemy.getHeight();
        enemy_dead = 0 - enemy_height;

        TextureRegion[][] tmp = TextureRegion.split(enemy, enemy.getWidth() / FRAME_COLS, enemy.getHeight());
        textureFrames = new TextureRegion[FRAME_COLS];
        animation = new Animation(0.075f, textureFrames);
        stateTime = 0f;
        int index = 0;
            for (int j = 0; j < FRAME_COLS; j++) {
                textureFrames[index++] = tmp[0][j];
        }
    }

    public void update(float delta) {
        if(alive) {
            if(hit && !wasSpeedIncreased) {
                SPEED *= 2;
                wasSpeedIncreased = true;
            }
            stateTime += delta;
            currentFrame = animation.getKeyFrame(stateTime, true);

            coordinateY = coordinateY - (SPEED * delta);

            if (coordinateY <= World.HEIGHT + enemy_height) {
                visible = true;
            } else {
                visible = false;
            }

            if(getY() + enemy_height <= 0) {
                isDead();
            }
        }
    }

    public void isDead() {
        alive = false;
        visible = false;
    }

    public float getX() {
        return coordinateX;
    }

    public float getY() {
        return coordinateY;
    }

    public TextureRegion getTexture() {
        return currentFrame;
    }

    public boolean isVisible() { return visible; }

    public boolean isHit() { return hit; }

    public void decreaseHealth(int minushealth) {
        hit = true;

        this.health = this.health - minushealth;
        if(health == 0 || health < 0) {
            isDead();
        }
    }

    public int getHealth() { return health; }

    public float getEnemyWidth() { return enemy_width; }

    public float getEnemyHeight() { return enemy_height; }

    public boolean isAlive() { return alive; }
}
