package de.ellpeck.rockbottom.api.world.gen;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.world.IChunk;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.gen.biome.Biome;
import de.ellpeck.rockbottom.api.world.gen.biome.level.BiomeLevel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public abstract class BiomeGen implements IWorldGenerator {

    protected final Map<Biome, INoiseGen> biomeNoiseGens = new HashMap<>();
    protected final ListMultimap<BiomeLevel, Biome> biomesPerLevel = ArrayListMultimap.create();
    protected final Map<BiomeLevel, Integer> totalWeights = new HashMap<>();
    protected final Random biomeRandom = new Random();
    protected INoiseGen levelHeightNoise;
    protected long[] layerSeeds;

    public abstract int getLevelTransition(IWorld world);

    public abstract int getBiomeTransition(IWorld world);

    public abstract int getBiomeBlobSize(IWorld world);

    public abstract int getLayerSeedScramble(IWorld world);

    public abstract int getNoiseSeedScramble(IWorld world);

    public abstract List<BiomeLevel> getGenerationLevels(Biome biome, IWorld world);

    @Override
    public void initWorld(IWorld world) {
        this.levelHeightNoise = RockBottomAPI.getApiHandler().makeSimplexNoise(Util.scrambleSeed(this.getNoiseSeedScramble(world), world.getSeed()));
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
        return RockBottomAPI.getApiHandler().getBiome(world, x, y, height, this.totalWeights, this.biomesPerLevel, this.biomeRandom, this.getBiomeBlobSize(world), this.layerSeeds, this.levelHeightNoise, this.getLevelTransition(world), this.getBiomeTransition(world));
    }

    public BiomeLevel getBiomeLevel(IWorld world, int x, int y, int height) {
        return RockBottomAPI.getApiHandler().getSmoothedLevelForPos(world, x, y, height, this.getLevelTransition(world), this.biomesPerLevel, this.levelHeightNoise);
    }
}
