package de.ellpeck.rockbottom.api.event.impl;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.event.Event;
import org.newdawn.slick.Graphics;

/**
 * This event is fired when an {@link AbstractEntityPlayer} is rendered
 * <br> It is not cancellable
 */
public class PlayerRenderEvent extends Event{

    public final IGameInstance game;
    public final IAssetManager assetManager;
    public final Graphics graphics;
    public final AbstractEntityPlayer player;
    public final float x;
    public final float y;

    public PlayerRenderEvent(IGameInstance game, IAssetManager assetManager, Graphics graphics, AbstractEntityPlayer player, float x, float y){
        this.game = game;
        this.assetManager = assetManager;
        this.graphics = graphics;
        this.player = player;
        this.x = x;
        this.y = y;
    }
}
