/*
 * This file ("WorldGenEvent.java") is part of the RockBottomAPI by Ellpeck.
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
import de.ellpeck.rockbottom.api.event.EventResult;
import de.ellpeck.rockbottom.api.world.IChunk;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.gen.IWorldGenerator;

/**
 * This event is fired when an {@link IWorldGenerator} is generating using
 * {@link IWorldGenerator#generate(IWorld, IChunk)}. Cancelling the event will
 * cause the generator not to generate. Setting this event's result to {@link
 * EventResult#MODIFIED} will make the generator generate even if its own {@link
 * IWorldGenerator#shouldGenerate(IWorld, IChunk)} method returns false.
 */
public class WorldGenEvent extends Event{

    public final IWorld world;
    public final IChunk chunk;
    public final IWorldGenerator generator;

    public WorldGenEvent(IWorld world, IChunk chunk, IWorldGenerator generator){
        this.world = world;
        this.chunk = chunk;
        this.generator = generator;
    }
}
