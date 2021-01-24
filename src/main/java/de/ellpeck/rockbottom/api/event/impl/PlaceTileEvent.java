/*
 * This file ("PlaceTileEvent.java") is part of the RockBottomAPI by Ellpeck.
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

import de.ellpeck.rockbottom.api.entity.player.AbstractPlayerEntity;
import de.ellpeck.rockbottom.api.event.Event;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

/**
 * This event is fired when a {@link Tile} is placed into the world by an {@link
 * AbstractPlayerEntity}, before {@link Tile#doPlace(IWorld, int, int,
 * TileLayer, ItemInstance, AbstractPlayerEntity)} is called. Note that the
 * state that will be placed might either be received via {@link
 * Tile#getPlacementState(IWorld, int, int, TileLayer, ItemInstance,
 * AbstractPlayerEntity)} or by a custom method if it is a custom
 * implementation. Cancelling the event will cause {@link Tile#doPlace(IWorld,
 * int, int, TileLayer, ItemInstance, AbstractPlayerEntity)} not to be called.
 */
public final class PlaceTileEvent extends Event {

    public final AbstractPlayerEntity player;
    public ItemInstance instance;
    public boolean removeItem;
    public TileLayer layer;
    public int x;
    public int y;

    public PlaceTileEvent(AbstractPlayerEntity player, ItemInstance instance, boolean removeItem, TileLayer layer, int x, int y) {
        this.player = player;
        this.instance = instance;
        this.removeItem = removeItem;
        this.layer = layer;
        this.x = x;
        this.y = y;
    }
}
