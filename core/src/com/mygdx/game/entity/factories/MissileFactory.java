package com.mygdx.game.entity.factories;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.AsteroidsGame;
import com.mygdx.game.entity.AbstractEntity;
import com.mygdx.game.entity.Asteroid;
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
        Sprite sprite = new Sprite(missileTexture);
        sprite.setRotation(direction%360);
        Vector2 size = new Vector2(20,20);
        Vector2 velocity = getVelocity(position, direction);
        Vector2 acc = new Vector2(0,0);
        double mass = 10;
        return new Missile(sprite, position, size, velocity, acc, mass);
    }

    private Vector2 getVelocity (Vector2 position, float direction) {
        Vector2 velocity = new Vector2();
        Vector2 target = getTarget(direction);
        velocity.set(target.x - position.x, target.y - position.y).nor().scl(100);
        return velocity;
    }

    private Vector2 getTarget(float direction) {
        Vector2 target = new Vector2();
        System.out.println("Shooting at angle " + direction);
        if (direction <= 45) {
            target.x = AsteroidsGame.GAME_WIDTH;
            double doubleY = target.x/2 * Math.tan(Math.toRadians(direction));
            target.y = (float)doubleY + AsteroidsGame.GAME_HEIGHT / 2;
        }
        else if (direction <= 90) {
            target.y = AsteroidsGame.GAME_HEIGHT;
            double doubleX = target.y/2 * Math.tan(Math.toRadians(90-direction));
            target.x = (float) doubleX + AsteroidsGame.GAME_WIDTH/2;
        }
        else if (direction <= 135){
            target.y = AsteroidsGame.GAME_HEIGHT;
            double doubleX = target.y/2 * Math.tan(Math.toRadians(direction-90));
            target.x = AsteroidsGame.GAME_WIDTH/2 - (float)doubleX;
        }
        else if (direction <= 180) {
            target.x = 0;
            double doubleY = AsteroidsGame.GAME_WIDTH/2 * Math.tan(Math.toRadians(180-direction));
            target.y = (float)doubleY + AsteroidsGame.GAME_HEIGHT/2;
        }
        else if (direction <= 225) {
            target.x = 0;
            double doubleY = AsteroidsGame.GAME_WIDTH/2 * Math.tan(Math.toRadians(direction-180));
            target.y = AsteroidsGame.GAME_HEIGHT/2 - (float)doubleY;
        }
        else if (direction <= 270) {
            target.y = 0;
            double doubleX = AsteroidsGame.GAME_HEIGHT/2 * Math.tan(Math.toRadians(270-direction));
            target.x = AsteroidsGame.GAME_WIDTH/2 - (float) doubleX;
        }
        else if (direction <= 315) {
            target.y = 0;
            double doubleX = AsteroidsGame.GAME_HEIGHT/2 * Math.tan(Math.toRadians(direction-270));
            target.x = AsteroidsGame.GAME_WIDTH/2 + (float) doubleX;
        }
        else { // 315 to 360
            target.x = AsteroidsGame.GAME_WIDTH;
            double doubleY = AsteroidsGame.GAME_WIDTH/2 * Math.tan(Math.toRadians(360-direction));
            target.y = AsteroidsGame.GAME_HEIGHT/2 - (float) doubleY;
        }
        System.out.println(target);
        return target;
    }

}
