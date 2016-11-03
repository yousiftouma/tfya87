package com.mygdx.game.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by youto814 on 03/11/16.
 */
public class Player extends AbstractEntity {

    public Player(Sprite sprite, Vector2 position, Vector2 size, Vector2 velocity, Vector2 acceleration,
                  double mass) {
        super(sprite, position, size, velocity, acceleration, mass);
        float newX = position.x - size.x/2;
        float newY = position.y - size.y/2;
        this.setPosition(new Vector2(newX, newY));
    }

    @Override
    public EntityType GetEntityType() {
        return EntityType.PLAYER;
    }
}
