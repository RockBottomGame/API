/*
 * This file ("BiomeBasic.java") is part of the RockBottomAPI by Ellpeck.
 * View the source code at <https://github.com/RockBottomGame>.
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

import de.ellpeck.rockbottom.api.util.reg.IResourceName;

public abstract class BiomeBasic extends Biome{

    private final int highestY;
    private final int lowestY;
    private final int weight;

    public BiomeBasic(IResourceName name, int highestY, int lowestY, int weight){
        super(name);
        this.highestY = highestY;
        this.lowestY = lowestY;
        this.weight = weight;
    }

    @Override
    public int getWeight(){
        return this.weight;
    }

    @Override
    public int getHighestGridPos(){
        return this.highestY;
    }

    @Override
    public int getLowestGridPos(){
        return this.lowestY;
    }
}
