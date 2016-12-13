package com.mygdx.game.collision;

import com.mygdx.game.entity.AbstractEntity;
import com.mygdx.game.entity.Asteroid;
import com.mygdx.game.entity.EntityType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by frewa814 on 2016-11-03.
 */
public final class CollisionDetector {
    private CollisionDetector() {}

    public static Iterable<CollisionPair> getCollisionPairs(List<AbstractEntity> entities) {
        Collection<CollisionPair> collisionPairs = new ArrayList<CollisionPair>();

	// only check collision from one direction
        for (int i = 0; i < entities.size(); i++) {
            AbstractEntity e1 = entities.get(i);
            for (int j = i+1; j < entities.size(); j++) {
                AbstractEntity e2 = entities.get(j);
                if (e1.getHitBox().overlaps(e2.getHitBox())){

		    CollisionPair pair = new CollisionPair(e1, e2);
		    collisionPairs.add(pair);
		    
		    // uncomment section below and comment out section above to check collisions for asteroids as
		    // if they were circles

		    // be extra precise with asteroids (they are of more circular shape)
//                    if (e1.getEntityType() == EntityType.ASTEROID && e2.getEntityType() == EntityType.ASTEROID){
//                        if (isProperCollision((Asteroid)e1,(Asteroid)e2)){
//                            CollisionPair pair = new CollisionPair(e1, e2);
//                            collisionPairs.add(pair);
//                        }
//                    }
//                    else {
//                        CollisionPair pair = new CollisionPair(e1, e2);
//                        collisionPairs.add(pair);
//                    }
                }
            }
        }
        return collisionPairs;
    }

    // Checks if two circles are overlapping
    private static boolean isProperCollision(Asteroid a1, Asteroid a2) {
        double distance = a1.distanceTo(a2);
        if (distance <= a1.getRadius() + a2.getRadius() - 8) return true;
        return false;
    }


}
