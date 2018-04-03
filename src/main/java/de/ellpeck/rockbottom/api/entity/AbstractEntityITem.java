/*
 * This file ("AbstractEntityITem.java") is part of the RockBottomAPI by Ellpeck.
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

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.world.IWorld;

public abstract class AbstractEntityItem extends Entity{

    public AbstractEntityItem(IWorld world){
        super(world);
    }

    public abstract ItemInstance getItem();

    public abstract void setItem(ItemInstance instance);

    public abstract boolean canPickUp();

    public static AbstractEntityItem spawn(IWorld world, ItemInstance inst, double x, double y, double motionX, double motionY){
        AbstractEntityItem item = RockBottomAPI.getInternalHooks().makeItem(world, inst, x, y, motionX, motionY);
        item.setPos(x, y);
        item.motionX = motionX;
        item.motionY = motionY;
        world.addEntity(item);
        return item;
    }
}
