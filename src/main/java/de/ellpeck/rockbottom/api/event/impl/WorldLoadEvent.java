package de.ellpeck.rockbottom.api.event.impl;

import de.ellpeck.rockbottom.api.event.Event;
import de.ellpeck.rockbottom.api.world.DynamicRegistryInfo;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.WorldInfo;

/**
 * This event is fired when an {@link IWorld} is loaded
 * <br> It is not cancellable
 */
public class WorldLoadEvent extends Event{

    public final IWorld world;
    public final WorldInfo worldInfo;
    public final DynamicRegistryInfo regInfo;

    public WorldLoadEvent(IWorld world, WorldInfo worldInfo, DynamicRegistryInfo regInfo){
        this.world = world;
        this.worldInfo = worldInfo;
        this.regInfo = regInfo;
    }
}
