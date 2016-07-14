package com.dreamchasers.cin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by root on 6/9/16.
 */
public class MyInputProcessor implements InputProcessor {
    int ammo;
    int count = 0;
    Camera camera;

    MyInputProcessor(Camera camera) {
        this.camera = camera;;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(pointer < 1) {
            //if (count < ammo - 1) {

                Vector3 coordinates = camera.unproject(new Vector3(screenX, screenY, 0));
                if(GlobalWeapon.globalAmmo != null) {
                    if(GlobalWeapon.globalCount != 0) {
                        GlobalWeapon.globalAmmo[count].setTarget(coordinates.x, coordinates.y);
                        GlobalWeapon.globalCount--;
                        count++;
                    }  else {
                      count = 0;
                        GlobalWeapon.globalCount = GlobalWeapon.globalAmmo.length;

                        GlobalWeapon.globalAmmo[count].setTarget(coordinates.x, coordinates.y);
                        GlobalWeapon.globalCount--;
                        count++;
                    }
                }
            }
        //}
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public int getCount() { return count; }
}
