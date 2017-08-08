package de.ellpeck.rockbottom.api.event.impl;

import de.ellpeck.rockbottom.api.event.Event;
import de.ellpeck.rockbottom.api.world.IWorld;

/**
 * This event is fired when an {@link IWorld} is unloaded
 * <br> It is not cancellable
 */
public class WorldUnloadEvent extends Event{
    public final IWorld world;

    public WorldUnloadEvent(IWorld world){
        this.world = world;
    }
}
