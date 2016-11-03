package com.mygdx.game.physics;

import com.mygdx.game.entity.AbstractEntity;

/**
 * Created by frewa814 on 2016-11-03.
 */
public class CollisionPair {
    private AbstractEntity e1;
    private AbstractEntity e2;

    public CollisionPair(AbstractEntity e1, AbstractEntity e2) {
        this.e2 = e2;
        this.e1 = e1;
    }

    public AbstractEntity getE1() {
        return e1;
    }

    public AbstractEntity getE2() {
        return e2;
    }
}
