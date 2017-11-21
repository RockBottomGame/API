/*
 * This file ("WorldGenOre.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.world.gen;

import de.ellpeck.rockbottom.api.Constants;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.world.IChunk;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.gen.biome.Biome;

import java.util.Collection;
import java.util.Random;
import java.util.Set;

public abstract class WorldGenOre implements IWorldGenerator{

    private final Random oreRandom = new Random();

    @Override
    public boolean shouldGenerate(IWorld world, IChunk chunk){
        return chunk.getGridY() <= this.getHighestGridPos() && chunk.getGridY() >= this.getLowestGridPos();
    }

    @Override
    public void generate(IWorld world, IChunk chunk){
        this.oreRandom.setSeed(Util.scrambleSeed(chunk.getX(), chunk.getY(), world.getSeed()));
        Collection<Biome> allowedBiomes = this.getAllowedBiomes();

        int amount = this.oreRandom.nextInt(this.getMaxAmount()+1);
        if(amount > 0){
            int radX = this.getClusterRadiusX();
            int radY = this.getClusterRadiusY();
            int radXHalf = Util.ceil((double)radX/2);
            int radYHalf = Util.ceil((double)radY/2);

            for(int i = 0; i < amount; i++){
                int startX = chunk.getX()+radX+this.oreRandom.nextInt(Constants.CHUNK_SIZE-radX*2);
                int startY = chunk.getY()+radY+this.oreRandom.nextInt(Constants.CHUNK_SIZE-radY*2);

                Biome biome = world.getBiome(startX, startY);
                if(allowedBiomes.contains(biome)){
                    int thisRadX = this.oreRandom.nextInt(radXHalf)+radXHalf;
                    int thisRadY = this.oreRandom.nextInt(radYHalf)+radYHalf;

                    for(int x = -thisRadX; x <= thisRadX; x++){
                        for(int y = -thisRadY; y <= thisRadY; y++){
                            if(this.oreRandom.nextInt(thisRadX) == x || this.oreRandom.nextInt(thisRadY) == y){
                                world.setState(startX+x, startY+y, this.getOreState());
                            }
                        }
                    }
                }
            }
        }
    }

    protected Set<Biome> getAllowedBiomes(){
        return RockBottomAPI.BIOME_REGISTRY.getUnmodifiable().values();
    }

    protected abstract int getMaxAmount();

    protected abstract int getClusterRadiusX();

    protected abstract int getClusterRadiusY();

    protected abstract TileState getOreState();

    protected abstract int getHighestGridPos();

    protected int getLowestGridPos(){
        return Integer.MIN_VALUE;
    }
}
