/*
 * This file ("SetBiomeEvent.java") is part of the RockBottomAPI by Ellpeck.
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
import de.ellpeck.rockbottom.api.world.IChunk;
import de.ellpeck.rockbottom.api.world.IChunkOrWorld;
import de.ellpeck.rockbottom.api.world.gen.biome.Biome;

/**
 * This event is fired when a {@link Biome} is set into the world using {@link IChunk#setBiomeInner(int, int, Biome)} or any of the helper methods like {@link IChunkOrWorld#setBiome(int, int, Biome)}. Note that the {@link #x} and {@link #y} variables are chunk-inner coordinates. Cancelling the event will cause the biome not to be set.
 */
public final class SetBiomeEvent extends Event {

    public final IChunk chunk;
    public Biome biome;
    public int x;
    public int y;

    public SetBiomeEvent(IChunk chunk, Biome biome, int x, int y) {
        this.chunk = chunk;
        this.biome = biome;
        this.x = x;
        this.y = y;
    }
}
