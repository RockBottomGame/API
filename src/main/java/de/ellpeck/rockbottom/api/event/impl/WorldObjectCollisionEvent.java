package de.ellpeck.rockbottom.api.event.impl;

import de.ellpeck.rockbottom.api.entity.MovableWorldObject;
import de.ellpeck.rockbottom.api.event.Event;
import de.ellpeck.rockbottom.api.util.BoundBox;

import java.util.List;

/**
 * This event is fired when a {@link MovableWorldObject} collides with a multitude of {@link BoundBox}es
 * <br> It is not cancellable
 */
public class WorldObjectCollisionEvent extends Event{

    public final MovableWorldObject object;
    public final BoundBox entityBoundBox;
    public final List<BoundBox> boundBoxes;

    public WorldObjectCollisionEvent(MovableWorldObject object, BoundBox entityBoundBox, List<BoundBox> boundBoxes){
        this.object = object;
        this.entityBoundBox = entityBoundBox;
        this.boundBoxes = boundBoxes;
    }
}
