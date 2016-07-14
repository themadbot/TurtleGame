package com.dreamchasers.cin;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by root on 6/9/16.
 */
public abstract class Weapon {
    public Sprite sprite;
    public float coordinateX;
    public float coordinateY;
    public float width;
    public float height;
    public Ammunition[] ammunition;

    Weapon() {
    }



    public float getX() { return coordinateX; }

    public float getY() { return coordinateY; }

    public float getWidth() { return width; }

    public float getHeight() { return  height; }

    public Sprite getSprite() { return sprite; }

    public Ammunition[] getBullets() { return ammunition; }

    public abstract void instantiateBullets(int i);

    public abstract void update(float delta);
}
