package com.mygdx.game.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by youto814 on 03/11/16.
 */
public class Player extends AbstractEntity {

    private Sprite sprite;
    private int angularSpeed;

    public Player(Sprite sprite, Vector2 position, Vector2 size, Vector2 velocity, Vector2 acceleration,
                  double mass) {
        super(sprite, position, size, velocity, acceleration, mass);
        // compensate for origin lower left corner
        float newX = position.x - size.x/2;
        float newY = position.y - size.y/2;
        this.setPosition(new Vector2(newX, newY));
        this.setOriginCenter();
    }

    public void rotateLeft(float delta){
        angularSpeed = 300;
        float angle = angularSpeed * delta;
        sprite.setRotation(sprite.getRotation() + angle);
    }

    public void rotateRight(float delta){
        angularSpeed = -300;
        float angle = angularSpeed * delta;
        sprite.setRotation(sprite.getRotation() + angle);
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.PLAYER;
    }
}
