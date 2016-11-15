package com.mygdx.game.entity.factories;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entity.AbstractEntity;
import com.mygdx.game.entity.Missile;

/**
 * Created by geoyi478 on 10/11/16.
 */
public class MissileFactory implements IMissileFactory {

    private Texture missileTexture;

    public MissileFactory(){
        missileTexture = new Texture(Gdx.files.internal("fireball.png"));
    }

    @Override
    public Missile createMissile(Vector2 position, float direction) {
        // This is temporary work, not finished.
        //TODO need to implement logic for shooting the missile
        Sprite sprite = new Sprite(missileTexture);
        sprite.setRotation(direction);
        Vector2 size = new Vector2(20,20);
        Vector2 velocity = new Vector2(50,30);
        Vector2 acc = new Vector2(0,0);
        return new Missile(sprite, position, size, velocity, acc, 0);

    }
}
