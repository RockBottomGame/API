package de.ellpeck.rockbottom.api.event.impl;

import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.event.Event;
import de.ellpeck.rockbottom.api.world.TileLayer;

/**
 * This event is fired when break progress is added to an {@link AbstractEntityPlayer} breaking a tile
 * <br> It is not cancellable
 * <p>
 * <br> Note: It is only fired on the client
 */
public class AddBreakProgressEvent extends Event{

    public final AbstractEntityPlayer player;
    public final TileLayer layer;
    public final int x;
    public final int y;
    public float totalProgress;
    public float progressAdded;

    public AddBreakProgressEvent(AbstractEntityPlayer player, TileLayer layer, int x, int y, float totalProgress, float progressAdded){
        this.player = player;
        this.layer = layer;
        this.x = x;
        this.y = y;
        this.totalProgress = totalProgress;
        this.progressAdded = progressAdded;
    }
}
