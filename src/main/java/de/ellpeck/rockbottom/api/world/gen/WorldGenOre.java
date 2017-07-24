/*
 * This file ("WorldGenOre.java") is part of the RockBottomAPI by Ellpeck.
 * View the source code at <https://github.com/Ellpeck/RockBottomAPI>.
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
 */

package de.ellpeck.rockbottom.api.world.gen;

import de.ellpeck.rockbottom.api.Constants;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.world.IChunk;
import de.ellpeck.rockbottom.api.world.IWorld;

import java.util.Random;

public abstract class WorldGenOre implements IWorldGenerator{

    @Override
    public boolean shouldGenerate(IWorld world, IChunk chunk, Random rand){
        return chunk.getGridY() <= this.getHighestGridPos() && chunk.getGridY() >= this.getLowestGridPos();
    }

    @Override
    public void generate(IWorld world, IChunk chunk, Random rand){
        int amount = rand.nextInt(this.getMaxAmount());
        if(amount > 0){
            int radX = this.getClusterRadiusX();
            int radY = this.getClusterRadiusY();
            int radXHalf = Util.ceil((double)radX/2);
            int radYHalf = Util.ceil((double)radY/2);

            for(int i = 0; i < amount; i++){
                int startX = chunk.getX()+radX+rand.nextInt(Constants.CHUNK_SIZE-radX*2);
                int startY = chunk.getY()+radY+rand.nextInt(Constants.CHUNK_SIZE-radY*2);

                int thisRadX = rand.nextInt(radXHalf)+radXHalf;
                int thisRadY = rand.nextInt(radYHalf)+radYHalf;

                for(int x = -thisRadX; x <= thisRadX; x++){
                    for(int y = -thisRadY; y <= thisRadY; y++){
                        if(rand.nextInt(thisRadX) == x || rand.nextInt(thisRadY) == y){
                            world.setState(startX+x, startY+y, this.getOreState());
                        }
                    }
                }
            }
        }
    }

    public abstract int getHighestGridPos();

    public int getLowestGridPos(){
        return Integer.MIN_VALUE;
    }

    public abstract int getMaxAmount();

    public abstract int getClusterRadiusX();

    public abstract int getClusterRadiusY();

    public abstract TileState getOreState();
}
