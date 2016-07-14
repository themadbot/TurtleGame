package com.dreamchasers.cin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by root on 6/21/16.
 */
public class Boomerang extends Weapon {
    Boomerang() {
        Sprite sprite = new Sprite(AssetManager.boomerang);
        super.sprite = sprite;
        coordinateX = Gdx.graphics.getWidth()/2 - getSprite().getWidth();
        coordinateY = getSprite().getHeight()/2;
    }
    @Override
    public void instantiateBullets(int i) {
        ammunition = new BoomerangBullet[i];
        for(int a=0; a < i; a++) {
            ammunition[a] = new BoomerangBullet();
        }
    }

    @Override
    public void update(float delta) {

    }

    public class BoomerangBullet extends Ammunition {
        private Vector2 velocity;
        private float speed;

        private boolean visible = false;

        BoomerangBullet() {
            position = new Vector2();
            velocity = new Vector2();
            texture = AssetManager.boomerang;
            position.x = Boomerang.this.getX();
            position.y = 0 + Boomerang.this.getSprite().getHeight() - 1;
            velocity.x = 0;
            velocity.y = 0;
            speed = 2f;
        }

        public void update(float delta) {
            if(super.isVisible()) {
                    if (getX() < 0 - texture.getWidth() || getX() > World.WIDTH + texture.getWidth() ||
                            getY() < 0 - texture.getHeight() || getY() > World.HEIGHT + texture.getHeight()) {
                        reset();
                }
                if (!isVisible()) {

                    if (isRight()) {
                        position.x = Boomerang.this.getX() + Boomerang.this.getSprite().getWidth()-texture.getWidth();
                    } else {
                        position.x = Boomerang.this.getX();
                    }

                    //shootToward(finalX, World.HEIGHT - finalY);
                    setVisible(true);
                }

                getPosition().add(getVelocity().x * delta, getVelocity().y * delta);
            }
        }

        public void collisionDetection(Enemy enemies[]) {
            for(int i=0; i < enemies.length; i++) {
                if (enemies[i].isVisible()) {
                    if (getX() >= enemies[i].getX() && getX() <= enemies[i].getX() + enemies[i].getEnemyWidth() &&
                            getY() >= enemies[i].getY() && getY() <= enemies[i].getY() + enemies[i].getEnemyHeight()) {
                        enemies[i].isDead();
                        reset();
                    }
                }
            }
        }

        public void setTarget(float targetX, float targetY) {
            velocity.set(targetX - position.x, (World.HEIGHT - targetY) - position.y);
        }

        public boolean isVisible() {
            if(visible) return true;
            return false;
        }

        public void reset() { setVisible(false); }


        public void setVisible(boolean visible) { this.visible = visible; }

        public Vector2 getPosition() { return position; }

        public Vector2 getVelocity() { return velocity; }
    }
}
