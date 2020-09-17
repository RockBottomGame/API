/*
 * This file ("IWorldGenerator.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.world.gen;

import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IChunk;
import de.ellpeck.rockbottom.api.world.IWorld;

public interface IWorldGenerator {

    default void initWorld(IWorld world) {

    }

    boolean shouldGenerate(IWorld world, IChunk chunk);

    void generate(IWorld world, IChunk chunk);

    int getPriority();

    default boolean needsPlayerToAllowGeneration(IWorld world, IChunk chunk) {
        return false;
    }

    default boolean doesPlayerAllowGeneration(IWorld world, IChunk chunk, AbstractEntityPlayer player) {
        return true;
    }

    default boolean generatesRetroactively() {
        return false;
    }

    default boolean generatesPerChunk() {
        return true;
    }

    default boolean shouldExistInWorld(IWorld world) {
        return true;
    }

    interface IFactory {

        IWorldGenerator create();

    }
}
