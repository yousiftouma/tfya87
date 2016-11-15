package com.mygdx.game.entity.factories;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entity.AbstractEntity;
import com.mygdx.game.entity.Asteroid;

/**
 * Created by youto814 on 03/11/16.
 */
public interface IAsteroidsFactory {
    Asteroid createAsteroid(Vector2 size);
    Asteroid createAsteroidsFromCollision(Vector2 size, Vector2 pos, Vector2 velocity);
}
