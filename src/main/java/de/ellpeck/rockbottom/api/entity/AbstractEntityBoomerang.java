/*
 * This file ("AbstractEntityBoomerang.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.entity;

import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.world.IWorld;

public abstract class AbstractEntityBoomerang extends Entity {

    public AbstractEntityBoomerang(IWorld world) {
        super(world);
    }

    public abstract void setStart(double x, double y);

    public abstract double getStartX();

    public abstract double getStartY();

    public abstract double getMaxDistanceSq();

    public abstract void setMaxDistance(double maxDistance);

    public abstract ItemInstance getItem();

    public abstract void setItem(ItemInstance item);
}
