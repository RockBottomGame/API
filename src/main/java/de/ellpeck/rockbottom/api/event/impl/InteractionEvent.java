package de.ellpeck.rockbottom.api.event.impl;

import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.event.Event;
import de.ellpeck.rockbottom.api.world.TileLayer;

/**
 * This event is fired when an {@link AbstractEntityPlayer} interacts with a tile in the world
 * <br> Cancelling it will cancel the interaction
 */
public class InteractionEvent extends Event{

    public final AbstractEntityPlayer player;
    public TileLayer layer;
    public int x;
    public int y;
    public boolean simulate;

    public InteractionEvent(AbstractEntityPlayer player, TileLayer layer, int x, int y, boolean simulate){
        this.player = player;
        this.layer = layer;
        this.x = x;
        this.y = y;
        this.simulate = simulate;
    }
}
