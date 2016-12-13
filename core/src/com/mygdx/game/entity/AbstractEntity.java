package com.mygdx.game.entity;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by youto814 on 03/11/16.
 */
public abstract class AbstractEntity {
    private static final int FULL_CIRCLE = 360;
    private Sprite sprite;
    private Vector2 position;
    private Vector2 size;
    private Rectangle hitBox;
    private Vector2 velocity;
    private Vector2 acceleration;
    private double mass;

    protected AbstractEntity(Sprite sprite, Vector2 position, Vector2 size, Vector2 velocity, Vector2 acceleration, double mass) {
        this.sprite = sprite;
        this.position = position;
        this.size = size;
        this.velocity = velocity;
        this.acceleration = acceleration;
        this.mass = mass;

        sprite.setPosition(position.x, position.y);
        sprite.setSize(size.x, size.y);
        this.hitBox = new Rectangle(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
    }

    public abstract EntityType getEntityType();

    public void draw (Batch batch) {
        sprite.draw(batch);
    }

    public void updatePosition(final float delta){
        float x = position.x + delta*velocity.x;
        float y = position.y + delta*velocity.y;
        setPosition(new Vector2(x,y));
    }

    public void updateVelocity(final float delta){
        velocity.x += delta*acceleration.x;
        velocity.y += delta*acceleration.y;
    }

    public double getMass() {
        return mass;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public Vector2 getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(Vector2 acceleration) {
        this.acceleration = acceleration;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
        sprite.setPosition(position.x, position.y);
        hitBox.setPosition(sprite.getX(), sprite.getY());
    }

    public float getDirection(){
        while (sprite.getRotation() < 0){
            sprite.setRotation(FULL_CIRCLE - sprite.getRotation());
        }
        return sprite.getRotation() % FULL_CIRCLE;
    }

    public void setDirection(float angle){
        while (angle < 0 ) angle = FULL_CIRCLE-Math.abs(angle);
        sprite.setRotation(angle % FULL_CIRCLE);
    }

    public void setOriginCenter() {
        sprite.setOrigin(size.x/2, size.y/2);
    }

    public Vector2 getSize() {
        return size;
    }
}
