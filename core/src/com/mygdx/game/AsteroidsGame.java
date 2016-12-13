package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entity.AbstractEntity;
import com.mygdx.game.entity.Asteroid;
import com.mygdx.game.entity.EntityType;
import com.mygdx.game.entity.Player;
import com.mygdx.game.entity.factories.AsteroidsFactory;
import com.mygdx.game.entity.factories.IAsteroidsFactory;
import com.mygdx.game.entity.factories.IMissileFactory;
import com.mygdx.game.entity.factories.MissileFactory;
import com.mygdx.game.collision.CollisionDetector;
import com.mygdx.game.collision.CollisionPair;

import java.util.ArrayList;
import java.util.Iterator;

public class AsteroidsGame {
    public static final int GAME_WIDTH = 1280;
    public static final int GAME_HEIGHT = 720;
    private static final float LEVEL_TIME = 30.0f;
    private static final float MAX_DELTA = 0.1f;
    public final static Vector2 ASTEROIDS_SIZE = new Vector2(64,64);
    public static final float MISSILE_DELAY = 0.3f;
    private static final double GRAVITY_CONSTANT = Math.pow(6.674*10.0, -11.0);
    private static final double EPS = Math.pow(10, -6);
    private static final int ASTEROID_MIN_SIZE = 16;
    private static final Vector2 PLAYER_SIZE = new Vector2(40, 40);
    private float timeBetweenAsteroids = 5.0f;
    private float timeToNewLevel = LEVEL_TIME;
    private float timeToNewAsteroids = 5.0f;
    private float timeToNewMissile = 0.0f;
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

	//entities.add(asteroidsFactory.createAsteroid(ASTEROIDS_SIZE));
        //entities.add(asteroidsFactory.createAsteroid(ASTEROIDS_SIZE, new Vector2(100,300), new Vector2(80,80)));
        //entities.add(asteroidsFactory.createAsteroid(ASTEROIDS_SIZE, new Vector2(500,300), new Vector2(-80,80)));

