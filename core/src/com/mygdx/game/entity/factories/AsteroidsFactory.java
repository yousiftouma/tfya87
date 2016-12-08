package com.mygdx.game.entity.factories;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.AsteroidsGame;
import com.mygdx.game.entity.Asteroid;

import java.util.Random;

/**
 * Created by youto814 on 03/11/16.
 */
public class AsteroidsFactory implements IAsteroidsFactory {
    private Random random;
    private Texture asteroidTexture;

    public AsteroidsFactory() {
        random = new Random();
        asteroidTexture = new Texture(Gdx.files.internal("asteroid.png"));
    }

    @Override
    public Asteroid createAsteroid(Vector2 size, Vector2 pos) {
        Sprite sprite = new Sprite(asteroidTexture);
        Vector2 velocity = new Vector2(0,0);
        Vector2 acc = new Vector2(0,0);
        double mass = 3*Math.pow(10, 22) * size.x * size.y;
        return new Asteroid(sprite, pos, size, velocity, acc, mass);
    }

    @Override
    public Asteroid createAsteroid(Vector2 size) {
        Sprite sprite = new Sprite(asteroidTexture);
        Vector2 velocity = new Vector2(80,80);
        Vector2 acc = new Vector2(0,0);
        double mass = 3 * size.x * size.y;
        return new Asteroid(sprite, getRandomStartPosition(), size, velocity, acc, mass);
    }

    @Override
    public Asteroid createAsteroidsFromCollision(Vector2 size, Vector2 pos, Vector2 velocity) {
        Sprite sprite = new Sprite(asteroidTexture);

        Vector2 acc = new Vector2(0,0);
        double mass = 3 * size.x * size.y;
        return new Asteroid(sprite, pos, size, velocity, acc, mass);
    }


    private Vector2 getRandomStartPosition(){
        int n = random.nextInt(2);
        int m = random.nextInt(2);
        float x;
        float y;
        if (n == 0) {
            // we will randomize x
            x = random.nextInt(AsteroidsGame.GAME_WIDTH);
            if (m == 0) {
                // we are at bottom of screen
                y = 0;
            }
            else {
                // we are at top of screen
                y = AsteroidsGame.GAME_HEIGHT;
            }
        }
        else {
            // we will randomize y
            y = random.nextInt(AsteroidsGame.GAME_HEIGHT);
            if (m == 0){
                // We are on left side of screen
                x = 0;
            }
            else {
                // We are on right side of screen
                x = AsteroidsGame.GAME_WIDTH;
            }
        }
        return new Vector2(x,y);
    }
}
