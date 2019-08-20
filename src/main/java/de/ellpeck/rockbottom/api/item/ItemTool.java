/*
 * This file ("ItemTool.java") is part of the RockBottomAPI by Ellpeck.
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

package de.ellpeck.rockbottom.api.item;

import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.render.item.IItemRenderer;
import de.ellpeck.rockbottom.api.render.item.ItemToolRenderer;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.HashMap;
import java.util.Map;

public class ItemTool extends ItemBasicTool {
    private final float miningSpeed;
    private final Map<ToolProperty, Integer> toolProperties = new HashMap<>();

    public ItemTool(ResourceName name, float miningSpeed, int durability, ToolProperty property, int level) {
        super(name, durability);
        this.miningSpeed = miningSpeed;

        this.addToolProperty(property, level);
    }

    @Override
    protected IItemRenderer createRenderer(ResourceName name) {
        return new ItemToolRenderer(name);
    }

    public ItemTool addToolProperty(ToolProperty property, int level) {
        this.toolProperties.put(property, level);
        return this;
    }

    @Override
    public Map<ToolProperty, Integer> getToolProperties(ItemInstance instance) {
        return this.toolProperties;
    }

    @Override
    public float getMiningSpeed(IWorld world, int x, int y, TileLayer layer, Tile tile, boolean isRightTool, ItemInstance instance) {
        return isRightTool ? this.miningSpeed : super.getMiningSpeed(world, x, y, layer, tile, isRightTool, instance);
    }

    @Override
    public void onTileBroken(IWorld world, int x, int y, TileLayer layer, AbstractEntityPlayer player, Tile tile, ItemInstance instance) {
        if (!world.isClient()) {
            this.takeDamage(instance, player, 1);
        }
    }
}