        entities.add(asteroidsFactory.createAsteroid(ASTEROIDS_SIZE, new Vector2(100,200)));
        entities.add(asteroidsFactory.createAsteroid(ASTEROIDS_SIZE, new Vector2(400,200)));
    }

    public void updateGame(float delta) {
        if (delta >= MAX_DELTA) delta = MAX_DELTA;
        spawnAsteroids(delta);
        changeLevel(delta);
        removeDisappearedMissiles();
	setGravitationalForces();
	updatePositions(delta);
	handleKeyInput(delta);
        Iterable<CollisionPair> collisionPairs = CollisionDetector.getCollisionPairs(entities);
        handleCollisions(collisionPairs);
    }

    private void removeDisappearedMissiles() {
        Iterator<AbstractEntity> iter = entities.iterator();
        while(iter.hasNext()){
            AbstractEntity entity = iter.next();
            if(entity.getEntityType().equals(EntityType.MISSILE)){
                float posX = entity.getPosition().x;
                float posY = entity.getPosition().y;
                if(posX < 0 || posX > GAME_WIDTH || posY < 0 || posY > GAME_HEIGHT){
                    iter.remove();
                }
            }
        }
    }


    private void handleCollisions(Iterable<CollisionPair> collisionPairs) {
        for (CollisionPair collisionPair : collisionPairs) {
            AbstractEntity e1 = collisionPair.getE1();
            AbstractEntity e2 = collisionPair.getE2();

            if (e1.getEntityType() == EntityType.ASTEROID) {
                switch (e2.getEntityType()) {
                    case ASTEROID:
                        asteroidAsteroidCollision((Asteroid)e1, (Asteroid)e2);
                        break;
                    case MISSILE:
                        missileAsteroidCollision(e2, e1);
                        break;
                    case PLAYER:
                        //gameOver = true;
                        break;
                    default:
                        break;
                }
            } else if (e1.getEntityType() == EntityType.PLAYER) {
                switch (e2.getEntityType()) {
                    case ASTEROID:
                         //gameOver = true;
                        break;
                    default:
                        break;
                }
            } else if (e1.getEntityType() == EntityType.MISSILE) {
                switch (e2.getEntityType()) {
                    case ASTEROID:
                        missileAsteroidCollision(e1, e2);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private void asteroidAsteroidCollision(Asteroid a1, Asteroid a2) {
	// separate asteroid from asteroid
	a1.separateSide(a2);
	a2.separateSide(a1);

	// conserving momentum and kinetic energy (elastic collision)
	// V1x' = (V1x*(m1-m2) + 2*m2*V2x) / (m1+m2)
        float newVelX1 = (float) (a1.getVelocity().x * (a1.getMass() - a2.getMass()) +
                (2 * a2.getMass() * a2.getVelocity().x))
                / (float) (a1.getMass() + a2.getMass());

	// V1y' = (V1y*(m1-m2) + 2*m2*V2y) / (m1+m2)
        float newVelY1 = (float) (a1.getVelocity().y * (a1.getMass() - a2.getMass()) +
                (2 * a2.getMass() * a2.getVelocity().y))
                / (float)(a1.getMass() + a2.getMass());

	// V2x' = (V2x*(m2-m1) + 2*m1*V1x) / (m1+m2)
        float newVelX2 = (float) (a2.getVelocity().x * (a2.getMass() - a1.getMass()) +
                (2 * a1.getMass() * a1.getVelocity().x))
                / (float) (a1.getMass() + a2.getMass());

	// V2y' = (V2y*(m2-m1) + 2*m1*V1y) / (m1+m2)
        float newVelY2 = (float) (a2.getVelocity().y * (a2.getMass() - a1.getMass()) +
                (2 * a1.getMass() * a1.getVelocity().y))
                / (float) (a1.getMass() + a2.getMass());

        a1.setVelocity(new Vector2(newVelX1, newVelY1));
        a2.setVelocity(new Vector2(newVelX2, newVelY2));
    }

    /**
     * When a missile hits an asteroid we split the asteroid in two rocks half the size, down to a certain limit
     * We then set off the two new rocks 90 degrees apart, 45 degrees off from the original rocks direction with
     * conserved speed
     * @param missile that hits the rock
     * @param asteroid that was hit
     */
    private void missileAsteroidCollision(AbstractEntity missile, AbstractEntity asteroid) {
        float posX = asteroid.getPosition().x;
        float posY = asteroid.getPosition().y;
        float sizeX = asteroid.getSize().x;
        float sizeY = asteroid.getSize().y;

        entities.remove(missile);
        entities.remove(asteroid);

        Vector2 a1NewVel = asteroid.getVelocity().cpy();
        Vector2 a2NewVel = asteroid.getVelocity().cpy();

        a1NewVel.setAngle(a1NewVel.angle()+45);
        a2NewVel.setAngle(a2NewVel.angle()-45);

        if(Math.abs(sizeX - ASTEROID_MIN_SIZE) <= EPS){
            entities.add(asteroidsFactory.createAsteroid(new Vector2(sizeX / 2, sizeX / 2),
							 new Vector2(posX - sizeX / 4, posY + sizeY / 4), a1NewVel));
            entities.add(asteroidsFactory.createAsteroid(new Vector2(sizeX / 2, sizeX / 2),
							 new Vector2(posX + sizeX / 4, posY - sizeY / 4), a2NewVel));
        }
    }

    private void setGravitationalForces() {
	// we check all rocks to all other rocks to sum the gravitational forces
        for (int i = 0; i < entities.size(); i++) {
            AbstractEntity e1 = entities.get(i);
            if (e1.getEntityType() != EntityType.ASTEROID) continue;
            double massE1 = e1.getMass();
            Vector2 posE1 = e1.getPosition();
            Vector2 newAcc = new Vector2(0,0);
            for (int j = 0; j < entities.size(); j++) {
                AbstractEntity e2 = entities.get(j);
                if (e2.getEntityType() != EntityType.ASTEROID) continue;
		// don't want to calculate towards ourselves
                if (e2 == e1) continue;
                double massE2 = e2.getMass();
                Vector2 posE2 = e2.getPosition();

		double distance = ((Asteroid)e1).distanceTo((Asteroid)e2);
		double gravitationalForce = ((massE1*massE2* GRAVITY_CONSTANT) / (distance*distance));
		double acc = gravitationalForce/massE1;

		double accX, accY;

                double deltaX = posE2.x - posE1.x;
                double deltaY = posE2.y - posE1.y;

		// avoid the exceptional case when we are exactly above or below each other and handle separately
		// to avoid division by zero
                if (Math.abs(deltaX) >= EPS){
		    double angle = Math.toDegrees(Math.atan(deltaY/deltaX));
                    accX = acc*Math.cos(Math.toRadians(angle));
                    accY = acc*Math.sin(Math.toRadians(angle));

                }
		else {
		    // all of the acceleration will be along y-axis
		    accX = 0;
		    accY = acc;
		}
		// make sure the acceleration points in the correct direction
		if ((deltaX < 0 && deltaY >= 0) || (deltaX < 0 && deltaY < 0)) {
		    accX *= -1;
		    accY *= -1;
		}
		newAcc.add((float) accX, (float) accY);
		e1.setAcceleration(newAcc);
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
	// limit the time between asteroids
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
            timeToNewAsteroids = timeBetweenAsteroids;
        }else timeToNewAsteroids -= delta;
    }

    private Player getPlayer() {
        Texture playerTexture = new Texture(Gdx.files.internal("spaceship_green.png"));
        Sprite playerSprite = new Sprite(playerTexture);
        return new Player(playerSprite, new Vector2(GAME_WIDTH/2, GAME_HEIGHT/2), PLAYER_SIZE,
                new Vector2(0,0), new Vector2(0,0), 0);
    }

    public ArrayList<AbstractEntity> getEntities(){
        return entities;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    private void handleKeyInput(float delta){

        if (Gdx.input.isKeyPressed(Keys.LEFT)){
            player.rotateLeft(delta);
        }
        if (Gdx.input.isKeyPressed(Keys.RIGHT)){
            player.rotateRight(delta);
        }
        timeToNewMissile -= delta;
        if(Gdx.input.isKeyJustPressed(Keys.SPACE) && timeToNewMissile <= 0){
            entities.add(0, missileFactory.createMissile(player.getPosition(), player.getDirection()));
            timeToNewMissile = MISSILE_DELAY;
        }


    }

}
