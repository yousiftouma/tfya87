package com.mygdx.game.physics;

import com.mygdx.game.entity.AbstractEntity;

import java.util.ArrayList;

/**
 * Created by frewa814 on 2016-11-03.
 */
public class CollisionDetector {
    public static ArrayList<CollisionPair> getCollisionPairs (ArrayList<AbstractEntity> entities) {
        ArrayList<CollisionPair> collisionPairs = new ArrayList<CollisionPair>();

        for (int i = 0; i < entities.size(); i++) {
            AbstractEntity e1 = entities.get(i);
            for (int j = i+1; j < entities.size(); j++) {
                AbstractEntity e2 = entities.get(j);
                if (e1.getHitBox().overlaps(e2.getHitBox())){
                    CollisionPair pair = new CollisionPair(e1, e2);
                    collisionPairs.add(pair);
                }
            }
        }
        return collisionPairs;
    }


}
