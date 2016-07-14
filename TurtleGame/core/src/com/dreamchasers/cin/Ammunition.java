package com.dreamchasers.cin;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by root on 6/9/16.
 */
public abstract class Ammunition {
    public Texture texture;
    public boolean visible = false;
    public boolean right = true;

    public Vector2 position;

    public float getX() { return position.x; }

    public float getY() { return position.y; }

    public void setVisible(boolean active) {
        this.visible = active;
    }

    public boolean isVisible() { return visible; }

    public abstract void setTarget(float x, float y);

    public void setRight(boolean right) { this.right = right; }

    public boolean isRight() { return right; }

    public abstract void update(float delta);

    public Texture getTexture() { return texture; }

    public abstract void collisionDetection(Enemy enemies[]);
}
