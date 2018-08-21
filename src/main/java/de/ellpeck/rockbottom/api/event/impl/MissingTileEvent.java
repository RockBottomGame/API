/*
 * This file ("MissingTileEvent.java") is part of the RockBottomAPI by Ellpeck.
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

import de.ellpeck.rockbottom.api.event.Event;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IChunk;
import de.ellpeck.rockbottom.api.world.IWorld;

/**
 * This event gets fired when a {@link TileState} is missing from the tile state
 * registry while a chunk is loaded. The event can be used to replace missing
 * tile states with other ones in case a name changes or a property gets added.
 * Change {@link #newState} to the new state you want to be loaded instead of
 * the old state with the name {@link #name}. This event cannot be cancelled.
 */
public final class MissingTileEvent extends Event {

    public final IWorld world;
    public final IChunk chunk;
    public final int id;
    public final ResourceName name;
    public TileState newState;

    public MissingTileEvent(IWorld world, IChunk chunk, int id, ResourceName name) {
        this.world = world;
        this.chunk = chunk;
        this.id = id;
        this.name = name;
    }
}
