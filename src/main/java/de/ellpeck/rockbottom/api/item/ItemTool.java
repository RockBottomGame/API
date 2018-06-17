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
 * © 2017 Ellpeck
 */

package de.ellpeck.rockbottom.api.item;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.inventory.IInventory;
import de.ellpeck.rockbottom.api.render.item.IItemRenderer;
import de.ellpeck.rockbottom.api.render.item.ItemToolRenderer;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemTool extends ItemBasic{

    private final int durability;
    private final float miningSpeed;
    private final Map<ToolType, Integer> toolTypes = new HashMap<>();

    public ItemTool(ResourceName name, float miningSpeed, int durability, ToolType type, int level){
        super(name);
        this.miningSpeed = miningSpeed;
        this.durability = durability;

        this.addToolType(type, level);
        this.maxAmount = 1;
    }

    @Override
    protected IItemRenderer createRenderer(ResourceName name){
        return new ItemToolRenderer(name);
    }

    public ItemTool addToolType(ToolType type, int level){
        this.toolTypes.put(type, level);
        return this;
    }

    @Override
    public Map<ToolType, Integer> getToolTypes(ItemInstance instance){
        return this.toolTypes;
    }

    @Override
    public float getMiningSpeed(IWorld world, int x, int y, TileLayer layer, Tile tile, boolean isRightTool){
        return isRightTool ? this.miningSpeed : super.getMiningSpeed(world, x, y, layer, tile, isRightTool);
    }

    @Override
    public void onTileBroken(IWorld world, int x, int y, TileLayer layer, AbstractEntityPlayer player, Tile tile, ItemInstance instance){
        if(!world.isClient()){
            IInventory inv = player.getInv();
            int selected = player.getSelectedSlot();

            int meta = instance.getMeta();
            if(meta < this.getHighestPossibleMeta()){
                instance.setMeta(meta+1);
                inv.notifyChange(selected);
            }
            else{
                inv.set(selected, null);
                RockBottomAPI.getInternalHooks().onToolBroken(world, player, instance);
            }
        }
    }

    @Override
    public void describeItem(IAssetManager manager, ItemInstance instance, List<String> desc, boolean isAdvanced){
        super.describeItem(manager, instance, desc, isAdvanced);

        int highest = this.getHighestPossibleMeta()+1;
        desc.add(manager.localize(ResourceName.intern("info.durability"), highest-instance.getMeta(), highest));
    }

    @Override
    public boolean useMetaAsDurability(){
        return true;
    }

    @Override
    public int getHighestPossibleMeta(){
        return this.durability-1;
    }


}
