/*
 * This file ("TileInteractEvent.java") is part of the RockBottomAPI by Ellpeck.
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

import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.event.Event;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

/**
 * This event is fired when an {@link Tile} is being interacted with, right
 * before its {@link Tile#onInteractWith(IWorld, int, int, TileLayer, double,
 * double, AbstractEntityPlayer)} method is called. Cancelling the event will
 * make the interaction not take place.
 */
public class TileInteractEvent extends Event{

    public final AbstractEntityPlayer player;
    public final TileState state;
    public final TileLayer layer;
    public final int x;
    public final int y;
    public final double mouseX;
    public final double mouseY;

    public TileInteractEvent(AbstractEntityPlayer player, TileState state, TileLayer layer, int x, int y, double mouseX, double mouseY){
        this.player = player;
        this.state = state;
        this.layer = layer;
        this.x = x;
        this.y = y;
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }
}
