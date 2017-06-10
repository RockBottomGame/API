package de.ellpeck.rockbottom.api.event.impl;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.event.Event;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.gui.IGuiManager;
import org.newdawn.slick.Graphics;

public class OverlayRenderEvent extends Event{

    public final IGameInstance game;
    public final IAssetManager assetManager;
    public final Graphics graphics;
    public final AbstractEntityPlayer player;
    public final IGuiManager guiManager;
    public final Gui gui;

    public OverlayRenderEvent(IGameInstance game, IAssetManager assetManager, Graphics graphics, AbstractEntityPlayer player, IGuiManager guiManager, Gui gui){
        this.game = game;
        this.assetManager = assetManager;
        this.graphics = graphics;
        this.player = player;
        this.guiManager = guiManager;
        this.gui = gui;
    }
}
