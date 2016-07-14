package com.dreamchasers.cin;
// this is  a test -- remove when found
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by root on 5/30/16
 */
public class Hero {
    private float coordinateY;
    private float coordinateX;

    private Sprite turtle;
    private boolean flippedRight;
    private boolean flippedLeft;

    Hero() {
        coordinateX = 0;
        coordinateY = 0;

        flippedLeft = false;
        flippedRight = true;

        turtle = new Sprite(AssetManager.turtle_right);
        coordinateX = Gdx.graphics.getWidth()/2 - turtle.getWidth();
    }

    public void update(float delta) {
        coordinateY = 0;
        turtle.setPosition(coordinateX, coordinateY);
        Vector2 touchPosition = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        if(touchPosition.x > World.WIDTH/2) {
            flippedLeft = false;
            if(!flippedRight) {
                turtle.flip(true, false);
                flippedRight = true;
            }
        } else if(touchPosition.x < World.WIDTH/2) {
            flippedRight = false;
            if(!flippedLeft) {
                turtle.flip(true, false);
                flippedLeft = true;
            }
        }

        if(coordinateX < 0) { coordinateX = 0; }
    }

    public float getX() {
        return coordinateX;
    }

    public float getY() {
        return coordinateY;
    }

    public Sprite getSprite() {
        return turtle;
    }
}
