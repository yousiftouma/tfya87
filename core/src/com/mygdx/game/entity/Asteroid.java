package com.mygdx.game.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.AsteroidsGame;

/**
 * Created by youto814 on 03/11/16.
 */
public class Asteroid extends AbstractEntity {

    public Asteroid(Sprite sprite, Vector2 position, Vector2 size, Vector2 velocity, Vector2 acceleration,
                    double mass) {
        super(sprite, position, size, velocity, acceleration, mass);
    }

    @Override
    public EntityType GetEntityType() {
        return EntityType.ASTEROID;
    }

    @Override
    public void updatePosition(final float delta){
        super.updatePosition(delta);

        // outside left
        if(this.getPosition().x < 0 - this.getSize().x){
            Vector2 position = new Vector2(AsteroidsGame.GAME_WIDTH, this.getPosition().y);
            this.setPosition(position);
        }
        // outside right
        else if (this.getPosition().x > AsteroidsGame.GAME_WIDTH){
            Vector2 position = new Vector2(0 - this.getSize().x , this.getPosition().y);
            this.setPosition(position);
        }
        // outside top
        else if(this.getPosition().y > AsteroidsGame.GAME_HEIGHT){
            Vector2 position = new Vector2(this.getPosition().x, 0 - this.getSize().y);
            this.setPosition(position);
        }
        // outside bottom
        else if(this.getPosition().y < 0 - this.getSize().y){
            Vector2 position = new Vector2(this.getPosition().x, AsteroidsGame.GAME_HEIGHT);
            this.setPosition(position);
        }

    }
}


