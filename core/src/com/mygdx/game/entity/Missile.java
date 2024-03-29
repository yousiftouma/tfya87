package com.mygdx.game.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by youto814 on 03/11/16.
 */
public class Missile extends AbstractEntity {

    public Missile(Sprite sprite, Vector2 position, Vector2 size, Vector2 velocity, Vector2 acceleration,
                   double mass) {
        super(sprite, position, size, velocity, acceleration, mass);
        this.setOriginCenter();
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.MISSILE;
    }
}
