package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entity.AbstractEntity;
import com.mygdx.game.entity.Player;
import com.mygdx.game.entity.factories.AsteroidsFactory;
import com.mygdx.game.entity.factories.IAsteroidsFactory;
import com.mygdx.game.entity.factories.IMissileFactory;
import com.mygdx.game.entity.factories.MissileFactory;
import com.mygdx.game.physics.CollisionDetector;
import com.mygdx.game.physics.CollisionPair;

import java.util.ArrayList;

public class AsteroidsGame {
    public static final int GAME_WIDTH = 1280;
    public static final int GAME_HEIGHT = 720;
    private static final float LEVEL_TIME = 30.0f;
    private static final float MAX_DELTA = 0.1f;
    public final static Vector2 ASTEROIDS_SIZE = new Vector2(64,64);
    private float timeBetweenAsteroids = 5.0f;
    private float timeToNewLevel = LEVEL_TIME;
    private float timeToNewAsteroids = 5.0f;
    private float timeToNewMissile = 1.0f;
    private float timeBetweenMissiles = 1.0f;
    private Player player;
    private ArrayList<AbstractEntity> entities;
    private IAsteroidsFactory asteroidsFactory;
    private IMissileFactory missileFactory;
    private boolean gameOver;

    public AsteroidsGame() {
        this.entities = new ArrayList<AbstractEntity>();
        this.asteroidsFactory = new AsteroidsFactory();
        this.missileFactory = new MissileFactory();
        this.player = getPlayer();
        this.gameOver = false;
        entities.add(player);
        entities.add(asteroidsFactory.createAsteroid(ASTEROIDS_SIZE));
        entities.add(asteroidsFactory.createAsteroid(ASTEROIDS_SIZE));
    }

    public void updateGame(float delta) {
        if (delta >= MAX_DELTA) delta = MAX_DELTA;
        
        //spawnAsteroids(delta);
        changeLevel(delta);
        updatePositions(delta);
        handleMovement(delta);
        updateGravity();
        ArrayList<CollisionPair> collisionPairs = CollisionDetector.getCollisionPairs(entities);

    }

    private void updateGravity() {
        for (int i = 0; i < entities.size(); i++) {
            AbstractEntity e1 = entities.get(i);
            double massE1 = e1.getMass();
            Vector2 posE1 = e1.getPosition();
            for (int j = i + 1; j < entities.size(); j++) {
                AbstractEntity e2 = entities.get(j);
                double massE2 = entities.get(j).getMass();
                Vector2 posE2 = entities.get(j).getPosition();

                double distance = Math.sqrt((Math.pow((posE1.x - posE2.x), 2)) + (Math.pow((posE1.y - posE2.y), 2)));
                float gravity = (float) ((massE1 * massE2) / (distance * 50000));
/*
                Vector2 newVelocityE1;
                Vector2 newVelocityE2;
                if (posE1.x - posE2.x < 0 && e1.getVelocity().x > 0) {
                    if (posE1.y - posE2.y < 0 && e1.getVelocity().y > 0) {
                        newVelocityE1 = new Vector2(e1.getVelocity().x + gravity, e1.getVelocity().y - gravity);
                        newVelocityE2 = new Vector2(e2.getVelocity().x - gravity, e2.getVelocity().y - gravity);
                        e1.setVelocity(newVelocityE1);
                        e2.setVelocity(newVelocityE2);
                    }
                    System.out.println(gravity);
                }
                */
            }
        }
    }

    private void updatePositions(final float delta){
        for (AbstractEntity entity : entities){
            entity.updateVelocity(delta);
            entity.updatePosition(delta);
        }
    }

    private void changeLevel (final float delta){
        if (timeBetweenAsteroids <= 1) return;

        if(timeToNewLevel <= 0){
            timeToNewLevel = LEVEL_TIME;
            timeBetweenAsteroids -= 0.5;
        }
        else timeToNewLevel -= delta;
    }

    private void spawnAsteroids (final float delta){
        if (timeToNewAsteroids <= 0){
            entities.add(asteroidsFactory.createAsteroid(ASTEROIDS_SIZE));
          //  entities.add(0,missileFactory.createMissile(player.getPosition(), player.getDirection()));
            timeToNewAsteroids = timeBetweenAsteroids;
        }else timeToNewAsteroids -= delta;
    }

    private Player getPlayer() {
        Texture playerTexture = new Texture(Gdx.files.internal("spaceship_green.png"));
        Sprite playerSprite = new Sprite(playerTexture);
        return new Player(playerSprite, new Vector2(GAME_WIDTH/2, GAME_HEIGHT/2), new Vector2(40, 40),
                new Vector2(0,0), new Vector2(0,0), 0 );
    }

    public ArrayList<AbstractEntity> getEntities(){
        return entities;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    private void handleMovement(float delta){

        if (Gdx.input.isKeyPressed(Keys.LEFT)){
            player.rotateLeft(delta);
        }
        if (Gdx.input.isKeyPressed(Keys.RIGHT)){
            player.rotateRight(delta);
        }
        timeToNewMissile -= delta;
        if(Gdx.input.isKeyJustPressed(Keys.SPACE) && timeToNewMissile <= 0){
            entities.add(0, missileFactory.createMissile(player.getPosition(), player.getDirection()));
            timeToNewMissile = timeBetweenMissiles;
        }


    }

}
