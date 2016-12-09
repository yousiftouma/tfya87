package com.mygdx.game.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.AsteroidsGame;
import com.mygdx.game.Side;

/**
 * Created by youto814 on 03/11/16.
 */
public class Asteroid extends AbstractEntity {

    private final static float ACCEPTABLE_ERROR = 0.001f;

    public Asteroid(Sprite sprite, Vector2 position, Vector2 size, Vector2 velocity, Vector2 acceleration,
                    double mass) {
        super(sprite, position, size, velocity, acceleration, mass);
    }

    @Override
    public EntityType getEntityType() {
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

    public float getRadius() {
        return this.getSize().x/2;
    }

    public double distanceTo(Asteroid a2){
        float radiusA1 = this.getRadius();
        float radiusA2 = a2.getRadius();
        float centerOfA1X = this.getPosition().x + radiusA1;
        float centerOfA1Y = this.getPosition().y + radiusA1;
        float centerOfA2X = a2.getPosition().x + radiusA2;
        float centerOfA2Y = a2.getPosition().y + radiusA2;

        float a = centerOfA1X - centerOfA2X;
        float b = centerOfA1Y - centerOfA2Y;

        return Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
    }

    /**
         * Seperates our Asteroid from the Asteroid we collided with, depending on
         * what side we collided with
         * @param object object we collided with
         */
        public void separateSide(Asteroid object) {
	    Side side = getCollisionSide(object);
            if (side == Side.TOP) {
                //to avoid setting fallspeed to 0 if not actually on top
//                if (velocity.y < 0) {
//                    velocity.y = 0;
//                }
                setPosition(new Vector2(getPosition().x, object.getPosition().y + object.getSize().y));
            } else if (side == Side.BOTTOM) {
                //to start falling upon collision with bottom
//                if (velocity.y > 0) {
//                    velocity.y = 0;
//                }
                setPosition(new Vector2(getPosition().x, object.getPosition().y - getSize().y));
            } else if (side == Side.LEFT) {
                setPosition(new Vector2(object.getPosition().x - getSize().x, getPosition().y));
            } else if (side == Side.RIGHT) {
                setPosition(new Vector2(object.getPosition().x + object.getSize().x, getPosition().y));
            }
        }

    /**
         * Checks where the overlap is smallest, thus determining which side we have collided with
         * comparisons are done by equalitychecks that are acceptable for the double datatype with a
         * reasonable acceptable error which we have defined
         * @param object object that we check collision with
         * @return returns which side we've collided with
         */
        private Side getCollisionSide(Asteroid object){
            double topDif = Math.abs(getPosition().y - (object.getPosition().y + object.getSize().y));

            double bottomDif = Math.abs((getPosition().y + getSize().y) - object.getPosition().y);

            double leftDif = Math.abs((getPosition().x + getSize().x) - object.getPosition().x);

            double rightDif = Math.abs(getPosition().x - (object.getPosition().x + object.getSize().x));

            double minDif = Math.min(Math.min(topDif, bottomDif), Math.min(leftDif, rightDif));

    	if (Math.abs(minDif - topDif) < ACCEPTABLE_ERROR){
    	    return Side.TOP;
    	}
            else if (Math.abs(minDif - bottomDif) < ACCEPTABLE_ERROR){
                return Side.BOTTOM;
            }
            else if (Math.abs(minDif - leftDif) < ACCEPTABLE_ERROR){
                return Side.LEFT;
            }
            else {
                return Side.RIGHT;
            }
        }
}


