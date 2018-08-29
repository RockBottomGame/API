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
