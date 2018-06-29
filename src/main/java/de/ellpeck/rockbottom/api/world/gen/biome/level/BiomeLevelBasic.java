/*
 * This file ("BiomeLevelBasic.java") is part of the RockBottomAPI by Ellpeck.
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

import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;

public class BiomeLevelBasic extends BiomeLevel {

    private final ResourceName name;
    private final int minY;
    private final int maxY;
    private final boolean sideBySide;
    private final int priority;

    public BiomeLevelBasic(ResourceName name, int minY, int maxY, boolean sideBySide, int priority) {
        this.name = name;
        this.minY = minY;
        this.maxY = maxY;
        this.sideBySide = sideBySide;
        this.priority = priority;
    }

    @Override
    public int getMinY(IWorld world, int x, int y, int surfaceHeight) {
        return this.minY == Integer.MIN_VALUE ? this.minY : surfaceHeight + this.minY;
    }

    @Override
    public int getMaxY(IWorld world, int x, int y, int surfaceHeight) {
        return this.maxY == Integer.MAX_VALUE ? this.maxY : surfaceHeight + this.maxY;
    }

    @Override
    public boolean isForcedSideBySide() {
        return this.sideBySide;
    }

    @Override
    public int getPriority() {
        return this.priority;
    }

    @Override
    public ResourceName getName() {
        return this.name;
    }
}
