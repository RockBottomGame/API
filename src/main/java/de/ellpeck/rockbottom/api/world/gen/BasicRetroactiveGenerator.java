/*
 * This file ("BasicRetroactiveGenerator.java") is part of the RockBottomAPI by Ellpeck.
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

import de.ellpeck.rockbottom.api.Registries;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IChunk;
import de.ellpeck.rockbottom.api.world.IWorld;

public abstract class BasicRetroactiveGenerator implements IWorldGenerator {

    protected final ResourceName name;

    public BasicRetroactiveGenerator(ResourceName name) {
        this.name = name;
    }

    @Override
    public boolean shouldGenerate(IWorld world, IChunk chunk) {
        return (!chunk.hasAdditionalData() || !chunk.getAdditionalData().getBoolean(name)) && this.shouldGenerateRetroactively(world, chunk);
    }

    @Override
    public void generate(IWorld world, IChunk chunk) {
        chunk.getOrCreateAdditionalData().addBoolean(name, true);
        this.generateRetroactively(world, chunk);
    }

    public abstract boolean shouldGenerateRetroactively(IWorld world, IChunk chunk);

    public abstract void generateRetroactively(IWorld world, IChunk chunk);
}
