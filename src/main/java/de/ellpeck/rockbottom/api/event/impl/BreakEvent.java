/*
 * This file ("BreakEvent.java") is part of the RockBottomAPI by Ellpeck.
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

import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.event.Event;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

/**
 * This event is fired when a {@link Tile} is broken by an {@link
 * AbstractEntityPlayer}. The effective variable determines if the tool being
 * held by the player is effective in making the tile drop its result.
 * Cancelling the event will result in the tile not being broken.
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
