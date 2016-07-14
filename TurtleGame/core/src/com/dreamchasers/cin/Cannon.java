package com.dreamchasers.cin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by root on 6/6/16
 */
public class Cannon extends Weapon {
    private Sprite sprite;
    private float rotation;

    private boolean flippedLeft;
    private boolean flippedRight;

    private CannonBullet[] cannonBullets;

    Cannon() {
        flippedLeft = false;
        flippedRight = true;

        rotation = 30;
        sprite = new Sprite(AssetManager.cannon_right);
        super.sprite = sprite;
        width = super.sprite.getWidth();
        height = sprite.getHeight();
        coordinateX = Gdx.graphics.getWidth()/2 - sprite.getWidth();
        coordinateY = sprite.getHeight()+10;
        sprite.setPosition(getX(), getY());
        sprite.setRotation(rotation);

        super.ammunition = new CannonBullet[20];
        for(int i=0; i < super.ammunition.length; i++) {
            super.ammunition[i] = new CannonBullet();
        }
    }

    public void update(float delta) {
        Vector2 touchPosition = new Vector2(Gdx.input.getX(), Gdx.input.getY());

        if(touchPosition.x > World.WIDTH/2) {
            coordinateX = Gdx.graphics.getWidth()/2 - sprite.getWidth()/2;

            flippedLeft = false;
            if(!flippedRight) {
                float currentRotation = sprite.getRotation();
                sprite.rotate(-sprite.getRotation()+30);
                sprite.flip(true, false);
                flippedRight = true;
            }
        } else if(touchPosition.x < World.WIDTH/2) {
            coordinateX = Gdx.graphics.getWidth()/2 - sprite.getWidth();

            flippedRight = false;
            if(!flippedLeft) {
                sprite.rotate(-sprite.getRotation()-30);
                sprite.flip(true, false);
                flippedLeft = true;
            }
        }
    }

    public float getX() {
        return coordinateX;
    }

    public float getY() {
        return coordinateY;
    }

    public float getRotation() { return rotation; }

    public void instantiateBullets(int i) {
    }

    /**
     * Created by root on 6/9/16
     */
    public class CannonBullet extends Ammunition {
        private Vector2 velocity;
        private float speed;

        CannonBullet() {
            position = new Vector2();
            velocity = new Vector2();
            texture = AssetManager.cannon_bullet;
            position.x = Cannon.this.getX();
            position.y = Cannon.this.getY() + Cannon.this.sprite.getHeight()/2 + Cannon.this.getRotation();
            velocity.x = 0;
            velocity.y = 0;
            speed = 1.2f;
        }

        public void update(float delta) {
            if(super.isVisible()) {
                if (getX() < 0 - texture.getWidth() || getX() > World.WIDTH + texture.getWidth() ||
                        getY() < 0 - texture.getHeight() || getY() > World.HEIGHT + texture.getHeight()) {

                        reset();
                    }

                getPosition().add(getVelocity().x * delta * speed, getVelocity().y * delta * speed);
            }
        }

        public void collisionDetection(Enemy enemies[]) {
            for(Enemy enemy : enemies) {
                if (enemy.isVisible()) {
                    if (getX() >= enemy.getX() && getX() <= enemy.getX()+ enemy.getEnemyWidth() &&
                            getY() >= enemy.getY() && getY() <= enemy.getY() + enemy.getEnemyHeight()) {

                        enemy.decreaseHealth(1);
                        reset();
                    }
                }
            }
        }

        public void setTarget(float targetX, float targetY) {
            AssetManager.cannon_bullet_sound.play(1f);
            velocity.set(targetX - position.x, targetY - position.y);
            setVisible(true);
        }

        public void reset() {
            super.setVisible(false);
            position.x = Cannon.this.getX();
            position.y = Cannon.this.getY() + Cannon.this.sprite.getHeight()/2 + Cannon.this.getRotation();
        }

        public Vector2 getPosition() { return position; }

        public Vector2 getVelocity() { return velocity; }
    }

}