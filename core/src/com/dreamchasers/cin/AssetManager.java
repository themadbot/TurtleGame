package com.dreamchasers.cin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by root on 6/12/16.
 */
public class AssetManager {
    public static Texture cannon_bullet;
    public static Texture cannon_right;
    public static Texture boomerang;

    public static Texture turtle_right;

    public static Texture enemy;

    public static Sound cannon_bullet_sound;

    public static boolean loaded = false;

    public static void Load() {
        loaded = true;
        cannon_bullet = new Texture("Weapons/cannon_bullet.png");
        cannon_right = new Texture("Weapons/cannon_right.png");
        boomerang =  new Texture("Weapons/boomerang.png");

        turtle_right = new Texture("turtle/turtle_right.png");

        enemy = new Texture("enemy.png");

        cannon_bullet_sound = Gdx.audio.newSound(Gdx.files.internal("Sounds/cannon_bullet_sound.wav"));
    }

    public static void dispose() {
        if(loaded) {
            loaded = false;
            cannon_bullet.dispose();
            cannon_right.dispose();
            boomerang.dispose();

            turtle_right.dispose();

            enemy.dispose();

            cannon_bullet_sound.dispose();
        }
    }
}
