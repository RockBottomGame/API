package de.ellpeck.rockbottom.api.event.impl;

import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.event.Event;
import de.ellpeck.rockbottom.api.world.TileLayer;

import java.util.List;

/**
 * This event is fired when an {@link AbstractEntityPlayer} interacts with the world
 * <br> Cancelling it will cancel the interaction
 */
public class InteractionEvent extends Event{

    public final AbstractEntityPlayer player;
    public List<Entity> entities;
    public TileLayer layer;
    public int x;
    public int y;
    public double mouseX;
    public double mouseY;

    public InteractionEvent(AbstractEntityPlayer player, List<Entity> entities, TileLayer layer, int x, int y, double mouseX, double mouseY){
        this.player = player;
        this.entities = entities;
        this.layer = layer;
        this.x = x;
        this.y = y;
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }
}
