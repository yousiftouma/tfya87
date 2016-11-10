package com.mygdx.game.entity.factories;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entity.Missile;

/**
 * Created by geoyi478 on 10/11/16.
 */
public interface IMissileFactory {
    Missile createMissile(Vector2 position, float direction);
}
