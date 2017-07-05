package de.ellpeck.rockbottom.api.event.impl;

import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.event.Event;
import de.ellpeck.rockbottom.api.world.TileLayer;

/**
 * This event is fired when an {@link AbstractEntityPlayer} destroys a tile
 * <br> Cancelling it will make the tile stay
 */
public class BreakEvent extends Event{

    public final AbstractEntityPlayer player;
    public TileLayer layer;
    public int x;
    public int y;
    public boolean effective;

    public BreakEvent(AbstractEntityPlayer player, TileLayer layer, int x, int y, boolean effective){
        this.player = player;
        this.layer = layer;
        this.x = x;
        this.y = y;
        this.effective = effective;
    }
}
