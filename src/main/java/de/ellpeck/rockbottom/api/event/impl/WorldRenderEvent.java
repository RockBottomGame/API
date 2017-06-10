package de.ellpeck.rockbottom.api.event.impl;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.event.Event;
import de.ellpeck.rockbottom.api.world.IWorld;
import org.newdawn.slick.Graphics;

public class WorldRenderEvent extends Event{

    public final IGameInstance game;
    public final IAssetManager assetManager;
    public final Graphics graphics;
    public final IWorld world;
    public final AbstractEntityPlayer player;

    public WorldRenderEvent(IGameInstance game, IAssetManager assetManager, Graphics graphics, IWorld world, AbstractEntityPlayer player){
        this.game = game;
        this.assetManager = assetManager;
        this.graphics = graphics;
        this.world = world;
        this.player = player;
    }
}
