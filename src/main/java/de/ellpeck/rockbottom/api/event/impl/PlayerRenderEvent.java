/*
 * This file ("PlayerRenderEvent.java") is part of the RockBottomAPI by Ellpeck.
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
 * © 2018 Ellpeck
 */

package de.ellpeck.rockbottom.api.event.impl;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.entity.player.AbstractPlayerEntity;
import de.ellpeck.rockbottom.api.event.Event;

/**
 * This event is fired after an {@link AbstractPlayerEntity} is rendered. Note
 * that during the firing of this event, the {@link IRenderer#getWorldScale()}
 * is applied to the GL context. This event cannot be cancelled. Use {@link Pre}
 * if you want to render something before the player is rendered, or if you want
 * to cancel the rendering of the player.
 */
public class PlayerRenderEvent extends Event {

    public final IGameInstance game;
    public final IAssetManager assetManager;
    public final IRenderer graphics;
    public final AbstractPlayerEntity player;
    public final float x;
    public final float y;

    public PlayerRenderEvent(IGameInstance game, IAssetManager assetManager, IRenderer graphics, AbstractPlayerEntity player, float x, float y) {
        this.game = game;
        this.assetManager = assetManager;
        this.graphics = graphics;
        this.player = player;
        this.x = x;
        this.y = y;
    }

    /**
     * This event is fired before na {@link AbstractPlayerEntity} is rendered.
     * Cancelling the event will cause the player not to be rendered.
     */
    public static final class Pre extends PlayerRenderEvent {

        public Pre(IGameInstance game, IAssetManager assetManager, IRenderer graphics, AbstractPlayerEntity player, float x, float y) {
            super(game, assetManager, graphics, player, x, y);
        }
    }
}
