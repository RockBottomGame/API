/*
 * This file ("BiomeLevel.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.world.gen.biome.level;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;

public abstract class BiomeLevel{

    public abstract int getMinY(IWorld world, int x, int y, int surfaceHeight);

    public abstract int getMaxY(IWorld world, int x, int y, int surfaceHeight);

    public abstract boolean isForcedSideBySide();

    public abstract int getPriority();

    public abstract ResourceName getName();

    public BiomeLevel register(){
        RockBottomAPI.BIOME_LEVEL_REGISTRY.register(this.getName(), this);
        return this;
    }
}
