/*
 * This file ("BiomeGen.java") is part of the RockBottomAPI by Ellpeck.
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

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IChunk;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.gen.biome.Biome;
import de.ellpeck.rockbottom.api.world.gen.biome.level.BiomeLevel;

import java.util.*;

public abstract class BiomeGen implements IWorldGenerator {

    protected ResourceName name;
    protected final Map<Biome, INoiseGen> biomeNoiseGens = new HashMap<>();
    protected final ListMultimap<BiomeLevel, Biome> biomesPerLevel = ArrayListMultimap.create();
    protected final Map<BiomeLevel, Integer> totalWeights = new HashMap<>();
    protected final Random biomeRandom = new Random();
    protected INoiseGen levelHeightNoise;
    protected INoiseGen levelBlobNoise;
    protected long[] layerSeeds;

    public BiomeGen(ResourceName name) {
        this.name = name;
    }

    public abstract int getLevelTransition(IWorld world);

    public abstract int getBiomeTransition(IWorld world);

    public abstract int getBiomeBlobSize(IWorld world);

    public abstract int getLayerSeedScramble(IWorld world);

    public abstract int getNoiseSeedScramble(IWorld world);

    public abstract Set<Biome> getBiomesToGen(IWorld world);

    public abstract Set<BiomeLevel> getLevelsToGen(IWorld world);

    @Override
    public void initWorld(IWorld world) {
        this.levelHeightNoise = RockBottomAPI.getApiHandler().makeSimplexNoise(Util.scrambleSeed(this.getNoiseSeedScramble(world), world.getSeed()));
        this.levelBlobNoise = RockBottomAPI.getApiHandler().makeSimplexNoise(Util.scrambleSeed(this.getLayerSeedScramble(world), world.getSeed()));
        this.layerSeeds = new long[this.getBiomeBlobSize(world)];

        RockBottomAPI.getApiHandler().initBiomeGen(world, this.getLayerSeedScramble(world), this.getBiomeBlobSize(world), this.layerSeeds, this.biomesPerLevel, this.totalWeights, this);
    }

    @Override
    public void generate(IWorld world, IChunk chunk) {
        RockBottomAPI.getApiHandler().generateBiomeGen(world, chunk, this, this.biomeNoiseGens);
    }

    @Override
    public boolean shouldGenerate(IWorld world, IChunk chunk) {
        return true;
    }

    @Override
    public int getPriority() {
        return 10000;
    }

    public Biome getBiome(IWorld world, int x, int y, int height) {
        return RockBottomAPI.getApiHandler().getBiome(world, x, y, height, this.totalWeights, this.biomesPerLevel, this.biomeRandom, this.getBiomeBlobSize(world), this.layerSeeds, this.levelHeightNoise, this.levelBlobNoise, this.getLevelTransition(world), this.getBiomeTransition(world));
        //return RockBottomAPI.getApiHandler().getBiome(world, x, y, height, this.totalWeights, this.biomesPerLevel, this.biomeRandom, this.getBiomeBlobSize(world), this.layerSeeds, this.levelHeightNoise, this.getLevelTransition(world), this.getBiomeTransition(world));
    }

    public BiomeLevel getBiomeLevel(IWorld world, int x, int y, int height) {
        return RockBottomAPI.getApiHandler().getSmoothedLevelForPos(world, x, y, height, this.getLevelTransition(world), this.biomesPerLevel, this.levelHeightNoise);
    }

    public Set<BiomeLevel> getBiomeLevels(IWorld world, int x, int y, int height) {
        return RockBottomAPI.getApiHandler().getSmoothedLevelsForPos(world, x, y, height, this.getLevelTransition(world), this.biomesPerLevel, this.levelHeightNoise);
    }
}
