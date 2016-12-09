package com.mygdx.game.physics;

import com.mygdx.game.entity.AbstractEntity;
import com.mygdx.game.entity.Asteroid;
import com.mygdx.game.entity.EntityType;

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
                    if (e1.getEntityType() == EntityType.ASTEROID && e2.getEntityType() == EntityType.ASTEROID){
                        if (isProperCollision((Asteroid)e1,(Asteroid)e2)){
                            CollisionPair pair = new CollisionPair(e1, e2);
                            collisionPairs.add(pair);
                        }
                    }
                    else {
                        CollisionPair pair = new CollisionPair(e1, e2);
                        collisionPairs.add(pair);
                    }
                }
            }
        }
        return collisionPairs;
    }

    private static boolean isProperCollision(Asteroid a1, Asteroid a2) {
        double distance = a1.distanceTo(a2);
        if (distance <= a1.getRadius() + a2.getRadius() - 8) return true;
        return false;
    }


}
