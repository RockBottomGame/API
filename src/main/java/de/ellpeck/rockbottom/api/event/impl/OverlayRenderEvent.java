/*
 * This file ("OverlayRenderEvent.java") is part of the RockBottomAPI by Ellpeck.
 * View the source code at <https://github.com/RockBottomGame/>.
 * View information on the project at <https://rockbottom.ellpeck.de/>.
 *
 * The RockBottomAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The RockBottomAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with the RockBottomAPI. If not, see <http://www.gnu.org/licenses/>.
 *
 * © 2017 Ellpeck
 */

package de.ellpeck.rockbottom.api.event.impl;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IGraphics;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.event.Event;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.gui.IGuiManager;

/**
 * This event is fired when the overlay is rendered. This takes place after all
 * {@link Gui} elements of the current gui are rendered. Note that, during the
 * firing of this event, the {@link IGraphics#getGuiScale()} is applied to the
 * GL context. This event cannot be cancelled.
 */
public class OverlayRenderEvent extends Event{

    public final IGameInstance game;
    public final IAssetManager assetManager;
    public final IGraphics graphics;
    public final AbstractEntityPlayer player;
    public final IGuiManager guiManager;
    public final Gui gui;

    public OverlayRenderEvent(IGameInstance game, IAssetManager assetManager, IGraphics graphics, AbstractEntityPlayer player, IGuiManager guiManager, Gui gui){
        this.game = game;
        this.assetManager = assetManager;
        this.graphics = graphics;
        this.player = player;
        this.guiManager = guiManager;
        this.gui = gui;
    }
}
