/*
 * This file ("HeightGen.java") is part of the RockBottomAPI by Ellpeck.
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

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.world.IChunk;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public abstract class HeightGen implements IWorldGenerator {

    protected INoiseGen noiseGen;

    public abstract int getMinHeight(IWorld world);

    public abstract int getMaxHeight(IWorld world);

    public abstract int getMaxMountainHeight(IWorld world);

    public abstract int getNoiseScramble(IWorld world);

    @Override
    public void initWorld(IWorld world) {
        this.noiseGen = RockBottomAPI.getApiHandler().makeSimplexNoise(Util.scrambleSeed(this.getNoiseScramble(world), world.getSeed()));
    }

    public int getHeight(IWorld world, TileLayer layer, int x) {
        return RockBottomAPI.getApiHandler().generateBasicHeight(world, layer, x, this.noiseGen, this.getMinHeight(world), this.getMaxHeight(world), this.getMaxMountainHeight(world));
    }

    @Override
    public boolean shouldGenerate(IWorld world, IChunk chunk) {
        return false;
    }

    @Override
    public void generate(IWorld world, IChunk chunk) {

    }

    @Override
    public boolean generatesPerChunk() {
        return false;
    }
}
