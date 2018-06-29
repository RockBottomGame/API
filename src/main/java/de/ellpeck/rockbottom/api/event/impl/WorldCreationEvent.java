/*
 * This file ("WorldCreationEvent.java") is part of the RockBottomAPI by Ellpeck.
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

import de.ellpeck.rockbottom.api.event.Event;
import de.ellpeck.rockbottom.api.world.DynamicRegistryInfo;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.WorldInfo;

import java.io.File;

/**
 * This event is fired when an {@link IWorld} is created for the first time.
 * Note that determining this is a vague process as the event is called whenever
 * the {@link WorldInfo} does not exist. This event cannot be cancelled.
 */
public final class WorldCreationEvent extends Event {

    public final File worldFile;
    public final IWorld world;
    public final WorldInfo info;
    public final DynamicRegistryInfo regInfo;

    public WorldCreationEvent(File worldFile, IWorld world, WorldInfo info, DynamicRegistryInfo regInfo) {
        this.worldFile = worldFile;
        this.world = world;
        this.info = info;
        this.regInfo = regInfo;
    }
}
