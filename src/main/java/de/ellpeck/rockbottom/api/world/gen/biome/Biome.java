/*
 * This file ("Biome.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.world.gen.biome;

import de.ellpeck.rockbottom.api.Constants;
import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IChunk;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.gen.INoiseGen;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public abstract class Biome{

    protected final IResourceName name;

    public Biome(IResourceName name){
        this.name = name;
    }

    public abstract int getWeight();

    public abstract int getHighestY();

    public abstract int getLowestY();

    public abstract TileState getState(IWorld world, IChunk chunk, int x, int y, TileLayer layer, INoiseGen noise);

    public int getExpectedSurfaceHeight(IWorld world, int x, TileLayer layer, INoiseGen noise){
        return Constants.CHUNK_SIZE;
    }

    public long getBiomeSeed(IWorld world){
        return world.getSeed();
    }

    public IResourceName getName(){
        return this.name;
    }

    public Biome register(){
        RockBottomAPI.BIOME_REGISTRY.register(this.getName(), this);
        return this;
    }

    public boolean hasGrasslandDecoration(){
        return false;
    }

    public float getFlowerChance(){
        return 0F;
    }

    public float getPebbleChance(){
        return 0F;
    }

    public boolean canTreeGrow(IWorld world, IChunk chunk, int x, int y){
        return false;
    }

    public TileState getFillerTile(IWorld world, IChunk chunk, int x, int y){
        return GameContent.TILE_SOIL.getDefState();
    }
}
