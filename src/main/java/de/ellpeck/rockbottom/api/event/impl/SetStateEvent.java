/*
 * This file ("SetStateEvent.java") is part of the RockBottomAPI by Ellpeck.
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
import de.ellpeck.rockbottom.api.world.IChunk;
import de.ellpeck.rockbottom.api.world.IChunkOrWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

/**
 * This event is fired when an {@link TileState} is set into the world using
 * {@link IChunk#setStateInner(TileLayer, int, int, TileState)}, meaning it will
 * also be called on any of the other helper methods like {@link
 * IChunkOrWorld#setState(TileLayer, int, int, TileState)}. Note that the {@link
 * #x} and {@link #y} values are chunk-inner coordinates. Cancelling the event
 * will cause the tile not to be set into the world.
 */
public class SetStateEvent extends Event{

    public final IChunk chunk;
    public TileState state;
    public TileLayer layer;
    public int x;
    public int y;

    public SetStateEvent(IChunk chunk, TileState state, TileLayer layer, int x, int y){
        this.chunk = chunk;
        this.state = state;
        this.layer = layer;
        this.x = x;
        this.y = y;
    }
}
