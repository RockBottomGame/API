/*
 * This file ("BiomeBasic.java") is part of the RockBottomAPI by Ellpeck.
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

import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.gen.biome.level.BiomeLevel;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class BiomeBasic extends Biome{

    private final int weight;
    private final List<BiomeLevel> levels;

    public BiomeBasic(ResourceName name, int weight, BiomeLevel... levels){
        super(name);
        this.weight = weight;
        this.levels = Collections.unmodifiableList(Arrays.asList(levels));
    }

    @Override
    public List<BiomeLevel> getGenerationLevels(IWorld world){
        return this.levels;
    }

    @Override
    public int getWeight(IWorld world){
        return this.weight;
    }
}
